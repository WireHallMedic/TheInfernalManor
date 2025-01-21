package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class AdvancementPanel extends TIMPanel implements GUIConstants
{
   public AdvancementPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Advancement ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}