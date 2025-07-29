package TheInfernalManor.Tools;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.GUI.*;
import StrictCurses.*;


public class ToolColorTester extends JFrame implements GUIConstants, MouseListener
{
   private int[] BG_COLOR_ARRAY = {VERY_DARK_GREEN, DARK_BLUE, DARK_YELLOW_GREEN, DARK_BROWN, DARK_GREY, DARK_RED};
   
   private SCPanel tilePanel;
   
   public ToolColorTester()
   {
      super();
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(500, 500);
      setTitle("Color Tester");
      
      setLayout(new GridLayout(1, 1));
      tilePanel = new SCPanel(new SCTilePalette("WidlerTiles_16x16.png", 16, 16), COLOR_ARRAY.length, 15);
      add(tilePanel);
      for(int i = 0; i < BG_COLOR_ARRAY.length; i++)
      {
         tilePanel.setTile(i, 0, 'X', WHITE, BG_COLOR_ARRAY[i]);
      }
      
      for(int x = 0; x < COLOR_ARRAY.length; x++)
      {
         tilePanel.setTileBG(x, 2, COLOR_ARRAY[x]);
         for(int y = 0; y < GRADIENT_ARRAY[0].length; y++)
            tilePanel.setTileBG(x, y + 4, GRADIENT_ARRAY[x][y]);
      }
      
      tilePanel.addMouseListener(this);
      setVisible(true);
   }
   
   public boolean isValidColor(int x, int y)
   {
      return x >= 0 && x < COLOR_ARRAY.length && y == 2;
   }
   
   public int getFGColor(int x, int y)
   {
      if(isValidColor(x, y))
      {
         return COLOR_ARRAY[x];
      }
      return 0;
   }
   
   public void mousePressed(MouseEvent me){}
   public void mouseReleased(MouseEvent me){}
   public void mouseEntered(MouseEvent me){}
   public void mouseExited(MouseEvent me){}
   
   public void mouseClicked(MouseEvent me)
   {
      int[] loc = tilePanel.getMouseLocTile();
      if(isValidColor(loc[0], loc[1]))
      {
         int fgColor = getFGColor(loc[0], loc[1]);
         for(int x = 0; x < BG_COLOR_ARRAY.length; x++)
            tilePanel.setTileFG(x, 0, fgColor);
      }
      tilePanel.repaint();
   }
   
   public static void main(String[] args)
   {
      ToolColorTester tct = new ToolColorTester();
   }
}