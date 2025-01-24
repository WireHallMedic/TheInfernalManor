package TheInfernalManor.GUI;

import TheInfernalManor.Item.*;
import TheInfernalManor.Engine.*;
import StrictCurses.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class InventoryPanel extends TIMPanel implements GUIConstants
{
   public InventoryPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Inventory ");
   }
   
   private void set()
   {
      if(GameState.getPlayerCharacter() == null)
         return;
         
      Vector<Item> itemList = GameState.getPlayerCharacter().getInventory().getItemList();
      for(int i = 0; i < itemList.size(); i++)
      {
         if(i < itemList.size())
         {
            setTileIndex(2, 3 + i, itemList.elementAt(i).getIconIndex());
            setTileFG(2, 3 + i, itemList.elementAt(i).getColor());
            overwriteLine(4, 3 + i, itemList.elementAt(i).getName(), getTilesWide() - 5);
         }
         else
         {
            fillTileIndex(1, 3 + i, getTilesWide() - 2, 1, ' ');
         }
      }
   }
   
   @Override
   public void paint(Graphics g)
   {
      set();
      super.paint(g);
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      if(ke.getKeyCode() == KeyEvent.VK_ESCAPE || ke.getKeyCode() == KeyEvent.VK_SPACE)
      {
         parentFrame.returnToMainPanel();
      }
   }
}