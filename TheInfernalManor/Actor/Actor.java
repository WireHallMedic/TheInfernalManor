package TheInfernalManor.Actor;

import java.util.*;
import TheInfernalManor.AI.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Ability.*;
import WidlerSuite.Coord;
import WidlerSuite.WSTools;

public class Actor extends ForegroundObject implements ActorConstants, ItemDropper
{
	private int[] location;
   private BaseAI ai;
   private int maxHealth;
   private int curHealth;
   private int maxEnergy;
   private int curEnergy;
   private int energyRecharge;
   private double partialEnergy;
   private int maxGuard;
   private int curGuard;
   private int ticksSinceHit;
   private int physicalDamage;
   private int magicalDamage;
   private int physicalArmor;
   private int magicalArmor;
   private int vision;
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
   private Gold gold;
   private ActorVisualEffect visualEffect;
   private Vector<StatusEffect> seList;
   private EquippableItem baseStats;
   private boolean inTurn;


   public BaseAI getAI(){return ai;}
   public int getMaxHealth(){return maxHealth;}
	public int getCurHealth(){return curHealth;}
	public int getMaxEnergy(){return maxEnergy;}
	public int getCurEnergy(){return curEnergy;}
	public int getEnergyRecharge(){return energyRecharge;}
	public int getMaxGuard(){return maxGuard;}
	public int getCurGuard(){return curGuard;}
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
   public Gold getGold(){return gold;}
   public int getPhysicalDamage(){return physicalDamage;}
	public int getMagicalDamage(){return magicalDamage;}
	public int getPhysicalArmor(){return physicalArmor;}
	public int getMagicalArmor(){return magicalArmor;}
   public Inventory getInventory(){return inventory;}
   public Vector<StatusEffect> getSEList(){return seList;}
   public boolean isInTurn(){return inTurn;}
   public int getVision(){return vision;}
   public EquippableItem getBaseStats(){return baseStats;}


   public void setAI(BaseAI newAI){ai = newAI;}
	public void setCurHealth(int c){curHealth = c;}
	public void setCurEnergy(int c){curEnergy = c;}
	public void setCurGuard(int c){curGuard = c;}
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
   public void setGold(Gold g){gold = g;}
   public void setInventory(Inventory i){inventory = i; i.setOwner(this);}
   public void setVisualEffect(ActorVisualEffect ve){visualEffect = ve;}
   public void setSEList(Vector<StatusEffect> newList){seList = newList;}
   public void setBaseStats(EquippableItem bs){baseStats = bs;}
   

   public Actor(String n, int icon)
   {
      super(n, icon, GUIConstants.WHITE);
      location = new int[2];
      setLocation(-1, -1);
      ai = new BaseAI(this);
      baseStats = new EquippableItem("Base Stats", 0, 0);
      baseStats.setMaxHealth(10);
      baseStats.setMaxEnergy(10);
      baseStats.setVision(10);
      maxHealth = 10;
      maxEnergy = 10;
      moveSpeed = ActionSpeed.NORMAL;
      interactSpeed = ActionSpeed.NORMAL;
      chargeLevel = FULLY_CHARGED;
      powerLevel = 1;
      basicAttack = AttackFactory.getBasicAttack();
      abilityList = new Vector<Ability>();
      naturalWeapon = WeaponFactory.getFist();
      armor = null;
      mainHand = null;
      offHand = null;
      relicList = new Vector<Relic>();
      gold = new Gold(0);
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
      if(GameState.getCurZone() != null)
         VisualEffectFactory.registerMovementEcho(this);
      location[0] = x;
      location[1] = y;
   }
   public void setLocation(Coord c){setLocation(c.x, c.y);}
   
   public Coord getLocation()
   {
      return new Coord(location[0], location[1]);
   }
   
   public int getXLocation()
   {
      return location[0];
   }
   
   public int getYLocation()
   {
      return location[1];
   }
   
   public int distanceTo(int x, int y)
   {
      return WSTools.getAngbandMetric(getXLocation(), getYLocation(), x, y);
   }
   public int distanceTo(Actor a){return distanceTo(a.getXLocation(), a.getYLocation());}
   
   public boolean isAdjacent(int x, int y)
   {
      return WSTools.getAngbandMetric(getXLocation(), getYLocation(), x, y) == 1;
   }
   public boolean isAdjacent(Actor a){return isAdjacent(a.getXLocation(), a.getYLocation());}
   
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
   
   public boolean canUseAbility(int index)
   {
      if(index >= abilityList.size() ||
         !abilityList.elementAt(index).isCharged() ||
         abilityList.elementAt(index).getEnergyCost() > getCurEnergy())
         return false;
      return true;
   }
   
   public void addAbility(Ability a)
   {
      if(abilityList.size() < AbilityConstants.MAXIMUM_ABILITIES)
         abilityList.add(a);
   }
   
   public Ability getPreferredAbility()
   {
      Ability a = getBasicAttack();
      for(int i = abilityList.size() - 1; i > -1; i--)
      {
         if(canUseAbility(i))
            a = getAbility(i);
      }
      return a;
   }
   
   public int getIndex(Ability a)
   {
      for(int i = 0; i < abilityList.size(); i++)
         if(abilityList.elementAt(i) == a)
            return i;
      return -1;
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
      curGuard = maxGuard;
      ticksSinceHit = TICKS_TO_RECOVER_BLOCK;
      for(int i = 0; i < abilityList.size(); i++)
         abilityList.elementAt(i).fullyCharge();
   }
   
   public void applyCombatDamage(int damage, boolean damageType)
   {
      // getting hit resets block clock
      if(damage > 0)
         ticksSinceHit = 0;
         
      // gets through block
      if(damage >= getCurGuard())
      {
         // reduce by block and set block to zero
         damage = damage - getCurGuard();
         setCurGuard(0);
         
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
         setCurGuard(getCurGuard() - damage);
      }
   }
   
