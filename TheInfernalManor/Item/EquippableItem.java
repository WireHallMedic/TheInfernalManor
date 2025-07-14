package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class EquippableItem extends Item implements GUIConstants
{
	private int physicalDamage;
	private int magicalDamage;
	private int physicalArmor;
	private int magicalArmor;
	private int guard;
   private int energyRecharge;   // each point of this is worth .25 energy per tick
   private int maxHealth;
   private int maxEnergy;
   private int vision;


	public int getPhysicalDamage(){return physicalDamage;}
	public int getMagicalDamage(){return magicalDamage;}
	public int getPhysicalArmor(){return physicalArmor;}
	public int getMagicalArmor(){return magicalArmor;}
	public int getGuard(){return guard;}
   public int getEnergyRecharge(){return energyRecharge;}
   public int getMaxHealth(){return maxHealth;}
   public int getMaxEnergy(){return maxEnergy;}
   public int getVision(){return vision;}


	public void setPhysicalDamage(int p){physicalDamage = p;}
	public void setMagicalDamage(int m){magicalDamage = m;}
	public void setPhysicalArmor(int p){physicalArmor = p;}
	public void setMagicalArmor(int m){magicalArmor = m;}
	public void setGuard(int b){guard = b;}
   public void setEnergyRecharge(int er){energyRecharge = er;}
   public void setMaxHealth(int mh){maxHealth = mh;}
   public void setMaxEnergy(int me){maxEnergy = me;}
   public void setVision(int v){vision = v;}

   
   public EquippableItem(String name, int icon, int color)
   {
      super(name, icon, color);
   }
   
   public EquippableItem(EquippableItem that)
   {
      super(that);
      this.add(that);
   }
   
   public void add(EquippableItem that)
   {
      this.physicalDamage += that.physicalDamage;
      this.magicalDamage += that.magicalDamage;
      this.physicalArmor += that.physicalArmor;
      this.magicalArmor += that.magicalArmor;
      this.guard += that.guard;
      this.energyRecharge += that.energyRecharge;
      this.maxHealth += that.maxHealth;
      this.maxEnergy += that.maxEnergy;
      this.vision += that.vision;
   }
   
   public Vector<String> getSummary()
   {
      Vector<String> strList = new Vector<String>();
      if(physicalDamage != 0)
         strList.add("Physical Damage " + GUITools.getSignedString(physicalDamage));
      if(magicalDamage != 0)
         strList.add("Magical Damage  " + GUITools.getSignedString(magicalDamage));
      if(physicalArmor != 0)
         strList.add("Physical Armor  " + GUITools.getSignedString(physicalArmor));
      if(magicalArmor != 0)
         strList.add("Magical Armor   " + GUITools.getSignedString(magicalArmor));
      if(guard != 0)
         strList.add("Guard           " + GUITools.getSignedString(guard));
      if(energyRecharge != 0)
         strList.add("Energy Recharge " + GUITools.getSignedString(energyRecharge));
      if(maxHealth != 0)
         strList.add("Max Health      " + GUITools.getSignedString(maxHealth));
      if(maxEnergy != 0)
         strList.add("Max Energy      " + GUITools.getSignedString(maxEnergy));
      if(vision != 0)
         strList.add("Vision          " + GUITools.getSignedString(vision));
      return strList;
   }
   
   public Vector<String> getComparisonSummary(EquippableItem that)
   {
      Vector<String> strList = new Vector<String>();
      String str = "";
      int val = 0;
      if(this.physicalDamage != 0 ||  that.physicalDamage != 0)
      {
         val = this.physicalDamage - that.physicalDamage;
         str = String.format("Physical Damage %s (%s)", GUITools.getSignedString(physicalDamage), GUITools.getSignedString(val));
         strList.add(str);
      }
      if(this.magicalDamage != 0 ||  that.magicalDamage != 0)
      {
         val = this.magicalDamage - that.magicalDamage;
         str = String.format("Magical Damage  %s (%s)", GUITools.getSignedString(magicalDamage), GUITools.getSignedString(val));
         strList.add(str);
      }
      if(this.physicalArmor != 0 ||  that.physicalArmor != 0)
      {
         val = this.physicalArmor - that.physicalArmor;
         str = String.format("Physical Armor  %s (%s)", GUITools.getSignedString(physicalArmor), GUITools.getSignedString(val));
         strList.add(str);
      }
      if(this.magicalArmor != 0 ||  that.magicalArmor != 0)
      {
         val = this.magicalArmor - that.magicalArmor;
         str = String.format("Magical Armor   %s (%s)", GUITools.getSignedString(magicalArmor), GUITools.getSignedString(val));
         strList.add(str);
      }
      if(this.guard != 0 ||  that.guard != 0)
      {
         val = this.guard - that.guard;
         str = String.format("Guard           %s (%s)", GUITools.getSignedString(guard), GUITools.getSignedString(val));
         strList.add(str);
      }
      if(this.energyRecharge != 0 ||  that.energyRecharge != 0)
      {
         val = this.energyRecharge - that.energyRecharge;
         str = String.format("Magical Damage  %s (%s)", GUITools.getSignedString(energyRecharge), GUITools.getSignedString(val));
         strList.add(str);
      }
      if(this.maxHealth != 0 ||  that.maxHealth != 0)
      {
         val = this.maxHealth - that.maxHealth;
         str = String.format("Max Health      %s (%s)", GUITools.getSignedString(maxHealth), GUITools.getSignedString(val));
         strList.add(str);
      }
      if(this.maxEnergy != 0 ||  that.maxEnergy != 0)
      {
         val = this.maxEnergy - that.maxEnergy;
         str = String.format("Max Energy      %s (%s)", GUITools.getSignedString(maxEnergy), GUITools.getSignedString(val));
         strList.add(str);
      }
      if(this.vision != 0 ||  that.vision != 0)
      {
         val = this.vision - that.vision;
         str = String.format("Vision          %s (%s)", GUITools.getSignedString(vision), GUITools.getSignedString(val));
         strList.add(str);
      }
      return strList;
   }
   
   
   public void adjustForQuality(ItemQuality quality)
   {
      double qualityMod = 1.0;
      switch(quality)
      {
         case LOW :        qualityMod = .75;
                           setName("Low-Quality " + getName());
                           break;
         case NORMAL :     return;
         case HIGH :       qualityMod = 1.25;
                           setName("High-Quality " + getName());
                           break;
         case MAGICAL :    qualityMod = 1.5; break;
         case RARE :       qualityMod = 1.75; break;
         case LEGENDARY :  qualityMod = 2.0; break;
      }
      physicalDamage = getAdjusted(physicalDamage, qualityMod);
      magicalDamage = getAdjusted(magicalDamage, qualityMod);
      physicalArmor = getAdjusted(physicalArmor, qualityMod);
      magicalArmor = getAdjusted(magicalArmor, qualityMod);
      guard = getAdjusted(guard, qualityMod);
      energyRecharge = getAdjusted(energyRecharge, qualityMod);
      maxHealth = getAdjusted(maxHealth, qualityMod);
      maxEnergy = getAdjusted(maxEnergy, qualityMod);
      // vision not adjusted by quality
   }
   
   private int getAdjusted(int base, double mod)
   {
      if(base < 1)
         return base;
      int newVal = 0;
      // adj up
      if(mod > 1.0)
      {
         newVal = (int)(base * mod);
         if(newVal == base)
            newVal = base + 1;
      }
      // adj down
      else if(mod < 1.0)
      {
         newVal = (int)(base * mod);
         if(newVal == base)
            newVal = base - 1;
         if(newVal < 1)
            newVal = 1;
      }
      return newVal;
   }   
   
   public boolean equals(EquippableItem that)
   {
      return super.equals(that) &&
         this.physicalDamage == that.physicalDamage &&
         this.magicalDamage == that.magicalDamage &&
         this.physicalArmor == that.physicalArmor &&
         this.magicalArmor == that.magicalArmor &&
         this.guard == that.guard &&
         this.energyRecharge == that.energyRecharge &&
         this.maxHealth == that.maxHealth &&
         this.maxEnergy == that.maxEnergy &&
         this.vision == that.vision;
   }
   
   public int numOfSerializedComponents()
   {
      return super.numOfSerializedComponents() + 9;
   }
   
   public String serialize()
   {
      String str = super.serialize();
      str = str.replace("ITEM@", "EQUIPPABLE_ITEM@");
      str += getSerializationString(physicalDamage);
      str += getSerializationString(magicalDamage);
      str += getSerializationString(physicalArmor);
      str += getSerializationString(magicalArmor);
      str += getSerializationString(guard);
      str += getSerializationString(energyRecharge);
      str += getSerializationString(maxHealth);
      str += getSerializationString(maxEnergy);
      str += getSerializationString(vision);
      return str;
   }
   
   public void deserialize(String str)
   {
      super.deserialize(str);
      String[] strList = getDeserializationArray(str);
      int startingIndex = super.numOfSerializedComponents();
      physicalDamage = Integer.parseInt(strList[startingIndex]);
      magicalDamage = Integer.parseInt(strList[startingIndex + 1]);
      physicalArmor = Integer.parseInt(strList[startingIndex + 2]);
      magicalArmor = Integer.parseInt(strList[startingIndex + 3]);
      guard = Integer.parseInt(strList[startingIndex + 4]);
      energyRecharge = Integer.parseInt(strList[startingIndex + 5]);
      maxHealth = Integer.parseInt(strList[startingIndex + 6]);
      maxEnergy = Integer.parseInt(strList[startingIndex + 7]);
      vision = Integer.parseInt(strList[startingIndex + 8]);
   }
   
   public void setTestingValues()
   {
      super.setTestingValues();
      physicalDamage = 1;
      magicalDamage = 2;
      physicalArmor = 3;
      magicalArmor = 4;
      guard = 5;
      energyRecharge = 6;
      maxHealth = 7;
      maxEnergy = 8;
      vision = 9;
   }
}