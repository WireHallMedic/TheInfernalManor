package TheInfernalManor.Actor;

import java.util.*;
import TheInfernalManor.AI.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Ability.*;

public class Actor extends ForegroundObject
{
   public static final int FULLY_CHARGED = 10;
   public static final int MAX_RELICS = 3;
   
	private int[] location;
   private BaseAI ai;
   private int maxHealth;
   private int curHealth;
   private int maxEnergy;
   private int curEnergy;
   private int maxBlock;
   private int curBlock;
   private int physicalDamage;
   private int magicalDamage;
   private int physicalArmor;
   private int magicalArmor;
   private ActionSpeed moveSpeed;
   private ActionSpeed interactSpeed;
   private int chargeLevel;
   private int powerLevel;
   private Attack basicAttack;
   private Weapon naturalWeapon;
   private Weapon mainHand;
   private Armor armor;
   private OffHand offHand;
   private Vector<Relic> relicList;
   private Inventory inventory;


   public BaseAI getAI(){return ai;}
   public int getMaxHealth(){return maxHealth;}
	public int getCurHealth(){return curHealth;}
	public int getMaxEnergy(){return maxEnergy;}
	public int getCurEnergy(){return curEnergy;}
	public int getMaxBlock(){return maxBlock;}
	public int getCurBlock(){return curBlock;}
   public ActionSpeed getMoveSpeed(){return moveSpeed;}
   public ActionSpeed getInteractSpeed(){return interactSpeed;}
   public int getChargeLevel(){return chargeLevel;}
   public int getPowerLevel(){return powerLevel;}
   public Attack getBasicAttack(){return basicAttack;}
   public Weapon getNaturalWeapon(){return naturalWeapon;}
   public Weapon getMainHand(){return mainHand;}
   public Armor getArmor(){return armor;}
   public OffHand getOffHand(){return offHand;}
   public Vector<Relic> getRelicList(){return relicList;}
   public int getPhysicalDamage(){return physicalDamage;}
	public int getMagicalDamage(){return magicalDamage;}
	public int getPhysicalArmor(){return physicalArmor;}
	public int getMagicalArmor(){return magicalArmor;}
   public Inventory getInventory(){return inventory;}


   public void setAI(BaseAI newAI){ai = newAI;}
	public void setMaxHealth(int m){maxHealth = m;}
	public void setCurHealth(int c){curHealth = c;}
	public void setMaxEnergy(int m){maxEnergy = m;}
	public void setCurEnergy(int c){curEnergy = c;}
	public void setCurBlock(int c){curBlock = c;}
   public void setMoveSpeed(ActionSpeed ms){moveSpeed = ms;}
   public void setInteractSpeed(ActionSpeed is){interactSpeed = is;}
   public void setChargeLevel(int cl){chargeLevel = cl;}
   public void setPowerLevel(int pl){powerLevel = pl;}
   public void setBasicAttack(Attack atk){basicAttack = atk;}
   public void setNaturalWeapon(Weapon nw){naturalWeapon = nw; calcItemStats();}
   public void setMainHand(Weapon mh){mainHand = mh; calcItemStats();}
   public void setArmor(Armor a){armor = a; calcItemStats();}
   public void setOffHand(OffHand oh){offHand = oh; calcItemStats();}
   public void setRelicList(Vector<Relic> list){relicList = list; calcItemStats();}
   public void setInventory(Inventory i){inventory = i; i.setOwner(this);}
   

   public Actor(String n, int icon)
   {
      super(n, icon, GUIConstants.WHITE);
      location = new int[2];
      setLocation(-1, -1);
      ai = new BaseAI(this);
      maxHealth = 10;
      maxEnergy = 10;
      moveSpeed = ActionSpeed.NORMAL;
      interactSpeed = ActionSpeed.NORMAL;
      chargeLevel = FULLY_CHARGED;
      powerLevel = 1;
      basicAttack = new Attack("Strike");
      Weapon w = new Weapon("Fist");
      naturalWeapon = w;
      armor = null;
      mainHand = null;
      offHand = null;
      relicList = new Vector<Relic>();
      inventory = new Inventory(this);
      calcItemStats();
      fullHeal();
   }
   
