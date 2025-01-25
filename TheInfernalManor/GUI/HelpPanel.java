package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;
import java.awt.*;

public class HelpPanel extends TIMPanel implements GUIConstants
{
   public HelpPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Help ");
      
      for(int i = 0; i < COLOR_ARRAY.length; i++)
      {
         setTileBG(1 + i, 3, COLOR_ARRAY[i]);
      }
      
      for(int i = 0; i < DARK_GREEN_GRADIENT.length; i++)
      {
         setTileBG(1 + i, 5, DARK_GREEN_GRADIENT[i]);
         setTileBG(1 + i, 6, DARK_GREEN);
         setTileBG(1 + i, 7, DARK_BLUE_GRADIENT[i]);
         setTileBG(1 + i, 8, DARK_BLUE);
         setTileBG(1 + i, 9, DARK_BROWN_GRADIENT[i]);
         setTileBG(1 + i, 10, DARK_BROWN);
         setTileBG(1 + i, 11, DARK_GREY_GRADIENT[i]);
         setTileBG(1 + i, 12, DARK_GREY);
      }
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      if(ke.getKeyCode() == KeyEvent.VK_ESCAPE || ke.getKeyCode() == KeyEvent.VK_SPACE)
      {
         parentFrame.returnToLastPanel();
      }
   }
}