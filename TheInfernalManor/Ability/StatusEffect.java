package TheInfernalManor.Ability;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;

public class StatusEffect extends EquippableItem implements GUIConstants, AbilityConstants
{
	private int startingDuration;
	private int remainingDuration;
	private boolean healsHealth;
	private boolean healsEnergy;


	public int getStartingDuration(){return startingDuration;}
	public int getRemainingDuration(){return remainingDuration;}
	public boolean isHealsHealth(){return healsHealth;}
	public boolean isHealsEnergy(){return healsEnergy;}


	public void setStartingDuration(int s){startingDuration = s;}
	public void setRemainingDuration(int r){remainingDuration = r;}
	public void setHealsHealth(boolean h){healsHealth = h;}
	public void setHealsEnergy(boolean h){healsEnergy = h;}


   public StatusEffect(String name, int icon, int fgColor)
   {
      super(name, icon, fgColor);
      setStartingDuration(MEDIUM_DURATION);
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