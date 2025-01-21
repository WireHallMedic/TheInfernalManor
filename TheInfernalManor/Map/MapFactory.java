package TheInfernalManor.Map;

public class MapFactory
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
      return map;
   }
}