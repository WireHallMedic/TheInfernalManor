package TheInfernalManor.Tools;

import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Map.*;
import WidlerSuite.*;
import StrictCurses.*;


public class ToolFinishedMapTester extends JFrame implements ActionListener, GUIConstants, MapConstants//, MouseListener, MouseMotionListener
{
   private LayoutPanel layoutPanel;
   private SCPanel mapPanel;
   private JPanel controlPanel;
   private JComboBox<MapConstants.MapType> typeDD;
   private JComboBox<MapConstants.MapSize> sizeDD;
   private JButton rerollB;
   private ZoneMap zoneMap;
   private int tilesWide = 100;
   private int tilesTall = 80;
   private int xCorner = 0;
   private int yCorner = 0;
   
   public ToolFinishedMapTester()
   {
      super();
      setLayout(null);
      layoutPanel = new LayoutPanel(this);
      add(layoutPanel);
      
      mapPanel = new SCPanel(new SCTilePalette("WidlerTiles_16x16.png", 16, 16), tilesWide, tilesTall);
      layoutPanel.add(mapPanel, .8, 1.0, 0.0, 0.0);
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(12, 1));
      
      typeDD = new JComboBox<MapConstants.MapType>(MapConstants.MapType.values());
      typeDD.addActionListener(this);
      controlPanel.add(typeDD);
      
      sizeDD = new JComboBox<MapConstants.MapSize>(MapConstants.MapSize.values());
      sizeDD.addActionListener(this);
      controlPanel.add(sizeDD);
      
      controlPanel.add(new JLabel());
      rerollB = new JButton("Reroll Map");
      rerollB.addActionListener(this);
      controlPanel.add(rerollB);
      layoutPanel.add(controlPanel, .2, 1.0, .8, 0.0);
      
      setSize(1600, 1000);
      setTitle("Finished Map Tester");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      zoneMap = null;
      
      setVisible(true);
      redrawMap();
   }
   

   
   private void redrawMap()
   {
      if(zoneMap == null)
      {
         for(int x = 0; x < tilesWide; x++)
         for(int y = 0; y < tilesTall; y++)
            mapPanel.setTileIndex(x, y, '.');
      }
      else
      {
         for(int x = 0; x < tilesWide; x++)
         for(int y = 0; y < tilesTall; y++)
         {
            if(zoneMap.isInBounds(x + xCorner, y + yCorner))
            {
               MapCell cell = zoneMap.getTile(x + xCorner, y + yCorner);
               mapPanel.setTile(x, y, cell.getIconIndex(), cell.getFGColor(), cell.getBGColor());
            }
            else
            {
               mapPanel.setTile(x, y, ' ', WHITE, BLACK);
            }
         }
      }
      mapPanel.repaint();
   }


   public void actionPerformed(ActionEvent ae)
   {
      zoneMap = ZoneMapFactory.generateZoneMap((MapConstants.MapType)typeDD.getSelectedItem(), 
                                               (MapConstants.MapSize)sizeDD.getSelectedItem());
      redrawMap();
   }
   
   public static void main(String[] args)
   {
      ToolFinishedMapTester fmt = new ToolFinishedMapTester();
   }
}