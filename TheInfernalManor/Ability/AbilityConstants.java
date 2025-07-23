package TheInfernalManor.Ability;

import TheInfernalManor.GUI.*;
import StrictCurses.*;

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
      HEALING           ("Healing", false, SCConstants.HEART_TILE, GUIConstants.DARK_RED),
      GREATER_HEALING   ("Greater Healing", false, SCConstants.HEART_TILE, GUIConstants.RED),
      FLEET             ("Fleet", false, '>', GUIConstants.YELLOW),
      HASTE             ("Hasted", false, SCConstants.RIGHT_DOUBLE_ANGLE_TILE, GUIConstants.YELLOW),
      FLYING            ("Flying", false, SCConstants.UP_TRIANGLE_TILE, GUIConstants.BLUE),
      SLUGGISH          ("Sluggish", true, '<', GUIConstants.YELLOW),
      SLOW              ("Slowed", true, SCConstants.LEFT_DOUBLE_ANGLE_TILE, GUIConstants.YELLOW),
      BURNING           ("Burning", true, SCConstants.CENTERED_CARET_TILE, GUIConstants.ORANGE),
      POISONED          ("Poisoned", true, SCConstants.HEART_TILE, GUIConstants.GREEN),
      BERSERK           ("Berserk", true, '!', GUIConstants.ORANGE),
      CONFUSED          ("Consfused", true, '?', GUIConstants.GREEN);
      
      public String name;
      public int iconImage;
      public int color;
      public boolean isHarmful;
      
      private OngoingEffect(String n, boolean h, int ii, int c)
      {
         name = n;
         isHarmful = h;
         iconImage = ii;
         color = c;
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
      
      public static StatusEffectBase getByIdentifier(String n)
      {
         for(StatusEffectBase seb : StatusEffectBase.values())
            if(seb.toString().equals(n.toUpperCase()))
               return seb;
         return null;
      }
   }
}