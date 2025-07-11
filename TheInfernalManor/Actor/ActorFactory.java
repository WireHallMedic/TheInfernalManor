package TheInfernalManor.Actor;

import TheInfernalManor.GUI.*;
import TheInfernalManor.AI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Ability.*;
import java.util.*;

public class ActorFactory implements ActorConstants, GUIConstants
{
   public static Actor getTestPlayer()
   {
      Actor a = new Actor("Player", '@');
      a.setAI(new PlayerAI(a));
      a.getAI().setPlayerControlled(true);
      a.getBaseStats().setMaxHealth(64);
      a.getBaseStats().setMaxEnergy(10);
      a.setLocation(1, 1);
      
      a.setMainHand(WeaponFactory.getSword());
      a.setOffHand(OffHandFactory.getShield());
      a.setArmor(ArmorFactory.getChainMail());
      
      a.addAbility(AttackFactory.getAuraBlast());
      a.addAbility(AttackFactory.getBlast());
      a.addAbility(AttackFactory.getConeAttack());
      
      a.getInventory().add(OffHandFactory.getTome());
      EquippableItem i = WeaponFactory.getSword();
      i.adjustForQuality(ItemQuality.LOW);
      a.getInventory().add(i);
      i = WeaponFactory.getSword();
      i.adjustForQuality(ItemQuality.HIGH);
      a.getInventory().add(i);
      i = WeaponFactory.getBow();
      i.adjustForQuality(ItemQuality.LOW);
      a.getInventory().add(i);
      i = WeaponFactory.getBow();
      i.adjustForQuality(ItemQuality.HIGH);
      a.getInventory().add(i);
      
      a.fullHeal();
      return a;
   }
   
   public static Actor getTestEnemy(int x, int y)
   {
      Actor a = new Actor("Basic Enemy", 'e');
      a.setAI(new StandardAI(a));
      a.setLocation(x, y);
      a.fullHeal();
      return a;
   }
   
   public static Actor getTestZombie(int x, int y)
   {
      Actor a = new Actor("Zombie", 'z');
      a.setAI(new ZombieAI(a));
      a.setLocation(x, y);
      a.fullHeal();
      return a;
   }
   
   public static Actor getTestWizard(int x, int y)
   {
      Actor a = new Actor("Wizard", 'w');
      a.setAI(new StandardAI(a));
      a.addAbility(AttackFactory.getBlast());
      a.setLocation(x, y);
      a.fullHeal();
      return a;
   }
   
   public static void setHealthByLevel(Actor a)
   {
      int level = Math.max(a.getPowerLevel(), 0);
      a.getBaseStats().setMaxHealth(5 * (level + 2));
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
                  break;
         case 1 : aList.add(getBandit(level, CombatRole.CONSCRIPT));
                  aList.add(getBandit(level, CombatRole.CONSCRIPT));
                  aList.add(getBandit(level, CombatRole.SCOUT));
                  break;
         case 2 : aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.SOLDIER));
                  break;
         case 3 : aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.ARCHER));
                  break;
         case 4 : aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.GRUNT));
                  aList.add(getBandit(level, CombatRole.LEADER));
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
      Weapon weapon = WeaponFactory.getDagger();
      Armor armor = null;
      OffHand offHand = null;
      switch(role)
      {
         case CONSCRIPT : a = getWolf(level);
                        a.setName("Attack Dog");
                        a.setIconIndex('d');
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
                        break;
         case SOLDIER : a.setName(a.getName() + " Enforcer");
                        a.setColor(DARK_RED);
                        weapon = WeaponFactory.getSword();
                        armor = ArmorFactory.getLeatherArmor();
                        offHand = OffHandFactory.getShield();
                        break;
         case ARCHER :  a.setName(a.getName() + " Hunter");
                        a.setColor(DARK_YELLOW);
                        weapon = WeaponFactory.getSling();
                        break;
         case LEADER :  a.setName(a.getName() + " Warlock");
                        a.setColor(LIGHT_PURPLE);
                        weapon = WeaponFactory.getWand();
                        armor = ArmorFactory.getRobes();
                        a.addAbility(AttackFactory.getBlast());
                        break;
      }
      a.setMainHand(weapon);
      a.setOffHand(offHand);
      a.setArmor(armor);
      a.fullHeal();
      return a;
   }
}