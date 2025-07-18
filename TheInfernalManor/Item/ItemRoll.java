package TheInfernalManor.Item;

import TheInfernalManor.Engine.*;

public enum ItemRoll implements Rollable
{
   GOLD        (0, 30, 21),
   MAIN_HAND   (0, 30, 5),
   OFF_HAND    (0, 30, 2),
   ARMOR       (0, 30, 3),
   RELIC       (0, 30, 1),
   CONSUMABLE  (0, 30, 10);
   
   private int minLevel;
   private int maxLevel;
   private int weight;
   
   private ItemRoll(int min, int max, int w)
   {
      minLevel = min;
      maxLevel = max;
      weight = w;
   }
   
   public int getMinLevel(){return minLevel;}
   public int getMaxLevel(){return maxLevel;}
   public int getWeight(){return weight;}
}