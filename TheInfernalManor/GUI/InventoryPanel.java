package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class InventoryPanel extends TIMPanel implements GUIConstants
{
   public InventoryPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Inventory ");
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