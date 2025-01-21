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
   @Test public void testLowPassable()
   {
      Assert.assertTrue("Clear is passable.", map.isLowPassable(1, 1));
      Assert.assertFalse("Wall is impassable.", map.isLowPassable(0, 1));
      Assert.assertFalse("OOB (Wall) is impassable without error.", map.isLowPassable(-1, 1));
   }
   @Test public void testHighPassable()
   {
      Assert.assertTrue("Clear is passable.", map.isHighPassable(1, 1));
      Assert.assertFalse("Wall is impassable.", map.isHighPassable(0, 1));
      Assert.assertFalse("OOB (Wall) is impassable without error.", map.isHighPassable(-1, 1));
   }
   @Test public void testTransparent()
   {
      Assert.assertTrue("Clear is transparent.", map.isTransparent(1, 1));
      Assert.assertFalse("Wall is not transparent.", map.isTransparent(0, 1));
      Assert.assertFalse("OOB (Wall) is not transparent without error.", map.isTransparent(-1, 1));
   }
}
