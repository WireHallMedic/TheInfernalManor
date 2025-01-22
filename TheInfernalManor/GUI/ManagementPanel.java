package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;

public class ManagementPanel extends TIMPanel implements GUIConstants
{
   private String[] optionList = {"[A]dvancement", "[Q]uest", "[C]haracter", "[I]nventory", "[S]hop", "[B]ank", "[P]references", "[R]eturn to Main Menu"};
   
   public ManagementPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Town ");
      for(int i = 0; i < optionList.length; i++)
         writeLine(5, 20 + i, optionList[i]);
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      switch(ke.getKeyCode())
      {
         case KeyEvent.VK_A:  parentFrame.setVisiblePanel("AdvancementPanel"); break;
         case KeyEvent.VK_Q:  parentFrame.setVisiblePanel("QuestNegotiationPanel"); break;
         case KeyEvent.VK_C:  parentFrame.setVisiblePanel("CharacterPanel"); break;
         case KeyEvent.VK_I:  parentFrame.setVisiblePanel("InventoryPanel"); break;
         case KeyEvent.VK_S:  parentFrame.setVisiblePanel("ShopPanel"); break;
         case KeyEvent.VK_B:  parentFrame.setVisiblePanel("BankPanel"); break;
         case KeyEvent.VK_P:  parentFrame.setVisiblePanel("PreferencesPanel"); break;
         case KeyEvent.VK_R:  parentFrame.setVisiblePanel("SplashPanel"); break;
      }
   }
   
   @Override
   public void setVisible(boolean v)
   {
      if(v)
         GameState.setGameMode(EngineConstants.MANAGEMENT_MODE);
      super.setVisible(v);
   }
}