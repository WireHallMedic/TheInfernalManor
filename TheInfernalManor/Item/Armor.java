package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class Armor extends EquippableItem implements GUIConstants
{
   public static final int CLOTH = 0;
   public static final int LIGHT = 1;
   public static final int MEDIUM = 2;
   public static final int HEAVY = 3;
   
	private int weight;


	public int getWeight(){return weight;}


	public void setWeight(int w){weight = w;}

   
   public Armor(String n)
   {
      super(n, ARMOR_ICON, WHITE);
      weight = CLOTH;
   }
   
   public Armor(Armor that)
   {
      super(that);
      this.weight = that.weight;
   }
   
   public String getWeightString()
   {
      return weightIntToString(weight);
   }
   
   public static String weightIntToString(int weightInt)
   {
      switch(weightInt)
      {
         case CLOTH :   return "Cloth";
         case LIGHT :   return "Light";
         case MEDIUM :  return "Medium";
         case HEAVY :   return "Heavy";
      }
      return "Unknown Weight";
   }
   
   public static int weightStringToInt(String weightStr)
   {
      if(weightStr.toUpperCase().equals("CLOTH")
         || weightStr.equals("" + CLOTH)) 
         return CLOTH;
      if(weightStr.toUpperCase().equals("LIGHT")
         || weightStr.equals("" + LIGHT)) 
         return LIGHT;
      if(weightStr.toUpperCase().equals("MEDIUM")
         || weightStr.equals("" + MEDIUM)) 
         return MEDIUM;
      if(weightStr.toUpperCase().equals("HEAVY")
         || weightStr.equals("" + HEAVY)) 
         return HEAVY;
      return -1;
   }
   
   @Override
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      String weightStr = "Weight          " + getWeightString();
      strList.insertElementAt(weightStr, 0);
      return strList;
   }
   
   public Vector<String> getComparisonSummary(Armor that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      String sizeStr = String.format("Weight          %s (%s)", getWeightString(), that.getWeightString());;
      strList.insertElementAt(sizeStr, 0);
      return strList;
   }
   
   public boolean equals(Armor that)
   {
      return super.equals(that) &&
         this.weight == that.weight;
   }
   
   public static int numOfSerializedComponents()
   {
      return EquippableItem.numOfSerializedComponents() + 1;
   }
   
   public String serialize()
   {
      String str = super.serialize();
      str = str.replace("EQUIPPABLE_ITEM@", "ARMOR@");
      str += getSerializationString(weightIntToString(weight));
      return str;
   }
   
   public void deserialize(String str)
   {
      super.deserialize(str);
      String[] strList = getDeserializationArray(str);
      int startingIndex = super.numOfSerializedComponents();
      weight = weightStringToInt(strList[startingIndex]);
   }
   
   public void setTestingValues()
   {
      super.setTestingValues();
      weight = MEDIUM;
   }
   
   @Override
   public void adjustForQuality(ItemQuality quality)
   {
      super.adjustForQuality(quality);
      switch(quality)
      {
         case HIGH :       physicalArmor = Math.max(physicalArmor, 1);
                           magicalArmor = Math.max(magicalArmor, 1);
                           break;
         case MAGICAL :    physicalArmor = Math.max(physicalArmor, 2);
                           magicalArmor = Math.max(magicalArmor, 2);
                           break;
         case RARE :       physicalArmor = Math.max(physicalArmor, 4);
                           magicalArmor = Math.max(magicalArmor, 4);
                           break;
         case LEGENDARY :  physicalArmor = Math.max(physicalArmor, 6);
                           magicalArmor = Math.max(magicalArmor, 6);
                           break;
      }
   }
}