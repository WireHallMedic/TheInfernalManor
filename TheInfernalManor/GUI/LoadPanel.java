package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;

public class LoadPanel extends TIMPanel implements GUIConstants
{
   public LoadPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Load ");
      writeLine(1, 3, "Escape to go back, any other key to start game.");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      if(ke.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
         parentFrame.returnToMainPanel();
      }
      else
      {
         GameState.setTestValues();
         parentFrame.setVisiblePanel("ManagementPanel");
      }
   }
}