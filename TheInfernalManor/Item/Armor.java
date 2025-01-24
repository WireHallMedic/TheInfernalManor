package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class Armor extends Item implements GUIConstants
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
}