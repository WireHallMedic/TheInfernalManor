package TheInfernalManor.Actor;

import TheInfernalManor.GUI.*;
import TheInfernalManor.AI.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Ability.*;
import java.util.*;

public class ActorFactory implements ActorConstants, GUIConstants, ItemConstants, AbilityConstants
{
   public static Actor getNewPlayer()
   {
      Actor a = new Actor("Player", '@');
      a.setAI(new PlayerAI(a));
      a.getAI().setPlayerControlled(true);
      a.setPowerLevel(1);
      setPlayerHealth(a);
      a.getBaseStats().setMaxEnergy(10);
      a.setMainHand(WeaponFactory.getBase("Dagger"));
      a.fullHeal();
      return a;
   }
   
   public static Vector<Actor> getPopulation(ZoneMap zone, int level)
   {
      Vector<Actor> actorList = new Vector<Actor>();
      for(TIMRoom room : zone.getRoomList())
      {
         Vector<Actor> newEnemies = new Vector<Actor>();
         if(zone.hasEntrance(room))
            continue;
         if(zone.hasChest(room) && RNG.nextDouble() < .9)
            newEnemies = ActorFactory.getBanditGroup(level);
         else if(RNG.nextDouble() < .75)
            newEnemies = ActorFactory.getBanditGroup(level);
         if(newEnemies.size() > 0)
         {
            for(Actor a : newEnemies)
            {
               a.setLocation(room.getCenter());
               actorList.add(a);
            }
         }
      }
      LootFactory.addLoot(actorList, level);
      return actorList;
   }
   
   public static Actor getTestPlayer()
   {
      Actor a = new Actor("Player", '@');
      a.setAI(new PlayerAI(a));
      a.getAI().setPlayerControlled(true);
      a.getBaseStats().setMaxHealth(64);
      a.getBaseStats().setMaxEnergy(10);
      a.setLocation(1, 1);
      
      a.setMainHand(WeaponFactory.getBase("Sword"));
      a.setOffHand(OffHandFactory.getBase("Shield"));
      a.setArmor(ArmorFactory.getBase("Leather Armor"));
      
      a.addAbility(AttackFactory.getAuraBlast());
      a.addAbility(AttackFactory.getBlast());
      a.addAbility(AttackFactory.getConeAttack());
      
      Relic relic = RelicFactory.getRelicFromBase(RelicBase.HELM);
      relic.setOngoingEffect(OngoingEffect.HEALING);
      relic.setName(relic.getName() + " of Regeneration");
      a.getInventory().add(relic);
      
      EquippableItem i = ArmorFactory.getBase("Chain Mail");
      EquipmentAffixFactory.getByPrefix("Toad's").apply(i, AffixBase.PREFIX);
      a.getInventory().add(i);
      
      i = WeaponFactory.getBase("Longbow");
      EquipmentAffixFactory.getByPrefix("Serpent's").apply(i, AffixBase.PREFIX);
      a.getInventory().add(i);
      
      i = WeaponFactory.getBase("Sword");
      EquipmentAffixFactory.getByPrefix("Dragon's").apply(i, AffixBase.PREFIX);
      a.getInventory().add(i);
      
      for(int iter = 0; iter < 5; iter++)
         a.getInventory().add(ConsumableFactory.randomConsumable(5));
      a.getInventory().add(ConsumableFactory.getDefensePotion());
      a.getInventory().add(ConsumableFactory.getFleetnessPotion());
      a.getInventory().add(ConsumableFactory.getHastePotion());
      
      a.fullHeal();
      return a;
   }
      
   public static void setHealthByLevel(Actor a)
   {
      int level = Math.max(a.getPowerLevel(), 0);
      a.getBaseStats().setMaxHealth(5 * (level + 2));
   }
   
   public static void setPlayerHealth(Actor a)
   {
      setHealthByLevel(a);
      a.getBaseStats().setMaxHealth(a.getBaseStats().getMaxHealth() + 10);
   }
   
