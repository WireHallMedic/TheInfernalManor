package TheInfernalManor.Item;

import java.util.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;

public class LootFactory implements GUIConstants
{
   public static Item getRandomLoot(int level)
   {
      ItemRoll roll = (ItemRoll)EngineTools.roll(ItemRoll.values(), level);
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
   
   public static void addLoot(Vector<Actor> aList, int level)
   {
      // tweak later to give conscripts less and leaders more
      for(Actor a : aList)
      {
         if(a == GameState.getPlayerCharacter())
            continue;
         double roll = RNG.nextDouble();
         if(roll >= .5)
            a.getInventory().add(getRandomLoot(level));
         if(roll >= .75)
            a.getInventory().add(getRandomLoot(level));
         
      }
   }
}