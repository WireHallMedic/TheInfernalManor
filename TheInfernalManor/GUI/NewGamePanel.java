package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;

public class NewGamePanel extends TIMPanel implements GUIConstants
{
   public NewGamePanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " New Game ");
      writeLine(1, 3, "Escape to go back, any other key to go to character creation.");
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
         parentFrame.setVisiblePanel("CharacterCreationPanel");
      }
   }
}