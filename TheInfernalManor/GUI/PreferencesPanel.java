package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class PreferencesPanel extends TIMPanel implements GUIConstants
{
   public PreferencesPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Preferences ");
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