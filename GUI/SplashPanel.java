package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class SplashPanel extends TIMPanel implements GUIConstants
{
   public SplashPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      writeLine(0, 0, "SplashPanel");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.setVisiblePanel("AdventurePanel");
   }
}