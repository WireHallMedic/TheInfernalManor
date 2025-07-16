package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;

public class ZoneMapPanel extends TIMPanel implements GUIConstants
{
   public ZoneMapPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame, TILES_WIDE / 2, TILES_TALL);
      GUITools.drawBorderWithTitle(this, " ZoneMap Panel ");
      writeLine(1, 3, "Escape to go back, any other key to start game.");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      switch(ke.getKeyCode())
      {
         case KeyEvent.VK_ESCAPE :
         case KeyEvent.VK_M :
         case KeyEvent.VK_SPACE :   parentFrame.returnToMainPanel();
      }
   }
}