package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;

public class MapCellFactory implements GUIConstants, MapConstants
{
   public static MapCell getDoor()
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
   
   public static MapCell getMapCell(MapCellBase base)
   {
      return getMapCell(base, WHITE, BLACK);
   }
   
   public static MapCell getMapCell(RoomTemplateCellMapping rtcm)
   {
      return getMapCell(rtcm.mapCellBase, WHITE, BLACK);
   }
}