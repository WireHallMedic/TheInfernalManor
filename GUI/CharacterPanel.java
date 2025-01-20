package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

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
      parentFrame.returnToLastPanel();
   }
}