   public boolean isDead()
   {
      return getCurHealth() <= 0;
   }
   
   public void die()
   {
      GameState.getCurZone().setDecoration(getXLocation(), getYLocation(), getCorpse());
      if(this != GameState.getPlayerCharacter())
      {
         for(Item i : takeItems())
         {
            GameState.getCurZone().dropItem(i, getXLocation(), getYLocation());
         }
      }
         
      GameState.notifyOfDeath(this);
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
      if(!ai.isPlayerControlled())
      {
         GameState.calcEnemyFoV(this);
      }
      ai.getMemory().update();
   }
   
   public void endTurn()
   {
      ai.getMemory().increment();
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
      EquippableItem sum = new EquippableItem(baseStats);
      sum.add(getWeapon());
      if(armor != null) sum.add(armor);
      if(offHand != null) sum.add(offHand);
      for(Relic relic : relicList)
         sum.add(relic);
      physicalDamage = sum.getPhysicalDamage();
      magicalDamage = sum.getMagicalDamage();
      physicalArmor = sum.getPhysicalArmor();
      magicalArmor = sum.getMagicalArmor();
      maxGuard = sum.getGuard();
      maxHealth = sum.getMaxHealth();
      maxEnergy = sum.getMaxEnergy();
      vision = sum.getVision();
      energyRecharge = sum.getEnergyRecharge() + 2;
      if(maxGuard < curGuard)
         curGuard = maxGuard;
      if(ticksSinceHit >= TICKS_TO_RECOVER_BLOCK)
         curGuard = maxGuard;
   }
   
   // initiative methods
   public boolean isCharged()
   {
      return chargeLevel >= FULLY_CHARGED;
   }
   
   public void charge()
   {
      // increment initiative
      if(chargeLevel < FULLY_CHARGED)
         chargeLevel++;
      
      // increment energy
      partialEnergy += .25 * energyRecharge;
      if(partialEnergy > 1.0)
      {
         int intComponent = (int)partialEnergy;
         curEnergy += intComponent;
         partialEnergy -= (double)intComponent;
         curEnergy = Math.min(maxEnergy, curEnergy);
      }
         
      // increment block
      if(ticksSinceHit < TICKS_TO_RECOVER_BLOCK)
         ticksSinceHit++;
      if(ticksSinceHit >= TICKS_TO_RECOVER_BLOCK)
         curGuard = getMaxGuard();
      
      // increment status effects
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
      
      // increment ability charges
      for(int i = 0; i < abilityList.size(); i++)
         abilityList.elementAt(i).charge();
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
   
   public boolean isEnemy(Actor that)
   {
      return ai.getTeam().isEnemy(that.getAI().getTeam());
   }
   
   public boolean isFriend(Actor that)
   {
      return ai.getTeam().isFriend(that.getAI().getTeam());
   }
   
   
   public boolean canSee(int x, int y)
   {
      if(ai.isPlayerControlled())
         return GameState.playerCanSee(x, y);
      return GameState.getEnemyFoV().isVisible(x, y);
   }
   public boolean canSee(Coord c){return canSee(c.x, c.y);}
   public boolean canSee(Actor a){return canSee(a.getXLocation(), a.getYLocation());}
   
   
   // execute actions
   public void takeStep(Direction dir)
   {
      takeStep(new Coord(getXLocation() + dir.x, getYLocation() + dir.y));
   }
   public void takeStep(Coord c)
   {
      setLocation(c.x, c.y);
   }
   
   public void doToggle(Direction dir)
   {
      doToggle(new Coord(getXLocation() + dir.x, getYLocation() + dir.y));
   }
   public void doToggle(Coord c)
   {
      GameState.getCurZone().doToggle(c.x, c.y);
   }
   
   public void doAttack(Attack attack, int x, int y)
   {
      GameState.resolveAttack(this, attack, x, y);
      if(attack != basicAttack)
      {
         curEnergy -= attack.getEnergyCost();
         attack.discharge();
      }
   }
   public void doAttack(Attack attack, Coord c)
   {
      doAttack(attack, c.x, c.y);
   }
   
   public void pickUp()
   {
      ZoneMap map = GameState.getCurZone();
      Item item = map.getItemAt(getXLocation(), getYLocation());
      map.setItemAt(getXLocation(), getYLocation(), null);
      if(item != null)
      {
         if(item instanceof Gold)
         {
            gold.add((Gold)item);
         }
         else
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
      if(!inventory.hasRoom() && !swapping)
         GameState.getCurZone().dropItem(item, getXLocation(), getYLocation());
      else
         inventory.add(item);
      calcItemStats();
   }
   public void unequipItem(int itemIndex){unequipItem(itemIndex, false);}
   
   
   public void unequipAll()
   {
      if(getMainHand() != null)
         unequipItem(Inventory.MAIN_HAND_SLOT, false);
      if(getOffHand() != null)
         unequipItem(Inventory.OFF_HAND_SLOT, false);
      if(getArmor() != null)
         unequipItem(Inventory.ARMOR_SLOT, false);
      for(int i = 0; i < MAX_RELICS; i++)
         if(getRelic(i) != null)
            unequipItem(Inventory.RELIC_SLOT + i, false);
   }
   
   
   // itemDropper functions
   public Vector<Item> getItems()
   {
      return inventory.getItemList();
   }
   public Vector<Item> takeItems()
   {
      unequipAll();
      Vector<Item> iList = getItems();
      inventory.setItemList(new Vector<Item>());
      return iList;
   }
   public void setItems(Vector<Item> list){inventory.setItemList(list);};
   public void addItem(Item i){inventory.add(i);}
}