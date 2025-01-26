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
   private int curIndex;
   private static final int SIDE_WIDTH = (TILES_WIDE - 3) / 2;
   private static final int LEFT_PANEL_X_ORIGIN = 1;
   private static final int RIGHT_PANEL_X_ORIGIN = 3 + SIDE_WIDTH;
   
   public InventoryPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Inventory ");
      curIndex = -1;
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
      overwriteLine(1, getTilesTall() - 2, "[D]rop, [R]emove equipped, [ENTER] to use/equip", SIDE_WIDTH);
      
      // cursor
      for(int i = 0; i < getTilesTall() - 5; i++)
      {
         if(i == curIndex)
            setTileIndex(LEFT_PANEL_X_ORIGIN, 3 + i, '>');
         else
            setTileIndex(LEFT_PANEL_X_ORIGIN, 3 + i, ' ');
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
      overwriteLine(RIGHT_PANEL_X_ORIGIN, 3, "Main Hand: " + mainHand, SIDE_WIDTH);
      overwriteLine(RIGHT_PANEL_X_ORIGIN, 4, "Off Hand:  " + offHand, SIDE_WIDTH);
      overwriteLine(RIGHT_PANEL_X_ORIGIN, 5, "Armor:     " + armor, SIDE_WIDTH);
      for(int i = 0; i < relic.length; i++)
         overwriteLine(RIGHT_PANEL_X_ORIGIN, 6 + i, "Relic " + (i + 1) +":   " + relic[i], SIDE_WIDTH);
      
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
      if(v && GameState.getPlayerCharacter() != null && getPlayerItemList().size() > 0)
         curIndex = 0;
      else
         curIndex = -1;
      super.setVisible(v);
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      switch(ke.getKeyCode())
      {
         case KeyEvent.VK_ESCAPE :
         case KeyEvent.VK_I :
         case KeyEvent.VK_SPACE :   parentFrame.returnToMainPanel();
                                    break;
         case KeyEvent.VK_UP :      
         case KeyEvent.VK_NUMPAD8 : decrementIndex(); break;
         case KeyEvent.VK_DOWN :  
         case KeyEvent.VK_NUMPAD2 : incrementIndex(); break;
         
         case KeyEvent.VK_D :       if(curIndex > -1)
                                    {
                                       ActionPlan ap = new ActionPlan(ActionType.DROP, curIndex);
                                       GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                       parentFrame.returnToMainPanel();
                                    }
                                    break;
         case KeyEvent.VK_ENTER :   if(curIndex > -1)
                                    {
                                       ActionPlan ap = new ActionPlan(ActionType.EQUIP, curIndex);
                                       GameState.getPlayerCharacter().getAI().setPendingAction(ap);
                                       parentFrame.returnToMainPanel();
                                    }
                                    break;
      }
   }
   
   public void incrementIndex()
   {
      Vector<Item> itemList = getPlayerItemList();
      if(itemList.size() == 0)
         curIndex = -1;
      else
      {
         curIndex++;
         if(curIndex == itemList.size())
            curIndex = 0;
      }
   }
   
   public void decrementIndex()
   {
      Vector<Item> itemList = getPlayerItemList();
      if(itemList.size() == 0)
         curIndex = -1;
      else
      {
         curIndex--;
         if(curIndex == -1)
            curIndex = itemList.size() - 1;
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