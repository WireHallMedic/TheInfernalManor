package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Ability.*;
import StrictCurses.*;

public class Consumable extends Item implements GUIConstants, SCConstants
{
	private StatusEffect statusEffect;


	public StatusEffect getStatusEffect(){return statusEffect;}


	public void setStatusEffect(StatusEffect s){statusEffect = s;}

   
   public Consumable(String name)
   {
      super(name, CONSUMABLE_ICON, WHITE);
      statusEffect = new StatusEffect("Unknown Status Effect", HEART_TILE, RED);
   }
}