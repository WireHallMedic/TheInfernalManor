package TheInfernalManor.Ability;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class AbilityTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() 
   {
      ;
   }

   
   @Test public void testActionSpeedsFaster()
   {
      Assert.assertEquals("Faster than instant is still instant.", ActionSpeed.INSTANT.faster(), ActionSpeed.INSTANT);
      Assert.assertEquals("Faster than fast is still fast.", ActionSpeed.FAST.faster(), ActionSpeed.FAST);
      Assert.assertEquals("Faster than normal is fast.", ActionSpeed.NORMAL.faster(), ActionSpeed.FAST);
      Assert.assertEquals("Faster than slow is normal.", ActionSpeed.SLOW.faster(), ActionSpeed.NORMAL);
   }
   
   @Test public void testActionSpeedsSlower()
   {
      Assert.assertEquals("Slower than instant is still instant.", ActionSpeed.INSTANT.slower(), ActionSpeed.INSTANT);
      Assert.assertEquals("Slower than fast is normal.", ActionSpeed.FAST.slower(), ActionSpeed.NORMAL);
      Assert.assertEquals("Slower than normal is slow.", ActionSpeed.NORMAL.slower(), ActionSpeed.SLOW);
      Assert.assertEquals("Slower than slow is still slow.", ActionSpeed.SLOW.slower(), ActionSpeed.SLOW);
   }
   
   @Test public void testActionSpeedsSloweest()
   {
      ActionSpeed as = ActionSpeed.slowest(ActionSpeed.INSTANT, ActionSpeed.FAST, ActionSpeed.NORMAL, ActionSpeed.SLOW);
      Assert.assertEquals("Static method slowest() finds slowest.", ActionSpeed.SLOW, as);
   }
   
   @Test public void testActionSpeedsFastest()
   {
      ActionSpeed as = ActionSpeed.fastest(ActionSpeed.SLOW, ActionSpeed.NORMAL, ActionSpeed.FAST, ActionSpeed.INSTANT);
      Assert.assertEquals("Static method fastest() finds fastest.", ActionSpeed.INSTANT, as);
   }
}
