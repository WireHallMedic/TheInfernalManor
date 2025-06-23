package TheInfernalManor.Map;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TIMRoomTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void testConnections() 
   {
      int width = 5;
      int height = 5;
      MapZone z = new MapZone(width, height);
      for(int x = 1; x < width - 1; x++)
      for(int y = 1; y < height - 1; y++)
      Assert.assertEquals("Default test added by jGRASP. Delete "
            + "this test once you have added your own.", 0, 1);
   }
}
