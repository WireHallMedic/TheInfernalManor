package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

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
   }
   
   public String getSizeString()
   {
      switch(size)
      {
         case LIGHT :   return "Light";
         case MEDIUM :  return "Medium";
         case HEAVY :   return "Heavy";
      }
      return "Unknown Size";
   }
   
   @Override
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      String sizeStr = "Weapon Type     " + getSizeString();
      strList.insertElementAt(sizeStr, 0);
      return strList;
   }
   
   public Vector<String> getComparisonSummary(Weapon that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      String sizeStr = String.format("Weapon Type     %s (%s)", getSizeString(), that.getSizeString());;
      strList.insertElementAt(sizeStr, 0);
      return strList;
   }
}