package TheInfernalManor.Ability;

public class Ability
{
   public static final boolean PHYSICAL = true;
   public static final boolean MAGICAL = false;
   
	protected String name;
	protected int energyCost;
	protected int rechargeTime;
	protected int chargeLevel;
	protected ActionSpeed speed;
	protected boolean abilityType;


	public String getName(){return name;}
	public int getEnergyCost(){return energyCost;}
	public int getRechargeTime(){return rechargeTime;}
	public int getChargeLevel(){return chargeLevel;}
	public ActionSpeed getSpeed(){return speed;}
	public boolean getAbilityType(){return abilityType;}


	public void setName(String n){name = n;}
	public void setEnergyCost(int e){energyCost = e;}
	public void setRechargeTime(int r){rechargeTime = r;}
	public void setChargeLevel(int c){chargeLevel = c;}
	public void setSpeed(ActionSpeed s){speed = s;}
	public void setAbilityType(boolean a){abilityType = a;}

   
   public Ability(String n)
   {
      name = n;
      energyCost = 0;
      rechargeTime = 0;
      chargeLevel = 0;
      speed = ActionSpeed.NORMAL;
      abilityType = PHYSICAL;
   }
   
   public Ability()
   {
      this("Unknown Ability");
   }
}