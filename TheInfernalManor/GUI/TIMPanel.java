package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class TIMPanel extends SCPanel implements GUIConstants, KeyListener, SwapPanel
{
   protected TIMFrame parentFrame;
   
   public String getPanelName(){return this.getClass().getSimpleName();}
   
   public TIMPanel(SCTilePalette tilePalette, TIMFrame pFrame, int w, int h)
   {
      super(tilePalette, w, h);
      parentFrame = pFrame;
   }
   public TIMPanel(SCTilePalette tilePalette, TIMFrame pFrame){this(tilePalette, pFrame, TILES_WIDE, TILES_TALL);}
   
   
   public void keyPressed(KeyEvent ke){}
   public void keyTyped(KeyEvent ke){}
   public void keyReleased(KeyEvent ke){}
}