package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;

public class MapCellFactory implements GUIConstants, MapConstants
{
   public static MapCell getEntrance()
   {
      return new MapCell(MapCellBase.ENTRANCE);
   }
   
   public static MapCell getExit()
   {
      return new MapCell(MapCellBase.EXIT);
   }
   
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
   
   public static Chest getChest(int level)
   {
      Chest c = new Chest();
      int iterations = RNG.nextInt(4) + 2;
      for(int i = 0; i < iterations; i++)
      {
         c.addItem(LootFactory.getRandomLoot(level));
      }
      return c;
   }
   public static Chest getChest(){return getChest(1);}
   
   public static ItemContainer getBarrel()
   {
      ItemContainer ic = new ItemContainer(MapCellBase.CONTAINER);
      ic.setBreakable(true);
      return ic;
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