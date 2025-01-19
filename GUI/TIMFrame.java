package TheInfernalManor.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import StrictCurses.*;

public class TIMFrame extends JFrame implements SCConstants, ComponentListener
{
   private SCTilePalette palette;
   private Vector<SCPanel> panelList;
   private JPanel basePanel;
   
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
      
      panelList = new Vector<SCPanel>();
      
      setVisible(true);
   }
   
   public void addPanel(SCPanel newPanel)
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
      for(SCPanel panel : panelList)
      {
         panel.setSize(basePanel.getWidth(), basePanel.getHeight());
      }
   }
}