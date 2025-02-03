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
      
      return map;
   }
}