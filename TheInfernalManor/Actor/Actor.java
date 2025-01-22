package TheInfernalManor.Actor;

import TheInfernalManor.AI.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.Engine.*;

public class Actor
{
   private String name;
	private int iconIndex;
	private int color;
	private int[] location;
   private BaseAI ai;


	public String getName(){return name;}
	public int getIconIndex(){return iconIndex;}
	public int getColor(){return color;}
   public BaseAI getAI(){return ai;}


	public void setName(String n){name = n;}
	public void setIconIndex(int i){iconIndex = i;}
	public void setColor(int c){color = c;}
   public void setAI(BaseAI newAI){ai = newAI;}

   public Actor(String n, int icon)
   {
      name = n;
      iconIndex = icon;
      color = GUIConstants.WHITE;
      location = new int[2];
      setLocation(-1, -1);
      ai = new BaseAI(this);
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
      return map.canStep(x, y, this) && !GameState.isActorAt(x, y);
   }
   
   public boolean canStep(Direction dir, ZoneMap map)
   {
      int x = getXLocation() + dir.x;
      int y = getYLocation() + dir.y;
      return canStep(x, y, map);
   }
   
   // AI methods
   public boolean hasPlan()
   {
      return ai.hasPlan();
   }
   
   public void plan()
   {
      ai.plan();
   }
   
   public void act()
   {
      ai.act();
   }
   
   
   // execute actions
   public void takeStep(Direction dir)
   {
      int xLoc = getXLocation() + dir.x;
      int yLoc = getYLocation() + dir.y;
      setLocation(xLoc, yLoc);
   }
   
   public void doToggle(Direction dir)
   {
      int xLoc = getXLocation() + dir.x;
      int yLoc = getYLocation() + dir.y;
      GameState.getCurZone().doToggle(xLoc, yLoc);
   }
   
}