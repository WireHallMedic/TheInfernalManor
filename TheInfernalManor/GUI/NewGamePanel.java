package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class NewGamePanel extends TIMPanel implements GUIConstants
{
   public NewGamePanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " New Game ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.setVisiblePanel("SplashPanel");
   }
}