package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class CharacterCreationPanel extends TIMPanel implements GUIConstants
{
   public CharacterCreationPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Character Creation ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}