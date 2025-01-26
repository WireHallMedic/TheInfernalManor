package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class RelicFactory implements GUIConstants
{
   public static Relic getHelm()
   {
      Relic r = new Relic("Helm");
      r.setRestriction(Relic.Restriction.HEAD);
      return r;
   }

   public static Relic getGloves()
   {
      Relic r = new Relic("Gloves");
      r.setRestriction(Relic.Restriction.HANDS);
      return r;
   }

   public static Relic getBoots()
   {
      Relic r = new Relic("Boots");
      r.setRestriction(Relic.Restriction.FEET);
      return r;
   }

   public static Relic getBracers()
   {
      Relic r = new Relic("Bracers");
      r.setRestriction(Relic.Restriction.ARMS);
      return r;
   }
   
   public static Relic getAmulet()
   {
      Relic r = new Relic("Amulet");
      return r;
   }


}