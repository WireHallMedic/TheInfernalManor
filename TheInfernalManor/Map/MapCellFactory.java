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
   
   public static MapCell getMapCell(MapCellBase base, int fg, int bg)
   {
      MapCell mc = new MapCell(base);
      mc.setFGColor(fg);
      mc.setBGColor(bg);
      return mc;
   }
}