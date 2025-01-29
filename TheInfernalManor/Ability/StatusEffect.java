package TheInfernalManor.Ability;

import TheInfernalManor.GUI.*;

public class StatusEffect implements GUIConstants, AbilityConstants
{
	private int remainingDuration;
	private int startingDuration;
	private int iconIndex;
	private int color;
	private String name;


	public int getRemainingDuration(){return remainingDuration;}
	public int getStartingDuration(){return startingDuration;}
	public int getIconIndex(){return iconIndex;}
	public int getColor(){return color;}
	public String getName(){return name;}


	public void setRemainingDuration(int r){remainingDuration = r;}
	public void setStartingDuration(int s){startingDuration = s; setRemainingDuration(s);}
	public void setIconIndex(int i){iconIndex = i;}
	public void setColor(int c){color = c;}
	public void setName(String n){name = n;}

   public StatusEffect()
   {
      setStartingDuration(MEDIUM_DURATION);
      setIconIndex('?');
      setColor(WHITE);
      setName("Unknown status effect");
   }
   
   public void increment()
   {
      remainingDuration--;
   }
   
   public boolean isExpired()
   {
      return remainingDuration <= 0;
   }

}