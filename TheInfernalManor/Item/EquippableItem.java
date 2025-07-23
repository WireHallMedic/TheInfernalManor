package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Ability.*;
import java.util.*;

public class EquippableItem extends StatItem implements GUIConstants, AbilityConstants
{
	protected StatusEffect procEffect;
	protected double procEffectChance;
   protected OngoingEffect ongoingEffect;


	public double getProcEffectChance(){return procEffectChance;}
   public OngoingEffect getOngoingEffect(){return ongoingEffect;}


	public void setProcEffect(StatusEffect s){procEffect = s;}
	public void setProcEffectChance(double s){procEffectChance = s;}
   public void setOngoingEffect(OngoingEffect oe){ongoingEffect = oe;}


   
   public EquippableItem(String name, int icon, int color)
   {
      super(name, icon, color);
      procEffect = null;
      procEffectChance = 0.0;
      ongoingEffect = null;
   }
   
   public EquippableItem(EquippableItem that)
   {
      this(that.getName(), that.getIconIndex(), that.getColor());
      this.add(that);
   }
   
   
	public StatusEffect getProcEffect()
   {
      if(procEffect == null)
         return null;
      return new StatusEffect(procEffect);
   }
   
   public void add(EquippableItem that)
   {
      super.add(that);
      if(this.procEffect == null)
      {
         this.procEffect = that.procEffect;
         this.procEffectChance = that.procEffectChance;
      }
      if(this.ongoingEffect == null)
      {
         this.ongoingEffect = that.ongoingEffect;
      }
   }
   
   public String getProcEffectString()
   {
      String str = "";
      if(procEffect != null)
      {
         str += (int)(procEffectChance * 100) + "% Chance of ";
         str += procEffect.toString();
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
      if(procEffect != null)
         strList.add("Status Effect   " + getProcEffectString());
      return strList;
   }
   
   public Vector<String> getComparisonSummary(EquippableItem that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      if(that.procEffect != null)
         strList.add("Status Effect  -" + that.procEffect);
      if(this.procEffect != null)
         strList.add("Status Effect  +" + this.procEffect);
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
      if(procEffect != null)
         str += getSerializationString(procEffect.toString());
      else
         str += getSerializationString("");
      str += getSerializationString(procEffectChance);
      return str;
   }
   
   public void deserialize(String str)
   {
      super.deserialize(str);
      String[] strList = getDeserializationArray(str);
      int startingIndex = super.numOfSerializedComponents();
      if(strList[startingIndex].equals(""))
         procEffect = null;
      else
         procEffect = StatusEffectFactory.getStatusEffectFromSerialization(strList[startingIndex]);
      procEffectChance = Double.parseDouble(strList[startingIndex + 1]);
   }
   
   public void setTestingValues()
   {
      super.setTestingValues();
   }
}