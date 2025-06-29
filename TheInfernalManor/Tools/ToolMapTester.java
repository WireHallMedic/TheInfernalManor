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
   private static final String[] layoutList = {"MapGrid", "GridOfGrids", "BSP", "BSP Islands", "BSPVillage"};
   private LayoutPanel layoutPanel;
   private SCPanel mapPanel;
   private JPanel controlPanel;
   private MapGrid mapGrid = null;
   private GridOfMapGrids gridOfGrids = null;
   private RoomTemplateDeck deck = null;
   private Vector<TIMRoom> roomList = null;
   private ZoneMap zoneMap;
   // basic controls
   private JButton loadDeckB;
   private JComboBox<String> mapTypeDD;
   private JButton rollGridB;
   private JButton rollTemplateB;
   private JButton rollRandomB;
   private JCheckBox showRoomsCB;
   // grid controls
   private JTextField roomsWideF;
   private JTextField roomsTallF;
   private JTextField connectivityF;
   private JTextField mRRF;
   private JCheckBox maxConnectionsCB;
   // grid of grid controls
   private JTextField lowerRoomsWideF;
   private JTextField lowerRoomsTallF;
   private JTextField lowerConnectivityF;
   private JTextField lowerMRRF;
   private JCheckBox lowerMaxConnectionsCB;
   // BSP controls
   private JTextField bspMaxRoomF;
   private JTextField bspMinRoomF;
   private JTextField bspConnectivityChanceF;
   private JTextField bspConnectivityRatioF;
   // BSP Dungeon controls
   private JTextField dungeonRoomMinF;
   private JTextField dungeonRoomMaxF;
   private JPanel upperControlPanel;
   private JPanel lowerControlPanel;
   private JPanel gridPanel;
   private JPanel bspPanel;
   private boolean gridPanelVisible;
   private static final int mapWidth = 80;
   private static final int mapHeight = 60;
   private int generationType = 0;
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
      controlPanel.setLayout(new GridLayout(2, 1));
      upperControlPanel = new JPanel();
      upperControlPanel.setLayout(new GridLayout(6, 1));
      controlPanel.add(upperControlPanel);
      
      lowerControlPanel = new JPanel();
      lowerControlPanel.setLayout(new GridLayout(1, 1));
      controlPanel.add(lowerControlPanel);
      
      gridPanel = new JPanel();
      gridPanel.setLayout(new GridLayout(12, 1));
      lowerControlPanel.add(gridPanel);
      gridPanelVisible = true;
      
      bspPanel = new JPanel();
      bspPanel.setLayout(new GridLayout(12, 1));
      // swaps out with gridPanel
      
      loadDeckB = new JButton("Load Deck");
      loadDeckB.addActionListener(this);
      upperControlPanel.add(loadDeckB);
      
      mapTypeDD = new JComboBox<String>(layoutList);
      mapTypeDD.addActionListener(this);
      upperControlPanel.add(mapTypeDD);
      
      rollGridB = new JButton("Reroll Grid");
      rollGridB.addActionListener(this);
      upperControlPanel.add(rollGridB);
      
      rollTemplateB = new JButton("Reroll Templates");
      rollTemplateB.addActionListener(this);
      upperControlPanel.add(rollTemplateB);
      
      rollRandomB = new JButton("Reroll Random Tiles");
      rollRandomB.addActionListener(this);
      upperControlPanel.add(rollRandomB);
      
      showRoomsCB = new JCheckBox("Show Rooms");
      showRoomsCB.addActionListener(this);
      upperControlPanel.add(showRoomsCB);
      
      JPanel anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Rooms Wide"));
      roomsWideF = new JTextField("5");
      anonPanel.add(roomsWideF);
      gridPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Rooms Tall"));
      roomsTallF = new JTextField("5");
      anonPanel.add(roomsTallF);
      gridPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Connectivity"));
      connectivityF = new JTextField(".5");
      anonPanel.add(connectivityF);
      gridPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Min Room Ratio"));
      mRRF = new JTextField(".75");
      anonPanel.add(mRRF);
      gridPanel.add(anonPanel);
      
      maxConnectionsCB = new JCheckBox("Maximize Connections");
      gridPanel.add(maxConnectionsCB);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Rooms Wide"));
      lowerRoomsWideF = new JTextField("3");
      anonPanel.add(lowerRoomsWideF);
      gridPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Rooms Tall"));
      lowerRoomsTallF = new JTextField("3");
      anonPanel.add(lowerRoomsTallF);
      gridPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Connectivity"));
      lowerConnectivityF = new JTextField(".5");
      anonPanel.add(lowerConnectivityF);
      gridPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Min Room Ratio"));
      lowerMRRF = new JTextField(".75");
      anonPanel.add(lowerMRRF);
      gridPanel.add(anonPanel);
      
      lowerMaxConnectionsCB = new JCheckBox("Maximize Lower Connections");
      gridPanel.add(lowerMaxConnectionsCB);
      
      // BSP controls
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Maximum Room Size"));
      bspMaxRoomF = new JTextField("25");
      anonPanel.add(bspMaxRoomF);
      bspPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Minimum Room Size"));
      bspMinRoomF = new JTextField("15");
      anonPanel.add(bspMinRoomF);
      bspPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Extra Connectivity Chance"));
      bspConnectivityChanceF = new JTextField(".5");
      anonPanel.add(bspConnectivityChanceF);
      bspPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Extra Connectivity Ratio"));
      bspConnectivityRatioF = new JTextField(".5");
      anonPanel.add(bspConnectivityRatioF);
      bspPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Room Min Size"));
      dungeonRoomMinF = new JTextField("5");
      anonPanel.add(dungeonRoomMinF);
      bspPanel.add(anonPanel);
      
      anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 2));
      anonPanel.add(new JLabel("Lower Room Min Size"));
      dungeonRoomMaxF = new JTextField("10");
      anonPanel.add(dungeonRoomMaxF);
      bspPanel.add(anonPanel);
      
      layoutPanel.add(controlPanel, .2, 1.0, .8, 0.0);
      
      generateRoomList();
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
         if(deck != null)
         {
            if(generationType == 0)
               generateMapGrid();
            else if(generationType == 1)
               generateGridOfMapGrids();
            generateZoneMap();
         }
      }
      if(ae.getSource() == rollGridB)
      {
         if(generationType == 0)
            generateMapGrid();
         else if(generationType == 1)
            generateGridOfMapGrids();
         else if(generationType == 2)
            generateRoomList();
         else if(generationType == 3)
            generateRoomList();
         xCorner = 0;
         yCorner = 0;
         generateZoneMap();
      }
      if(ae.getSource() == rollTemplateB)
      {
         if(generationType == 0)
            mapGrid.populateTemplateMap();
         else if(generationType == 1)
            gridOfGrids.populateTemplateMaps();
         generateZoneMap();
      }
      if(ae.getSource() == rollRandomB)
      {
         generateZoneMap();
      }
      if(ae.getSource() == mapTypeDD)
      {
         generationType = mapTypeDD.getSelectedIndex();
         if(generationType == 0 ||generationType == 1)
         {
            if(!gridPanelVisible)
               swapLowerControlPanel();
         }
         if(generationType == 2 || generationType == 3 || generationType == 4)
         {
            if(gridPanelVisible)
               swapLowerControlPanel();
            generateRoomList();
            generateZoneMap();
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
         if(showRoomsCB.isSelected())
         {
            for(TIMRoom room : zoneMap.getRoomList())
            {
               if(!room.isParent)
                  mapPanel.fillTileFG(room.origin.x - xCorner, room.origin.y - yCorner, room.size.x, room.size.y, RED);
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
            if(!deck.isUniformSquareSize())
            {
               deck = null;
               JOptionPane.showMessageDialog(this, "Tile set must be squares of uniform size.","Load Attempt Aborted",  JOptionPane.ERROR_MESSAGE);
            }
   		}
   		catch(Exception ex){System.out.println("Exception while loading: " + ex.toString());}
      }
   }
   
   private void generateRoomList()
   {
      try
      {
         int roomMax = Integer.parseInt(bspMaxRoomF.getText());
         int roomMin = Integer.parseInt(bspMinRoomF.getText());
         TIMBinarySpacePartitioning.setPartitionChance(.5);
         if(generationType == 4)
            roomList = TIMBinarySpacePartitioning.partition(mapWidth - 2, mapHeight - 2, roomMin, roomMax);
         else
            roomList = TIMBinarySpacePartitioning.partition(mapWidth, mapHeight, roomMin, roomMax);
      }
      catch(Exception ex)
      {
         System.out.println("Exception when generating roomList: " + ex.toString());
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
      if(generationType == 0)
      {
         if(mapGrid != null)
         {
            zoneMap = GridZoneMapFactory.generate(mapGrid.getTemplateMap());
         }
         else
            zoneMap = null;
      }
      else if(generationType == 1)
      {
         if(gridOfGrids != null)
         {
            zoneMap = GridZoneMapFactory.generate(gridOfGrids, 10);
         }
         else
            zoneMap = null;
      }
      else if(generationType == 2)
      {
         if(roomList.size() == 0)
            generateRoomList();
         try
         {
            double connChance = Double.parseDouble(bspConnectivityChanceF.getText());
            double connRatio = Double.parseDouble(bspConnectivityRatioF.getText());
            zoneMap = BSPZoneMapFactory.generate(roomList, connChance, connRatio);
         }
         catch(Exception ex)
         {
            System.out.println("Exception when generating BSP map: " + ex.toString());
         }
      }
      else if(generationType == 3)
      {
         if(roomList.size() == 0)
            generateRoomList();
         try
         {
            double connChance = Double.parseDouble(bspConnectivityChanceF.getText());
            double connRatio = Double.parseDouble(bspConnectivityRatioF.getText());
            int lowerMin = Integer.parseInt(dungeonRoomMinF.getText());
            int lowerMax = Integer.parseInt(dungeonRoomMaxF.getText());
            zoneMap = BSPZoneMapFactory.generateDungeon(roomList, lowerMin, lowerMax, connChance, connRatio);
         }
         catch(Exception ex)
         {
            System.out.println("Exception when generating BSP Island map: " + ex.toString());
         }
      }
      else if(generationType == 4)
      {
         if(roomList.size() == 0)
            generateRoomList();
         try
         {
            double connChance = Double.parseDouble(bspConnectivityChanceF.getText());
            double connRatio = Double.parseDouble(bspConnectivityRatioF.getText());
            int lowerMin = Integer.parseInt(dungeonRoomMinF.getText());
            int lowerMax = Integer.parseInt(dungeonRoomMaxF.getText());
            zoneMap = BSPZoneMapFactory.generateVillage(roomList, lowerMin, lowerMax, connChance, connRatio);
         }
         catch(Exception ex)
         {
            System.out.println("Exception when generating BSP Village map: " + ex.toString());
         }
      }
      zoneMap.applyPalette(MapPalette.getBasePalette());
   }
   
   private void swapLowerControlPanel()
   {
      gridPanelVisible = !gridPanelVisible;
      if(gridPanelVisible)
      {
         lowerControlPanel.remove(bspPanel);
         lowerControlPanel.add(gridPanel);
      }
      else
      {
         lowerControlPanel.remove(gridPanel);
         lowerControlPanel.add(bspPanel);
      }
      lowerControlPanel.revalidate();
      lowerControlPanel.repaint();
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