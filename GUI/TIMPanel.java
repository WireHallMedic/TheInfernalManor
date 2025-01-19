package TheInfernalManor.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import StrictCurses.*;

public class TIMPanel extends SCPanel implements GUIConstants
{
   private TIMFrame parentFrame;
   
   public TIMPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, TILES_WIDE, TILES_TALL);
      parentFrame = pFrame;
   }
}