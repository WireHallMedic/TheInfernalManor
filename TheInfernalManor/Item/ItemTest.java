package TheInfernalManor.Item;

import TheInfernalManor.Ability.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ItemTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }


   @Test public void testWeapon()
   {
      Actor attacker = new Actor("", ' ');
      Actor defender = new Actor("", ' ');
      RNG.setSeed(0);
      int baseDamage = Combat.damageRoll(attacker, defender, attacker.getBasicAttack());
      RNG.setSeed(0);
      Weapon w = new Weapon("Test Weapon");
      w.setPhysicalDamage(4);
      attacker.setMainHand(w);
      int alteredDamage = Combat.damageRoll(attacker, defender, attacker.getBasicAttack());
      Assert.assertTrue("Weapon increases physical damage", baseDamage < alteredDamage);
   }

   @Test public void testArmor()
   {
      Actor defender = new Actor("", ' ');
      defender.getBaseStats().setMaxHealth(10);
      Armor a = new Armor("Test armor");
      a.setPhysicalArmor(5);
      a.setGuard(10);
      defender.setArmor(a);
      defender.fullHeal();
      defender.applyCombatDamage(5, Ability.PHYSICAL);
      Assert.assertEquals("Armor is not applied before block", 5, defender.getCurGuard());
      
      a.setGuard(0);
      defender.setArmor(a);
      defender.fullHeal();
      defender.applyCombatDamage(3, Ability.PHYSICAL);
      Assert.assertEquals("Armor does not reduce unblocked damage below 1", 9, defender.getCurHealth());
      
      defender.fullHeal();
      defender.applyCombatDamage(7, Ability.PHYSICAL);
      Assert.assertEquals("Armor applies full defense if damage > armor", 8, defender.getCurHealth());
      
      defender.fullHeal();
      defender.applyCombatDamage(5, Ability.MAGICAL);
      Assert.assertEquals("Physical armor does not help against magic", defender.getCurHealth(), 5);
      
      defender.fullHeal();
      a.setPhysicalArmor(0);
      a.setMagicalArmor(5);
      defender.setArmor(a);
      defender.applyCombatDamage(5, Ability.PHYSICAL);
      Assert.assertEquals("Magical armor does not help against physical", defender.getCurHealth(), 5);
   }
   
   
   @Test public void testWeaponSerialization()
   {
      Weapon a = new Weapon("Weapon A");
      Weapon b = new Weapon("Weapon B");
      a.setTestingValues();
      Assert.assertFalse("Items do not match before.", a.equals(b));
      b.deserialize(a.serialize());
      Assert.assertTrue("Items match after.", a.equals(b));
   }
   
   
   @Test public void testArmorSerialization()
   {
      Armor a = new Armor("Armor A");
      Armor b = new Armor("Armor B");
      a.setTestingValues();
      Assert.assertFalse("Items do not match before.", a.equals(b));
      b.deserialize(a.serialize());
      Assert.assertTrue("Items match after.", a.equals(b));
   }
   
   
   @Test public void testOffHandSerialization()
   {
      OffHand a = new OffHand("OffHand A");
      OffHand b = new OffHand("OffHand B");
      a.setTestingValues();
      Assert.assertFalse("Items do not match before.", a.equals(b));
      b.deserialize(a.serialize());
      Assert.assertTrue("Items match after.", a.equals(b));
   }
   
   
   @Test public void testRelicSerialization()
   {
      Relic a = new Relic("Relic A");
      Relic b = new Relic("Relic B");
      a.setTestingValues();
      Assert.assertFalse("Items do not match before.", a.equals(b));
      b.deserialize(a.serialize());
      Assert.assertTrue("Items match after.", a.equals(b));
   }
   
   
   @Test public void testGoldSerialization()
   {
      Gold a = new Gold(27);
      Gold b = new Gold(195);
      Assert.assertFalse("Items do not match before.", a.equals(b));
      b.deserialize(a.serialize());
      Assert.assertTrue("Items match after.", a.equals(b));
   }
}
