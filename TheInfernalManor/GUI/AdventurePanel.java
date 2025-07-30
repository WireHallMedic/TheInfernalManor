package TheInfernalManor.GUI;

import TheInfernalManor.AI.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Ability.*;
import StrictCurses.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import WidlerSuite.*;

public class AdventurePanel extends JPanel implements GUIConstants, ComponentListener, SwapPanel, KeyListener
{
   public static final int MESSAGE_PANEL_HEIGHT = TILES_TALL - 3 - MAP_PANEL_SIZE;
   public static final int MESSAGE_PANEL_WIDTH = TILES_WIDE - 2;
   public static final int MESSAGE_PANEL_X_ORIGIN = 1;
   public static final int MESSAGE_PANEL_Y_ORIGIN = MAP_PANEL_SIZE + 2;
   public static final int CHARACTER_SUMMARY_PANEL_WIDTH = (TILES_WIDE - 4 - (MAP_PANEL_SIZE * 2)) / 2;
   public static final int CHARACTER_SUMMARY_PANEL_X_ORIGIN = 1;
   public static final int ENVIRONMENT_PANEL_WIDTH = CHARACTER_SUMMARY_PANEL_WIDTH;
   public static final int ENVIRONMENT_PANEL_X_ORIGIN = TILES_WIDE - 1 - CHARACTER_SUMMARY_PANEL_WIDTH;
   public static final int MAP_PANEL_X_ORIGIN = CHARACTER_SUMMARY_PANEL_WIDTH + 2;
   public static final int NORMAL_MODE = 0;
   public static final int ADJACENT_TARGET_MODE = 1;
   public static final int RANGED_TARGET_MODE = 2;
   public static final int LOOK_MODE = 3;
   
   private MapPanel mapPanel;
   private InfoPanel infoPanel;
   private TIMFrame parentFrame;
   private static int mode;
   private static int targetX;
   private static int targetY;
   private static Ability pendingAbility;
   
   public String getPanelName(){return this.getClass().getSimpleName();}
   public static void setMode(int newMode){mode = newMode;}
   
   public static int getMode(){return mode;}
   public static int getTargetX(){return targetX;}
   public static int getTargetY(){return targetY;}
   public static Coord getTarget(){return new Coord(targetX, targetY);}
   public static Ability getPendingAbility(){return pendingAbility;}
   
   public AdventurePanel(SCTilePalette x1y2TilePalette, SCTilePalette x1y1TilePalette, TIMFrame pFrame)
   {
      super();
      setLayout(null);
      parentFrame = pFrame;
      mode = NORMAL_MODE;
      pendingAbility = null;
      infoPanel = new InfoPanel(x1y2TilePalette, pFrame);
      mapPanel = new MapPanel(x1y1TilePalette);
      mapPanel.setVisible(true);
      add(mapPanel);
      infoPanel.setSize(getWidth(), getHeight());
      infoPanel.setLocation(0, 0);
      add(infoPanel);
      
      setBorder();
      addComponentListener(this);
      setMapPanel();
   }
   
   private void centerTarget()
   {
      if(GameState.getPlayerCharacter() != null)
      {
         targetX = GameState.getPlayerCharacter().getXLocation();
         targetY = GameState.getPlayerCharacter().getYLocation();
      }
   }
   
   public static int getPendingRange()
   {
      if(pendingAbility == null)
         return -1;
      int range = pendingAbility.getRange();
      if(range == AbilityConstants.USE_WEAPON_RANGE)
         range = GameState.getPlayerCharacter().getWeapon().getRange();
      return range;
   }
   
   public static ActionType getPendingType()
   {
      if(pendingAbility == null)
         return null;
      if(pendingAbility == GameState.getPlayerCharacter().getBasicAttack())
         return ActionType.BASIC_ATTACK;
      return ActionType.ABILITY;
   }
   
   public static int getPendingIndex()
   {
      Vector<Ability> abilityList = GameState.getPlayerCharacter().getAbilityList();
      for(int i = 0; i < abilityList.size(); i++)
         if(abilityList.elementAt(i) == pendingAbility)
            return i;
      return 0;
   }
   
   // set up to get more info if necessary
   public void selectAbilityToUse(Ability a)
   {
      Actor player = GameState.getPlayerCharacter();
      if(a != player.getBasicAttack())
      {
         if(!a.isCharged())
         {
            MessagePanel.addMessage(a.getName() + " is not yet charged.");
            return;
         }
         else if(a.getEnergyCost() > player.getCurEnergy())
         {
            MessagePanel.addMessage("You do not have enough energy for " + a.getName() + ".");
            return;
         }
      }
      pendingAbility = a;
      if(getPendingRange() == 0)
      {
         setPlan(getPendingType(), null, getPendingIndex());
      }
      if(getPendingRange() == 1)
      {
         mode = ADJACENT_TARGET_MODE;
      }
      else if(getPendingRange() > 1)
      {
         mode = RANGED_TARGET_MODE;
      }
      centerTarget();
   }
   
