package TheInfernalManor.GUI;

import TheInfernalManor.Engine.*;
import StrictCurses.*;
import java.awt.event.*;

public class SplashPanel extends TIMPanel implements GUIConstants
{
   private String[] optionList = {"[N]ew Game", "[L]oad Game", "[P]references", "[H]elp", "[Q]uit"};
   
   public SplashPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      writeLine(0, 0, "The Infernal Manor");
      for(int i = 0; i < optionList.length; i++)
         writeLine(5, 20 + i, optionList[i]);
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      switch(ke.getKeyCode())
      {
         case KeyEvent.VK_H:  parentFrame.setVisiblePanel("HelpPanel"); break;
         case KeyEvent.VK_L:  parentFrame.setVisiblePanel("LoadPanel"); break;
         case KeyEvent.VK_P:  parentFrame.setVisiblePanel("PreferencesPanel"); break;
         case KeyEvent.VK_N:  parentFrame.setVisiblePanel("NewGamePanel"); break;
         case KeyEvent.VK_T:  GameState.setTestValues();
                              parentFrame.setVisiblePanel("AdventurePanel");
                              break;
         case KeyEvent.VK_Q:  System.exit(0);
      }
   }
}