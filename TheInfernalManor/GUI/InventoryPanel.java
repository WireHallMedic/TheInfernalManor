package TheInfernalManor.GUI;

import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import StrictCurses.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class InventoryPanel extends TIMPanel implements GUIConstants
{
   private int curIndex;
   
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
      
      for(int i = 0; i < itemList.size(); i++)
      {
         if(i < itemList.size())
         {
            setTileIndex(4, 3 + i, itemList.elementAt(i).getIconIndex());
            setTileFG(4, 3 + i, itemList.elementAt(i).getColor());
            overwriteLine(6, 3 + i, itemList.elementAt(i).getName(), getTilesWide() - 7);
         }
         else
         {
            fillTileIndex(1, 3 + i, getTilesWide() - 2, 1, ' ');
         }
      }
      overwriteLine(1, getTilesTall() - 2, "[D]rop, [R]emove equipped, [ENTER] to use/equip", getTilesWide() - 2);
      
      // cursor
      for(int i = 0; i < getTilesTall() - 5; i++)
      {
         if(i == curIndex)
            setTileIndex(2, 3 + i, '>');
         else
            setTileIndex(2, 3 + i, ' ');
      }
   }
   
   @Override
   public void paint(Graphics g)
   {
      
      if(GameState.getPlayerCharacter() != null)
         set();
      super.paint(g);
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