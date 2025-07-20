package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Ability.*;
import java.util.*;

public class EquippableItem extends StatItem implements GUIConstants
{
	protected double statusEffectChance;
	protected StatusEffect statusEffect;

   
   public EquippableItem(String name, int icon, int color)
   {
      super(name, icon, color);
   }
   
   public EquippableItem(EquippableItem that)
   {
      this(that.getName(), that.getIconIndex(), that.getColor());
      this.add(that);
   }
   
   public void add(EquippableItem that)
   {
      super.add(that);
   }
   
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      return strList;
   }
   
   public Vector<String> getComparisonSummary(EquippableItem that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      return strList;
   }
   
   public boolean equals(EquippableItem that)
   {
      return super.equals(that);
   }
   
   public static int numOfSerializedComponents()
   {
      return StatItem.numOfSerializedComponents();
   }
   
   public String serialize()
   {
      String str = super.serialize();
      str = str.replace("STAT_ITEM@", "EQUIPPABLE_ITEM@");
   //    str += getSerializationString(physicalDamage);
//       str += getSerializationString(magicalDamage);
      return str;
   }
   
   public void deserialize(String str)
   {
      super.deserialize(str);
      String[] strList = getDeserializationArray(str);
      int startingIndex = super.numOfSerializedComponents();
   }
   
   public void setTestingValues()
   {
      super.setTestingValues();
   }
}