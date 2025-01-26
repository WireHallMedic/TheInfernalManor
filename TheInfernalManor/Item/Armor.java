package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

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
   
   @Override
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      String weightStr = "Weight          ";
      switch(weight)
      {
         case CLOTH :   weightStr += "Cloth"; break;
         case LIGHT :   weightStr += "Light"; break;
         case MEDIUM :  weightStr += "Medium"; break;
         case HEAVY :   weightStr += "Heavy"; break;
      }
      strList.insertElementAt(weightStr, 0);
      return strList;
   }
   
}