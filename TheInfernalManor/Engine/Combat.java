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
         // hit flash
         if(!defender.isDead())
         {
            ActorVisualEffect hitEffect = new ActorVisualEffect(defender, null, GUITools.getGradient(GUIConstants.RED, defender.getColor(), 12));
            hitEffect.setTicksPerFrame(1);
            AnimationManager.add(hitEffect);
         }
         resolveProcEffects(attacker, defender);
      }
   }
   
   private static void resolveProcEffects(Actor attacker, Actor defender)
   {
      Actor target = null;
      // attacker's weapon
      if(attacker.getWeapon().getProcEffect() != null &&
         RNG.nextDouble() <= attacker.getWeapon().getProcEffectChance())
      {
         StatusEffect se = attacker.getWeapon().getProcEffect();
         if(se.isHarmful())
         {
            defender.add(se);
            target = defender;
         }
         else
         {
            attacker.add(se);
            target = attacker;
         }
         MessagePanel.addMessage(target.getName() + " is " + se.getName() + "!");
      }
      // defender's armor; only works adjacent
      if(defender.getArmor() != null &&
         defender.getArmor().getProcEffect() != null &&
         RNG.nextDouble() <= defender.getArmor().getProcEffectChance() &&
         attacker.isAdjacent(defender))
      {
         StatusEffect se = defender.getArmor().getProcEffect();
         if(se.isHarmful())
         {
            attacker.add(se);
            target = attacker;
         }
         else
         {
            defender.add(se);
            target = defender;
         }
         MessagePanel.addMessage(target.getName() + " is " + se.getName() + "!");
      }
   }
}