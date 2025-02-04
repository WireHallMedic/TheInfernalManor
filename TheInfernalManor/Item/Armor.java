package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class Armor extends EquippableItem implements GUIConstants
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
   
   public Armor(Armor that)
   {
      super(that);
      this.weight = that.weight;
   }
   
   public String getWeightString()
   {
      switch(weight)
      {
         case CLOTH :   return "Cloth";
         case LIGHT :   return "Light";
         case MEDIUM :  return "Medium";
         case HEAVY :   return "Heavy";
      }
      return "Unknown Weight";
   }
   
   @Override
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      String weightStr = "Weight          " + getWeightString();
      strList.insertElementAt(weightStr, 0);
      return strList;
   }
   
   public Vector<String> getComparisonSummary(Armor that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      String sizeStr = String.format("Weight          %s (%s)", getWeightString(), that.getWeightString());;
      strList.insertElementAt(sizeStr, 0);
      return strList;
   }
   
}