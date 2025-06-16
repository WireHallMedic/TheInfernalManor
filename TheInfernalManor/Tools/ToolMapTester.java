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


public class ToolMapTester extends JFrame implements ActionListener, GUIConstants
{
   private LayoutPanel layoutPanel;
   private SCPanel mapPanel;
   private JPanel controlPanel;
   private MapGrid mapGrid = null;
   private RoomTemplateDeck deck = null;
   private JButton loadDeckB;
   private JButton rollGridB;
   private JButton rollTemplateB;
   private JButton rollRandomB;
   private JTextField roomsWideF;
   private JTextField roomsTallF;
   private JTextField connectivityF;
   private JTextField mRRF;
   private ZoneMap zoneMap;
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
      
      rollRandomB = new JButton("Reroll Random Tiles");
      rollRandomB.addActionListener(this);
      controlPanel.add(rollRandomB);
      
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
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Connectivity"));
      connectivityF = new JTextField(".5");
      anonPanel.add(connectivityF);
      controlPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Min Room Ratio"));
      mRRF = new JTextField(".75");
      anonPanel.add(mRRF);
      controlPanel.add(anonPanel);
      
      layoutPanel.add(controlPanel, .2, 1.0, .8, 0.0);
      
      setSize(1600, 1000);
      setTitle("Map Tester");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      zoneMap = null;
      
      setVisible(true);
      redrawMap();
   }
   
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == loadDeckB)
      {
         load();
         generateMapGrid();
         generateZoneMap();
      }
      if(ae.getSource() == rollGridB)
      {
         generateMapGrid();
         generateZoneMap();
      }
      if(ae.getSource() == rollTemplateB)
      {
         mapGrid.populateTemplateMap();
         generateZoneMap();
      }
      if(ae.getSource() == rollRandomB)
      {
         generateZoneMap();
      }
      redrawMap();
   }
   
   private void redrawMap()
   {
      if(zoneMap == null)
      {
         for(int x = 0; x < mapWidth; x++)
         for(int y = 0; y < mapHeight; y++)
            mapPanel.setTileIndex(x, y, '.');
      }
      else
      {
         for(int x = 0; x < mapWidth; x++)
         for(int y = 0; y < mapHeight; y++)
         {
            if(zoneMap.isInBounds(x, y))
            {
               MapCell cell = zoneMap.getTile(x, y);
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
   
   private void load()
   {
      JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
         "TIM Template Deck ", "ttd");
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION) 
      {
         String fileName = chooser.getSelectedFile().getAbsolutePath();
   		Vector<String> saveString = new Vector<String>();
   		try
   		{
   			Scanner inFile = new Scanner(new FileReader(fileName));
   			while(inFile.hasNext())
   				saveString.add(inFile.nextLine().replace("\n", ""));
   			inFile.close();
            deck = new RoomTemplateDeck(saveString);
   		}
   		catch(Exception ex){System.out.println("Exception while loading: " + ex.toString());}
      }
   }
   
   private void generateMapGrid()
   {
      try
      {
         int rWide = Integer.parseInt(roomsWideF.getText());
         int rTall = Integer.parseInt(roomsTallF.getText());
         double conn = Double.parseDouble(connectivityF.getText());
         double ratio = Double.parseDouble(mRRF.getText());
         mapGrid = new MapGrid(rWide, rTall, conn, deck, ratio);
      }
      catch(Exception ex)
      {
         System.out.println("Exception while generating map grid: " + ex.toString());
         mapGrid = null;
      }
   }
   
   private void generateZoneMap()
   {
      if(mapGrid != null)
      {
         zoneMap = ZoneMapFactory.generate(mapGrid.getTemplateMap());
      }
      else
         zoneMap = null;
   }

   
   public static void main(String[] args)
   {
      ToolMapTester tmt = new ToolMapTester();
   }
}