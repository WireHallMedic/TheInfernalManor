package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class LoadPanel extends TIMPanel implements GUIConstants
{
   public LoadPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Load ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.setVisiblePanel("SplashPanel");
   }
}