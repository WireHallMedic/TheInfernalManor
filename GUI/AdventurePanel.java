package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class AdventurePanel extends TIMPanel implements GUIConstants
{
   public AdventurePanel(SCTilePalette x1y2TilePalette, SCTilePalette x1y1TilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawSimpleBorder(this);
      writeLine(1, 1, "AdventurePanel");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.setVisiblePanel("SplashPanel");
   }
}