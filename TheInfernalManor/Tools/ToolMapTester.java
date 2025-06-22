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


public class ToolMapTester extends JFrame implements ActionListener, GUIConstants, MapConstants, MouseListener, MouseMotionListener
{
   private LayoutPanel layoutPanel;
   private SCPanel mapPanel;
   private JPanel controlPanel;
   private MapGrid mapGrid = null;
   private GridOfMapGrids gridOfGrids = null;
   private RoomTemplateDeck deck = null;
   private JButton loadDeckB;
   private JComboBox<MapTypes> mapTypeDD;
   private JButton rollGridB;
   private JButton rollTemplateB;
   private JButton rollRandomB;
   private JTextField roomsWideF;
   private JTextField roomsTallF;
   private JTextField connectivityF;
   private JTextField mRRF;
   private JCheckBox maxConnectionsCB;
   private JTextField lowerRoomsWideF;
   private JTextField lowerRoomsTallF;
   private JTextField lowerConnectivityF;
   private JTextField lowerMRRF;
   private JCheckBox lowerMaxConnectionsCB;
   private ZoneMap zoneMap;
   private static final int mapWidth = 80;
   private static final int mapHeight = 60;
   boolean continuousGeneration = true;
   private int xCorner;
   private int yCorner;
   private int[] lastMouseLoc = {0, 0};
   
   public ToolMapTester()
   {
      super();
      setLayout(null);
      layoutPanel = new LayoutPanel(this);
      add(layoutPanel);
      
      mapPanel = new SCPanel(new SCTilePalette("WidlerTiles_16x16.png", 16, 16), mapWidth, mapHeight);
      layoutPanel.add(mapPanel, .8, 1.0, 0.0, 0.0);
      mapPanel.addMouseListener(this);
      mapPanel.addMouseMotionListener(this);
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(15, 1));
      
      loadDeckB = new JButton("Load Deck");
      loadDeckB.addActionListener(this);
      controlPanel.add(loadDeckB);
      
      mapTypeDD = new JComboBox<MapTypes>(MapTypes.values());
      mapTypeDD.addActionListener(this);
      controlPanel.add(mapTypeDD);
      
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
      
      maxConnectionsCB = new JCheckBox("Maximize Connections");
      controlPanel.add(maxConnectionsCB);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Rooms Wide"));
      lowerRoomsWideF = new JTextField("3");
      anonPanel.add(lowerRoomsWideF);
      controlPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Rooms Tall"));
      lowerRoomsTallF = new JTextField("3");
      anonPanel.add(lowerRoomsTallF);
      controlPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Connectivity"));
      lowerConnectivityF = new JTextField(".5");
      anonPanel.add(lowerConnectivityF);
      controlPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Min Room Ratio"));
      lowerMRRF = new JTextField(".75");
      anonPanel.add(lowerMRRF);
      controlPanel.add(anonPanel);
      
      lowerMaxConnectionsCB = new JCheckBox("Maximize Lower Connections");
      controlPanel.add(lowerMaxConnectionsCB);
      
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
         if(continuousGeneration)
            generateMapGrid();
         else
            generateGridOfMapGrids();
         generateZoneMap();
      }
      if(ae.getSource() == rollGridB)
      {
         if(continuousGeneration)
            generateMapGrid();
         else
            generateGridOfMapGrids();
         generateZoneMap();
      }
      if(ae.getSource() == rollTemplateB)
      {
         if(continuousGeneration)
            mapGrid.populateTemplateMap();
         else
            gridOfGrids.populateTemplateMaps();
         generateZoneMap();
      }
      if(ae.getSource() == rollRandomB)
      {
         generateZoneMap();
      }
      if(ae.getSource() == mapTypeDD)
      {
         switch(mapTypeDD.getSelectedItem())
         {
            case MapTypes.ROAD     : 
            case MapTypes.FIELD    :
            case MapTypes.DUNGEON  :
            case MapTypes.CAVERN   : continuousGeneration = true; break;
            case MapTypes.FOREST   :
            case MapTypes.MOUNTAIN :
            case MapTypes.CATACOMB : continuousGeneration = false; break;
            default :
         }
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
         if(maxConnectionsCB.isSelected())
         {
            mapGrid.maximizeConnections();
            mapGrid.populateTemplateMap();
         }
      }
      catch(Exception ex)
      {
         System.out.println("Exception while generating map grid: " + ex.toString());
         mapGrid = null;
      }
   }
   
   private void generateGridOfMapGrids()
   {
      try
      {
         int rWide = Integer.parseInt(roomsWideF.getText());
         int rTall = Integer.parseInt(roomsTallF.getText());
         double conn = Double.parseDouble(connectivityF.getText());
         double ratio = Double.parseDouble(mRRF.getText());
         int lWide = Integer.parseInt(lowerRoomsWideF.getText());
         int lTall = Integer.parseInt(lowerRoomsTallF.getText());
         double lConn = Double.parseDouble(lowerConnectivityF.getText());
         double lRatio = Double.parseDouble(lowerMRRF.getText());
         gridOfGrids = new GridOfMapGrids(rWide, rTall, conn, deck, ratio);
         gridOfGrids.setLowerConnectivity(lConn);
         gridOfGrids.setLowerMinRatio(lRatio);
         gridOfGrids.setLowerWidth(lWide);
         gridOfGrids.setLowerHeight(lTall);
         if(maxConnectionsCB.isSelected())
            gridOfGrids.maximizeConnections();
         if(lowerMaxConnectionsCB.isSelected())
            gridOfGrids.maximizeLowerConnections();
      }
      catch(Exception ex)
      {
         System.out.println("Exception while generating grid of map grids: " + ex.toString());
         mapGrid = null;
      }
   }
   
   private void generateZoneMap()
   {
      if(continuousGeneration)
      {
         if(mapGrid != null)
         {
            zoneMap = ZoneMapFactory.generate(mapGrid.getTemplateMap());
         }
         else
            zoneMap = null;
      }
      else
      {
         if(gridOfGrids != null)
         {
            zoneMap = ZoneMapFactory.generate(gridOfGrids);
         }
         else
            zoneMap = null;
      }
   }
   
   public void mouseClicked(MouseEvent me)
   {
      int[] mouseLoc = mapPanel.getMouseLocTile();
      int xShift = mouseLoc[0] - ((mapWidth + 1)/ 2);
      int yShift = mouseLoc[1] - ((mapHeight + 1) / 2);
      xCorner += xShift;
      yCorner += yShift;
      redrawMap();
   }
   public void mousePressed(MouseEvent me){lastMouseLoc = mapPanel.getMouseLocTile();}
   public void mouseReleased(MouseEvent me){}
   public void mouseEntered(MouseEvent me){}
   public void mouseExited(MouseEvent me){}
   
   public void mouseMoved(MouseEvent me){}
   public void mouseDragged(MouseEvent me)
   {
      mapPanel.mouseMoved(me); // because mouseDragged prevents mouseMoved, which SCPanel needs to update mouseloc
      int[] dragLoc = mapPanel.getMouseLocTile();
      int xShift = lastMouseLoc[0] - dragLoc[0];
      int yShift = lastMouseLoc[1] - dragLoc[1];
      if(xShift != 0 || yShift != 0)
      {
         xCorner += xShift;
         yCorner += yShift;
         lastMouseLoc = dragLoc;
         redrawMap();
      }
   }

   
   public static void main(String[] args)
   {
      ToolMapTester tmt = new ToolMapTester();
   }
}