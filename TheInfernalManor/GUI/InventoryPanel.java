package TheInfernalManor.GUI;

import TheInfernalManor.AI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Engine.*;
import StrictCurses.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class InventoryPanel extends TIMPanel implements GUIConstants
{
   private static final int SIDE_WIDTH = (TILES_WIDE - 3) / 2;
   private static final int LEFT_PANEL_X_ORIGIN = 1;
   private static final int RIGHT_PANEL_X_ORIGIN = 3 + SIDE_WIDTH;
   public static final int MAX_SUMMARY_LINES = 8;
   public static final int STANDARD_MODE = 0;
   public static final int UNEQUIP_MODE = 1;
   public static final int EQUIPPED_SLOTS = 3 + Actor.MAX_RELICS;
   
   private int curIndex;
   private int mode;
   private boolean onLeft;
   
   public InventoryPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Inventory ");
      curIndex = -1;
      onLeft = true;
      mode = STANDARD_MODE;
   }
   
   private Vector<Item> getPlayerItemList()
   {
      return GameState.getPlayerCharacter().getInventory().getItemList();
   }
   
   private void set()
   {
      bindCurIndex();
      Vector<Item> itemList = getPlayerItemList();
      Actor player = GameState.getPlayerCharacter();
      
      // list items
      for(int i = 0; i < TILES_TALL - 6; i++)
      {
         if(i < itemList.size())
         {
            setTileIndex(LEFT_PANEL_X_ORIGIN + 2, 3 + i, itemList.elementAt(i).getIconIndex());
            setTileFG(LEFT_PANEL_X_ORIGIN + 2, 3 + i, itemList.elementAt(i).getColor());
            overwriteLine(LEFT_PANEL_X_ORIGIN + 4, 3 + i, itemList.elementAt(i).getName(), SIDE_WIDTH - (LEFT_PANEL_X_ORIGIN + 5));
         }
         else
         {
            fillTileIndex(1, 3 + i, SIDE_WIDTH, 1, ' ');
         }
      }
      if(mode == STANDARD_MODE)
         overwriteLine(1, getTilesTall() - 2, "[D]rop, [R]emove equipped, [ENTER] to use/equip", TILES_WIDE - 2);
      else if(mode == UNEQUIP_MODE)
         overwriteLine(1, getTilesTall() - 2, "Remove [M]ain Hand, [O]ff Hand, [A]rmor, or Relic[1, 2, 3]? [ESCAPE] to cancel", TILES_WIDE - 2);
      
      // left cursor
      for(int i = 0; i < getTilesTall() - 5; i++)
      {
         if(i == curIndex && onLeft)
            setTileIndex(LEFT_PANEL_X_ORIGIN, 3 + i, '>');
         else
            setTileIndex(LEFT_PANEL_X_ORIGIN, 3 + i, ' ');
      }
      // right cursor
      for(int i = 0; i < EQUIPPED_SLOTS; i++)
      {
         if(i == curIndex && !onLeft)
            setTileIndex(RIGHT_PANEL_X_ORIGIN, 3 + i, '>');
         else
            setTileIndex(RIGHT_PANEL_X_ORIGIN, 3 + i, ' ');
      }
      
      // equipped
      String mainHand = "None";
      String offHand = "None";
      String armor = "None";
      String[] relic = new String[Actor.MAX_RELICS];
      if(player.getMainHand() != null)
         mainHand = player.getMainHand().getName();
      if(player.getOffHand() != null)
         offHand = player.getOffHand().getName();
      if(player.getArmor() != null)
         armor = player.getArmor().getName();
      for(int i = 0; i < Actor.MAX_RELICS; i++)
      {
         if(i >= player.getRelicList().size())
            relic[i] = "None";
         else
            relic[i] = player.getRelicList().elementAt(i).getName();
      }
      overwriteLine(RIGHT_PANEL_X_ORIGIN + 2, 3, "Main Hand: " + mainHand, SIDE_WIDTH - 2);
      overwriteLine(RIGHT_PANEL_X_ORIGIN + 2, 4, "Off Hand:  " + offHand, SIDE_WIDTH - 2);
      overwriteLine(RIGHT_PANEL_X_ORIGIN + 2, 5, "Armor:     " + armor, SIDE_WIDTH - 2);
      for(int i = 0; i < relic.length; i++)
         overwriteLine(RIGHT_PANEL_X_ORIGIN + 2, 6 + i, "Relic " + (i + 1) +":   " + relic[i], SIDE_WIDTH - 2);
      
      // current selection
      if(curIndex != -1 && curIndex < itemList.size() && onLeft)
      {
         Item curItem = itemList.elementAt(curIndex);
         Vector<String> strList = curItem.getSummary();
         // comparison summaries
         if(curItem instanceof Weapon && player.getMainHand() != null)
         {
            strList = ((Weapon)curItem).getComparisonSummary(player.getMainHand());
         }
         if(curItem instanceof OffHand && player.getOffHand() != null)
         {
            strList = ((OffHand)curItem).getComparisonSummary(player.getOffHand());
         }
         if(curItem instanceof Armor && player.getArmor() != null)
         {
            strList = ((Armor)curItem).getComparisonSummary(player.getArmor());
         }
         overwriteLine(RIGHT_PANEL_X_ORIGIN, 12, curItem.getName(), SIDE_WIDTH);
         int comparisonStart = RIGHT_PANEL_X_ORIGIN + 19;
         for(int i = 0; i < MAX_SUMMARY_LINES; i++)
         {
            if(i < strList.size())
               overwriteLine(RIGHT_PANEL_X_ORIGIN, 13 + i, strList.elementAt(i), SIDE_WIDTH);
            else
               overwriteLine(RIGHT_PANEL_X_ORIGIN, 13 + i, "", SIDE_WIDTH);
            // color-code comparisons
            Vector<int[]> compLoc = findText(RIGHT_PANEL_X_ORIGIN, 13 + i, SIDE_WIDTH, 1, "(+");
            if(compLoc.size() > 0)
               fillTileFG(compLoc.elementAt(0)[0], 13 + i, TILES_WIDE - 1 - compLoc.elementAt(0)[0], 1, GREEN);
            else
            {
               compLoc = findText(RIGHT_PANEL_X_ORIGIN, 13 + i, SIDE_WIDTH, 1, "(-");
               if(compLoc.size() > 0)
                  fillTileFG(compLoc.elementAt(0)[0], 13 + i, TILES_WIDE - 1 - compLoc.elementAt(0)[0], 1, RED);
               else
                  fillTileFG(RIGHT_PANEL_X_ORIGIN, 13 + i, SIDE_WIDTH, 1, WHITE);
            }
         }
      }
      // right summary
      else if(!onLeft)
      {
         Item item = getSelectedEquipped();
         Vector<String> summaryList = new Vector<String>();
         if(item != null)
         {
            if(item instanceof Weapon)
               summaryList = ((Weapon)item).getSummary();
            else if(item instanceof OffHand)
               summaryList = ((OffHand)item).getSummary();
            else if(item instanceof Armor)
               summaryList = ((Armor)item).getSummary();
            else
               summaryList = item.getSummary();
            overwriteLine(RIGHT_PANEL_X_ORIGIN, 12, item.getName(), SIDE_WIDTH);
         }
         else // no item
            overwriteLine(RIGHT_PANEL_X_ORIGIN, 12, "", SIDE_WIDTH);
         for(int i = 0; i < MAX_SUMMARY_LINES + 1; i++)
         {
            if(i < summaryList.size())
               overwriteLine(RIGHT_PANEL_X_ORIGIN, 13 + i, summaryList.elementAt(i), SIDE_WIDTH);
            else
               overwriteLine(RIGHT_PANEL_X_ORIGIN, 13 + i, "", SIDE_WIDTH);
         }
      }
      
   }
   
   private Item getSelectedEquipped()
   {
      Item item = null;
      Actor player = GameState.getPlayerCharacter();
      if(curIndex == Inventory.MAIN_HAND_SLOT)
         item = player.getMainHand();
      if(curIndex == Inventory.OFF_HAND_SLOT)
         item = player.getOffHand();
      if(curIndex == Inventory.ARMOR_SLOT)
         item = player.getArmor();
      if(curIndex >= Inventory.RELIC_SLOT)
         item = player.getRelic(curIndex - Inventory.RELIC_SLOT);
      return item;
   }
   
   @Override
   public void paint(Graphics g)
   {
      
      if(GameState.getPlayerCharacter() != null)
      {
         bindCurIndex();
         set();
      }
      super.paint(g);
   }
   
   @Override
   public void setVisible(boolean v)
   {
      if(v && GameState.getPlayerCharacter() != null)
      {
         curIndex = 0;
         if(getPlayerItemList().size() > 0)
            onLeft = true;
         else
            onLeft = false;
      }
      else
         curIndex = -1;
      
      mode = STANDARD_MODE;
      super.setVisible(v);
   }
   
   private void switchSides()
   {
      if(!onLeft && getPlayerItemList().size() == 0)
         return;
      onLeft = !onLeft;
      curIndex = 0;
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {  
      ActionPlan ap = null;
      
      // change sides unless unequipping
      if(mode == STANDARD_MODE)
      {
         switch(ke.getKeyCode())
         {
            case KeyEvent.VK_RIGHT :      
            case KeyEvent.VK_NUMPAD6 :
            case KeyEvent.VK_LEFT :  
            case KeyEvent.VK_NUMPAD4 : switchSides(); break;
         }
      }
      
      // change curIndex, unless both on left and unequipping
      if(mode == STANDARD_MODE || !onLeft)
      {
         switch(ke.getKeyCode())
         {
            case KeyEvent.VK_UP :      
            case KeyEvent.VK_NUMPAD8 : decrementIndex(); break;
            case KeyEvent.VK_DOWN :  
            case KeyEvent.VK_NUMPAD2 : incrementIndex(); break;
         }
      }
      
      if(mode == STANDARD_MODE)
      {
         switch(ke.getKeyCode())
         {
            case KeyEvent.VK_ESCAPE :
            case KeyEvent.VK_I :
            case KeyEvent.VK_SPACE :   parentFrame.returnToMainPanel();
                                       break;
            
            case KeyEvent.VK_D :       if(curIndex > -1)
                                       {
                                          ap = new ActionPlan(ActionType.DROP, curIndex);
                                          GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                          parentFrame.returnToMainPanel();
                                       }
                                       break;
            case KeyEvent.VK_ENTER :   if(curIndex > -1)
                                       {
                                          ap = new ActionPlan(ActionType.EQUIP, curIndex);
                                          GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                          parentFrame.returnToMainPanel();
                                       }
                                       break;
            case KeyEvent.VK_R :       mode = UNEQUIP_MODE;
                                       switchSides();
                                       break;
         }
      }
      else if(mode == UNEQUIP_MODE)
      {
         switch(ke.getKeyCode())
         {
            case KeyEvent.VK_ESCAPE :  mode = STANDARD_MODE;
                                       switchSides();
                                       break;
            case KeyEvent.VK_ENTER :   ap = new ActionPlan(ActionType.REMOVE, curIndex);
                                       GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                       parentFrame.returnToMainPanel();
                                       break;
            case KeyEvent.VK_M :       ap = new ActionPlan(ActionType.REMOVE, Inventory.MAIN_HAND_SLOT);
                                       GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                       parentFrame.returnToMainPanel();
                                       break;
            case KeyEvent.VK_O :       ap = new ActionPlan(ActionType.REMOVE, Inventory.OFF_HAND_SLOT);
                                       GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                       parentFrame.returnToMainPanel();
                                       break;
            case KeyEvent.VK_A :       ap = new ActionPlan(ActionType.REMOVE, Inventory.ARMOR_SLOT);
                                       GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                       parentFrame.returnToMainPanel();
                                       break;
            case KeyEvent.VK_1 :       
            case KeyEvent.VK_2 :       
            case KeyEvent.VK_3 :       
            case KeyEvent.VK_4 :       
            case KeyEvent.VK_5 :       
            case KeyEvent.VK_6 :       
            case KeyEvent.VK_7 :       
            case KeyEvent.VK_8 :       
            case KeyEvent.VK_9 :       int relicVal = ke.getKeyCode() - KeyEvent.VK_1;
                                       if(relicVal < Actor.MAX_RELICS)
                                       {
                                          ap = new ActionPlan(ActionType.REMOVE, Inventory.RELIC_SLOT + relicVal);
                                          GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                          parentFrame.returnToMainPanel();
                                       }
                                       break;
         }
      }
   }
   
   public void incrementIndex()
   {
      Vector<Item> itemList = getPlayerItemList();
      if(onLeft)
      {
         if(itemList.size() == 0)
            curIndex = -1;
         else
         {
            curIndex++;
            if(curIndex == itemList.size())
               curIndex = 0;
         }
      }
      else // on right
      {
         curIndex++;
         if(curIndex == EQUIPPED_SLOTS)
            curIndex = 0;
      }
   }
   
   public void decrementIndex()
   {
      Vector<Item> itemList = getPlayerItemList();
      if(onLeft)
      {
         if(itemList.size() == 0)
            curIndex = -1;
         else
         {
            curIndex--;
            if(curIndex == -1)
               curIndex = itemList.size() - 1;
         }
      }
      else // on right
      {
         curIndex--;
         if(curIndex == -1)
            curIndex = EQUIPPED_SLOTS - 1;
      }
   }
   
   private void bindCurIndex()
   {
      Vector<Item> itemList = getPlayerItemList();
      if(itemList.size() == 0)
         curIndex = -1;
      else
      {
         curIndex = Math.max(curIndex, 0);
         curIndex = Math.min(curIndex, itemList.size() - 1);
      }
   }
}