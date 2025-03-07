package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class EquippableItem extends Item implements GUIConstants
{
	private int physicalDamage;
	private int magicalDamage;
	private int physicalArmor;
	private int magicalArmor;
	private int block;
   private int energyRecharge;   // each point of this is worth .25 energy per tick
   private int maxHealth;
   private int maxEnergy;
   private int vision;


	public int getPhysicalDamage(){return physicalDamage;}
	public int getMagicalDamage(){return magicalDamage;}
	public int getPhysicalArmor(){return physicalArmor;}
	public int getMagicalArmor(){return magicalArmor;}
	public int getBlock(){return block;}
   public int getEnergyRecharge(){return energyRecharge;}
   public int getMaxHealth(){return maxHealth;}
   public int getMaxEnergy(){return maxEnergy;}
   public int getVision(){return vision;}


	public void setPhysicalDamage(int p){physicalDamage = p;}
	public void setMagicalDamage(int m){magicalDamage = m;}
	public void setPhysicalArmor(int p){physicalArmor = p;}
	public void setMagicalArmor(int m){magicalArmor = m;}
	public void setBlock(int b){block = b;}
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
      this.block += that.block;
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
      if(block != 0)
         strList.add("Block           " + GUITools.getSignedString(block));
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
      if(this.block != 0 ||  that.block != 0)
      {
         val = this.block - that.block;
         str = String.format("Block           %s (%s)", GUITools.getSignedString(block), GUITools.getSignedString(val));
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
      block = getAdjusted(block, qualityMod);
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
}