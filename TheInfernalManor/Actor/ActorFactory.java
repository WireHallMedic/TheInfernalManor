package TheInfernalManor.Actor;

import TheInfernalManor.GUI.*;

public class ActorFactory
{
   public static Actor getTestPlayer()
   {
      Actor a = new Actor("Player", '@');
      a.setLocation(1, 1);
      return a;
   }
   
   public static Actor getTestEnemy(int x, int y)
   {
      Actor a = new Actor("Enemy", 'e');
      a.setLocation(x, y);
      return a;
   }
}