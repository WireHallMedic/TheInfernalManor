package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;

public class RelicFactory implements GUIConstants, ItemConstants
{
   private static final String[] JEWELRY_NAMES = {"Ring", "Amulet", "Bracelet", "Gem", "Charm"};
   public static Relic randomRelic(int level)
   {
      RelicBase roll = (RelicBase)EngineTools.roll(RelicBase.values(), level);
      Relic r = new Relic("temp");
      r.setRestriction(roll.getRestriction());
      switch(roll)
      {
         case HELM:        r.setName("Helm");
                           break;
         case GLOVES:      r.setName("Gloves");
                           break; 
         case BOOTS:       r.setName("Boots");
                           break;
         case BRACERS:     r.setName("Bracers");
                           break;
         case JEWELRY:     r.setName(JEWELRY_NAMES[RNG.nextInt(JEWELRY_NAMES.length)]);
                           break;
      }
      return r;
   }
   
}