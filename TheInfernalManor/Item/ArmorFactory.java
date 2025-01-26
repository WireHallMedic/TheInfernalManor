package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class ArmorFactory implements GUIConstants
{
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