package TheInfernalManor.Ability;

public class Ability
{
   public static final boolean PHYSICAL = true;
   public static final boolean MAGICAL = false;
   
	private String name;
	private int energyCost;
	private int rechargeTime;
	private int chargeLevel;
	private ActionSpeed speed;
	private boolean abilityType;


	public String getName(){return name;}
	public int getEnergyCost(){return energyCost;}
	public int getRechargeTime(){return rechargeTime;}
	public int getChargeLevel(){return chargeLevel;}
	public ActionSpeed getSpeed(){return speed;}
	public boolean isAbilityType(){return abilityType;}


	public void setName(String n){name = n;}
	public void setEnergyCost(int e){energyCost = e;}
	public void setRechargeTime(int r){rechargeTime = r;}
	public void setChargeLevel(int c){chargeLevel = c;}
	public void setSpeed(ActionSpeed s){speed = s;}
	public void setAbilityType(boolean a){abilityType = a;}

   

}