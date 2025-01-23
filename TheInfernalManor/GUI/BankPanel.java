package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class BankPanel extends TIMPanel implements GUIConstants
{
   public BankPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Bank ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}