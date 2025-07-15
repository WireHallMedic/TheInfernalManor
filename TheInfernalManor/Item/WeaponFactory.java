package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;
import java.io.*;
import java.util.*;

public class WeaponFactory implements GUIConstants, ItemConstants
{
   public static WeaponBaseItem[] baseList = getBaseList();
   
   public static Weapon getBase(String name)
   {
      for(WeaponBaseItem wb : baseList)
         if(wb.is(name))
            return wb.getCopy();
      return null;
   }
   
   private static WeaponBaseItem[] getBaseList()
   {
      BufferedReader bReader = EngineTools.getTextReader("WeaponBases.csv");
      Vector<WeaponBaseItem> list = new Vector<WeaponBaseItem>();
      try
      {
         bReader.readLine(); // discard header
         String str = bReader.readLine();
         while(str != null)
         {
            list.add(new WeaponBaseItem(str));
            str = bReader.readLine();
         }
      }
      catch(Exception ex)
      {
         System.out.println(ex.toString());
      }
      return list.toArray(new WeaponBaseItem[list.size()]);
   }
   
   
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
   
   
   private static class WeaponBaseItem implements Rollable
   {
      private int minLevel;
      private int maxLevel;
      private int weight;
      private Weapon weapon;
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
      public Weapon getCopy(){return new Weapon(weapon);}
      
      public boolean is(String str){return str.equals(weapon.getName());}
      
      private WeaponBaseItem(String serialStr)
      {
         weapon = new Weapon("Temp");
         weapon.deserialize(serialStr);
         int startingIndex = Weapon.numOfSerializedComponents();
         String[] strList = weapon.getDeserializationArray(serialStr);
         minLevel = Integer.parseInt(strList[startingIndex]);
         maxLevel = Integer.parseInt(strList[startingIndex + 1]);
         weight = Integer.parseInt(strList[startingIndex + 2]);
      }
   }
}