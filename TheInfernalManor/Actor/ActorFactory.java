package TheInfernalManor.Actor;

import TheInfernalManor.GUI.*;
import TheInfernalManor.AI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Ability.*;

public class ActorFactory
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
}