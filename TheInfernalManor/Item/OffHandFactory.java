package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;

public class OffHandFactory implements GUIConstants, ItemConstants
{
   public static OffHand randomOffHand(int level)
   {
      OffHandBase roll = (OffHandBase)EngineTools.roll(OffHandBase.values(), level);
      ItemQuality quality = (ItemQuality)EngineTools.roll(ItemQuality.values(), level);
      OffHand o = new OffHand("temp");
      switch(roll)
      {
         case SHIELD:      o = getShield();
                           break;
         case ORB:         o = getOrb();
                           break; 
         case TOME:        o = getTome();
                           break;
      }
      switch(quality)
      {
         case LOW :  o.adjustForQuality(ItemQuality.LOW); break; 
         case HIGH : o.adjustForQuality(ItemQuality.HIGH); break; 
      }
      return o;
   }
   
   
   public static OffHand getShield()
   {
      OffHand oh = new OffHand("Shield");
      oh.setGuard(5);
      return oh;
   }
   
   public static OffHand getOrb()
   {
      OffHand oh = new OffHand("Orb");
      oh.setMagicalDamage(4);
      return oh;
   }
   
   public static OffHand getTome()
   {
      OffHand oh = new OffHand("Tome");
      oh.setEnergyRecharge(2);
      oh.setMagicalArmor(3);
      return oh;
   }
   
}