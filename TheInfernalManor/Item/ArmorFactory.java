package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;
import java.io.*;
import java.util.*;

public class ArmorFactory implements GUIConstants, ItemConstants
{
   public static ArmorBase[] baseList = setBaseList();
   
   public static Armor getBase(String name)
   {
      for(ArmorBase ab : baseList)
         if(ab.is(name))
            return ab.getCopy();
      throw new Error("No armor named " + name);
   }
   
   private static ArmorBase[] setBaseList()
   {
      BufferedReader bReader = EngineTools.getTextReader("/TheInfernalManor/DataFiles/ArmorBases.csv");
      Vector<ArmorBase> list = new Vector<ArmorBase>();
      try
      {
         bReader.readLine(); // discard header
         String str = bReader.readLine();
         while(str != null)
         {
            list.add(new ArmorBase(str));
            str = bReader.readLine();
         }
      }
      catch(Exception ex)
      {
         System.out.println(ex.toString());
      }
      return list.toArray(new ArmorBase[list.size()]);
   }
   public static Armor randomArmor(int level)
   {
      ArmorBase base = (ArmorBase)EngineTools.roll(baseList, level);
      ItemQuality quality = (ItemQuality)EngineTools.roll(ItemQuality.values(), level);
      Armor a = base.getCopy();
      a.adjustForQuality(quality);
      if(quality == ItemQuality.RARE)
      {
         AffixBase prefix = EquipmentAffixFactory.getArmorAffix(level);
         AffixBase suffix = null;
         while(suffix == null || suffix.conflicts(prefix))
            suffix = EquipmentAffixFactory.getArmorAffix(level);
         prefix.apply(a, AffixBase.PREFIX);
         suffix.apply(a, AffixBase.SUFFIX);
      }
      if(quality == ItemQuality.MAGICAL)
      {
         AffixBase affix = EquipmentAffixFactory.getArmorAffix(level);
         if(RNG.nextBoolean())
            affix.apply(a, AffixBase.PREFIX);
         else
            affix.apply(a, AffixBase.SUFFIX);
      }
      return a;
   }   
   
   private static class ArmorBase implements Rollable
   {
      private int minLevel;
      private int maxLevel;
      private int weight;
      private Armor armor;
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
      public Armor getCopy(){return new Armor(armor);}
      
      public boolean is(String str){return str.toLowerCase().equals(armor.getName().toLowerCase());}
      
      private ArmorBase(String serialStr)
      {
         armor = new Armor("Temp");
         armor.deserialize(serialStr);
         int startingIndex = Armor.numOfSerializedComponents();
         String[] strList = armor.getDeserializationArray(serialStr);
         minLevel = Integer.parseInt(strList[startingIndex]);
         maxLevel = Integer.parseInt(strList[startingIndex + 1]);
         weight = Integer.parseInt(strList[startingIndex + 2]);
      }
   }
}