   // tell player to do the ability
   private void setPlan(ActionType actionType, Direction dir, int index)
   {
      ActionPlan ap = new ActionPlan(actionType, dir);
      if(dir == null)
      {
         ap.setTargetX(targetX);
         ap.setTargetY(targetY);
      }
      if(actionType == ActionType.ABILITY)
         ap.setIndex(getPendingIndex());
      GameState.getPlayerCharacter().getAI().setPendingAction(ap);
      mode = NORMAL_MODE;
   }
   
   private void setBorder()
   {
      int w = infoPanel.getTilesWide();
      int h = infoPanel.getTilesTall();
      boolean[][] borderArr = new boolean[w][h];
      // full border
      GUITools.setBorderBox(0, 0, w, h, borderArr);
      // mapPanel border
      GUITools.setBorderBox(MAP_PANEL_X_ORIGIN - 1, 0, (MAP_PANEL_SIZE * 2) + 2, MAP_PANEL_SIZE + 2, borderArr);
      // messagePanel border
      GUITools.setBorderBox(MESSAGE_PANEL_X_ORIGIN - 1, MESSAGE_PANEL_Y_ORIGIN - 1, 
         MESSAGE_PANEL_WIDTH + 2, MESSAGE_PANEL_HEIGHT + 2, borderArr);
      GUITools.applyBorderTileArray(borderArr, infoPanel);
   }
   
   // listener for basePanel
   public void componentHidden(ComponentEvent e){}
   public void componentMoved(ComponentEvent e){}
   public void componentShown(ComponentEvent e){}
   public void componentResized(ComponentEvent e)
   {
      resize();
   }
   
   public void resize()
   {
      infoPanel.setSize(getWidth(), getHeight());
      setMapPanel();
   }
   
   @Override
   public void paint(Graphics g)
   {
      infoPanel.set();
      setMessagePanel();
      super.paint(g);
   }
   
   @Override
   public void setVisible(boolean v)
   {
      if(v)
      {
         GameState.setGameMode(EngineConstants.ADVENTURE_MODE);
         centerTarget();
      }
      super.setVisible(v);
   }
   
   private void setMapPanel()
   {
      int displayWidth = infoPanel.getWidth() - (infoPanel.getImageXInset() * 2);
      int displayHeight = infoPanel.getHeight() - (infoPanel.getImageYInset() * 2);
      double trueTileWidth = (double)displayWidth / infoPanel.getTilesWide();
      double trueTileHeight = (double)displayHeight / infoPanel.getTilesTall();
      int xInset = (int)(infoPanel.getImageXInset() + (trueTileWidth * MAP_PANEL_X_ORIGIN));
      int yInset = (int)(infoPanel.getImageYInset() + trueTileHeight);
      int width = (int)(trueTileWidth * MAP_PANEL_SIZE * 2);
      int height = (int)(trueTileHeight * MAP_PANEL_SIZE);
      mapPanel.setLocation(xInset, yInset);
      mapPanel.setSize(width, height);
   }
   
   private void setMessagePanel()
   {
      for(int i = 0; i < MESSAGE_PANEL_HEIGHT; i++)
      {
         String str = MessagePanel.getString(i);
         int fg = MessagePanel.getColor(i);
         for(int j = 0; j < MESSAGE_PANEL_WIDTH; j++)
         {
            if(j < str.length())
               infoPanel.setTile(MESSAGE_PANEL_X_ORIGIN + j, MESSAGE_PANEL_Y_ORIGIN + i, str.charAt(j), fg, BLACK);
            else
               infoPanel.setTile(MESSAGE_PANEL_X_ORIGIN + j, MESSAGE_PANEL_Y_ORIGIN + i, ' ', WHITE, BLACK);
         }
      }
   }
   
   private void directionPressed(Direction dir)
   {
      if(mode == NORMAL_MODE)
      {
         setPlan(ActionType.CONTEXTUAL, dir, 0);
      }
      else
      {
         targetX += dir.x;
         targetY += dir.y;
         if(mode == ADJACENT_TARGET_MODE)
         {
            setPlan(getPendingType(), null, getPendingIndex());
         }
      }
   }
   
   private boolean playerStandingOnItem()
   {
      ZoneMap map = GameState.getCurZone();
      int x = GameState.getPlayerCharacter().getXLocation();
      int y = GameState.getPlayerCharacter().getYLocation();
      return map.isItemAt(x, y);
   }
   
