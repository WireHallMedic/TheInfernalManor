package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class AdventurePanel extends TIMPanel implements GUIConstants
{
   private TIMPanel mapPanel;
   
   public AdventurePanel(SCTilePalette x1y2TilePalette, SCTilePalette x1y1TilePalette, TIMFrame pFrame)
   {
      super(x1y2TilePalette, pFrame);
      GUITools.drawSimpleBorder(this);
      setBorder();
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.setVisiblePanel("SplashPanel");
   }
   
   private void setBorder()
   {
      int w = getTilesWide();
      int h = getTilesTall();
      boolean[][] borderArr = new boolean[w][h];
      for(int x = 0; x < w; x++)
      {
         borderArr[x][0] = true;
         borderArr[x][h - 1] = true;
      }
      for(int y = 0; y < h; y++)
      {
         borderArr[0][y] = true;
         borderArr[w - 1][y] = true;
      }
      for(int x = 1; x < (MAP_PANEL_SIZE * 2) + 2; x++)
      {
         borderArr[x][MAP_PANEL_SIZE + 2] = true;
      }
      for(int y = 1; y < h; y++)
      {
         borderArr[(MAP_PANEL_SIZE * 2) + 2][y] = true;
      }
      GUITools.applyBorderTileArray(borderArr, this);
   }
}