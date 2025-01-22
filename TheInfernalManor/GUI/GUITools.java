package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import java.awt.*;

public class GUITools implements GUIConstants
{
   // selects the tiles to draw a border, then applies them to the panel
   public static void applyBorderTileArray(boolean[][] borderArr, SCPanel panel)
   {
      int width = borderArr.length;
      int height = borderArr[0].length;
      int[][] adjacencyArr = new int[width][height];
      for(int x = 0; x < width - 1; x++)
      for(int y = 0; y < height; y++)
         if(borderArr[x][y])
            adjacencyArr[x+1][y] += SCConstants.ADJACENT_LEFT;
      for(int x = 1; x < width; x++)
      for(int y = 0; y < height; y++)
         if(borderArr[x][y])
            adjacencyArr[x-1][y] += SCConstants.ADJACENT_RIGHT;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height - 1; y++)
         if(borderArr[x][y])
            adjacencyArr[x][y+1] += SCConstants.ADJACENT_TOP;
      for(int x = 0; x < width; x++)
      for(int y = 1; y < height; y++)
         if(borderArr[x][y])
            adjacencyArr[x][y-1] += SCConstants.ADJACENT_BOTTOM;
      
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
         if(borderArr[x][y])
            panel.setTileIndex(x, y, SCConstants.BOX_TILE_ORIGIN + adjacencyArr[x][y]);
   }
   
   // draw a simple border
   public static void drawSimpleBorder(SCPanel panel)
   {
      applyBorderTileArray(getBorderArray(panel), panel);
   }
   
   // draw a border, with boxout containing title
   public static void drawBorderWithTitle(SCPanel panel, String title)
   {
      int startX = ((panel.getTilesWide() - title.length()) / 2) - 1;
      boolean[][] borderArr = getBorderArray(panel);
      for(int x = 0; x < title.length() + 2; x++)
         borderArr[startX + x][2] = true;
      borderArr[startX][1] = true;
      borderArr[startX + title.length() + 1][1] = true;
      applyBorderTileArray(borderArr, panel);
      panel.writeLine(startX + 1, 1, title);
   }
   
   
   // get layout for simple border of entire panel
   public static boolean[][] getBorderArray(SCPanel panel)
   {
      int w = panel.getTilesWide();
      int h = panel.getTilesTall();
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
      return borderArr;
   }
   
   public static int changeSaturation(int originalColor, double deltaSat)
   {
      Color c = new Color(originalColor);
      int r = c.getRed();
      int g = c.getGreen();
      int b = c.getBlue();
      float[] hsb = Color.RGBtoHSB(r, g, b, null);
      hsb[1] = hsb[1] * (float)deltaSat;
      hsb[1] = Math.min(1.0f, hsb[1]);
      c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
      return c.getRGB();
   }
   
   public static int setSaturation(int originalColor, double newSat)
   {
      newSat = Math.min(1.0, Math.max(0.0, newSat));
      Color c = new Color(originalColor);
      int r = c.getRed();
      int g = c.getGreen();
      int b = c.getBlue();
      float[] hsb = Color.RGBtoHSB(r, g, b, null);
      c = Color.getHSBColor(hsb[0], (float)newSat, hsb[2]);
      return c.getRGB();
   }
}