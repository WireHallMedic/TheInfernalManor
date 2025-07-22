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
      Relic r = getRelicFromBase(roll);
      ItemQuality quality = (ItemQuality)EngineTools.roll(RELIC_QUALITIES, level);
      if(quality == ItemQuality.RARE)
      {
         AffixBase prefix = EquipmentAffixFactory.getRelicAffix(roll, level);
         while(!prefix.okayForRelic())
            prefix = EquipmentAffixFactory.getRelicAffix(roll, level);
         AffixBase suffix = null;
         while(suffix == null || suffix.conflicts(prefix) || !suffix.okayForRelic())
            suffix = EquipmentAffixFactory.getRelicAffix(roll, level);
         prefix.apply(r, AffixBase.PREFIX);
         suffix.apply(r, AffixBase.SUFFIX);
      }
      else // magical
      {
         AffixBase affix = EquipmentAffixFactory.getRelicAffix(roll, level);
         while(!affix.okayForRelic())
            affix = EquipmentAffixFactory.getRelicAffix(roll, level);
         if(RNG.nextBoolean())
            affix.apply(r, AffixBase.PREFIX);
         else
            affix.apply(r, AffixBase.SUFFIX);
      }
      return r;
   }
   
   public static Relic getRelicFromBase(RelicBase base)
   {
      Relic r = new Relic("New Relic");
      r.setRestriction(base.getRestriction());
      switch(base)
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
   
   public static Relic generateRelic(RelicBase base, AffixBase prefix, AffixBase suffix)
   {
      Relic r = getRelicFromBase(base);
      if(prefix != null)
         prefix.apply(r, AffixBase.PREFIX);
      if(suffix != null)
         suffix.apply(r, AffixBase.SUFFIX);
      return r;
   }
   
}