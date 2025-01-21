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
}