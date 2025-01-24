package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import java.util.*;

public class Inventory
{
	private Actor owner;
	private Vector<Item> itemList;
   public static final int MAXIMUM_SIZE = 25;


	public Actor getOwner(){return owner;}
	public Vector<Item> getItemList(){return itemList;}


	public void setOwner(Actor o){owner = o;}
	public void setItemList(Vector<Item> i){itemList = i;}

   
   public Inventory(Actor o)
   {
      owner = o;
      itemList = new Vector<Item>();
   }
   
   public void add(Item i)
   {
      itemList.add(i);
   }
   
   public boolean hasRoom()
   {
      return itemList.size() < MAXIMUM_SIZE;
   }
   
   

}