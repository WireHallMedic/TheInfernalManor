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
   public static final int TICKS_TO_RECOVER_BLOCK = 10;
   
	private int[] location;
   private BaseAI ai;
   private int maxHealth;
   private int curHealth;
   private int maxEnergy;
   private int curEnergy;
   private int maxBlock;
   private int curBlock;
   private int ticksSinceHit;
   private int physicalDamage;
   private int magicalDamage;
   private int physicalArmor;
   private int magicalArmor;
   private ActionSpeed moveSpeed;
   private ActionSpeed interactSpeed;
   private int chargeLevel;
   private int powerLevel;
   private Attack basicAttack;
   private Vector<Ability> abilityList;
   private Weapon naturalWeapon;
   private Weapon mainHand;
   private Armor armor;
   private OffHand offHand;
   private Vector<Relic> relicList;
   private Inventory inventory;
   private ActorVisualEffect visualEffect;
   private Vector<StatusEffect> seList;
   private boolean inTurn;


   public BaseAI getAI(){return ai;}
   public int getMaxHealth(){return maxHealth;}
	public int getCurHealth(){return curHealth;}
	public int getMaxEnergy(){return maxEnergy;}
	public int getCurEnergy(){return curEnergy;}
	public int getMaxBlock(){return maxBlock;}
	public int getCurBlock(){return curBlock;}
   public int getTicksSinceHit(){return ticksSinceHit;}
   public ActionSpeed getMoveSpeed(){return moveSpeed;}
   public ActionSpeed getInteractSpeed(){return interactSpeed;}
   public int getChargeLevel(){return chargeLevel;}
   public int getPowerLevel(){return powerLevel;}
   public Attack getBasicAttack(){return basicAttack;}
   public Vector<Ability> getAbilityList(){return abilityList;}
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
   public Vector<StatusEffect> getSEList(){return seList;}
   public boolean isInTurn(){return inTurn;}


   public void setAI(BaseAI newAI){ai = newAI;}
	public void setMaxHealth(int m){maxHealth = m;}
	public void setCurHealth(int c){curHealth = c;}
	public void setMaxEnergy(int m){maxEnergy = m;}
	public void setCurEnergy(int c){curEnergy = c;}
	public void setCurBlock(int c){curBlock = c;}
   public void setTicksSineHit(int tsh){ticksSinceHit = tsh;}
   public void setMoveSpeed(ActionSpeed ms){moveSpeed = ms;}
   public void setInteractSpeed(ActionSpeed is){interactSpeed = is;}
   public void setChargeLevel(int cl){chargeLevel = cl;}
   public void setPowerLevel(int pl){powerLevel = pl;}
   public void setBasicAttack(Attack atk){basicAttack = atk;}
   public void setAbilityList(Vector<Ability> al){abilityList = al;}
   public void setNaturalWeapon(Weapon nw){naturalWeapon = nw; calcItemStats();}
   public void setMainHand(Weapon mh){mainHand = mh; calcItemStats();}
   public void setArmor(Armor a){armor = a; calcItemStats();}
   public void setOffHand(OffHand oh){offHand = oh; calcItemStats();}
   public void setRelicList(Vector<Relic> list){relicList = list; calcItemStats();}
   public void setInventory(Inventory i){inventory = i; i.setOwner(this);}
   public void setVisualEffect(ActorVisualEffect ve){visualEffect = ve;}
   public void setSEList(Vector<StatusEffect> newList){seList = newList;}
   

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
      basicAttack = AttackFactory.getBasicAttack();
      abilityList = new Vector<Ability>();
      Weapon w = new Weapon("Fist");
      naturalWeapon = w;
      armor = null;
      mainHand = null;
      offHand = null;
      relicList = new Vector<Relic>();
      inventory = new Inventory(this);
      visualEffect = null;
      seList = new Vector<StatusEffect>();
      inTurn = false;
      calcItemStats();
      fullHeal();
   }
   
   
   @Override
   public int getColor()
   {
      if(visualEffect != null && visualEffect.hasFGList())
         return visualEffect.getFG();
      return super.getColor();
   }
   
   @Override
   public int getIconIndex()
   {
      if(visualEffect != null && visualEffect.hasIconList())
         return visualEffect.getIcon();
      return super.getIconIndex();
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
   
   public Ability getAbility(int index)
   {
      if(index >= abilityList.size())
         return null;
      return abilityList.elementAt(index);
   }
   
   public void addAbility(Ability a)
   {
      if(abilityList.size() < AbilityConstants.MAXIMUM_ABILITIES)
         abilityList.add(a);
   }
   
   // status effect methods
   public void add(StatusEffect se)
   {
      seList.add(se);
   }
   
   // resource methods
   public void fullHeal()
   {
      curHealth = maxHealth;
      curEnergy = maxEnergy;
      curBlock = maxBlock;
      ticksSinceHit = TICKS_TO_RECOVER_BLOCK;
   }
   
   public void applyCombatDamage(int damage, boolean damageType)
   {
      // getting hit resets block clock
      if(damage > 0)
         ticksSinceHit = 0;
         
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
      
      if(isDead())
         die();
   }
   
   public boolean isDead()
   {
      return getCurHealth() <= 0;
   }
   
   public void die()
   {
      GameState.getCurZone().setDecoration(getXLocation(), getYLocation(), getCorpse());
   }
   
   public ForegroundObject getCorpse()
   {
      return new ForegroundObject(getName() + " Corpse", '%', RED);
   }
   
   public void startTurn()
   {
      if(!inTurn)
      {
         inTurn = true;
      }
   }
   
   public void endTurn()
   {
      inTurn = false;
   }
   
   // item methods
   public Weapon getWeapon()
   {
      if(mainHand == null)
         return naturalWeapon;
      return mainHand;
   }
   
   public void addRelic(Relic r)
   {
      relicList.add(r);
      calcItemStats();
   }
   
   public Relic getRelic(int index)
   {
      if(index >= relicList.size())
         return null;
      return relicList.elementAt(index);
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
      if(maxBlock < curBlock)
         curBlock = maxBlock;
      if(ticksSinceHit >= TICKS_TO_RECOVER_BLOCK)
         curBlock = maxBlock;
   }
   
   // initiative methods
   public boolean isCharged()
   {
      return chargeLevel >= FULLY_CHARGED;
   }
   
   public void charge()
   {
      // charge initiative
      if(chargeLevel < FULLY_CHARGED)
         chargeLevel++;
         
      // manage block
      if(ticksSinceHit < TICKS_TO_RECOVER_BLOCK)
         ticksSinceHit++;
      if(ticksSinceHit >= TICKS_TO_RECOVER_BLOCK)
         curBlock = getMaxBlock();
      
      // manage status effects
      for(int i = 0; i < seList.size(); i++)
      {
         StatusEffect se = seList.elementAt(i);
         se.increment();
         if(se.isExpired())
         {
            seList.removeElementAt(i);
            i--;
         }
      }
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
   
   
   // execute actions
   public void takeStep(Direction dir)
   {
      int xLoc = getXLocation() + dir.x;
      int yLoc = getYLocation() + dir.y;
      setLocation(xLoc, yLoc);
   }
   
   public void doToggle(Direction dir)
   {
      int xLoc = getXLocation() + dir.x;
      int yLoc = getYLocation() + dir.y;
      GameState.getCurZone().doToggle(xLoc, yLoc);
   }
   
   public void doAttack(Attack attack, int x, int y)
   {
      GameState.resolveAttack(this, attack, x, y);
   }
   
   public void pickUp()
   {
      ZoneMap map = GameState.getCurZone();
      Item item = map.getItemAt(getXLocation(), getYLocation());
      map.setItemAt(getXLocation(), getYLocation(), null);
      if(item != null)
      {
         getInventory().add(item);
      }
      if(this == GameState.getPlayerCharacter())
      {
         MessagePanel.addMessage("You picked up a(n) " + item.getName());
      }
   }
   
   public void dropFromInventory(int itemIndex)
   {
      ZoneMap map = GameState.getCurZone();
      if(map.dropItem(inventory.getItemAt(itemIndex), getXLocation(), getYLocation()))
      {
         inventory.removeItemAt(itemIndex);
      }
      else
      {
         MessagePanel.addMessage("No room to drop that here.");
      }
   }
   
   public void equipFromInventory(int itemIndex)
   {
      Item item = inventory.getItemAt(itemIndex);
      inventory.removeItemAt(itemIndex);
      if(item instanceof Weapon)
      {
         if(getMainHand() != null)
            unequipItem(Inventory.MAIN_HAND_SLOT, true);
         Weapon wpn = (Weapon)item;
         setMainHand(wpn);
         // equipping heavy weapon unequips offhand
         if(wpn.getSize() == Weapon.HEAVY)
            if(getOffHand() != null)
               unequipItem(Inventory.OFF_HAND_SLOT, false);
      }
      if(item instanceof OffHand)
      {
         if(getOffHand() != null)
            unequipItem(Inventory.OFF_HAND_SLOT, true);
         setOffHand((OffHand)item);
         // equipping offhand unequips heavy weapon
         if(getMainHand() != null)
            if(getMainHand().getSize() == Weapon.HEAVY)
               unequipItem(Inventory.MAIN_HAND_SLOT, false);
      }
      if(item instanceof Armor)
      {
         if(getArmor() != null)
            unequipItem(Inventory.ARMOR_SLOT, true);
         setArmor((Armor)item);
      }
      if(item instanceof Relic)
      {
         Relic relic = (Relic)item;
         // unequip conflicting relics
         for(int i = 0; i < MAX_RELICS; i++)
            if(relic.conflictsWith(getRelic(i)))
               unequipItem(Inventory.RELIC_SLOT + i, true);
         // unequip last relic if no room
         if(getRelicList().size() == MAX_RELICS)
            unequipItem(Inventory.RELIC_SLOT + MAX_RELICS - 1, true);
         // equip relic
         addRelic((Relic)item);
      }
   }
   
   public void unequipItem(int slot, boolean swapping)
   {
      Item item = null;
      if(slot == Inventory.MAIN_HAND_SLOT)
      {
         item = getMainHand();
         setMainHand(null);
      }
      if(slot == Inventory.OFF_HAND_SLOT)
      {
         item = getOffHand();
         setOffHand(null);
      }
      if(slot == Inventory.ARMOR_SLOT)
      {
         item = getArmor();
         setArmor(null);
      }
      if(slot >= Inventory.RELIC_SLOT)
      {
         item = relicList.elementAt(slot - Inventory.RELIC_SLOT);
         relicList.removeElementAt(slot - Inventory.RELIC_SLOT);
      }
      if(!inventory.hasRoom())
         GameState.getCurZone().dropItem(item, getXLocation(), getYLocation());
      else
         inventory.add(item);
      calcItemStats();
   }
   public void unequipItem(int itemIndex){unequipItem(itemIndex, false);}
   
}