package TheInfernalManor.Ability;

public class Attack extends Ability
{
   double power;
   
   public double getPower(){return power;}
   
   public void setPower(double p){power = p;}
   
   public Attack(String n)
   {
      super(n);
      power = 1.0;
   }
   
   public Attack()
   {
      this("Unknown Attack");
   }
}