package TheInfernalManor.Ability;

public class Attack extends Ability
{
   double power;
   int baseDamage;
   
   public double getPower(){return power;}
   public int getBaseDamage(){return baseDamage;}
   
   public void setPower(double p){power = p;}
   public void setBaseDamage(int bd){baseDamage = bd;}
   
   public Attack(String n)
   {
      super(n);
      power = 1.0;
      baseDamage = 0;
   }
   
   public Attack()
   {
      this("Unknown Attack");
   }
}