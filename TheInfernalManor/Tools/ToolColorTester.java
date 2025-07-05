package TheInfernalManor.Tools;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.GUI.*;
import StrictCurses.*;


public class ToolColorTester extends JFrame implements GUIConstants, MouseListener
{
   private int[] BG_COLOR_ARRAY = {VERY_DARK_GREEN, DARK_BLUE, DARK_BROWN, DARK_GREY, DARK_RED};
   
   private SCPanel tilePanel;
   
   public ToolColorTester()
   {
      super();
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(500, 500);
      setTitle("Color Tester");
      
      setLayout(new GridLayout(1, 1));
      tilePanel = new SCPanel(new SCTilePalette("WidlerTiles_16x16.png", 16, 16), 10, 10);
      add(tilePanel);
      for(int i = 0; i < BG_COLOR_ARRAY.length; i++)
      {
         tilePanel.setTile(i, 0, 'X', WHITE, BG_COLOR_ARRAY[i]);
      }
      
      for(int i = 0; i < COLOR_ARRAY.length; i++)
      {
         int x = i % 10;
         int y = 2 + (i / 10);
         tilePanel.setTileBG(x, y, COLOR_ARRAY[i]);
      }
      
      setVisible(true);
   }
   
   public void mousePressed(MouseEvent me){}
   public void mouseReleased(MouseEvent me){}
   public void mouseEntered(MouseEvent me){}
   public void mouseExited(MouseEvent me){}
   
   public void mouseClicked(MouseEvent me){}
   
   public static void main(String[] args)
   {
      ToolColorTester tct = new ToolColorTester();
   }
}