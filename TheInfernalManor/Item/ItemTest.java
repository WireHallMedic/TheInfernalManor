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
      defender.setMaxHealth(10);
      defender.setMaxBlock(10);
      Armor a = new Armor("Test armor");
      a.setPhysicalArmor(5);
      defender.setArmor(a);
      defender.fullHeal();
      defender.applyCombatDamage(5, Ability.PHYSICAL);
      Assert.assertEquals("Armor is not applied before block", defender.getCurBlock(), 5);
      
      defender.setMaxBlock(0);
      defender.fullHeal();
      defender.applyCombatDamage(3, Ability.PHYSICAL);
      Assert.assertEquals("Armor does not reduce unblocked damage below 1", defender.getCurHealth(), 9);
      
      defender.fullHeal();
      defender.applyCombatDamage(7, Ability.PHYSICAL);
      Assert.assertEquals("Armor applies full defense if damage > armor", defender.getCurHealth(), 8);
      
      defender.fullHeal();
      defender.applyCombatDamage(5, Ability.MAGICAL);
      Assert.assertEquals("Physical armor does not help against magic", defender.getCurHealth(), 5);
      
      defender.fullHeal();
      a.setPhysicalArmor(0);
      a.setMagicalArmor(5);
      defender.applyCombatDamage(5, Ability.PHYSICAL);
      Assert.assertEquals("Magical armor does not help against physical", defender.getCurHealth(), 5);
   }
}