   public static Actor getWolf(int level)
   {
      Actor a = new Actor("Wolf", 'w');
      a.setColor(LIGHT_GREY);
      a.setPowerLevel(level);
      a.setAI(new StandardAI(a));
      a.setNaturalWeapon(WeaponFactory.getWolfJaws());
      a.setMoveSpeed(ActionSpeed.FAST);
      a.fullHeal();
      return a;
   }
   
   public static Vector<Actor> getBanditGroup(int level)
   {
      Vector<Actor> aList = new Vector<Actor>();
      switch(RNG.nextInt(5))
      {
         case 0 : aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.GRUNT));
                  for(int i = 0; i < level / 4; i++)
                     aList.add(getBandit(level, CombatRole.GRUNT));
                  break;
         case 1 : aList.add(getBandit(level, CombatRole.CONSCRIPT));
                  aList.add(getBandit(level, CombatRole.CONSCRIPT));
                  aList.add(getBandit(level, CombatRole.SCOUT));
                  for(int i = 0; i < level / 5; i++)
                     aList.add(getBandit(level, CombatRole.SCOUT));
                  for(int i = 0; i < level / 5; i++)
                     aList.add(getBandit(level, CombatRole.CONSCRIPT));
                  break;
         case 2 : aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.SOLDIER));
                  for(int i = 0; i < level / 5; i++)
                     aList.add(getBandit(level, CombatRole.SOLDIER));
                  for(int i = 0; i < level / 5; i++)
                     aList.add(getBandit(level, CombatRole.GRUNT));
                  break;
         case 3 : aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.ARCHER));
                  for(int i = 0; i < level / 5; i++)
                     aList.add(getBandit(level, CombatRole.ARCHER));
                  for(int i = 0; i < level / 5; i++)
                     aList.add(getBandit(level, CombatRole.GRUNT));
                  break;
         case 4 : aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.LEADER));
                  for(int i = 0; i < level / 5; i++)
                     aList.add(getBandit(level, CombatRole.LEADER));
                  for(int i = 0; i < level / 5; i++)
                     aList.add(getBandit(level, CombatRole.GRUNT));
                  break;
      }
      return aList;
   }
   
   
   public static Actor getBandit(int level, CombatRole role)
   {
      Actor a = new Actor("Bandit", 'b');
      a.setPowerLevel(level);
      setHealthByLevel(a);
      a.setAI(new StandardAI(a));
      Weapon weapon = WeaponFactory.getBase("Dagger");
      Armor armor = null;
      OffHand offHand = null;
      switch(role)
      {
         case CONSCRIPT : a = getWolf(level);
                        a.setName("Attack Dog");
                        a.setIconIndex('d');
                        a.setQuality(Quality.CONSCRIPT);
                        int health = a.getMaxHealth() / 2;
                        a.getBaseStats().setMaxHealth(health);
                        weapon = null;
                        break;
         case GRUNT :   ;
                        break;
         case SCOUT :   a.setName(a.getName() + " Tracker");
                        a.setColor(GREEN);
                        armor = null;
                        a.setMoveSpeed(ActionSpeed.FAST);
                        a.setQuality(Quality.VETERAN);
                        break;
         case SOLDIER : a.setName(a.getName() + " Enforcer");
                        a.setColor(DARK_RED);
                        weapon = WeaponFactory.getBase("Sword");
                        armor = ArmorFactory.getBase("Leather Armor");
                        offHand = OffHandFactory.getBase("Shield");
                        a.setQuality(Quality.VETERAN);
                        break;
         case ARCHER :  a.setName(a.getName() + " Hunter");
                        a.setColor(DARK_YELLOW);
                        weapon = WeaponFactory.getBase("Sling");
                        a.setQuality(Quality.VETERAN);
                        break;
         case LEADER :  a.setName(a.getName() + " Warlock");
                        a.setColor(LIGHT_PURPLE);
                        weapon = WeaponFactory.getBase("Wand");
                        armor = ArmorFactory.getBase("Robes");
                        a.addAbility(AttackFactory.getBlast());
                        a.setQuality(Quality.VETERAN);
                        break;
      }
      a.setMainHand(weapon);
      a.setOffHand(offHand);
      a.setArmor(armor);
      a.fullHeal();
      return a;
   }
}