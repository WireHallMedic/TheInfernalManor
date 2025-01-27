package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class Weapon extends Item implements GUIConstants
{
   public static final int LIGHT = 0;
   public static final int MEDIUM = 1;
   public static final int HEAVY = 2;
   
	private int size;
   private int range;


	public int getSize(){return size;}
   public int getRange(){return range;}


	public void setSize(int s){size = s;}
   public void setRange(int r){range = r;}

   
   public Weapon(String n)
   {
      super(n, WEAPON_ICON, WHITE);
      size = MEDIUM;
      range = 1;
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
      strList.insertElementAt("Weapon Type     " + getSizeString(), 0);
      strList.insertElementAt("Range           " + getRange(), 1);
      return strList;
   }
   
   public Vector<String> getComparisonSummary(Weapon that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      String str = String.format("Weapon Type     %s (%s)", getSizeString(), that.getSizeString());
      strList.insertElementAt(str, 0);
      str = String.format("Range           %s (%s)", getRange(), GUITools.getSignedString(this.getRange() - that.getRange()));
      strList.insertElementAt(str, 1);
      return strList;
   }
}