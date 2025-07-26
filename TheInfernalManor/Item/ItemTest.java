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
   
   
   @Test public void testWeaponGeneration()
   {
      for(AffixBase base : EquipmentAffixFactory.baseList)
      {
         if(base.getCategory().equals("WEAPON") ||
            base.getCategory().equals("RANGED") ||
            base.getCategory().equals("MELEE") ||
            base.getCategory().equals("ALL"))
         {
            Weapon w = WeaponFactory.getBase("Sword");
            base.apply(w, AffixBase.PREFIX);
            Assert.assertNotNull("Weapon affix " + base.getSuffixName() + " generates properly.", w);
         }
      }
   }
   
   
   @Test public void testOffHandGeneration()
   {
      for(AffixBase base : EquipmentAffixFactory.baseList)
      {
         if(base.getCategory().equals("OFFHAND") ||
            base.getCategory().equals("SHIELD") ||
            base.getCategory().equals("IMPLEMENT") ||
            base.getCategory().equals("ALL"))
         {
            OffHand oe = OffHandFactory.getBase("Shield");
            if(base.getCategory().equals("IMPLEMENT"))
               oe = OffHandFactory.getBase("Orb");
            base.apply(oe, AffixBase.PREFIX);
            Assert.assertNotNull("OffHand affix " + base.getSuffixName() + " generates properly.", oe);
         }
      }
   }
   
   
   @Test public void testArmorGeneration()
   {
      for(AffixBase base : EquipmentAffixFactory.baseList)
      {
         if(base.getCategory().equals("ARMOR") ||
            base.getCategory().equals("ALL"))
         {
            Armor a = ArmorFactory.getBase("Leather Armor");
            base.apply(a, AffixBase.PREFIX);
            Assert.assertNotNull("Armor affix " + base.getSuffixName() + " generates properly.", a);
         }
      }
   }
   
   
   @Test public void testRelicGeneration()
   {
      for(AffixBase base : EquipmentAffixFactory.baseList)
      {
         Relic r = null;
         if(base.getCategory().equals("RELIC") ||
            base.getCategory().equals("HELM") ||
            base.getCategory().equals("GLOVES") ||
            base.getCategory().equals("BOOTS") ||
            base.getCategory().equals("BRACERS") ||
            base.getCategory().equals("ALL"))
         {
            if(base.getCategory().equals("HELM"))
               r = RelicFactory.generateRelic(ItemConstants.RelicBase.HELM, base, null);
            else if(base.getCategory().equals("GLOVES"))
               r = RelicFactory.generateRelic(ItemConstants.RelicBase.GLOVES, base, null);
            else if(base.getCategory().equals("BOOTS"))
               r = RelicFactory.generateRelic(ItemConstants.RelicBase.BOOTS, base, null);
            else if(base.getCategory().equals("BRACERS"))
               r = RelicFactory.generateRelic(ItemConstants.RelicBase.BRACERS, base, null);
            else 
               r = RelicFactory.generateRelic(ItemConstants.RelicBase.JEWELRY, base, null);
            Assert.assertNotNull("Relic affix " + base.getSuffixName() + " generates properly.", r);
         }
      }
   }
}
