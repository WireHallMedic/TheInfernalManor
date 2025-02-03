package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;

public class MapCellFactory implements GUIConstants
{
   public static MapCell getDoor()
   {
      MapCell a = new MapCell(MapCellBase.DOOR_CLOSED);
      MapCell b = new MapCell(MapCellBase.DOOR_OPEN);
      
      return new ToggleTile(a, b);
   }
   
   public static MapCell getChest()
   {
      MapCell a = new MapCell(MapCellBase.CHEST_CLOSED);
      MapCell b = new MapCell(MapCellBase.CHEST_OPEN);
      ToggleTile tt = new ToggleTile(a, b);
      tt.setOneUseOnly(true);
      tt.setBrokenForm(new MapCell(MapCellBase.ROUGH));
      return tt;
   }
   
   public static MapCell getMapCell(MapCellBase base, int fg, int bg)
   {
      MapCell mc = new MapCell(base);
      mc.setFGColor(fg);
      mc.setBGColor(bg);
      return mc;
   }
}