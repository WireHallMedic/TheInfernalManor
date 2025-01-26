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
         setTileBG(1 + i, 8, DARK_BLUE_GRADIENT[i]);
         setTileBG(1 + i, 9, DARK_BLUE);
         setTileBG(1 + i, 11, DARK_BROWN_GRADIENT[i]);
         setTileBG(1 + i, 12, DARK_BROWN);
         setTileBG(1 + i, 14, DARK_GREY_GRADIENT[i]);
         setTileBG(1 + i, 15, DARK_GREY);
         setTileBG(1 + i, 17, DARK_RED_GRADIENT[i]);
         setTileBG(1 + i, 18, DARK_RED);
      }
      
      setTile(11, 5, '@', GREEN, DARK_GREEN_GRADIENT[9]);
      setTile(11, 8, '@', BLUE, DARK_BLUE_GRADIENT[9]);
      setTile(11, 11, '@', BROWN, DARK_BROWN_GRADIENT[9]);
      setTile(11, 14, '@', GREY, DARK_GREY_GRADIENT[9]);
      setTile(11, 17, '@', RED, DARK_RED_GRADIENT[9]);
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