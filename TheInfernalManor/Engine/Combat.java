package TheInfernalManor.Engine;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.GUI.*;
import java.util.*;

public class Combat
{
   public static boolean attackRoll(Actor attacker, Actor defender, Attack attack)
   {
      return true;
   }
   
   public static int damageRoll(Actor attacker, Actor defender, Attack attack)
   {
      int damageCalc = attacker.getPowerLevel() + attack.getBaseDamage();
      if(attack.getAbilityType() == Ability.PHYSICAL)
         damageCalc += attacker.getPhysicalDamage();
      else
         damageCalc += attacker.getMagicalDamage();
      double maxDamage = damageCalc * attack.getPower();
      double damageRoll = RNG.nextDouble() * .25;
      damageRoll = maxDamage - (maxDamage * damageRoll);
      int damage = (int)Math.round(damageRoll);
      if(attack.getPower() > 0.0)
         damage = Math.max(1, damage);
      return damage;
   }
   
   public static void resolveAttack(Actor attacker, Actor defender, Attack attack)
   {
      if(attackRoll(attacker, defender, attack))
      {
         int damage = damageRoll(attacker, defender, attack);
         defender.applyCombatDamage(damage, attack.getAbilityType());
         if(attacker == GameState.getPlayerCharacter() || defender == GameState.getPlayerCharacter());
         {
            String str = String.format("%s strikes %s for %d damage.", attacker.getName(), defender.getName(), damage);
            if(defender.isDead())
               str += String.format(" %s is slain!", defender.getName());
            MessagePanel.addMessage(str);
         }
      }
   }
}