   public void keyPressed(KeyEvent ke)
   {
      Actor player = GameState.getPlayerCharacter();
      switch(ke.getKeyCode())
      {
         // directions
         case KeyEvent.VK_UP :      
         case KeyEvent.VK_NUMPAD8 : directionPressed(Direction.NORTH); break;
         case KeyEvent.VK_NUMPAD9 : directionPressed(Direction.NORTHEAST); break;
         case KeyEvent.VK_RIGHT :  
         case KeyEvent.VK_NUMPAD6 : directionPressed(Direction.EAST); break;
         case KeyEvent.VK_NUMPAD3 : directionPressed(Direction.SOUTHEAST); break;
         case KeyEvent.VK_DOWN :  
         case KeyEvent.VK_NUMPAD2 : directionPressed(Direction.SOUTH); break;
         case KeyEvent.VK_NUMPAD1 : directionPressed(Direction.SOUTHWEST); break;
         case KeyEvent.VK_LEFT :  
         case KeyEvent.VK_NUMPAD4 : directionPressed(Direction.WEST); break;
         case KeyEvent.VK_NUMPAD7 : directionPressed(Direction.NORTHWEST); break;
         case KeyEvent.VK_PERIOD :  
         case KeyEvent.VK_NUMPAD5 : directionPressed(Direction.ORIGIN); break;
         
         // specific actions
         case KeyEvent.VK_G :       if(playerStandingOnItem())
                                    {
                                       if(player.getInventory().hasRoom())
                                          player.getAI().setPendingAction(new ActionPlan(ActionType.PICK_UP, Direction.ORIGIN));
                                       else
                                          MessagePanel.addMessage("Your inventory is full.");
                                    }
                                    else
                                    {
                                       MessagePanel.addMessage("Nothing to pick up here.");
                                    }
                                    break;
         case KeyEvent.VK_ESCAPE :  if(mode != NORMAL_MODE)
                                       mode = NORMAL_MODE;
                                    pendingAbility = null;
                                    centerTarget();
                                    break;
         case KeyEvent.VK_L :       if(mode == LOOK_MODE)
                                       mode = NORMAL_MODE;
                                       if(mode == NORMAL_MODE)
                                    {
                                       mode = LOOK_MODE;
                                       centerTarget();
                                    }
                                    break;
         case KeyEvent.VK_A :       if(mode == NORMAL_MODE)
                                    {
                                       selectAbilityToUse(player.getBasicAttack());
                                       centerTarget();
                                    }
                                    break;
         case KeyEvent.VK_X :       if(mode == NORMAL_MODE)
                                    {
                                       MapCell cell = GameState.getCurZone().getTile(player.getLocation());
                                       if(cell instanceof Exit)
                                          GameState.useExit((Exit)cell);
                                       else
                                          MessagePanel.addMessage("You are not standing on an exit.");
                                    }
                                    break;
         case KeyEvent.VK_ENTER :   if(mode == RANGED_TARGET_MODE)
                                    {
                                       setPlan(getPendingType(), null, getPendingIndex());
                                    }
                                    break;
         case KeyEvent.VK_1 :       
         case KeyEvent.VK_2 :       
         case KeyEvent.VK_3 :       
         case KeyEvent.VK_4 :       
         case KeyEvent.VK_5 :       
         case KeyEvent.VK_6 :       
         case KeyEvent.VK_7 :       
         case KeyEvent.VK_8 :       
         case KeyEvent.VK_9 :       
         case KeyEvent.VK_0 :       if(mode == NORMAL_MODE)
                                    {
                                       int index = ke.getKeyCode() - KeyEvent.VK_1;
                                       if(index == -1)
                                          index = 10;
                                       Ability a = player.getAbility(index);
                                       if(a != null)
                                       {
                                          selectAbilityToUse(a);
                                          centerTarget();
                                       }
                                    }
                                    break;
         
         // change screen
         case KeyEvent.VK_P :       parentFrame.setVisiblePanel("PreferencesPanel"); break;
         case KeyEvent.VK_I :       parentFrame.setVisiblePanel("InventoryPanel"); break;
         case KeyEvent.VK_C :       parentFrame.setVisiblePanel("CharacterPanel"); break;
         case KeyEvent.VK_H :       parentFrame.setVisiblePanel("HelpPanel"); break;
         case KeyEvent.VK_M :       parentFrame.setVisiblePanel("ZoneMapPanel"); break;
         
         // testing
         case KeyEvent.VK_BACK_QUOTE : player.add(StatusEffectFactory.getStatusEffect(AbilityConstants.StatusEffectBase.FROZEN, 10));
                                       //player.add(StatusEffectFactory.getStatusEffect(AbilityConstants.StatusEffectBase.POISONED, 10));
                                       break;
      }
   }
   public void keyTyped(KeyEvent ke){}
   public void keyReleased(KeyEvent ke){}
}