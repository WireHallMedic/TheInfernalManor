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
}