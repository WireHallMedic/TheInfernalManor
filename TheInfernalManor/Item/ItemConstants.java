package TheInfernalManor.Item;

import TheInfernalManor.Engine.*;
import TheInfernalManor.GUI.*;
import java.util.*;

public interface ItemConstants 
{  
   public enum RelicBase implements Rollable
   {
      HELM     (0, 30, 5, Relic.Restriction.HEAD),
      GLOVES   (0, 30, 5, Relic.Restriction.HANDS),
      BOOTS    (0, 30, 5, Relic.Restriction.FEET),
      BRACERS  (0, 30, 5, Relic.Restriction.ARMS),
      JEWELRY  (0, 30, 5, null);
      
      private int minLevel;
      private int maxLevel;
      private int weight;
      private Relic.Restriction restriction;
      
      private RelicBase(int min, int max, int w, Relic.Restriction r)
      {
         minLevel = min;
         maxLevel = max;
         weight = w;
         restriction = r;
      }
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
      public Relic.Restriction getRestriction(){return restriction;}
   }
   
   
   public enum ConsumableBase implements Rollable
   {
      HEALING_P         (0, 30, 10),
      DEFENSE_P         (0, 30, 10),
      OFFENSE_P         (0, 30, 10),
      MOVE_HASTE_P      (0, 30, 10);
//      HASTE_P           (0, 30, 5),
//      FLIGHT_P          (0, 30, 5);
      
      private int minLevel;
      private int maxLevel;
      private int weight;
      
      private ConsumableBase(int min, int max, int w)
      {
         minLevel = min;
         maxLevel = max;
         weight = w;
      }
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
   }
}
