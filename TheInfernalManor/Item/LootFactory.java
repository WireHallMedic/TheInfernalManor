package TheInfernalManor.Item;

import java.util.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;

public class LootFactory implements GUIConstants
{
   public static Item getRandomLoot(int level, ItemRoll[] values)
   {
      if(values == null)
         values = ItemRoll.values();
      ItemRoll roll = (ItemRoll)EngineTools.roll(values, level);
      switch(roll)
      {
         case GOLD         : return new Gold(1 + (RNG.nextInt(level * 10)));
         case MAIN_HAND    : return WeaponFactory.randomWeapon(level);
         case OFF_HAND     : return OffHandFactory.randomOffHand(level);
         case ARMOR        : return ArmorFactory.randomArmor(level);
         case RELIC        : return RelicFactory.randomRelic(level);
         case CONSUMABLE   : return ConsumableFactory.randomConsumable(level);
      }
      return null;
   }
   public static Item getRandomLoot(int level){return getRandomLoot(level, null);}
   
   public static void addLoot(Vector<Actor> aList, int level)
   {
      // tweak later to give conscripts less and leaders more
      for(Actor a : aList)
      {
         if(a == GameState.getPlayerCharacter())
            continue;
         int reps = 0;
         switch(a.getQuality())
         {
            // conscripts have a 50% chance of 1 item
            case CONSCRIPT :  if(RNG.nextDouble() <= .5)
                                 a.getInventory().add(getRandomLoot(level));
                              break;
            // regulars have a 25% chance of 1-2 items
            case REGULAR :    if(RNG.nextDouble() <= .75)
                              {
                                 reps = 1 + RNG.nextInt(2);
                                 for(int i = 0; i < reps; i++)
                                    a.getInventory().add(getRandomLoot(level));
                              }
                              break;
            // veterans have 1-3 items
            case VETERAN :    reps = 1 + RNG.nextInt(3);
                              for(int i = 0; i < reps; i++)
                                 a.getInventory().add(getRandomLoot(level));
                              break;
            // elites have 2-4 items
            case ELITE :      reps = 2 + RNG.nextInt(3);
                              for(int i = 0; i < reps; i++)
                                 a.getInventory().add(getRandomLoot(level));
                              break;
            // uniques have 3-5 items, one of which is guaranteed equippable
            case UNIQUE :     a.getInventory().add(getRandomLoot(level, ItemRoll.gearValues()));
                              reps = 2 + RNG.nextInt(3);
                              for(int i = 0; i < reps; i++)
                                 a.getInventory().add(getRandomLoot(level));
                              break;
         }
      }
   }
}