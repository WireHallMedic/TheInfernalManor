package TheInfernalManor.GUI;

import java.util.*;
import StrictCurses.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Map.*;

public class InfoPanel extends TIMPanel implements GUIConstants
{
   public static final int CHARACTER_SUMMARY_PANEL_X_ORIGIN = AdventurePanel.CHARACTER_SUMMARY_PANEL_X_ORIGIN;
   public static final int ENVIRONMENT_PANEL_X_ORIGIN = AdventurePanel.ENVIRONMENT_PANEL_X_ORIGIN;
   public static final int SIDE_PANEL_WIDTH = AdventurePanel.ENVIRONMENT_PANEL_WIDTH;
   public static final int Y_ORIGIN = 1;
   
   public InfoPanel(SCTilePalette x1y2TilePalette, TIMFrame pFrame)
   {
      super(x1y2TilePalette, pFrame);
   }
   
   public void set()
   {
      if(GameState.getPlayerCharacter() == null)
         return;
      setCharacterSummaryPanel();
      setEnvironmentPanel();
    }
    
    private void setCharacterSummaryPanel()
    {
      Actor player = GameState.getPlayerCharacter();
      // draw name
      writeLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN, player.getName());
      
      // draw block info
      writeLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 1, "Block  [        ]");
      fillTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 1, 17, 1, WHITE);
      fillTileBG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8, Y_ORIGIN + 1, 8, 1, RED);
      int[] bar = GUITools.getBar(player.getCurBlock(), player.getMaxBlock(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8 + i, Y_ORIGIN + 1, bar[i]);
      
      // draw health info
      writeLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 2, "Health [        ]");
      fillTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 2, 17, 1, YELLOW);
      fillTileBG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8, Y_ORIGIN + 2, 8, 1, RED);
      bar = GUITools.getBar(player.getCurHealth(), player.getMaxHealth(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8 + i, Y_ORIGIN + 2, bar[i]);
      
      // draw energy info
      writeLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 3, "Energy [        ]");
      fillTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 3, 17, 1, BLUE);
      fillTileBG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8, Y_ORIGIN + 3, 8, 1, RED);
      bar = GUITools.getBar(player.getCurEnergy(), player.getMaxEnergy(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8 + i, Y_ORIGIN + 3, bar[i]);
    }
    
    private void setEnvironmentPanel()
    {
      String str = "Unknown mode";
      switch(AdventurePanel.getMode())
      {
         case AdventurePanel.NORMAL_MODE:          str = "Normal Mode"; break;
         case AdventurePanel.ADJACENT_TARGET_MODE: str = "Adj Target Mode"; break;
         case AdventurePanel.RANGED_TARGET_MODE:   str = "Ranged Target Mode"; break;
         case AdventurePanel.LOOK_MODE:            str = "Look Mode"; break;
      }
      overwriteLine(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN, str, SIDE_PANEL_WIDTH);
    }
}