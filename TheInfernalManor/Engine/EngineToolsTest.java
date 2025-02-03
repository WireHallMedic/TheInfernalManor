package TheInfernalManor.Engine;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class EngineToolsTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() 
   {
   
   }


   @Test public void testRollableFiltering() 
   {
      Rollable[] list = new Rollable[4];
      list[0] = new TestRollable(1, 5, 1);
      list[1] = new TestRollable(5, 10, 1);
      list[2] = new TestRollable(4, 6, 1);
      list[3] = new TestRollable(1, 5, 0);
      
      Rollable[] rolled = EngineTools.filterRollable(list, 5);
      Assert.assertEquals("Zero-weight rollables are filtered out.", 3, rolled.length);
      Assert.assertFalse("Zero-weight rollables are filtered out.", contains(rolled, list[3]));
      
      rolled = EngineTools.filterRollable(list, 6);
      boolean test = !contains(rolled, list[0]) && contains(rolled, list[1]) && contains(rolled, list[2]) && !contains(rolled, list[3]);
      Assert.assertTrue("Level filtering works", test);
   }


   @Test public void testRollableGeneration() 
   {
      Rollable[] list = new Rollable[4];
      list[0] = new TestRollable(1, 5, 1);
      list[1] = new TestRollable(5, 10, 1);
      list[2] = new TestRollable(4, 6, 1);
      list[3] = new TestRollable(1, 5, 0);
      
      // the seeding just worked out like that
      RNG.setSeed(1);
      Assert.assertEquals("Generate item 0 from list.", list[0], EngineTools.roll(list, 5));
      RNG.setSeed(2);
      Assert.assertEquals("Generate item 1 from list.", list[1], EngineTools.roll(list, 5));
      RNG.setSeed(3);
      Assert.assertEquals("Generate item 2 from list.", list[2], EngineTools.roll(list, 5));
   }
   
   private boolean contains(Rollable[] vals, Rollable test)
   {
      for(Rollable item : vals)
         if(item == test)
            return true;
      return false;
   }
   
   private class TestRollable implements Rollable
   {
      private int minLevel;
      private int maxLevel;
      private int weight;
      
      public TestRollable(int min, int max, int w)
      {
         minLevel = min;
         maxLevel = max;
         weight = w;
      }
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
   }
}
