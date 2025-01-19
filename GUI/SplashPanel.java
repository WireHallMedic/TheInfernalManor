package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class SplashPanel extends TIMPanel implements GUIConstants
{
   public SplashPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      for(int x = 0; x < getTilesWide(); x++)
      for(int y = 0; y < getTilesTall(); y++)
         setTileIndex(x, y, '#');
      writeLine(0, 0, "SplashPanel");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.setVisiblePanel("HelpPanel");
   }
}