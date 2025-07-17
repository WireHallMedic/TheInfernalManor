/*

Not really worth being its own class, but really handy to be able to ask, "is this a door?"

*/

package TheInfernalManor.Map;

import StrictCurses.*;

public class Door extends ToggleTile
{
   public Door()
   {
      super(new MapCell(MapCellBase.DOOR_CLOSED), new MapCell(MapCellBase.DOOR_OPEN));
      setBreakable(true);
   }
}