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
   
   public enum OngoingEffect
   {
      HEALING           ("Healing", false),
      GREATER_HEALING   ("Greater Healing", false),
      FLEET             ("Fleet", false),
      HASTE             ("Hasted", false),
      FLYING            ("Flying", false),
      SLUGGISH          ("Sluggish", true),
      SLOW              ("Slowed", true),
      BURNING           ("Burning", true),
      POISONED          ("Poisoned", true),
      BERSERK           ("Berserk", true),
      CONFUSED          ("Consfused", true);
      
      public String name;
      public boolean isHarmful;
      
      private OngoingEffect(String n, boolean h)
      {
         name = n;
         isHarmful = h;
      }
      
      public OngoingEffect getByIdentifier(String n)
      {
         for(OngoingEffect oe : OngoingEffect.values())
            if(oe.toString().equals(n.toUpperCase()))
               return oe;
         return null;
      }
   }

   
   public enum StatusEffectBase
   {
      HEALING           ("Healing"),
      GREATER_HEALING   ("Greater Healing"),
      FLEET             ("Fleet"),
      HASTE             ("Hasted"),
      FLYING            ("Flying"),
      SLUGGISH          ("Sluggish"),
      SLOW              ("Slowed"),
      BURNING           ("Burning"),
      POISONED          ("Poisoned"),
      BERSERK           ("Berserk"),
      CONFUSED          ("Consfused");
      
      private String name;
      
      public String getName(){return name;}
      
      private StatusEffectBase(String n)
      {
         name = n;
      }
   }
}