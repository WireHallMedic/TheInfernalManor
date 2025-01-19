package TheInfernalManor.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import StrictCurses.*;

public class TIMFrame extends JFrame implements SCConstants, ComponentListener, KeyListener
{
   private SCTilePalette x1y1Palette;
   private SCTilePalette x1y2Palette;
   private Vector<TIMPanel> panelList;
   private JPanel basePanel;
   private TIMPanel curPanel;
   private SplashPanel splashPanel;
   private HelpPanel helpPanel;
   
   public TIMFrame()
   {
      super();      
      setSize(1500, 800);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("The Infernal Manor");
      setLayout(new GridLayout(1, 1));
      basePanel = new JPanel();
      
      basePanel.setBackground(new Color(DEFAULT_BG_COLOR));
      basePanel.setLayout(null);
      add(basePanel);
      
      String x1y1Str = "TheInfernalManor/images/WidlerTiles_8x16.png";
      String x1y2Str = "TheInfernalManor/images/WidlerTiles_16x16.png";
      
      if(TIMFrame.class.getResource("TIMFrame.class").toString().contains(".jar"))
      {
         x1y1Str = "WidlerTiles_8x16.png";
         x1y2Str = "WidlerTiles_16x16.png";
      }
      x1y1Palette = new SCTilePalette(x1y1Str, 8, 16);
      x1y2Palette = new SCTilePalette(x1y2Str, 16, 16);
      
      panelList = new Vector<TIMPanel>();
      
      addKeyListener(this);
      basePanel.addComponentListener(this);
      basePanel.addKeyListener(this);
      
      splashPanel = new SplashPanel(x1y2Palette, this);
      addPanel(splashPanel);
      helpPanel = new HelpPanel(x1y2Palette, this);
      addPanel(helpPanel);
      
      setVisible(true);
      setVisiblePanel("SplashPanel");
   }
   
   public void addPanel(TIMPanel newPanel)
   {
      newPanel.setLocation(0, 0);
      newPanel.setSize(basePanel.getWidth(), basePanel.getHeight());
      newPanel.setVisible(false);
      basePanel.add(newPanel);
      panelList.add(newPanel);
   }
   
   // listener for basePanel
   public void componentHidden(ComponentEvent e){}
   public void componentMoved(ComponentEvent e){}
   public void componentShown(ComponentEvent e){}
   public void componentResized(ComponentEvent e)
   {
      for(TIMPanel panel : panelList)
      {
         panel.setSize(basePanel.getWidth(), basePanel.getHeight());
      }
   }
   
   public void setVisiblePanel(String panelName)
   {
      for(TIMPanel panel : panelList)
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
   
   public void keyPressed(KeyEvent ke)
   {
      if(curPanel != null)
         curPanel.keyPressed(ke);
   }
   public void keyTyped(KeyEvent ke)
   {
      if(curPanel != null)
         curPanel.keyTyped(ke);
   }
   public void keyReleased(KeyEvent ke)
   {
      if(curPanel != null)
         curPanel.keyReleased(ke);
   }
}