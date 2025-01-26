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
      a.fullHeal();
      a.setLocation(1, 1);
      OffHand shield = new OffHand("Small Shield");
      shield.setBlock(5);
      a.setOffHand(shield);
      a.getInventory().add(new Weapon("Sword"));
      a.getInventory().add(new Weapon("Spear"));
      a.getInventory().add(new Armor("Armor"));
      a.getInventory().add(new Relic("Relic 1"));
      a.getInventory().add(new Relic("Relic 2"));
      a.getInventory().add(new Relic("Relic 3"));
      a.getInventory().add(new Relic("Relic 4"));
      return a;
   }
   
   public static Actor getTestEnemy(int x, int y)
   {
      Actor a = new Actor("Wandering Enemy", 'e');
      a.setAI(new WanderAI(a, .5));
      a.setLocation(x, y);
      return a;
   }
}