package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;

public class RelicFactory implements GUIConstants, ItemConstants
{
   public static Relic randomRelic(int level)
   {
      RelicBase roll = (RelicBase)EngineTools.roll(RelicBase.values(), level);
      Relic r = new Relic("temp");
      switch(roll)
      {
         case HELM:        r = getHelm();
                           break;
         case GLOVES:      r = getGloves();
                           break; 
         case BOOTS:       r = getBoots();
                           break;
         case BRACERS:     r = getBracers();
                           break;
         case AMULET:      r = getAmulet();
                           break;
      }
      return r;
   }
   
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