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
      
      MapCell a = new MapCell('*', false, true, true);
      MapCell b = new MapCell('*', false, true, true);
      a.setFGColor(BLUE);
      b.setFGColor(RED);
      ToggleTile fancyThing = new ToggleTile(a, b);
      fancyThing.setOneUseOnly(true);
      map.setTile(10, 7, fancyThing);
      map.setItemAt(1, 2, new Armor("Filthy Rags"));
      return map;
   }
}