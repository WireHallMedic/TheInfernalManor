package TheInfernalManor.Engine;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Map.*;
import java.util.*;

public class Combat
{
   public static boolean attackRoll(Actor attacker, Actor defender, Attack attack)
   {
      return true;
   }
   
   public static int damageRoll(Actor attacker, Actor defender, Attack attack)
   {
      double maxDamage = attacker.getPowerLevel() * attack.getPower();
      double damageRoll = RNG.nextDouble() * .25;
      damageRoll = maxDamage - (maxDamage * damageRoll);
      int damage = (int)Math.round(damageRoll);
      if(attack.getPower() > 0.0)
         damage = Math.max(1, damage);
      return damage;
   }
}