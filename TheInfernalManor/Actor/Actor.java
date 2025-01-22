package TheInfernalManor.Actor;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.Engine.*;

public class Actor
{
   private String name;
	private int iconIndex;
	private int color;
	private int[] location;


	public String getName(){return name;}
	public int getIconIndex(){return iconIndex;}
	public int getColor(){return color;}


	public void setName(String n){name = n;}
	public void setIconIndex(int i){iconIndex = i;}
	public void setColor(int c){color = c;}

   public Actor(String n, int icon)
   {
      name = n;
      iconIndex = icon;
      color = GUIConstants.WHITE;
      location = new int[2];
      setLocation(-1, -1);
   }
   
   public void setLocation(int x, int y)
   {
      location[0] = x;
      location[1] = y;
   }
   
   public int[] getLocation()
   {
      int[] locCopy = {location[0], location[1]};
      return locCopy;
   }
   
   public int getXLocation()
   {
      return location[0];
   }
   
   public int getYLocation()
   {
      return location[1];
   }
   
   public boolean canStep(int x, int y, ZoneMap map)
   {
      return map.getTile(x, y).isLowPassable();
   }
   
   public boolean canStep(Direction dir, ZoneMap map)
   {
      int x = getXLocation() + dir.x;
      int y = getYLocation() + dir.y;
      return canStep(x, y, map);
   }
}