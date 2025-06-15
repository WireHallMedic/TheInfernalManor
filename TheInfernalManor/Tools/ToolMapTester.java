package TheInfernalManor.Tools;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Map.*;
import WidlerSuite.*;
import StrictCurses.*;


public class ToolMapTester extends JFrame implements ActionListener
{
   private LayoutPanel layoutPanel;
   private SCPanel mapPanel;
   private JPanel controlPanel;
   private MapGrid mapGrid = null;
   private RoomTemplateDeck deck = null;
   private JButton loadDeckB;
   private JButton rollGridB;
   private JButton rollTemplateB;
   private JTextField roomsWideF;
   private JTextField roomsTallF;
   private static final int mapWidth = 80;
   private static final int mapHeight = 60;
   
   public ToolMapTester()
   {
      super();
      setLayout(null);
      layoutPanel = new LayoutPanel(this);
      add(layoutPanel);
      
      mapPanel = new SCPanel(new SCTilePalette("WidlerTiles_16x16.png", 16, 16), mapWidth, mapHeight);
      layoutPanel.add(mapPanel, .8, 1.0, 0.0, 0.0);
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(10, 1));
      
      loadDeckB = new JButton("Load Deck");
      loadDeckB.addActionListener(this);
      controlPanel.add(loadDeckB);
      
      rollGridB = new JButton("Reroll Grid");
      rollGridB.addActionListener(this);
      controlPanel.add(rollGridB);
      
      rollTemplateB = new JButton("Reroll Templates");
      rollTemplateB.addActionListener(this);
      controlPanel.add(rollTemplateB);
      
      JPanel anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Rooms Wide"));
      roomsWideF = new JTextField("5");
      anonPanel.add(roomsWideF);
      controlPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Rooms Tall"));
      roomsTallF = new JTextField("5");
      anonPanel.add(roomsTallF);
      controlPanel.add(anonPanel);
      
      layoutPanel.add(controlPanel, .2, 1.0, .8, 0.0);
      
      setSize(1600, 1000);
      setTitle("Map Tester");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == loadDeckB){}
      if(ae.getSource() == rollGridB){}
      if(ae.getSource() == rollTemplateB){}
      redrawMap();
   }
   
   private void redrawMap()
   {
      for(int x = 0; x < mapWidth; x++)
      for(int y = 0; y < mapHeight; y++)
         mapPanel.setTileIndex(x, y, '.');
      mapPanel.repaint();
   }
   
   public static void main(String[] args)
   {
      ToolMapTester tmt = new ToolMapTester();
   }
}