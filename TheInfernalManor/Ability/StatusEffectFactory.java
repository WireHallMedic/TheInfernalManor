package TheInfernalManor.Ability;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import StrictCurses.*;
import java.util.*;

public class StatusEffectFactory implements GUIConstants, AbilityConstants, SCConstants
{
   public static StatusEffect getStatusEffect(StatusEffectBase base, int duration)
   {
      StatusEffect se = null;
      switch(base)
      {
         case HEALING:           se = new StatusEffect("Healing", OngoingEffect.HEALING);
                                 se.setStartingDuration(duration);
                                 break;
         case GREATER_HEALING:   se = new StatusEffect("Greater Healing", OngoingEffect.GREATER_HEALING);
                                 se.setStartingDuration(duration); 
                                 break;
         case FLEET:             se = new StatusEffect("Fleet", OngoingEffect.FLEET);
                                 se.setStartingDuration(duration);
                                 break;
         case HASTE:             se = new StatusEffect("Haste", OngoingEffect.HASTE);
                                 se.setStartingDuration(duration);
                                 break;
         case FLYING:            se = new StatusEffect("Flying", OngoingEffect.FLYING);
                                 se.setStartingDuration(duration);
                                 break;
         case SLUGGISH:          se = new StatusEffect("Sluggish", OngoingEffect.SLUGGISH);
                                 se.setStartingDuration(duration);
                                 break;
         case SLOW:              se = new StatusEffect("Slowed", OngoingEffect.SLOW);
                                 se.setStartingDuration(duration);
                                 break;
         case BURNING:           se = new StatusEffect("Burning", OngoingEffect.BURNING);
                                 se.setStartingDuration(duration);
                                 break;
         case POISONED:          se = new StatusEffect("Poisoned", OngoingEffect.POISONED);
                                 se.setStartingDuration(duration);
                                 break;
         case BERSERK:           se = new StatusEffect("Berserk", OngoingEffect.BERSERK);
                                 se.setStartingDuration(duration);
                                 break;
         case CONFUSED:          se = new StatusEffect("CONFUSED", OngoingEffect.CONFUSED);
                                 se.setStartingDuration(duration);
                                 break;
         default:                throw new Error("Unrecognized StatusEffectBase :" + base.getName());
      }
      return se;
   }
   public static StatusEffect getStatusEffect(StatusEffectBase base){return getStatusEffect(base, MEDIUM_DURATION);}

   public static StatusEffect getStatusEffectForRelic(StatusEffectBase base)
   {
      return getStatusEffect(base, RELIC_DURATION);
   }
   
   public static StatusEffect getStatusEffectFromSerialization(String str)
   {
      StatusEffectBase base = StatusEffectBase.getByIdentifier(str);
      return getStatusEffect(base);
   }

   
}