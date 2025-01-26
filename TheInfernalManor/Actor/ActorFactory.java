package TheInfernalManor.Actor;

import TheInfernalManor.GUI.*;
import TheInfernalManor.AI.*;
import TheInfernalManor.Item.*;

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
      a.getInventory().add(WeaponFactory.getDagger());
      a.getInventory().add(WeaponFactory.getSword());
      a.getInventory().add(WeaponFactory.getGreatsword());
      a.getInventory().add(ArmorFactory.getRobes());
      a.getInventory().add(ArmorFactory.getLeatherArmor());
      a.getInventory().add(ArmorFactory.getChainMail());
      a.getInventory().add(ArmorFactory.getPlateMail());
      a.getInventory().add(new Relic("Relic 1"));
      a.getInventory().add(new Relic("Relic 2"));
      a.getInventory().add(new Relic("Relic 3"));
      a.getInventory().add(new Relic("Relic 4"));
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