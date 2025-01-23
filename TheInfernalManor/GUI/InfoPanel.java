package TheInfernalManor.GUI;

import java.util.*;
import StrictCurses.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Map.*;

public class InfoPanel extends TIMPanel implements GUIConstants
{
   public static final int X_ORIGIN = (MAP_PANEL_SIZE * 2) + 2;
   public static final int Y_ORIGIN = 1;
   
   public InfoPanel(SCTilePalette x1y2TilePalette, TIMFrame pFrame)
   {
      super(x1y2TilePalette, pFrame);
   }
   
   public void set()
   {
      // draw block info
      Actor player = GameState.getPlayerCharacter();
      writeLine(X_ORIGIN, Y_ORIGIN + 1, "Block  [        ]");
      fillTileFG(X_ORIGIN, Y_ORIGIN + 1, 17, 1, WHITE);
      fillTileBG(X_ORIGIN + 8, Y_ORIGIN + 1, 8, 1, RED);
      int[] bar = GUITools.getBar(player.getCurBlock(), player.getMaxBlock(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(X_ORIGIN + 8 + i, Y_ORIGIN + 1, bar[i]);
      
      // draw health info
      writeLine(X_ORIGIN, Y_ORIGIN + 2, "Health [        ]");
      fillTileFG(X_ORIGIN, Y_ORIGIN + 2, 17, 1, YELLOW);
      fillTileBG(X_ORIGIN + 8, Y_ORIGIN + 2, 8, 1, RED);
      bar = GUITools.getBar(player.getCurHealth(), player.getMaxHealth(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(X_ORIGIN + 8 + i, Y_ORIGIN + 2, bar[i]);
      
      // draw energy info
      writeLine(X_ORIGIN, Y_ORIGIN + 3, "Energy [        ]");
      fillTileFG(X_ORIGIN, Y_ORIGIN + 3, 17, 1, BLUE);
      fillTileBG(X_ORIGIN + 8, Y_ORIGIN + 3, 8, 1, RED);
      bar = GUITools.getBar(player.getCurEnergy(), player.getMaxEnergy(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(X_ORIGIN + 8 + i, Y_ORIGIN + 3, bar[i]);
    }
}