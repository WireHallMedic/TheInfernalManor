package TheInfernalManor.Ability;

import TheInfernalManor.Actor.*;

public class Ability implements AbilityConstants
{   
	protected String name;
	protected int energyCost;
	protected int rechargeTime;
	protected int chargeLevel;
	protected ActionSpeed speed;
	protected boolean abilityType;
   protected EffectShape shape;
   protected int range;
   protected int radius;


	public String getName(){return name;}
	public int getEnergyCost(){return energyCost;}
	public int getRechargeTime(){return rechargeTime;}
	public int getChargeLevel(){return chargeLevel;}
	public ActionSpeed getSpeed(){return speed;}
	public boolean getAbilityType(){return abilityType;}
   public EffectShape getShape(){return shape;}
   public int getRange(){return range;}
   public int getRadius(){return radius;}


	public void setName(String n){name = n;}
	public void setEnergyCost(int e){energyCost = e;}
	public void setRechargeTime(int r){rechargeTime = r;}
	public void setChargeLevel(int c){chargeLevel = c;}
	public void setSpeed(ActionSpeed s){speed = s;}
	public void setAbilityType(boolean a){abilityType = a;}
   public void setShape(EffectShape s){shape = s;}
   public void setRange(int r){range = r;}
   public void setRadius(int r){radius = r;}

   
   public Ability(String n)
   {
      name = n;
      energyCost = 0;
      rechargeTime = 0;
      chargeLevel = 0;
      speed = ActionSpeed.NORMAL;
      abilityType = PHYSICAL;
      shape = EffectShape.POINT;
      range = 0;
      radius = 0;
   }
   
   public Ability()
   {
      this("Unknown Ability");
   }
   
   public boolean isCharged()
   {
      return chargeLevel >= rechargeTime;
   }
   
   public void charge()
   {
      if(!isCharged())
         chargeLevel++;
   }
   
   public void fullyCharge()
   {
      chargeLevel = rechargeTime;
   }
   
   public void discharge()
   {
      chargeLevel = 0;
   }
   
   public int getRange(Actor a)
   {
      if(range == USE_WEAPON_RANGE)
         return a.getWeapon().getRange();
      return range;
   }
}