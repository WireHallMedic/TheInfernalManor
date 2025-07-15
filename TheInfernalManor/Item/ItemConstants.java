package TheInfernalManor.Item;

import TheInfernalManor.Engine.*;
import TheInfernalManor.GUI.*;
import java.util.*;

public interface ItemConstants 
{  
   public enum ArmorBase implements Rollable
   {
      ROBES       (0, 30, 5),
      LEATHER     (0, 30, 10),
      CHAIN_MAIL  (0, 30, 10),
      PLATE_MAIL  (0, 30, 5);
      
      private int minLevel;
      private int maxLevel;
      private int weight;
      
      private ArmorBase(int min, int max, int w)
      {
         minLevel = min;
         maxLevel = max;
         weight = w;
      }
      
      public int getMinLevel(){return minLevel;}
      public int getMaxLevel(){return maxLevel;}
      public int getWeight(){return weight;}
   }
   
   
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
      DAGGER      (0, 30, 5),
      SWORD       (0, 30, 5),
      GREATSWORD  (0, 30, 5),
      SLING       (0, 30, 5),
      BOW         (0, 30, 5),
      WAND        (0, 30, 5),
      STAFF       (0, 30, 5);
      
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
