package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;
import java.io.*;
import java.util.*;

public class WeaponFactory implements GUIConstants, ItemConstants
{
   public static WeaponBase[] baseList = setBaseList();
   
   public static Weapon getBase(String name)
   {
      for(WeaponBase wb : baseList)
         if(wb.is(name))
            return wb.getCopy();
      throw new Error("No weapon named " + name);
   }
   
   private static WeaponBase[] setBaseList()
   {
      BufferedReader bReader = EngineTools.getTextReader("WeaponBases.csv");
      Vector<WeaponBase> list = new Vector<WeaponBase>();
      try
      {
         bReader.readLine(); // discard header
         String str = bReader.readLine();
         while(str != null)
         {
            list.add(new WeaponBase(str));
            str = bReader.readLine();
         }
      }
      catch(Exception ex)
      {
         System.out.println(ex.toString());
      }
      return list.toArray(new WeaponBase[list.size()]);
   }
   
   
   public static Weapon randomWeapon(int level)
   {
      WeaponBase base = (WeaponBase)EngineTools.roll(baseList, level);
      ItemQuality quality = (ItemQuality)EngineTools.roll(ItemQuality.values(), level);
      Weapon w = base.getCopy();
      w.adjustForQuality(quality);
      if(quality == ItemQuality.RARE)
      {
         AffixBase prefix = EquipmentAffexFactory.getWeaponAffix(level);
         AffixBase suffix = null;
         while(suffix == null || suffix.conflicts(prefix))
            suffix = EquipmentAffexFactory.getWeaponAffix(level);
         prefix.apply(w, AffixBase.PREFIX);
         suffix.apply(w, AffixBase.SUFFIX);
      }
      if(quality == ItemQuality.MAGICAL)
      {
         AffixBase affix = EquipmentAffexFactory.getWeaponAffix(level);
         if(RNG.nextBoolean())
            affix.apply(w, AffixBase.PREFIX);
         else
            affix.apply(w, AffixBase.SUFFIX);
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
   
      
   private static class WeaponBase implements Rollable
   {
      private int minLevel;
      private int maxLevel;
      private int weight;
      private Weapon weapon;
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
      public Weapon getCopy(){return new Weapon(weapon);}
      
      public boolean is(String str){return str.toLowerCase().equals(weapon.getName().toLowerCase());}
      
      private WeaponBase(String serialStr)
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