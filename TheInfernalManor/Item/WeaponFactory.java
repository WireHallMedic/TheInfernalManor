package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class WeaponFactory implements GUIConstants
{
   public static Weapon getDagger()
   {
      Weapon w = new Weapon("Dagger");
      w.setSize(Weapon.LIGHT);
      w.setPhysicalDamage(3);
      return w;
   }
   
   public static Weapon getSword()
   {
      Weapon w = new Weapon("Sword");
      w.setSize(Weapon.MEDIUM);
      w.setPhysicalDamage(6);
      return w;
   }
   
   public static Weapon getGreatsword()
   {
      Weapon w = new Weapon("Greatsword");
      w.setSize(Weapon.HEAVY);
      w.setPhysicalDamage(9);
      return w;
   }
   
}