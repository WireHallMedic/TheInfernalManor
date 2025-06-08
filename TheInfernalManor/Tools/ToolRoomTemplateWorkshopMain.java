
package TheInfernalManor.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Map.*;
import StrictCurses.*;

public class ToolRoomTemplateWorkshopMain extends JFrame implements ActionListener
{
   private JPanel mapPanel;
   private JPanel controlPanel;
   private SCTilePalette palette;
   private SCPanel drawingPanel;
   private SCPanel displayPanel;
   private RoomTemplate roomTemplate;
   
   public ToolRoomTemplateWorkshopMain()
   {
      super();
      setSize(1400, 1000);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setTitle("Room Template Workshop");
      
      setLayout(new GridLayout(1, 2));
      
      mapPanel = new JPanel();
      mapPanel.setLayout(new GridLayout(2, 1));
      add(mapPanel);
      
      controlPanel = new JPanel();
      controlPanel.setBackground(Color.RED);
      add(controlPanel);
      
      palette = new SCTilePalette("WidlerTiles_16x16.png", 16, 16);
      drawingPanel = new SCPanel(palette, 21, 21);
      mapPanel.add(drawingPanel);
      displayPanel = new SCPanel(palette, 21, 21);
      mapPanel.add(displayPanel);
      
      for(int x = 0; x < 21; x++)
      for(int y = 0; y < 21; y++)
         drawingPanel.setTileIndex(x, y, '.');
      
      setVisible(true);
      
      javax.swing.Timer timer = new javax.swing.Timer(1000 / 60, this);
      timer.start();
   }
   
   public void actionPerformed(ActionEvent ae)
   {
   
   }
   
   public static final void main(String[] args)
   {
      ToolRoomTemplateWorkshopMain frame = new ToolRoomTemplateWorkshopMain();
   }
}