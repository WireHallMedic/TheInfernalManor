package TheInfernalManor.Ability;

public interface AbilityConstants
{
   public static final boolean PHYSICAL = true;
   public static final boolean MAGICAL = false;
   
   public enum EffectShape
   {
      POINT,   // affects single tile
      BEAM,    // affects all tiles in a line
      BLAST,   // affects all tiles in radius of point
      AURA;    // affects all tiles in radius of point, excluding point
   }
}