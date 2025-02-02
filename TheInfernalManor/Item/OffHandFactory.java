package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class OffHandFactory implements GUIConstants
{
   public static OffHand getShield()
   {
      OffHand oh = new OffHand("Shield");
      oh.setBlock(5);
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