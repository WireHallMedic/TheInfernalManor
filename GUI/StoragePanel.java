package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class StoragePanel extends TIMPanel implements GUIConstants
{
   public StoragePanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Storage ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}