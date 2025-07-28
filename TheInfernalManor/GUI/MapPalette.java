package TheInfernalManor.GUI;

import TheInfernalManor.Map.*;


public class MapPalette implements GUIConstants
{
   public static final int BASE = 0;
   public static final int ALTERNATE = 1;    
   public static final int DECORATION_1 = 2; 
   public static final int DECORATION_2 = 3; 
   public static final int INDICATOR = 4;   // such as a path 
   public static final int VARIATIONS = 5;
   
   private int[][] fgArr;
   private int[][] bgArr;
   private String[][] nameArr;
   
   public MapPalette()
   {
      fgArr = new int[MapCellBase.values().length][VARIATIONS];
      bgArr = new int[MapCellBase.values().length][VARIATIONS];
      nameArr = new String[MapCellBase.values().length][VARIATIONS];
      for(int i = 0; i < fgArr.length; i++)
      for(int j = 0; j < VARIATIONS; j++)
      {
         fgArr[i][j] = WHITE;
         bgArr[i][j] = DARK_GREY;
         nameArr[i][j] = "";
      }
   }
   
   public void set(int i, int v, String name, int fgColor, int bgColor)
   {
      fgArr[i][v] = fgColor;
      bgArr[i][v] = bgColor;
      nameArr[i][v] = name;
   }
   public void set(int i, String name, int fgColor, int bgColor)
   {
      set(i, 0, name, fgColor, bgColor);
   }
   
   public void set(MapCellBase base, String name, int fgColor, int bgColor)
   {
      set(base.ordinal(), name, fgColor, bgColor);
   }
   
   public void set(MapCellBase base, int v, String name, int fgColor, int bgColor)
   {
      set(base.ordinal(), v, name, fgColor, bgColor);
   }
   
   public void setAllVariations(MapCellBase base, String name, int fgColor, int bgColor)
   {
      setAllVariations(base.ordinal(), name, fgColor, bgColor);
   }
   
   public void setAllVariations(int index, String name, int fgColor, int bgColor)
   {
      for(int v = 0; v < VARIATIONS; v++)
      {
         set(index, v, name, fgColor, bgColor);
      }
   }
   
   public int getFGColor(int i){return getFGColor(i, 0);}
   public int getBGColor(int i){return getBGColor(i, 0);}
   public String getName(int i){return getName(i, 0);}
   public int getFGColor(MapCellBase base){return getFGColor(base.ordinal());}
   public int getBGColor(MapCellBase base){return getBGColor(base.ordinal());}
   public String getNaqme(MapCellBase base){return getName(base.ordinal());}
   
   public int getFGColor(int i, int v){return fgArr[i][v];}
   public int getBGColor(int i, int v){return bgArr[i][v];}
   public String getName(int i, int v){return nameArr[i][v];}
   public int getFGColor(MapCellBase base, int v){return getFGColor(base.ordinal(), v);}
   public int getBGColor(MapCellBase base, int v){return getBGColor(base.ordinal(), v);}
   public String getNaqme(MapCellBase base, int v){return getName(base.ordinal(), v);}
   
