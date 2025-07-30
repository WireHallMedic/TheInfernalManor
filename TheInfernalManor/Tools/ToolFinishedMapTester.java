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


public class ToolFinishedMapTester extends JFrame implements ActionListener, GUIConstants, MapConstants, MouseListener, MouseMotionListener
{
   private LayoutPanel layoutPanel;
   private SCPanel mapPanel;
   private JPanel controlPanel;
   private JComboBox<MapConstants.MapType> typeDD;
   private JComboBox<MapConstants.MapSize> sizeDD;
   private JCheckBox showRoomsCB;
   private JButton rerollB;
   private ZoneMap zoneMap;
   private int tilesWide = 80;
   private int tilesTall = 60;
   private int xCorner = 0;
   private int yCorner = 0;
   private int[] lastMouseLoc = {0, 0};
   
   public ToolFinishedMapTester()
   {
      super();
      setLayout(null);
      layoutPanel = new LayoutPanel(this);
      add(layoutPanel);
      
      mapPanel = new SCPanel(new SCTilePalette("WidlerTiles_16x16.png", 16, 16), tilesWide, tilesTall);
      mapPanel.setCenterImage(true);
      mapPanel.addMouseListener(this);
      mapPanel.addMouseMotionListener(this);
      layoutPanel.add(mapPanel, .8, 1.0, 0.0, 0.0);
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(12, 1));
      
      typeDD = new JComboBox<MapConstants.MapType>(MapConstants.MapType.values());
      typeDD.addActionListener(this);
      controlPanel.add(typeDD);
      
      sizeDD = new JComboBox<MapConstants.MapSize>(MapConstants.MapSize.values());
      sizeDD.addActionListener(this);
      controlPanel.add(sizeDD);
      
      showRoomsCB = new JCheckBox("Show Rooms");
      showRoomsCB.addActionListener(this);
      controlPanel.add(showRoomsCB);
      
      controlPanel.add(new JLabel());
      rerollB = new JButton("Reroll Map");
      rerollB.addActionListener(this);
      controlPanel.add(rerollB);
      layoutPanel.add(controlPanel, .2, 1.0, .8, 0.0);
      
      setSize(1600, 1000);
      setTitle("Finished Map Tester");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      zoneMap = null;
      rerollB.doClick();
      
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
         if(showRoomsCB.isSelected())
            for(TIMRoom room : zoneMap.getRoomList())
            {
               for(int x = 0; x < room.size.x; x++)
               for(int y = 0; y < room.size.y; y++)
                  mapPanel.fillTileFG(room.origin.x - xCorner, room.origin.y - yCorner, room.size.x, room.size.y, RED);
            }
      }
      mapPanel.repaint();
   }


   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() != showRoomsCB)
         zoneMap = ZoneMapFactory.generateZoneMap((MapConstants.MapType)typeDD.getSelectedItem(), 
                                                  (MapConstants.MapSize)sizeDD.getSelectedItem());
      redrawMap();
   }
   
   public void mouseClicked(MouseEvent me)
   {
      int[] mouseLoc = mapPanel.getMouseLocTile();
      int xShift = mouseLoc[0] - ((tilesWide + 1)/ 2);
      int yShift = mouseLoc[1] - ((tilesTall + 1) / 2);
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
      ToolFinishedMapTester fmt = new ToolFinishedMapTester();
   }
}