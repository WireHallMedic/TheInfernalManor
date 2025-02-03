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
      a.setMaxHealth(64);
      a.setMaxEnergy(10);
      a.setLocation(1, 1);
      
      a.setMainHand(WeaponFactory.getSword());
      a.setOffHand(OffHandFactory.getShield());
      a.setArmor(ArmorFactory.getChainMail());
      
      a.addAbility(AttackFactory.getAuraBlast());
      a.addAbility(AttackFactory.getBlast());
      a.addAbility(AttackFactory.getConeAttack());
      
      a.getInventory().add(OffHandFactory.getTome());
      Item i = WeaponFactory.getSword();
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
      Actor a = new Actor("Wandering Enemy", 'e');
      a.setAI(new WanderAI(a, .5));
      a.setLocation(x, y);
      a.fullHeal();
      return a;
   }
}