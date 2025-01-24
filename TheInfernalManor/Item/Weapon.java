package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class Weapon extends Item implements GUIConstants
{
   public static final int LIGHT = 0;
   public static final int MEDIUM = 1;
   public static final int HEAVY = 2;
   
	private int size;


	public int getSize(){return size;}


	public void setSize(int s){size = s;}

   
   public Weapon(String n)
   {
      super(n, WEAPON_ICON, WHITE);
      size = MEDIUM;
      setPhysicalDamage(2);
   }
}