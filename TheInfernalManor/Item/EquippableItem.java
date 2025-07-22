package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Ability.*;
import java.util.*;

public class EquippableItem extends StatItem implements GUIConstants, AbilityConstants
{
	protected StatusEffect statusEffect;
	protected double statusEffectChance;
   protected OngoingEffect ongoingEffect;


	public StatusEffect getStatusEffect(){return new StatusEffect(statusEffect);}
	public double getStatusEffectChance(){return statusEffectChance;}
   public OngoingEffect getOngoingEffect(){return ongoingEffect;}


	public void setStatusEffect(StatusEffect s){statusEffect = s;}
	public void setStatusEffectChance(double s){statusEffectChance = s;}
   public void setOngoingEffect(OngoingEffect oe){ongoingEffect = oe;}


   
   public EquippableItem(String name, int icon, int color)
   {
      super(name, icon, color);
      statusEffect = null;
      statusEffectChance = 0.0;
      ongoingEffect = null;
   }
   
   public EquippableItem(EquippableItem that)
   {
      this(that.getName(), that.getIconIndex(), that.getColor());
      this.add(that);
   }
   
   public void add(EquippableItem that)
   {
      super.add(that);
      if(this.statusEffect == null)
      {
         this.statusEffect = that.statusEffect;
         this.statusEffectChance = that.statusEffectChance;
      }
      if(this.ongoingEffect == null)
      {
         this.ongoingEffect = that.ongoingEffect;
      }
   }
   
   public String getStatusEffectString()
   {
      String str = "";
      if(statusEffect != null)
      {
         str += (int)(statusEffectChance * 100) + "% Chance of ";
         str += statusEffect.toString();
      }
      return str;
   }
   
   public String getOngoingEffectString()
   {
      String str = "";
      if(ongoingEffect != null)
      {
         str += ongoingEffect.toString() + " while equipped";
      }
      return str;
   }
   
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      if(statusEffect != null)
         strList.add("Status Effect   " + getStatusEffectString());
      return strList;
   }
   
   public Vector<String> getComparisonSummary(EquippableItem that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      if(that.statusEffect != null)
         strList.add("Status Effect  -" + that.statusEffect);
      if(this.statusEffect != null)
         strList.add("Status Effect  +" + this.statusEffect);
      return strList;
   }
   
   public boolean equals(EquippableItem that)
   {
      return super.equals(that);
   }
   
   public static int numOfSerializedComponents()
   {
      return StatItem.numOfSerializedComponents() + 2;
   }
   
   public String serialize()
   {
      String str = super.serialize();
      str = str.replace("STAT_ITEM@", "EQUIPPABLE_ITEM@");
      if(statusEffect != null)
         str += getSerializationString(statusEffect.toString());
      else
         str += getSerializationString("");
      str += getSerializationString(statusEffectChance);
      return str;
   }
   
   public void deserialize(String str)
   {
      super.deserialize(str);
      String[] strList = getDeserializationArray(str);
      int startingIndex = super.numOfSerializedComponents();
      //statusEffect = strList[startingIndex];
      statusEffectChance = Double.parseDouble(strList[startingIndex + 1]);
   }
   
   public void setTestingValues()
   {
      super.setTestingValues();
   }
}