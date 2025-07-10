package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
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
         case CONSUMABLE   : return new Gold(1 + (RNG.nextInt(level * 10)));
      }
      return null;
   }
}