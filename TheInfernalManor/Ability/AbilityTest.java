package TheInfernalManor.Ability;

import TheInfernalManor.Item.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
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
   
   @Test public void damageRangeTest()
   {
      Actor actor = new Actor("", ' ');
      actor.setPowerLevel(10);
      Attack attack = new Attack();
      int rolled8 = 0;
      int rolled9 = 0;
      int rolled10 = 0;
      for(int i = 0; i < 100; i++)
      {
         int roll = Combat.damageRoll(actor, actor, attack);
         if(roll == 8)
            rolled8++;
         if(roll == 9)
            rolled9++;
         if(roll == 10)
            rolled10++;
      }
      Assert.assertEquals("All results in range", rolled8 + rolled9 + rolled10, 100);
      Assert.assertTrue("Rolled 8 at least once", rolled8 > 0);
      Assert.assertTrue("Rolled 9 at least once", rolled9 > 0);
      Assert.assertTrue("Rolled 10 at least once", rolled10 > 0);
   }
   
   @Test public void applyDamageTest()
   {
      Actor attacker = new Actor("", ' ');
      Actor defender = new Actor("", ' ');
      Armor shield = new Armor("");
      shield.setBlock(1);
      defender.setArmor(shield);
      defender.fullHeal();
      Assert.assertEquals("Defender at full block", defender.getCurBlock(), 1);
      Assert.assertEquals("Defender at full health", defender.getCurHealth(), 10);
      Combat.resolveAttack(attacker, defender, attacker.getBasicAttack());
      Assert.assertNotEquals("Defender not at full block", defender.getCurBlock(), 1);
      Assert.assertEquals("Defender at full health", defender.getCurHealth(), 10);
      Combat.resolveAttack(attacker, defender, attacker.getBasicAttack());
      Assert.assertNotEquals("Defender not at full block", defender.getCurBlock(), 1);
      Assert.assertNotEquals("Defender not at full health", defender.getCurHealth(), 10);
   }
}
