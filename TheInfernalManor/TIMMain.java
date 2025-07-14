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
      System.out.println(WeaponFactory.getDagger().serialize());
      System.out.println(WeaponFactory.getSword().serialize());
      System.out.println(WeaponFactory.getGreatsword().serialize());
      System.out.println(WeaponFactory.getSling().serialize());
      System.out.println(WeaponFactory.getBow().serialize());
      System.out.println(WeaponFactory.getWand().serialize());
      System.out.println(WeaponFactory.getStaff().serialize());
   }
}