package TheInfernalManor.Map;

public class MapFactory
{
   public static ZoneMap getTestMap1()
   {
      int w = 20;
      int h = 20;
      ZoneMap map = new ZoneMap(w, h);
      for(int x = 0; x < w; x++)
      for(int y = 0; y < h; y++)
         map.setTile(x, y, new MapCell(MapCellBase.CLEAR));
      map.setTile(2, 2, new MapCell(MapCellBase.WALL));
      map.setTile(w - 4, h - 3, new MapCell(MapCellBase.WALL));
      map.setTile(w - 3, 2, new MapCell(MapCellBase.WALL));
      map.setTile(2, h - 3, new MapCell(MapCellBase.WALL));
      return map;
   }
}