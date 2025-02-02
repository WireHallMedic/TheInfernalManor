package TheInfernalManor.GUI;

import java.util.*;
import StrictCurses.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Map.*;

public class InfoPanel extends TIMPanel implements GUIConstants
{
   public static final int CHARACTER_SUMMARY_PANEL_X_ORIGIN = AdventurePanel.CHARACTER_SUMMARY_PANEL_X_ORIGIN;
   public static final int ENVIRONMENT_PANEL_X_ORIGIN = AdventurePanel.ENVIRONMENT_PANEL_X_ORIGIN;
   public static final int SIDE_PANEL_WIDTH = AdventurePanel.ENVIRONMENT_PANEL_WIDTH;
   public static final int SIDE_PANEL_HEIGHT = AdventurePanel.MESSAGE_PANEL_Y_ORIGIN - 2;
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
      
      // draw status effect info
      Vector<StatusEffect> seList = player.getSEList();
      for(int i = 0; i < SIDE_PANEL_WIDTH; i++)
      {
         if(i < seList.size())
         {
            StatusEffect se = seList.elementAt(i);
            setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + i, Y_ORIGIN + 4, se.getIconIndex());
            setTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + i, Y_ORIGIN + 4, se.getColor());
         }
         else
         {
            setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + i, Y_ORIGIN + 4, ' ');
            setTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + i, Y_ORIGIN + 4, WHITE);
         }
      }
      // abilities
      for(int i = 0; i < 10; i++)
      {
         int keyVal = i + 1;
         if(keyVal == 10)
            keyVal = 0;
         String str = "<No Ability>";
         Ability ability = player.getAbility(i);
         if(ability != null)
            str = ability.getName();
         overwriteLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 5 + i, String.format("[%d]: %s", keyVal, str), SIDE_PANEL_WIDTH);
      }
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
      Vector<Actor> aList = GameState.getActorList();
      int writeLine = 2;
      for(Actor a : aList)
      {
         if(a != GameState.getPlayerCharacter())
            writeLine += showActorSummary(writeLine, a);
      }
      // clear remaining rows
      for(int i = writeLine + 1; i < Y_ORIGIN + SIDE_PANEL_HEIGHT; i++)
      {
         fillTile(ENVIRONMENT_PANEL_X_ORIGIN, i, SIDE_PANEL_WIDTH, 1, ' ', WHITE, BLACK);
      }
    }
    
    // returns number of rows taken to write the summary
    private int showActorSummary(int startRow, Actor a)
    {
      // icon and name
      setTileIndex(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + startRow, a.getIconIndex());
      setTileFG(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + startRow, a.getColor());
      overwriteLine(ENVIRONMENT_PANEL_X_ORIGIN + 1, Y_ORIGIN + startRow, " " + a.getName(), SIDE_PANEL_WIDTH - 2);
      
      // draw block info
      int firstWidth = "  [        ]".length();
      writeLine(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + startRow + 1, "  [        ]");
      fillTileFG(ENVIRONMENT_PANEL_X_ORIGIN + 3, Y_ORIGIN + startRow + 1, 8, 1, WHITE);
      fillTileBG(ENVIRONMENT_PANEL_X_ORIGIN + 3, Y_ORIGIN + startRow + 1, 8, 1, RED);
      int[] bar = GUITools.getBar(a.getCurBlock(), a.getMaxBlock(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(ENVIRONMENT_PANEL_X_ORIGIN + 3 + i, Y_ORIGIN + startRow + 1, bar[i]);
      
      // draw health info
      writeLine(ENVIRONMENT_PANEL_X_ORIGIN + firstWidth, Y_ORIGIN + startRow + 1, "[        ]");
      fillTileFG(ENVIRONMENT_PANEL_X_ORIGIN + firstWidth + 1, Y_ORIGIN + startRow + 1, 8, 1, YELLOW);
      fillTileBG(ENVIRONMENT_PANEL_X_ORIGIN + firstWidth + 1, Y_ORIGIN + startRow + 1, 8, 1, RED);
      bar = GUITools.getBar(a.getCurHealth(), a.getMaxHealth(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(ENVIRONMENT_PANEL_X_ORIGIN + firstWidth + 1 + i, Y_ORIGIN + startRow + 1, bar[i]);
      
      return 2;
    }
}