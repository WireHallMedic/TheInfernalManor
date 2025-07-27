package TheInfernalManor.Ability;

import java.util.*;

public class AttackFactory implements AbilityConstants
{
   public static Vector<Attack> getStartingAttacks()
   {
      Vector<Attack> list = new Vector<Attack>();
      list.add(getBeamAttack());
      list.add(getConeAttack());
      list.add(getBlast());
      list.add(getAuraBlast());
      return list;
   }
   
   public static Attack getBasicAttack()
   {
      Attack a = new Attack("Strike");
      
      a.setPower(1.0);
      a.setBaseDamage(0);
      a.setRange(USE_WEAPON_RANGE);
      a.setEnergyCost(0);
      a.setRechargeTime(0);
      a.setSpeed(ActionSpeed.NORMAL);
      a.setAbilityType(Ability.PHYSICAL);
      a.setShape(Ability.EffectShape.POINT);
      return a;
   }
   
   public static Attack getBeamAttack()
   {
      Attack a = new Attack("Beam");
      
      a.setPower(1.0);
      a.setBaseDamage(5);
      a.setRange(5);
      a.setEnergyCost(5);
      a.setRechargeTime(10);
      a.setSpeed(ActionSpeed.NORMAL);
      a.setAbilityType(Ability.MAGICAL);
      a.setShape(Ability.EffectShape.BEAM);
      return a;
   }
   
   public static Attack getConeAttack()
   {
      Attack a = new Attack("Cone");
      
      a.setPower(1.0);
      a.setBaseDamage(5);
      a.setRange(7);
      a.setEnergyCost(5);
      a.setRechargeTime(10);
      a.setSpeed(ActionSpeed.NORMAL);
      a.setAbilityType(Ability.MAGICAL);
      a.setShape(Ability.EffectShape.CONE);
      a.setRadius(1);
      return a;
   }
   
   public static Attack getBigConeAttack()
   {
      Attack a = new Attack("Big Cone");
      
      a.setPower(1.0);
      a.setBaseDamage(5);
      a.setRange(9);
      a.setEnergyCost(5);
      a.setRechargeTime(10);
      a.setSpeed(ActionSpeed.NORMAL);
      a.setAbilityType(Ability.MAGICAL);
      a.setShape(Ability.EffectShape.CONE);
      a.setRadius(2);
      return a;
   }
   
   public static Attack getBlast()
   {
      Attack a = new Attack("Blast");
      
      a.setPower(1.0);
      a.setBaseDamage(5);
      a.setRange(9);
      a.setEnergyCost(5);
      a.setRechargeTime(10);
      a.setSpeed(ActionSpeed.NORMAL);
      a.setAbilityType(Ability.MAGICAL);
      a.setShape(Ability.EffectShape.BLAST);
      a.setRadius(1);
      return a;
   }
   
   public static Attack getAuraBlast()
   {
      Attack a = new Attack("Aura Blast");
      
      a.setPower(1.0);
      a.setBaseDamage(5);
      a.setRange(0);
      a.setEnergyCost(5);
      a.setRechargeTime(10);
      a.setSpeed(ActionSpeed.NORMAL);
      a.setAbilityType(Ability.MAGICAL);
      a.setShape(Ability.EffectShape.AURA);
      a.setRadius(2);
      return a;
   }
}