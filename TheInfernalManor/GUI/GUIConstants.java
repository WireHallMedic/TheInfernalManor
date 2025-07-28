package TheInfernalManor.GUI;

import java.awt.*;

public interface GUIConstants
{
   public static final int TILES_WIDE = 100;
   public static final int TILES_TALL = 32;
   public static final int MAP_PANEL_SIZE = 25;
   
   public static final int TICKS_PER_SECOND = 24;
   public static final int FRAME_RATE = 1000 / TICKS_PER_SECOND;
   
   public static final int WEAPON_ICON = '}';
   public static final int ARMOR_ICON = ']';
   public static final int OFFHAND_ICON = ')';
   public static final int CONSUMABLE_ICON = '+';
   public static final int RELIC_ICON = '*';
   public static final int LEGENDARY_WEAPON_ICON = '{';
   public static final int LEGENDARY_ARMOR_ICON = '[';
   public static final int LEGENDARY_OFFHAND_ICON = '(';
   
   // colors as ints rather than Color.getRGB() to be compile-time constant
   public static final int WHITE = -1;
   public static final int BLACK = -16777216;
   public static final int RED = -65536;
   public static final int DARK_RED = -5111808;
   public static final int VERY_DARK_RED = -8650752;
   public static final int ORANGE = -32768;
   public static final int DARK_ORANGE = -3381760;
   public static final int YELLOW = -256;
   public static final int DARK_YELLOW = -3289856;
   public static final int YELLOW_GREEN = -5580489;
   public static final int DARK_YELLOW_GREEN = -9728221;
   public static final int GREEN = -11681715;
   public static final int DARK_GREEN = -14460637;
   public static final int VERY_DARK_GREEN = -15121895;
   public static final int TURQUOISE = -16724788;
   public static final int BLUE = -10066177;
   public static final int DARK_BLUE = -13421696;
   public static final int LIGHT_PURPLE = -3931964;
   public static final int PURPLE = -8375424;
   public static final int PINK = -20561;
   public static final int BROWN = -10074327;
   public static final int LIGHT_BROWN = -3174318;
   public static final int DARK_BROWN = -12111588;
   public static final int LIGHT_GREY = -4144960;
   public static final int GREY = -8355712;
   public static final int DARK_GREY = -12566464;
   
   public static final int FIRE_COLOR = ORANGE;
   public static final int POISON_COLOR = YELLOW_GREEN;
   public static final int ICE_COLOR = BLUE;
   
   
   public static final int SELECTED_COLOR = YELLOW;
   public static final int RETICLE_COLOR = TURQUOISE;
   
   public static final int[] COLOR_ARRAY = {
      RED, DARK_RED, VERY_DARK_RED, ORANGE, DARK_ORANGE, YELLOW, DARK_YELLOW, YELLOW_GREEN, DARK_YELLOW_GREEN, GREEN, DARK_GREEN, TURQUOISE, BLUE, DARK_BLUE, 
      LIGHT_PURPLE, PURPLE, PINK, LIGHT_BROWN, BROWN, DARK_BROWN, WHITE, LIGHT_GREY, GREY, DARK_GREY, BLACK
      };
   public static final int[][] GRADIENT_ARRAY = getArrayOfGradients();
   
   private static int[][] getArrayOfGradients()
   {
      int[][] arr = new int[COLOR_ARRAY.length][];
      for(int i = 0; i < arr.length; i++)
         arr[i] = GUITools.getGradient(COLOR_ARRAY[i]);
      return arr;
   }
}