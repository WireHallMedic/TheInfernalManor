package TheInfernalManor.Map;

import java.awt.*;
import TheInfernalManor.GUI.*;

public class MapFactory implements GUIConstants
{
   public static ZoneMap getTestMap1()
   {
      int w = 20;
      int h = 20;
      ZoneMap map = new ZoneMap(w, h);
      for(int x = 1; x < w - 1; x++)
      for(int y = 1; y < h - 1; y++)
         map.setTile(x, y, new MapCell(MapCellBase.CLEAR));
      map.setTile(2, 2, new MapCell(MapCellBase.WALL));
      map.setTile(w - 3, h - 3, new MapCell(MapCellBase.WALL));
      map.setTile(w - 3, 2, new MapCell(MapCellBase.WALL));
      map.setTile(2, h - 3, new MapCell(MapCellBase.WALL));
      for(int x = 0; x < w; x++)
      for(int y = 0; y < h; y++)
         map.getTile(x, y).setFGColor(Color.CYAN.getRGB());
      map.setTile(10, 5, MapCellFactory.getDoor());
      
      MapCell a = new MapCell('*', false, true, true);
      MapCell b = new MapCell('*', false, true, true);
      a.setFGColor(BLUE);
      b.setFGColor(RED);
      ToggleTile fancyThing = new ToggleTile(a, b);
      fancyThing.setOneUseOnly(true);
      map.setTile(10, 7, fancyThing);
      return map;
   }
}