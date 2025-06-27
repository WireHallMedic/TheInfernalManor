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
   
   public static final int WHITE = Color.WHITE.getRGB();
   public static final int BLACK = Color.BLACK.getRGB();
   public static final int RED = -39322;
   public static final int DARK_RED = -7981771;
   public static final int ORANGE = -8602;
   public static final int YELLOW = -154;
   public static final int GREEN = -11681715;
   public static final int DARK_GREEN = -14460637;
   public static final int VERY_DARK_GREEN = new Color(DARK_GREEN).darker().darker().getRGB();
   public static final int BLUE = -10066177;
   public static final int DARK_BLUE = -13421696;
   public static final int PURPLE = -8375424;
   public static final int PINK = -20561;
   public static final int DARK_PINK = -2583404;
   public static final int BROWN = -10074327;
   public static final int LIGHT_BROWN = new Color(BROWN).brighter().brighter().getRGB();
   public static final int DARK_BROWN = -14543091;
   public static final int LIGHT_GREY = -4144960;
   public static final int GREY = -8355712;
   public static final int DARK_GREY = -12566464;
   
   public static final int BRIGHT_RED = Color.RED.getRGB();
   public static final int BRIGHT_YELLOW = Color.YELLOW.getRGB();
   public static final int BRIGHT_ORANGE = Color.ORANGE.getRGB();
   
   public static final int SELECTED_COLOR = YELLOW;
   public static final int RETICLE_COLOR = BLUE;
   
   public static final int[] COLOR_ARRAY = {
      RED, DARK_RED, ORANGE, YELLOW, GREEN, DARK_GREEN, BLUE, DARK_BLUE, PURPLE, PINK,
      DARK_PINK, BROWN, DARK_BROWN, WHITE, LIGHT_GREY, GREY, DARK_GREY, BLACK
      };
   
   public static final int[] DARK_GREEN_GRADIENT = GUITools.getGradient(DARK_GREEN);
   public static final int[] VERY_DARK_GREEN_GRADIENT = GUITools.getGradient(GUITools.getAverageColor(DARK_GREEN, VERY_DARK_GREEN));
   public static final int[] DARK_BLUE_GRADIENT = GUITools.getGradient(DARK_BLUE);
   public static final int[] DARK_BROWN_GRADIENT = GUITools.getGradient(DARK_BROWN);
   public static final int[] DARK_GREY_GRADIENT = GUITools.getGradient(DARK_GREY);
   public static final int[] DARK_RED_GRADIENT = GUITools.getGradient(DARK_RED);
}