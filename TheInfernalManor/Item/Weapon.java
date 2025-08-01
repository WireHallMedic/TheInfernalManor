package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class Weapon extends EquippableItem implements GUIConstants
{
   public static final int LIGHT = 0;
   public static final int MEDIUM = 1;
   public static final int HEAVY = 2;
   
	private int size;
   private int range;


	public int getSize(){return size;}
   public int getRange(){return range;}


	public void setSize(int s){size = s;}
   public void setRange(int r){range = r;}

   
   public Weapon(String n)
   {
      super(n, WEAPON_ICON, WHITE);
      size = MEDIUM;
      range = 1;
   }
   
   public Weapon(Weapon that)
   {
      super(that);
      this.size = that.size;
      this.range = that.range;
   }
   
   public String getSizeString()
   {
      return sizeIntToString(size);
   }
   
   public static String sizeIntToString(int sizeInt)
   {
      switch(sizeInt)
      {
         case LIGHT :   return "Light";
         case MEDIUM :  return "Medium";
         case HEAVY :   return "Heavy";
      }
      return "Unknown Size";
   }
   
   public static int sizeStringToInt(String sizeStr)
   {
      if(sizeStr.toUpperCase().equals("LIGHT")
         || sizeStr.equals("" + LIGHT)) 
         return LIGHT;
      if(sizeStr.toUpperCase().equals("MEDIUM")
         || sizeStr.equals("" + MEDIUM)) 
         return MEDIUM;
      if(sizeStr.toUpperCase().equals("HEAVY")
         || sizeStr.equals("" + HEAVY)) 
         return HEAVY;
      return -1;
   }
   
   @Override
   public void adjustForQuality(ItemQuality quality)
   {
      super.adjustForQuality(quality);
      if(range > 2)
      {
         switch(quality)
         {
            case LOW :        range = Math.max(3, range - 1); break;
            case NORMAL :     break;
            case HIGH :       range += 1; break;
            case MAGICAL :    range += 1; break;
            case RARE :       range += 2; break;
            case LEGENDARY :  range += 3; break;
         }
      }      
      switch(quality)
      {
         case HIGH :       physicalDamage = Math.max(physicalDamage, 1);
                           magicalDamage = Math.max(magicalDamage, 1);
                           break;
         case MAGICAL :    physicalDamage = Math.max(physicalDamage, 2);
                           magicalDamage = Math.max(magicalDamage, 2);
                           break;
         case RARE :       physicalDamage = Math.max(physicalDamage, 4);
                           magicalDamage = Math.max(magicalDamage, 4);
                           break;
         case LEGENDARY :  physicalDamage = Math.max(physicalDamage, 6);
                           magicalDamage = Math.max(magicalDamage, 6);
                           break;
      }
   }
   
   @Override
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      strList.insertElementAt("Weapon Type     " + getSizeString(), 0);
      strList.insertElementAt("Range           " + getRange(), 1);
      return strList;
   }
   
   public Vector<String> getComparisonSummary(Weapon that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      String str = String.format("Weapon Type     %s (%s)", getSizeString(), that.getSizeString());
      strList.insertElementAt(str, 0);
      str = String.format("Range           %s (%s)", getRange(), GUITools.getSignedString(this.getRange() - that.getRange()));
      strList.insertElementAt(str, 1);
      return strList;
   }
   
   public boolean equals(Weapon that)
   {
      return super.equals(that) &&
         this.size == that.size &&
         this.range == that.range;
   }
   
   public static int numOfSerializedComponents()
   {
      return EquippableItem.numOfSerializedComponents() + 2;
   }
   
   public String serialize()
   {
      String str = super.serialize();
      str = str.replace("EQUIPPABLE_ITEM@", "WEAPON@");
      str += getSerializationString(sizeIntToString(size));
      str += getSerializationString(range);
      return str;
   }
   
   public void deserialize(String str)
   {
      super.deserialize(str);
      String[] strList = getDeserializationArray(str);
      int startingIndex = super.numOfSerializedComponents();
      size = sizeStringToInt(strList[startingIndex]);
      range = Integer.parseInt(strList[startingIndex + 1]);
   }
   
   public void setTestingValues()
   {
      super.setTestingValues();
      range = 10;
      size = MEDIUM;
   }
}