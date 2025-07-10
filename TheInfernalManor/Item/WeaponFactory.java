package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;

public class WeaponFactory implements GUIConstants, ItemConstants
{
   public static Weapon randomWeapon(int level)
   {
      WeaponBase roll = (WeaponBase)EngineTools.roll(WeaponBase.values(), level);
      ItemQuality quality = (ItemQuality)EngineTools.roll(ItemQuality.values(), level);
      Weapon w = new Weapon("temp");
      switch(roll)
      {
         case DAGGER:      w = getDagger();
                           break;
         case SWORD:       w = getSword();
                           break; 
         case GREATSWORD:  w = getGreatsword();
                           break;
         case SLING:       w = getSling();
                           break; 
         case BOW:         w = getBow();
                           break;
         case WAND:        w = getWand();
                           break;   
         case STAFF:       w = getStaff();
                           break;
      }
      switch(quality)
      {
         case LOW :  w.adjustForQuality(ItemQuality.LOW); break; 
         case HIGH : w.adjustForQuality(ItemQuality.HIGH); break; 
      }
      return w;
   }
   
   // natural weapons
   public static Weapon getFist()
   {
      Weapon w = new Weapon("Fist");
      w.setSize(Weapon.LIGHT);
      return w;
   }
   
   public static Weapon getWolfJaws()
   {
      Weapon w = new Weapon("Jaws");
      w.setSize(Weapon.LIGHT);
      w.setPhysicalDamage(5);
      return w;
   }
   
   // basic melee weapons
   public static Weapon getDagger()
   {
      Weapon w = new Weapon("Dagger");
      w.setSize(Weapon.LIGHT);
      w.setPhysicalDamage(3);
      return w;
   }
   
   public static Weapon getSword()
   {
      Weapon w = new Weapon("Sword");
      w.setSize(Weapon.MEDIUM);
      w.setPhysicalDamage(6);
      return w;
   }
   
   public static Weapon getGreatsword()
   {
      Weapon w = new Weapon("Greatsword");
      w.setSize(Weapon.HEAVY);
      w.setPhysicalDamage(9);
      return w;
   }
   
   // basic ranged weapons
   public static Weapon getSling()
   {
      Weapon w = new Weapon("Sling");
      w.setSize(Weapon.MEDIUM);
      w.setPhysicalDamage(3);
      w.setRange(5);
      return w;
   }
   
   public static Weapon getBow()
   {
      Weapon w = new Weapon("Bow");
      w.setSize(Weapon.HEAVY);
      w.setPhysicalDamage(6);
      w.setRange(10);
      return w;
   }
   
   // basic magic implements
   public static Weapon getWand()
   {
      Weapon w = new Weapon("Wand");
      w.setSize(Weapon.LIGHT);
      w.setPhysicalDamage(0);
      w.setMagicalDamage(3);
      return w;
   }
   
   public static Weapon getStaff()
   {
      Weapon w = new Weapon("Staff");
      w.setSize(Weapon.HEAVY);
      w.setPhysicalDamage(4);
      w.setMagicalDamage(6);
      return w;
   }
   
   
   
}