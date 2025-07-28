package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import java.awt.*;
import java.lang.*;

public class GUITools implements GUIConstants, SCConstants
{
   private static final int[] BAR_ARRAY = {
      ' ', BLOCK_ONE_EIGHTH_TILE, BLOCK_TWO_EIGHTHS_TILE,
      BLOCK_THREE_EIGHTHS_TILE, BLOCK_FOUR_EIGHTHS_TILE,
      BLOCK_FIVE_EIGHTHS_TILE, BLOCK_SIX_EIGHTHS_TILE,
      BLOCK_SEVEN_EIGHTHS_TILE, FULL_BLOCK_TILE
   };
   
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
      setBorderBox(0, 0, w, h, borderArr);
      return borderArr;
   }
   
   // set box of border tiles
   public static void setBorderBox(int xOrigin, int yOrigin, int w, int h, boolean[][] borderArr)
   {
      for(int x = 0; x < w; x++)
      {
         borderArr[xOrigin + x][yOrigin] = true;
         borderArr[xOrigin + x][yOrigin + h - 1] = true;
      }
      for(int y = 0; y < h; y++)
      {
         borderArr[xOrigin][yOrigin + y] = true;
         borderArr[xOrigin + w - 1][yOrigin + y] = true;
      }
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
   
   // returns an array of tileIconIndexes to represent a bar, like health or energy
   public static int[] getBar(int cur, int max, int barLength)
   {
      int[] indexArr = new int[barLength];
      // DIV0 safety
      if(max == 0)
         max = 1;
      // */1028 to avoid floating point arithmetic
      int transitionPoint = (((cur * 1028) / max) * barLength * 8) / 1028;
      for(int i = 0; i < barLength; i++)
      {
         if(transitionPoint >= 8)
            indexArr[i] = BAR_ARRAY[8];
         else if(transitionPoint <= 0)
            indexArr[i] = BAR_ARRAY[0];
         else
            indexArr[i] = BAR_ARRAY[transitionPoint];;
         transitionPoint -= 8;
      }
      // always show at least one segment if cur is above zero
      if(indexArr[0] == ' ' && cur > 0)
         indexArr[0] = BLOCK_ONE_EIGHTH_TILE;
      
      return indexArr;
   }
   
   // returns the average of the two colors
   public static int getAverageColor(int a, int b)
   {
      Color c1 = new Color(a);
      Color c2 = new Color(b);
      int red = (c1.getRed() + c2.getRed()) / 2;
      int green = (c1.getGreen() + c2.getGreen()) / 2;
      int blue = (c1.getBlue() + c2.getBlue()) / 2;
      return new Color(red, green, blue).getRGB();
   }
   
   // returns a gradient from 45% darker to original
   public static int[] getGradient(int original)
   {
      Color c = new Color(original);
      int red = c.getRed();
      int green = c.getGreen();
      int blue = c.getBlue();
      int[] results = new int[10];
      for(int i = 0; i < 10; i++)
      {
         double multiplier = (.05 * i) + .55;
         int r = Math.min(255, Math.max(0, (int)(red * multiplier)));
         int g = Math.min(255, Math.max(0, (int)(green * multiplier)));
         int b = Math.min(255, Math.max(0, (int)(blue * multiplier)));
         Color c2 = new Color(r, g, b);
         results[i] = c2.getRGB();
      }
      return results;
   }
   
   public static int[] getGradient(int start, int end, int length)
   {
      Color cStart = new Color(start);
      Color cEnd = new Color(end);
      int[] gradient = new int[length];
      double deltaRed = (double)(cEnd.getRed() - cStart.getRed()) / length;
      double deltaGreen = (double)(cEnd.getGreen() - cStart.getGreen()) / length;
      double deltaBlue = (double)(cEnd.getBlue() - cStart.getBlue()) / length;
      for(int i = 0; i < length; i++)
      {
         Color c = new Color(cStart.getRed() + (int)(deltaRed * i),
                             cStart.getGreen() + (int)(deltaGreen * i),
                             cStart.getBlue() + (int)(deltaBlue * i));
         gradient[i] = c.getRGB();
      }
      return gradient;
   }
   
   public static String getSignedString(int val)
   {
      if(val < 0)
         return "" + val;
      else if(val > 0)
         return "+" + val;
      // val == 0
      char[] charArr = "x0".toCharArray();
      charArr[0] = (char)SCConstants.PLUS_MINUS_TILE;
      return new String(charArr);
   }
   
   public static String getSignedString(double val)
   {
      char[] valArr = String.format("+%.2f", val).toCharArray();
      if(val < 0)
         valArr[0] = '-';
      else if(val == 0)
         valArr[0] = (char)SCConstants.PLUS_MINUS_TILE;
      return new String(valArr);
   }
   
   public static String serializeColor(int color)
   {
      String str = "UNKNOWN_COLOR";
      switch(color)
      {
         case WHITE:             str = "WHITE"; break;
         case BLACK:             str = "BLACK"; break;
         case RED:               str = "RED"; break;
         case DARK_RED:          str = "DARK_RED"; break;
         case VERY_DARK_RED:     str = "VERY_DARK_RED"; break;
         case ORANGE:            str = "ORANGE"; break;
         case DARK_ORANGE:       str = "DARK_ORANGE"; break;
         case YELLOW:            str = "YELLOW"; break;
         case DARK_YELLOW:       str = "DARK_YELLOW"; break;
         case YELLOW_GREEN:      str = "YELLOW_GREEN"; break;
         case DARK_YELLOW_GREEN: str = "DARK_YELLOW_GREEN"; break;
         case GREEN:             str = "GREEN"; break;
         case DARK_GREEN:        str = "DARK_GREEN"; break;
         case VERY_DARK_GREEN:   str = "VERY_DARK_GREEN"; break;
         case TURQUOISE:         str = "TURQUOISE"; break;
         case BLUE:              str = "BLUE"; break;
         case DARK_BLUE:         str = "DARK_BLUE"; break;
         case LIGHT_PURPLE:      str = "LIGHT_PURPLE"; break;
         case PURPLE:            str = "PURPLE"; break;
         case PINK:              str = "PINK"; break;
         case BROWN:             str = "BROWN"; break;
         case LIGHT_BROWN:       str = "LIGHT_BROWN"; break;
         case DARK_BROWN:        str = "DARK_BROWN"; break;
         case LIGHT_GREY:        str = "LIGHT_GREY"; break;
         case GREY:              str = "GREY"; break;
         case DARK_GREY:         str = "DARK_GREY"; break;
         default:                throw new RuntimeException("Attempt to serialize unknown color: " + color);
      }
      return str;
   }
   
   public static int deserializeColor(String str)
   {
      int val = 0;
      if(str.equals("WHITE")) val = WHITE;
      else if(str.equals("BLACK")) val = BLACK;
      else if(str.equals("RED")) val = RED;
      else if(str.equals("DARK_RED")) val = DARK_RED;
      else if(str.equals("VERY_DARK_RED")) val = VERY_DARK_RED;
      else if(str.equals("ORANGE")) val = ORANGE;
      else if(str.equals("DARK_ORANGE")) val = DARK_ORANGE;
      else if(str.equals("YELLOW")) val = YELLOW;
      else if(str.equals("DARK_YELLOW")) val = DARK_YELLOW;
      else if(str.equals("YELLOW_GREEN")) val = YELLOW_GREEN;
      else if(str.equals("DARK_YELLOW_GREEN")) val = DARK_YELLOW_GREEN;
      else if(str.equals("GREEN")) val = GREEN;
      else if(str.equals("DARK_GREEN")) val = DARK_GREEN;
      else if(str.equals("VERY_DARK_GREEN")) val = VERY_DARK_GREEN;
      else if(str.equals("TURQUOISE")) val = TURQUOISE;
      else if(str.equals("BLUE")) val = BLUE;
      else if(str.equals("DARK_BLUE")) val = DARK_BLUE;
      else if(str.equals("LIGHT_PURPLE")) val = LIGHT_PURPLE;
      else if(str.equals("PURPLE")) val = PURPLE;
      else if(str.equals("PINK")) val = PINK;
      else if(str.equals("BROWN")) val = BROWN;
      else if(str.equals("LIGHT_BROWN")) val = LIGHT_BROWN;
      else if(str.equals("DARK_BROWN")) val = DARK_BROWN;
      else if(str.equals("LIGHT_GREY")) val = LIGHT_GREY;
      else if(str.equals("GREY")) val = GREY;
      else if(str.equals("DARK_GREY")) val = DARK_GREY;
      else throw new RuntimeException("Attempt to deserialize unknown color: " + str);
      return val;
   }
   
   public static String getArticle(String str)
   {
      switch(str.toLowerCase().charAt(0))
      {
         case 'a' :
         case 'e' :
         case 'i' :
         case 'o' :
         case 'u' : return "an";
      }
      return "a";
   }
   
   public static String prependArticle(String str)
   {
      return getArticle(str) + " " + str;
   }
}