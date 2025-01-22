package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class HelpPanel extends TIMPanel implements GUIConstants
{
   public HelpPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Help ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      if(ke.getKeyCode() == KeyEvent.VK_ESCAPE || ke.getKeyCode() == KeyEvent.VK_SPACE)
      {
         parentFrame.returnToLastPanel();
      }
   }
}