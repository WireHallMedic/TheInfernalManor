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
         case HEALING:           se = new StatusEffect("Healing", HEART_TILE, DARK_RED);
                                 se.addEffect(StatusEffect.OngoingEffect.HEALING);
                                 se.setStartingDuration(duration);
                                 break;
         case GREATER_HEALING:   se = new StatusEffect("Greater Healing", HEART_TILE, RED);
                                 se.addEffect(StatusEffect.OngoingEffect.GREATER_HEALING);
                                 se.setStartingDuration(duration); 
                                 break;
         case FLEET:             se = new StatusEffect("Fleet", '>', YELLOW);
                                 se.addEffect(StatusEffect.OngoingEffect.FLEET);
                                 se.setStartingDuration(duration);
                                 break;
         case HASTE:             se = new StatusEffect("Haste", RIGHT_DOUBLE_ANGLE_TILE, YELLOW);
                                 se.addEffect(StatusEffect.OngoingEffect.HASTE);
                                 se.setStartingDuration(duration);
                                 break;
         case FLYING:            se = new StatusEffect("Flying", UP_TRIANGLE_TILE, BLUE);
                                 se.addEffect(StatusEffect.OngoingEffect.FLYING);
                                 se.setStartingDuration(duration);
                                 break;
         case SLUGGISH:          se = new StatusEffect("Sluggish", '<', YELLOW);
                                 se.addEffect(StatusEffect.OngoingEffect.SLUGGISH);
                                 se.setStartingDuration(duration);
                                 break;
         case SLOW:              se = new StatusEffect("Slowed", LEFT_DOUBLE_ANGLE_TILE, YELLOW);
                                 se.addEffect(StatusEffect.OngoingEffect.SLOW);
                                 se.setStartingDuration(duration);
                                 break;
         case BURNING:           se = new StatusEffect("Burning", CENTERD_CARET_TILE, ORANGE);
                                 se.addEffect(StatusEffect.OngoingEffect.BURNING);
                                 se.setStartingDuration(duration);
                                 break;
         case POISONED:          se = new StatusEffect("Poisoned", HEART_TILE, GREEN);
                                 se.addEffect(StatusEffect.OngoingEffect.POISONED);
                                 se.setStartingDuration(duration);
                                 break;
         case BERSERK:           se = new StatusEffect("Berserk", '!', ORANGE);
                                 se.addEffect(StatusEffect.OngoingEffect.BERSERK);
                                 se.setStartingDuration(duration);
                                 break;
         case CONFUSED:          se = new StatusEffect("CONFUSED", '?', GREEN);
                                 se.addEffect(StatusEffect.OngoingEffect.CONFUSED);
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

   
}