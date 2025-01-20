package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class GameOverPanel extends TIMPanel implements GUIConstants
{
   public GameOverPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Game Over ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}