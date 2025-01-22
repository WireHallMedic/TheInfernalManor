package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class QuestNegotiationPanel extends TIMPanel implements GUIConstants
{
   public QuestNegotiationPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Available Quests ");
      writeLine(1, 3, "Escape to go back, any other key to start game.");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      if(ke.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
         parentFrame.returnToLastPanel();
      }
      else
      {
         parentFrame.setVisiblePanel("AdventurePanel");
      }
   }
}