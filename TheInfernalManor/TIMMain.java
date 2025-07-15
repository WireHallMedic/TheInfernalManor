package TheInfernalManor;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Item.*;


public class TIMMain
{
   public static void main(String[] args)
   {
      TIMFrame frame = new TIMFrame();
      GameState gameState = new GameState();
      System.out.println(OffHandFactory.getShield().serialize());
      System.out.println(OffHandFactory.getOrb().serialize());
      System.out.println(OffHandFactory.getTome().serialize());
   }
}