package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;
import java.io.*;
import java.util.*;

public class OffHandFactory implements GUIConstants, ItemConstants
{
   public static OffHandBase[] baseList = setBaseList();
   
   public static OffHand getBase(String name)
   {
      for(OffHandBase ohb : baseList)
         if(ohb.is(name))
            return ohb.getCopy();
      throw new Error("No offhand item named " + name);
   }
   
   private static OffHandBase[] setBaseList()
   {
      BufferedReader bReader = EngineTools.getTextReader("OffHandBases.csv");
      Vector<OffHandBase> list = new Vector<OffHandBase>();
      try
      {
         bReader.readLine(); // discard header
         String str = bReader.readLine();
         while(str != null)
         {
            list.add(new OffHandBase(str));
            str = bReader.readLine();
         }
      }
      catch(Exception ex)
      {
         System.out.println(ex.toString());
      }
      return list.toArray(new OffHandBase[list.size()]);
   }
   
   public static OffHand randomOffHand(int level)
   {
      OffHandBase base = (OffHandBase)EngineTools.roll(baseList, level);
      ItemQuality quality = (ItemQuality)EngineTools.roll(ItemQuality.values(), level);
      OffHand o = base.getCopy();
      o.adjustForQuality(quality);
      if(quality == ItemQuality.RARE)
      {
         AffixBase prefix = EquipmentAffixFactory.getOffHandAffix(base.isShield(), level);
         AffixBase suffix = null;
         while(suffix == null || suffix.conflicts(prefix))
            suffix = EquipmentAffixFactory.getOffHandAffix(base.isShield(), level);
         prefix.apply(o, AffixBase.PREFIX);
         suffix.apply(o, AffixBase.SUFFIX);
      }
      if(quality == ItemQuality.MAGICAL)
      {
         AffixBase affix = EquipmentAffixFactory.getOffHandAffix(base.isShield(), level);
         if(RNG.nextBoolean())
            affix.apply(o, AffixBase.PREFIX);
         else
            affix.apply(o, AffixBase.SUFFIX);
      }
      return o;
   }
   
   
   private static class OffHandBase implements Rollable
   {
      private int minLevel;
      private int maxLevel;
      private int weight;
      private OffHand offHand;
      private boolean shield;
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
      public OffHand getCopy(){return new OffHand(offHand);}
      public boolean isShield(){return shield;}
      
      public boolean is(String str){return str.toLowerCase().equals(offHand.getName().toLowerCase());}
      
      private OffHandBase(String serialStr)
      {
         offHand = new OffHand("Temp");
         offHand.deserialize(serialStr);
         int startingIndex = OffHand.numOfSerializedComponents();
         String[] strList = offHand.getDeserializationArray(serialStr);
         shield = strList[startingIndex].toUpperCase().equals("SHIELD");
         minLevel = Integer.parseInt(strList[startingIndex + 1]);
         maxLevel = Integer.parseInt(strList[startingIndex + 2]);
         weight = Integer.parseInt(strList[startingIndex + 3]);
      }
   }
}