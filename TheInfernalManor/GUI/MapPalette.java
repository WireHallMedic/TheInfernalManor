package TheInfernalManor.GUI;

import TheInfernalManor.Map.*;


public class MapPalette implements GUIConstants
{
   public static final int CLEAR_DECORATION_1 = MapCellBase.values().length;
   public static final int CLEAR_DECORATION_2 = CLEAR_DECORATION_1 + 1;
   public static final int CLEAR_DECORATION_3 = CLEAR_DECORATION_2 + 1;
   private static final int ARRAY_LENGTH = CLEAR_DECORATION_3 + 1;
   
   private int fgArr[];
   private int bgArr[];
   private String nameArr[];
   
   public MapPalette()
   {
      fgArr = new int[ARRAY_LENGTH];
      bgArr = new int[ARRAY_LENGTH];
      nameArr = new String[ARRAY_LENGTH];
      for(int i = 0; i < fgArr.length; i++)
      {
         fgArr[i] = WHITE;
         bgArr[i] = DARK_GREY;
         nameArr[i] = "";
      }
   }
   
   public void set(int i, String name, int fgColor, int bgColor)
   {
      fgArr[i] = fgColor;
      bgArr[i] = bgColor;
      nameArr[i] = name;
   }
   
   public void set(MapCellBase base, String name, int fgColor, int bgColor)
   {
      set(base.ordinal(), name, fgColor, bgColor);
   }
   
   public int getFGColor(int i){return fgArr[i];}
   public int getBGColor(int i){return bgArr[i];}
   public String getName(int i){return nameArr[i];}
   public int getFGColor(MapCellBase base){return getFGColor(base.ordinal());}
   public int getBGColor(MapCellBase base){return getBGColor(base.ordinal());}
   public String getNaqme(MapCellBase base){return getName(base.ordinal());}
   
   public static MapPalette getBasePalette()
   {
      MapPalette mp = new MapPalette();
      mp.set(MapCellBase.CLEAR, "Grass", GREEN, VERY_DARK_GREEN);
      mp.set(MapCellBase.ROUGH, "Rough", GREEN, VERY_DARK_GREEN);
      mp.set(MapCellBase.WALL, "Tree", GREEN, VERY_DARK_GREEN);
      mp.set(MapCellBase.LOW_WALL, "Stump", GREEN, VERY_DARK_GREEN);
      mp.set(MapCellBase.SHALLOW_LIQUID, "Water", BLUE, DARK_BLUE);
      mp.set(MapCellBase.DEEP_LIQUID, "Deep Water", BLUE, DARK_BLUE);
      mp.set(MapCellBase.CHEST_CLOSED, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.set(MapCellBase.CHEST_OPEN, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.set(MapCellBase.DOOR_CLOSED, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.set(MapCellBase.DOOR_OPEN, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.set(MapCellBase.TOGGLE_UNFLIPPED, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.set(MapCellBase.TOGGLE_FLIPPED, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.set(CLEAR_DECORATION_1, "Flowers", YELLOW, VERY_DARK_GREEN);
      mp.set(CLEAR_DECORATION_1, "Flowers", BLUE, VERY_DARK_GREEN);
      mp.set(CLEAR_DECORATION_1, "Stones", LIGHT_GREY, VERY_DARK_GREEN);
      return mp;
   }
}
