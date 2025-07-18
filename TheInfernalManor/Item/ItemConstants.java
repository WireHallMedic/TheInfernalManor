package TheInfernalManor.Item;

import TheInfernalManor.Engine.*;
import TheInfernalManor.GUI.*;
import java.util.*;

public interface ItemConstants 
{  
   public enum RelicBase implements Rollable
   {
      HELM     (0, 30, 5),
      GLOVES   (0, 30, 5),
      BOOTS    (0, 30, 5),
      BRACERS  (0, 30, 5),
      AMULET   (0, 30, 5);
      
      private int minLevel;
      private int maxLevel;
      private int weight;
      
      private RelicBase(int min, int max, int w)
      {
         minLevel = min;
         maxLevel = max;
         weight = w;
      }
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
   }
   
   
   public enum ConsumableBase implements Rollable
   {
      HEALING_P         (0, 30, 20),
      DEFENSE_P         (0, 30, 5),
      OFFENSE_P         (0, 30, 5);
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
