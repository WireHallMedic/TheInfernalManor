package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;

public class RelicFactory implements GUIConstants, ItemConstants
{
   private static final String[] JEWELRY_NAMES = {"Ring", "Amulet", "Bracelet", "Gem", "Charm"};
   public static final ItemQuality[] RELIC_QUALITIES = {ItemQuality.MAGICAL, ItemQuality.RARE, ItemQuality.LEGENDARY};
   
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
      ItemQuality quality = (ItemQuality)EngineTools.roll(RELIC_QUALITIES, level);
      if(quality == ItemQuality.RARE)
      {
         AffixBase prefix = EquipmentAffexFactory.getRelicAffix(roll, level);
         AffixBase suffix = null;
         while(suffix == null || suffix.conflicts(prefix))
            suffix = EquipmentAffexFactory.getRelicAffix(roll, level);
         prefix.apply(r, AffixBase.PREFIX);
         suffix.apply(r, AffixBase.SUFFIX);
      }
      else // magical
      {
         AffixBase affix = EquipmentAffexFactory.getRelicAffix(roll, level);
         if(RNG.nextBoolean())
            affix.apply(r, AffixBase.PREFIX);
         else
            affix.apply(r, AffixBase.SUFFIX);
      }
      return r;
   }
   
}