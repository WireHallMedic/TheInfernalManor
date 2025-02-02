package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class Item extends ForegroundObject implements GUIConstants
{
	private int physicalDamage;
	private int magicalDamage;
	private int physicalArmor;
	private int magicalArmor;
	private int block;
   private int energyRecharge;   // each point of this is worth .25 energy per tick


	public int getPhysicalDamage(){return physicalDamage;}
	public int getMagicalDamage(){return magicalDamage;}
	public int getPhysicalArmor(){return physicalArmor;}
	public int getMagicalArmor(){return magicalArmor;}
	public int getBlock(){return block;}
   public int getEnergyRecharge(){return energyRecharge;}


	public void setPhysicalDamage(int p){physicalDamage = p;}
	public void setMagicalDamage(int m){magicalDamage = m;}
	public void setPhysicalArmor(int p){physicalArmor = p;}
	public void setMagicalArmor(int m){magicalArmor = m;}
	public void setBlock(int b){block = b;}
   public void setEnergyRecharge(int er){energyRecharge = er;}

   
   public Item(String name, int icon, int color)
   {
      super(name, icon, color);
   }
   
   public Item(Item that)
   {
      super(that);
      this.add(that);
   }
   
   public void add(Item that)
   {
      this.physicalDamage += that.physicalDamage;
      this.magicalDamage += that.magicalDamage;
      this.physicalArmor += that.physicalArmor;
      this.magicalArmor += that.magicalArmor;
      this.block += that.block;
      this.energyRecharge += that.energyRecharge;
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
      if(block != 0)
         strList.add("Energy Recharge " + GUITools.getSignedString(energyRecharge));
      return strList;
   }
   
   public Vector<String> getComparisonSummary(Item that)
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
      if(this.energyRecharge != 0.0 ||  that.energyRecharge != 0.0)
      {
         val = this.energyRecharge - that.energyRecharge;
         str = String.format("Magical Damage  %s (%s)", GUITools.getSignedString(energyRecharge), GUITools.getSignedString(val));
         strList.add(str);
      }
      return strList;
   }
}