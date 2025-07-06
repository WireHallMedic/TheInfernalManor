package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;

public class MapCellFactory implements GUIConstants, MapConstants
{
   public static Door getDoor()
   {
      return new Door();
   }
   
   public static MapCell getGate()
   {
      Door d = new Door();
      d.getStateA().setTransparent(true);
      d.getStateA().setHighPassable(true);
      return d;
   }
   
   public static Chest getChest()
   {
      return new Chest();
   }
   
   public static MapCell getMapCell(MapCellBase base, int fg, int bg)
   {
      MapCell mc = new MapCell(base);
      if(base == MapCellBase.DOOR_CLOSED)
         mc = getDoor();
      if(base == MapCellBase.CHEST_CLOSED)
         mc = getChest();
      mc.setFGColor(fg);
      mc.setBGColor(bg);
      return mc;
   }
   
   public static MapCell getMapCell(MapCellBase base)
   {
      return getMapCell(base, WHITE, BLACK);
   }
   
   public static MapCell getMapCell(RoomTemplateCellMapping rtcm)
   {
      return getMapCell(rtcm.mapCellBase, WHITE, BLACK);
   }
}