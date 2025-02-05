package TheInfernalManor.Map;

import java.awt.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;

public class MapFactory implements GUIConstants
{
   public static ZoneMap getTestMap1()
   {
      int w = 20;
      int h = 20;
      ZoneMap map = new ZoneMap(w, h);
      for(int x = 1; x < w - 1; x++)
      for(int y = 1; y < h - 1; y++)
         map.setTile(x, y, MapCellFactory.getMapCell(MapCellBase.CLEAR, LIGHT_GREY, DARK_GREY));
      map.setTile(2, 2, MapCellFactory.getMapCell(MapCellBase.WALL, LIGHT_GREY, DARK_GREY));
      map.setTile(w - 3, h - 3, MapCellFactory.getMapCell(MapCellBase.WALL, LIGHT_GREY, DARK_GREY));
      map.setTile(w - 3, 2, MapCellFactory.getMapCell(MapCellBase.WALL, LIGHT_GREY, DARK_GREY));
      map.setTile(2, h - 3, MapCellFactory.getMapCell(MapCellBase.WALL, LIGHT_GREY, DARK_GREY));
      map.setTile(10, 5, MapCellFactory.getDoor());
      map.setTile(10, 7, MapCellFactory.getChest());
      map.setItemAt(1, 2, new Armor("Filthy Rags"));
      
      MapCell fragilePillar = MapCellFactory.getMapCell(MapCellBase.WALL, BROWN, DARK_GREY);
      fragilePillar.setBrokenForm(MapCellFactory.getMapCell(MapCellBase.ROUGH, BROWN, DARK_GREY));
      map.setTile(10, 9, new MapCell(fragilePillar));
      map.setTile(10, 10, new MapCell(fragilePillar));
      map.setTile(11, 9, new MapCell(fragilePillar));
      map.setTile(11, 10, new MapCell(fragilePillar));
      
      for(int x = 1; x < 19; x++)
         map.setTile(x, 12, MapCellFactory.getMapCell(MapCellBase.LOW_WALL, LIGHT_GREY, DARK_GREY));
      for(int y = 13; y < 19; y++)
      {
         map.setTile(5, y, MapCellFactory.getMapCell(MapCellBase.LOW_WALL, LIGHT_GREY, DARK_GREY));
         map.setTile(10, y, MapCellFactory.getMapCell(MapCellBase.LOW_WALL, LIGHT_GREY, DARK_GREY));
         map.setTile(15, y, MapCellFactory.getMapCell(MapCellBase.LOW_WALL, LIGHT_GREY, DARK_GREY));
      }
      map.setTile(2, 12, MapCellFactory.getGate());
      map.setTile(7, 12, MapCellFactory.getGate());
      map.setTile(12, 12, MapCellFactory.getGate());
      map.setTile(17, 12, MapCellFactory.getGate());
      
      map.setItemAt(2, 3, new Gold(10));
      
      return map;
   }
}