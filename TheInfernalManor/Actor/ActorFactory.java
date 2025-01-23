package TheInfernalManor.Actor;

import TheInfernalManor.GUI.*;
import TheInfernalManor.AI.*;

public class ActorFactory
{
   public static Actor getTestPlayer()
   {
      Actor a = new Actor("Player", '@');
      a.setAI(new PlayerAI(a));
      a.getAI().setPlayerControlled(true);
      a.setMaxHealth(64);
      a.setMaxEnergy(10);
      a.setMaxBlock(10);
      a.fullHeal();
      a.setLocation(1, 1);
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