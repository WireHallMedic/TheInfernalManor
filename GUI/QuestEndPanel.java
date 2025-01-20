package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class QuestEndPanel extends TIMPanel implements GUIConstants
{
   public QuestEndPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Quest End ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}