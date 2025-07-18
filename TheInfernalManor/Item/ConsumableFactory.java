package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Engine.*;

public class ConsumableFactory implements GUIConstants, ItemConstants
{
   public static Consumable randomConsumable(int level)
   {
      ConsumableBase roll = (ConsumableBase)EngineTools.roll(ConsumableBase.values(), level);
      ItemQuality quality = (ItemQuality)EngineTools.roll(ItemQuality.nonMagicValues(), level);
      Consumable c = new Consumable("temp");
      switch(roll)
      {
         case HEALING_P:   c = getHealingPotion();
                           break;
         case DEFENSE_P:   c = getDefensePotion();
                           break; 
         case OFFENSE_P:   c = getOffensivePotion();
                           break;
      }
      switch(quality)
      {
         case LOW :  c.setName("Weak " + c.getName());
                     c.getStatusEffect().setStartingDuration(c.getStatusEffect().getStartingDuration() / 2);
                     c.setDescription(c.getDescription() + " Lasts half as long as normal.");
                     break; 
         case HIGH : c.setName("Strong " + c.getName());
                     c.getStatusEffect().setStartingDuration(c.getStatusEffect().getStartingDuration() * 2);
                     c.setDescription(c.getDescription() + " Lasts twice as long as normal.");
                     break;
      }
      return c;
   }
   
   private static Consumable getPotionBase(String name)
   {
      Consumable c = new Consumable(name);
      c.getStatusEffect().addEffect(StatusEffect.OngoingEffect.HEALING);
      return c;
   }
   
   public static Consumable getHealingPotion()
   {
      Consumable c = new Consumable("Healing Potion");
      c.setDescription("Heals twices as much as other potions.");
      c.getStatusEffect().setName("Greater Healing");
      c.getStatusEffect().addEffect(StatusEffect.OngoingEffect.GREATER_HEALING);
      return c;
   }
   
   public static Consumable getDefensePotion()
   {
      Consumable c = getPotionBase("Defense Potion");
      c.setDescription("Increases your defenses and heals.");
      c.getStatusEffect().setName("Bulwark");
      c.getStatusEffect().setPhysicalArmor(5);
      c.getStatusEffect().setMagicalArmor(5);
      return c;
   }
   
   public static Consumable getOffensivePotion()
   {
      Consumable c = getPotionBase("Offense Potion");
      c.setDescription("Increases your offenses and heals.");
      c.getStatusEffect().setName("Aggressive");
      c.getStatusEffect().setPhysicalDamage(5);
      c.getStatusEffect().setMagicalDamage(5);
      return c;
   }
   
}