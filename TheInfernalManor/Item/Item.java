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


	public int getPhysicalDamage(){return physicalDamage;}
	public int getMagicalDamage(){return magicalDamage;}
	public int getPhysicalArmor(){return physicalArmor;}
	public int getMagicalArmor(){return magicalArmor;}
	public int getBlock(){return block;}


	public void setPhysicalDamage(int p){physicalDamage = p;}
	public void setMagicalDamage(int m){magicalDamage = m;}
	public void setPhysicalArmor(int p){physicalArmor = p;}
	public void setMagicalArmor(int m){magicalArmor = m;}
	public void setBlock(int b){block = b;}

   
   public Item(String name, int icon, int color)
   {
      super(name, icon, color);
   }
   
   public void add(Item that)
   {
      this.physicalDamage += that.physicalDamage;
      this.magicalDamage += that.magicalDamage;
      this.physicalArmor += that.physicalArmor;
      this.magicalArmor += that.magicalArmor;
      this.block += that.block;
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
      return strList;
   }
}