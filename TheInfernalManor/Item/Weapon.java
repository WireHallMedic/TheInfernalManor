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
   
   @Override
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      String sizeStr = "Weapon Type     ";
      switch(size)
      {
         case LIGHT :   sizeStr += "Light"; break;
         case MEDIUM :  sizeStr += "Medium"; break;
         case HEAVY :   sizeStr += "Heavy"; break;
      }
      strList.insertElementAt(sizeStr, 0);
      return strList;
   }
}