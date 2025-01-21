package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class QuestNegotiationPanel extends TIMPanel implements GUIConstants
{
   public QuestNegotiationPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Available Quests ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
}