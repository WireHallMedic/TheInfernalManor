package TheInfernalManor.GUI;

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
      parentFrame.setVisiblePanel("HelpPanel");
   }
}