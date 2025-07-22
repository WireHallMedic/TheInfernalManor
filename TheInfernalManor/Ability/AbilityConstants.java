package TheInfernalManor.Ability;

public interface AbilityConstants
{
   public static final boolean PHYSICAL = true;
   public static final boolean MAGICAL = false;
   public static final int USE_WEAPON_RANGE = -1;
   
   public static final int RELIC_DURATION = 1;
   public static final int SHORT_DURATION = 10;
   public static final int MEDIUM_DURATION = 20;
   public static final int LONG_DURATION = 40;
   
   public static final int MAXIMUM_ABILITIES = 10;
   
   public enum EffectShape
   {
      POINT,      // affects single tile
      BEAM,       // affects all tiles in a line
      BLAST,      // affects all tiles in radius of point
      CONE,       // cone that bulges to radius
      AURA;       // affects all tiles in radius of point, excluding point
   }
   
   public enum StatusEffectBase
   {
      GREATER_HEALING   ("Greater Healing"),
      FLEET             ("Fleet"),
      HASTE             ("Hasted"),
      FLYING            ("Flying"),
      SLUGGISH          ("Sluggish"),
      SLOW              ("Slowed"),
      BURNING           ("Burning"),
      POISONED          ("Poisoned");
      
      public String name;
      
      private StatusEffectBase(String n)
      {
         name = n;
      }
   }
}