   public void setLocation(int x, int y)
   {
      location[0] = x;
      location[1] = y;
   }
   
   public int[] getLocation()
   {
      int[] locCopy = {location[0], location[1]};
      return locCopy;
   }
   
   public int getXLocation()
   {
      return location[0];
   }
   
   public int getYLocation()
   {
      return location[1];
   }
   
   public boolean canStep(int x, int y, ZoneMap map)
   {
      return map.canStep(x, y, this) && !GameState.isActorAt(x, y);
   }
   
   public boolean canStep(Direction dir, ZoneMap map)
   {
      int x = getXLocation() + dir.x;
      int y = getYLocation() + dir.y;
      return canStep(x, y, map);
   }
   
   // resource methods
   public void fullHeal()
   {
      curHealth = maxHealth;
      curEnergy = maxEnergy;
      curBlock = maxBlock;
   }
   
   public void applyCombatDamage(int damage, boolean damageType)
   {
      // gets through block
      if(damage >= getCurBlock())
      {
         // reduce by block and set block to zero
         damage = damage - getCurBlock();
         setCurBlock(0);
         
         // apply armor
         if(getArmor() != null && damage > 0)
         {
            if(damageType == Ability.PHYSICAL)
               damage -= getPhysicalArmor();
            else
               damage -= getMagicalArmor();
            damage = Math.max(damage, 1);
         }
         
         // take damage
         setCurHealth(getCurHealth() - damage);
      }
      // does not get through block
      else
      {
         setCurBlock(getCurBlock() - damage);
      }
   }
   
   public boolean isDead()
   {
      return getCurHealth() <= 0;
   }
   
   public void die()
   {
      ; // no death effects yet implemented
   }
   
   // item methods
   public Weapon getWeapon()
   {
      if(mainHand == null)
         return naturalWeapon;
      return mainHand;
   }
   
   public void calcItemStats()
   {
      Item sum = new Item("", ' ', 0);
      sum.add(getWeapon());
      if(armor != null) sum.add(armor);
      if(offHand != null) sum.add(offHand);
      for(Item relic : relicList)
         sum.add(relic);
      physicalDamage = sum.getPhysicalDamage();
      magicalDamage = sum.getMagicalDamage();
      physicalArmor = sum.getPhysicalArmor();
      magicalArmor = sum.getMagicalArmor();
      maxBlock = sum.getBlock();
   }
   
   // initiative methods
   public boolean isCharged()
   {
      return chargeLevel >= FULLY_CHARGED;
   }
   
   public void charge()
   {
      if(chargeLevel < FULLY_CHARGED)
         chargeLevel++;
   }
   
   public void discharge(ActionSpeed speed)
   {
      chargeLevel -= speed.ticks;
   }
   
   // AI methods
   public boolean hasPlan()
   {
      return ai.hasPlan();
   }
   
   public void plan()
   {
      ai.plan();
   }
   
   public void act()
   {
      ai.act();
   }
   
   
   // execute actions or delay
   public void takeStep(Direction dir)
   {
      int xLoc = getXLocation() + dir.x;
      int yLoc = getYLocation() + dir.y;
      setLocation(xLoc, yLoc);
      discharge(getMoveSpeed());
   }
   
   public void doToggle(Direction dir)
   {
      int xLoc = getXLocation() + dir.x;
      int yLoc = getYLocation() + dir.y;
      GameState.getCurZone().doToggle(xLoc, yLoc);
      discharge(getInteractSpeed());
   }
   
   public void doAttack(Attack attack, int x, int y)
   {
      Actor defender = GameState.getActorAt(x, y);
      if(defender != null)
      {
         Combat.resolveAttack(this, defender, attack);
      }
      discharge(attack.getSpeed());
   }
   
   public void pickUp()
   {
      ZoneMap map = GameState.getCurZone();
      Item item = map.getItemAt(getXLocation(), getYLocation());
      map.setItemAt(getXLocation(), getYLocation(), null);
      if(item != null)
      {
         getInventory().add(item);
         discharge(getInteractSpeed());
      }
      if(this == GameState.getPlayerCharacter())
      {
         MessagePanel.addMessage("You picked up a(n) " + item.getName());
      }
   }
   
}