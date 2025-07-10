package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;

public class ArmorFactory implements GUIConstants, ItemConstants
{
   public static Armor randomArmor(int level)
   {
      ArmorBase roll = (ArmorBase)EngineTools.roll(ArmorBase.values(), level);
      ItemQuality quality = (ItemQuality)EngineTools.roll(ItemQuality.values(), level);
      Armor a = new Armor("temp");
      switch(roll)
      {
         case ROBES:       a = getRobes();
                           break;
         case LEATHER:     a = getLeatherArmor();
                           break; 
         case CHAIN_MAIL:  a = getChainMail();
                           break;
         case PLATE_MAIL:  a = getPlateMail();
                           break;
      }
      switch(quality)
      {
         case LOW :  a.adjustForQuality(ItemQuality.LOW); break; 
         case HIGH : a.adjustForQuality(ItemQuality.HIGH); break; 
      }
      return a;
   }
   
   public static Armor getRobes()
   {
      Armor a = new Armor("Robes");
      a.setWeight(Armor.CLOTH);
      return a;
   }
   
   public static Armor getLeatherArmor()
   {
      Armor a = new Armor("Leather Armor");
      a.setPhysicalArmor(2);
      a.setWeight(Armor.LIGHT);
      return a;
   }
   
   public static Armor getChainMail()
   {
      Armor a = new Armor("Chain Mail");
      a.setPhysicalArmor(4);
      a.setWeight(Armor.MEDIUM);
      return a;
   }
   
   public static Armor getPlateMail()
   {
      Armor a = new Armor("Plate Mail");
      a.setPhysicalArmor(7);
      a.setWeight(Armor.HEAVY);
      return a;
   }
}