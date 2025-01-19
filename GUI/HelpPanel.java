package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class HelpPanel extends TIMPanel implements GUIConstants
{
   public HelpPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      writeLine(0, 0, "HelpPanel");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.setVisiblePanel("SplashPanel");
   }
}