package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class ShopPanel extends TIMPanel implements GUIConstants
{
   public ShopPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Shop ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}