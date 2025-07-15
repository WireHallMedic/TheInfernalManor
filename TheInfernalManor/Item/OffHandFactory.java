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
      return null;
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
      switch(quality)
      {
         case LOW :  o.adjustForQuality(ItemQuality.LOW); break; 
         case HIGH : o.adjustForQuality(ItemQuality.HIGH); break; 
      }
      return o;
   }
   
   
   public static OffHand getShield()
   {
      OffHand oh = new OffHand("Shield");
      oh.setGuard(5);
      return oh;
   }
   
   public static OffHand getOrb()
   {
      OffHand oh = new OffHand("Orb");
      oh.setMagicalDamage(4);
      return oh;
   }
   
   public static OffHand getTome()
   {
      OffHand oh = new OffHand("Tome");
      oh.setEnergyRecharge(2);
      oh.setMagicalArmor(3);
      return oh;
   }
   
   
   private static class OffHandBase implements Rollable
   {
      private int minLevel;
      private int maxLevel;
      private int weight;
      private OffHand offHand;
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
      public OffHand getCopy(){return new OffHand(offHand);}
      
      public boolean is(String str){return str.equals(offHand.getName());}
      
      private OffHandBase(String serialStr)
      {
         offHand = new OffHand("Temp");
         offHand.deserialize(serialStr);
         int startingIndex = OffHand.numOfSerializedComponents();
         String[] strList = offHand.getDeserializationArray(serialStr);
         minLevel = Integer.parseInt(strList[startingIndex]);
         maxLevel = Integer.parseInt(strList[startingIndex + 1]);
         weight = Integer.parseInt(strList[startingIndex + 2]);
      }
   }
}