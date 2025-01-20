package TheInfernalManor.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import StrictCurses.*;

public class TIMFrame extends JFrame implements SCConstants, ComponentListener, KeyListener, GUIConstants
{
   private SCTilePalette x1y1Palette;
   private SCTilePalette x1y2Palette;
   private Vector<SwapPanel> panelList;
   private JPanel basePanel;
   private SwapPanel curPanel;
   private SwapPanel lastPanel;
   
   private SplashPanel splashPanel;
   private HelpPanel helpPanel;
   private AdventurePanel adventurePanel;
   
   public TIMFrame()
   {
      super();      
      setSize(100, 100);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("The Infernal Manor");
      setLayout(new GridLayout(1, 1));
      basePanel = new JPanel();
      
      basePanel.setBackground(new Color(DEFAULT_BG_COLOR));
      basePanel.setLayout(null);
      add(basePanel);
      
      x1y1Palette = new SCTilePalette("WidlerTiles_16x16.png", 16, 16);
      x1y2Palette = new SCTilePalette("WidlerTiles_8x16.png", 8, 16);
      
      panelList = new Vector<SwapPanel>();
      
      addKeyListener(this);
      basePanel.addComponentListener(this);
      basePanel.addKeyListener(this);
      
      splashPanel = new SplashPanel(x1y2Palette, this);
      addPanel(splashPanel);
      helpPanel = new HelpPanel(x1y2Palette, this);
      addPanel(helpPanel);
      adventurePanel = new AdventurePanel(x1y2Palette, x1y1Palette, this);
      addPanel(adventurePanel);
      
      setVisible(true);
      snapToPreferredSize();
      setVisiblePanel("SplashPanel");
   }
   
   public void addPanel(SwapPanel newPanel)
   {
      newPanel.setLocation(0, 0);
      newPanel.setSize(basePanel.getWidth(), basePanel.getHeight());
      newPanel.setVisible(false);
      basePanel.add((Component)newPanel);
      panelList.add(newPanel);
   }
   
   // listener for basePanel
   public void componentHidden(ComponentEvent e){}
   public void componentMoved(ComponentEvent e){}
   public void componentShown(ComponentEvent e){}
   public void componentResized(ComponentEvent e)
   {
      for(SwapPanel panel : panelList)
      {
         panel.setSize(basePanel.getWidth(), basePanel.getHeight());
      }
   }
   
   public void setVisiblePanel(String panelName)
   {
      for(SwapPanel panel : panelList)
      {
         if(panel.getPanelName().equals(panelName))
         {
            panel.setVisible(true);
            curPanel = panel;
         }
         else
            panel.setVisible(false);
      }
      repaint();
   }
   
   private void snapToPreferredSize()
   {
      Insets insets = getInsets();
      int newWidth = (TILES_WIDE * 8 * 2) + insets.left + insets.right;
      int newHeight = (TILES_TALL * 16 * 2) + insets.top + insets.bottom;
      setSize(newWidth, newHeight);
   }
   
   public void keyPressed(KeyEvent ke)
   {
      if(curPanel != null)
         ((KeyListener)curPanel).keyPressed(ke);
   }
   public void keyTyped(KeyEvent ke)
   {
      if(curPanel != null)
         ((KeyListener)curPanel).keyTyped(ke);
   }
   public void keyReleased(KeyEvent ke)
   {
      if(curPanel != null)
         ((KeyListener)curPanel).keyReleased(ke);
   }
}