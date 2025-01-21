package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;

public class CharacterPanel extends TIMPanel implements GUIConstants
{
   public CharacterPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Character ");
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