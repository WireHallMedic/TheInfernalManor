package TheInfernalManor.Ability;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import java.util.*;

public class StatusEffectFactory implements GUIConstants, AbilityConstants
{
   public static StatusEffect getStatusEffect(StatusEffect base, int duration)
   {
//          GREATER_HEALING   ("Greater Healing"),
//       FLEET             ("Fleet"),
//       HASTE             ("Hasted"),
//       FLYING            ("Flying"),
//       SLUGGISH          ("Sluggish"),
//       SLOW              ("Slowed"),
//       BURNING           ("Burning"),
//       POISONED          ("Poisoned");
      return null;
   }
   public static StatusEffect getStatusEffect(StatusEffect base){return getStatusEffect(base, MEDIUM_DURATION);}
}