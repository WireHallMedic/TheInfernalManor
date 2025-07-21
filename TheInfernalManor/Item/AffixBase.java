package TheInfernalManor.Item;

import java.util.*;
import java.io.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;

public class AffixBase implements Rollable, GUIConstants, ItemConstants
{
   public static final boolean PREFIX = true;
   public static final boolean SUFFIX = !PREFIX;
   
   private int minLevel;
   private int maxLevel;
   private int weight;
   private EquippableItem item;
   private String prefixName;
   private String suffixName;
   private String specialAttributes;
   private String category;
   
   public int getMinLevel(){return minLevel;}
   public int getMaxLevel(){return maxLevel;}
   public int getWeight(){return weight;}
   public EquippableItem getCopy(){return new EquippableItem(item);}
   public String getPrefixName(){return prefixName;}
   public String getSuffixName(){return suffixName;}
   public String getSpecialAttributes(){return specialAttributes;}
   public String getCategory(){return category;}
   
   public boolean isCategory(String str){return str.toLowerCase().equals(category.toLowerCase());}
   
   public AffixBase(String serialStr)
   {
      try
      {
         item = new EquippableItem("Temp", 0, 0);
         item.deserialize(serialStr);
         int startingIndex = EquippableItem.numOfSerializedComponents();
         String[] strList = item.getDeserializationArray(serialStr);
         specialAttributes = strList[startingIndex];
         prefixName = strList[startingIndex + 1];
         suffixName = strList[startingIndex + 2];
         category = strList[startingIndex + 3];
         minLevel = Integer.parseInt(strList[startingIndex + 4]);
         maxLevel = Integer.parseInt(strList[startingIndex + 5]);
         weight = Integer.parseInt(strList[startingIndex + 6]);
      }
      catch(Exception ex)
      {
         System.out.println("Error deserializing AffixBase: " + ex.toString());
      }
   }
   
   public void apply(EquippableItem ei, boolean affixType)
   {
      ei.add(item);
      if(affixType == PREFIX)
         ei.setName(prefixName + " " + ei.getName());
      else
         ei.setName(ei.getName() + " " + suffixName);
   }
   
   public boolean conflicts(AffixBase that)
   {
      if(!this.specialAttributes.equals("") &&
         this.specialAttributes.toLowerCase().equals(that.specialAttributes.toLowerCase()))
         return true;
      return false;
   }
}
