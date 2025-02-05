package TheInfernalManor.Map;

import StrictCurses.*;

public class Door extends ToggleTile
{
   public Door()
   {
      super(new MapCell(MapCellBase.DOOR_CLOSED), new MapCell(MapCellBase.DOOR_OPEN));
   }
}