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
      a.getInventory().add(WeaponFactory.getDagger());
      a.getInventory().add(WeaponFactory.getSword());
      a.getInventory().add(WeaponFactory.getGreatsword());
      a.getInventory().add(WeaponFactory.getSling());
      a.getInventory().add(WeaponFactory.getBow());
      a.getInventory().add(ArmorFactory.getRobes());
      a.getInventory().add(ArmorFactory.getLeatherArmor());
      a.getInventory().add(ArmorFactory.getChainMail());
      a.getInventory().add(ArmorFactory.getPlateMail());
      a.getInventory().add(OffHandFactory.getShield());
      a.getInventory().add(OffHandFactory.getOrb());
      a.getInventory().add(OffHandFactory.getTome());
      Relic r = RelicFactory.getHelm();
      r.setName("Circlet");
      r.setMagicalDamage(2);
      a.getInventory().add(r);
      r = RelicFactory.getHelm();
      r.setPhysicalArmor(2);
      a.getInventory().add(r);
      a.getInventory().add(RelicFactory.getBoots());
      a.getInventory().add(RelicFactory.getAmulet());
      a.fullHeal();
      
      a.setBasicAttack(AttackFactory.getBlastAttack());
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