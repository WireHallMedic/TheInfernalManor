package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class ManagementPanel extends TIMPanel implements GUIConstants
{
   public ManagementPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Town ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}