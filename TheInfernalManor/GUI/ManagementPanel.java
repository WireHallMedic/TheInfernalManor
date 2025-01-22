package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;

public class ManagementPanel extends TIMPanel implements GUIConstants
{
   public ManagementPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Town ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.returnToLastPanel();
   }
   
   @Override
   public void setVisible(boolean v)
   {
      if(v)
         GameState.setGameMode(EngineConstants.MANAGEMENT_MODE);
      super.setVisible(v);
   }
}