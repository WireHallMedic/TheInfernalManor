package TheInfernalManor.Item;

import TheInfernalManor.Engine.*;

public enum ItemQuality implements Rollable
{
   LOW         (0, 30, 25),
   NORMAL      (0, 30, 100),
   HIGH        (3, 30, 25),
   MAGICAL     (6, 30, 50),
   RARE        (9, 30, 25),
   LEGENDARY   (12, 30, 10);
   
   private int minLevel;
   private int maxLevel;
   private int weight;
   
   public static ItemQuality[] nonMagicValues()
   {
      ItemQuality[] nonMagicList = {LOW, NORMAL, HIGH};
      return nonMagicList;
   }
   
   private ItemQuality(int min, int max, int w)
   {
      minLevel = min;
      maxLevel = max;
      weight = w;
   }
   
   public int getMinLevel(){return minLevel;}
   public int getMaxLevel(){return maxLevel;}
   public int getWeight(){return weight;}
}