   public static MapPalette getBasePalette()
   {
      MapPalette mp = new MapPalette();
      mp.setAllVariations(MapCellBase.CLEAR, "Grass", GREEN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.ROUGH, "Rough", GREEN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.WALL, "Tree", GREEN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.LOW_WALL, "Stump", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.SHALLOW_LIQUID, "Water", BLUE, DARK_BLUE);
      mp.setAllVariations(MapCellBase.DEEP_LIQUID, "Deep Water", BLUE, DARK_BLUE);
      mp.setAllVariations(MapCellBase.PIT, "Pit", WHITE, BLACK);
      mp.setAllVariations(MapCellBase.CONTAINER, "Barrel", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.CHEST_CLOSED, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.CHEST_OPEN, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.DOOR_CLOSED, "", LIGHT_BROWN, DARK_GREY);
      mp.setAllVariations(MapCellBase.DOOR_OPEN, "", LIGHT_BROWN, DARK_GREY);
      mp.setAllVariations(MapCellBase.TOGGLE_UNFLIPPED, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.TOGGLE_FLIPPED, "", LIGHT_BROWN, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.ENTRANCE, "Entrance", WHITE, VERY_DARK_GREEN);
      mp.setAllVariations(MapCellBase.EXIT, "Exit", WHITE, VERY_DARK_GREEN);
      
      mp.set(MapCellBase.CLEAR, ALTERNATE, "Floor", LIGHT_GREY, DARK_GREY);
      mp.set(MapCellBase.CLEAR, DECORATION_1, "Flowers", YELLOW, VERY_DARK_GREEN);
      mp.set(MapCellBase.CLEAR, DECORATION_2, "Flowers", BLUE, VERY_DARK_GREEN);
      mp.set(MapCellBase.CLEAR, INDICATOR, "Path", BROWN, VERY_DARK_GREEN);
      
      mp.set(MapCellBase.WALL, ALTERNATE, "Wall", LIGHT_GREY, DARK_GREY);
      mp.set(MapCellBase.LOW_WALL, ALTERNATE, "Window", LIGHT_GREY, DARK_GREY);
      
      mp.set(MapCellBase.CONTAINER, ALTERNATE, "", LIGHT_BROWN, DARK_GREY);
      mp.set(MapCellBase.CHEST_CLOSED, ALTERNATE, "", LIGHT_BROWN, DARK_GREY);
      mp.set(MapCellBase.CHEST_OPEN, ALTERNATE, "", LIGHT_BROWN, DARK_GREY);
      mp.set(MapCellBase.TOGGLE_UNFLIPPED, ALTERNATE, "", LIGHT_BROWN, DARK_GREY);
      mp.set(MapCellBase.TOGGLE_FLIPPED, ALTERNATE, "", LIGHT_BROWN, DARK_GREY);
      return mp;
   }
   
   public static MapPalette getDungeonPalette()
   {
      MapPalette mp = new MapPalette();
      mp.setAllVariations(MapCellBase.CLEAR, "Stone", LIGHT_GREY, DARK_GREY);
      mp.setAllVariations(MapCellBase.ROUGH, "Broken Stone", LIGHT_GREY, DARK_GREY);
      mp.setAllVariations(MapCellBase.WALL, "Wall", LIGHT_GREY, DARK_GREY);
      mp.setAllVariations(MapCellBase.LOW_WALL, "Raised Stone", LIGHT_GREY, DARK_GREY);
      mp.setAllVariations(MapCellBase.SHALLOW_LIQUID, "Water", BLUE, DARK_BLUE);
      mp.setAllVariations(MapCellBase.DEEP_LIQUID, "Deep Water", BLUE, DARK_BLUE);
      mp.setAllVariations(MapCellBase.PIT, "Pit", WHITE, BLACK);
      mp.setAllVariations(MapCellBase.CONTAINER, "Barrel", LIGHT_BROWN,  DARK_GREY);
      mp.setAllVariations(MapCellBase.CHEST_CLOSED, "", LIGHT_BROWN,  DARK_GREY);
      mp.setAllVariations(MapCellBase.CHEST_OPEN, "", LIGHT_BROWN,  DARK_GREY);
      mp.setAllVariations(MapCellBase.DOOR_CLOSED, "", LIGHT_BROWN, DARK_GREY);
      mp.setAllVariations(MapCellBase.DOOR_OPEN, "", LIGHT_BROWN, DARK_GREY);
      mp.setAllVariations(MapCellBase.TOGGLE_UNFLIPPED, "", LIGHT_BROWN,  DARK_GREY);
      mp.setAllVariations(MapCellBase.TOGGLE_FLIPPED, "", LIGHT_BROWN,  DARK_GREY);
      mp.setAllVariations(MapCellBase.ENTRANCE, "Entrance", WHITE,  DARK_GREY);
      mp.setAllVariations(MapCellBase.EXIT, "Exit", WHITE,  DARK_GREY);
      return mp;
   }
   
   public static MapPalette getSwampPalette()
   {
      MapPalette mp = getBasePalette();
      mp.setAllVariations(MapCellBase.SHALLOW_LIQUID, "Water", YELLOW_GREEN, DARK_YELLOW_GREEN);
      mp.setAllVariations(MapCellBase.DEEP_LIQUID, "Deep Water", YELLOW_GREEN, DARK_YELLOW_GREEN);
      return mp;
   }
}
