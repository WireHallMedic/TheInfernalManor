package TheInfernalManor.Map;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class MapTest 
{
   private ZoneMap map;

   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() 
   {
      map = new ZoneMap(10, 10);
      for(int x = 0; x < 10; x++)
      for(int y = 0; y < 10; y++)
      {
         map.setTile(x, y, new MapCell(MapCellBase.WALL));
      }
      for(int x = 1; x < 9; x++)
      for(int y = 1; y < 9; y++)
      {
         map.setTile(x, y, new MapCell(MapCellBase.CLEAR));
      }
   }

   
   // test boundaries
   @Test public void testBoundaries()
   {
      Assert.assertTrue("Tile 1, 1, is passable.", map.isLowPassable(1, 1));
      Assert.assertFalse("Tile -1, 1, is impassable without error.", map.isLowPassable(-1, 1));
   }
}
