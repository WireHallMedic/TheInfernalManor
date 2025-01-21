package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class QuestInProgressPanel extends TIMPanel implements GUIConstants
{
   public QuestInProgressPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Quest ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}