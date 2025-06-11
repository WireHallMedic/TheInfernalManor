
package TheInfernalManor.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Map.*;
import StrictCurses.*;

public class ToolRoomTemplateWorkshopMain extends JFrame implements ActionListener, MouseListener, MapConstants
{
   private JPanel mapPanel;
   private JPanel controlPanel;
   private JPanel controlSubpanel1;
   private JPanel controlSubpanel2;
   private JPanel controlSubpanel3;
   private SCTilePalette palette;
   private SCPanel drawingPanel;
   private RoomTemplate roomTemplate;
   private char[] charArr;
   private JButton[] charButtonArr;
   private JLabel currentlySelectedL;
   private JButton[] connectionButtonArr;
   private JLabel connectionL;
   private JRadioButton setB;
   private JRadioButton iRB;
   private JRadioButton dRB;
   private char selectedChar;
   private static final int SET_COLOR = Color.BLACK.getRGB();
   private static final int I_R_COLOR = Color.BLUE.darker().getRGB();
   private static final int D_R_COLOR = Color.RED.darker().getRGB();
   private RoomTemplate curRoomTemplate;
   
   public ToolRoomTemplateWorkshopMain()
   {
      super();
      setSize(1400, 1000);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setTitle("Room Template Workshop");
      selectedChar = '.';
      
      setLayout(new GridLayout(1, 2));
      
      mapPanel = new JPanel();
      mapPanel.setLayout(new GridLayout(1, 1));
      add(mapPanel);
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(3, 1));
      add(controlPanel);
      
      controlSubpanel1 = new JPanel();
      controlSubpanel1.setLayout(new GridLayout(3, 1));
      controlPanel.add(controlSubpanel1);
      controlSubpanel2 = new JPanel();
      controlPanel.add(controlSubpanel2);
      controlSubpanel3 = new JPanel();
      controlPanel.add(controlSubpanel3);
      
      palette = new SCTilePalette("WidlerTiles_16x16.png", 16, 16);
      drawingPanel = new SCPanel(palette, 21, 21);
      drawingPanel.addMouseListener(this);
      mapPanel.add(drawingPanel);
      
      roomTemplate = new RoomTemplate(21, 21);
      
      for(int x = 0; x < 21; x++)
      for(int y = 0; y < 21; y++)
         roomTemplate.set(x, y, '.', false, false);
      
      setDrawingButtons();
      setConnectionButtons();
      setDrawingPanel();
      updateCurrentLabels();
      setVisible(true);
      
   }
   
   private void setDrawingButtons()
   {
      int len = RoomTemplateCellMapping.values().length;
      controlSubpanel2.setLayout(new GridLayout((len + 4) / 3, 2));
      charArr = new char[len];
      charButtonArr = new JButton[len];
      for(int i = 0; i < len; i++)
      {
         char c = RoomTemplateCellMapping.values()[i].character;
         charButtonArr[i] = new JButton("" + c);
         charArr[i] = c;
         charButtonArr[i].addActionListener(this);
         controlSubpanel2.add(charButtonArr[i]);
      }
      JPanel anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(1, 3));
      ButtonGroup group = new ButtonGroup();
      setB = new JRadioButton("Set");
      setB.setSelected(true);
      setB.addActionListener(this);
      setB.setForeground(Color.WHITE);
      setB.setBackground(new Color(SET_COLOR));
      group.add(setB);
      anonPanel.add(setB);
      iRB = new JRadioButton("iRand");
      iRB.addActionListener(this);
      iRB.setForeground(Color.WHITE);
      iRB.setBackground(new Color(I_R_COLOR));
      group.add(iRB);
      anonPanel.add(iRB);
      dRB = new JRadioButton("dRand");
      dRB.addActionListener(this);
      dRB.setForeground(Color.WHITE);
      dRB.setBackground(new Color(D_R_COLOR));
      group.add(dRB);
      anonPanel.add(dRB);
      controlSubpanel2.add(anonPanel);
      
      currentlySelectedL = new JLabel("Currently Selected: ");
      controlSubpanel2.add(currentlySelectedL);
   }
   
   private void setConnectionButtons()
   {
      int len = ConnectionType.values().length;
      connectionButtonArr = new JButton[len];
      JPanel anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(3, 3));
      controlSubpanel1.add(anonPanel);
      
      for(int i = 0; i < len; i++)
      {
         String str = ConnectionType.values()[i].name;
         connectionButtonArr[i] = new JButton(str);
         connectionButtonArr[i].addActionListener(this);
         anonPanel.add(connectionButtonArr[i]);
      }
      connectionL = new JLabel("Connection Type: ");
      anonPanel.add(connectionL);
   }
   
   private void updateCurrentLabels()
   {
      currentlySelectedL.setText("Currently Selected: " + selectedChar);
      roomTemplate.setConnectionType();
      connectionL.setText("Connection Type: " + roomTemplate.getConnectionType().name);
   }
   
   public void setDrawingPanel()
   {
      for(int x = 0; x < 21; x++)
      for(int y = 0; y < 21; y++)
      {
         int c = SET_COLOR;
         if(roomTemplate.isIndependentlyRandom(x, y))
            c = I_R_COLOR;
         if(roomTemplate.isDependentlyRandom(x, y))
            c = D_R_COLOR;
         drawingPanel.setTileIndex(x, y, roomTemplate.getCellMapping(x, y).character);
         drawingPanel.setTileBG(x, y, c);
      }
      drawingPanel.repaint();
   }
   
   private void mouseClickedInDrawingPanel(MouseEvent me)
   {
      int[] mouseLoc = drawingPanel.getMouseLocTile();
      if(!roomTemplate.isInBounds(mouseLoc[0], mouseLoc[1]))
         return;
      if(me.getButton() == MouseEvent.BUTTON1)
      {
         boolean iR = iRB.isSelected();
         boolean dR = dRB.isSelected();
         roomTemplate.set(mouseLoc[0], mouseLoc[1], selectedChar, iR, dR);
      }
      if(me.getButton() == MouseEvent.BUTTON3)
      {
         char c = roomTemplate.getChar(mouseLoc[0], mouseLoc[1]);
         if(c != (char)0)
         {
            pressCharButton(c);
            if(roomTemplate.isIndependentlyRandom(mouseLoc[0], mouseLoc[1]))
               iRB.doClick();
            else if(roomTemplate.isDependentlyRandom(mouseLoc[0], mouseLoc[1]))
               dRB.doClick();
            else
               setB.doClick();
         }
      }
      setDrawingPanel();
      updateCurrentLabels();
   }
   
   private void pressCharButton(char c)
   {
      for(int i = 0; i < RoomTemplateCellMapping.values().length; i++)
      {
         if(c == charArr[i])
            charButtonArr[i].doClick();
      }
   }
   
   // listeners
   public void mouseClicked(MouseEvent me){mouseClickedInDrawingPanel(me);}
   public void mousePressed(MouseEvent me){}
   public void mouseReleased(MouseEvent me){}
   public void mouseEntered(MouseEvent me){}
   public void mouseExited(MouseEvent me){}
   
   public void actionPerformed(ActionEvent ae)
   {
      for(int i = 0; i < RoomTemplateCellMapping.values().length; i++)
      {
         if(ae.getSource() == charButtonArr[i])
         {
            selectedChar = charArr[i];
            break;
         }
      }
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         if(ae.getSource() == connectionButtonArr[i])
         {
            setWalls(ConnectionType.values()[i]);
            break;
         }
      }
      updateCurrentLabels();
   }
   
   private void setWalls(ConnectionType type)
   {
      switch(type)
      {
         case ISOLATED :   fillNorthWall('#');
                           fillEastWall('#');
                           fillSouthWall('#');
                           fillWestWall('#');
                           break;
         case TERMINAL :   fillNorthWall('.');
                           fillEastWall('#');
                           fillSouthWall('#');
                           fillWestWall('#');
                           break;
         case STRAIGHT :   fillNorthWall('.');
                           fillSouthWall('.');
                           fillEastWall('#');
                           fillWestWall('#');
                           break;
         case ELBOW :      fillNorthWall('.');
                           fillEastWall('.');
                           fillSouthWall('#');
                           fillWestWall('#');
                           break;
         case TEE :        fillNorthWall('.');
                           fillEastWall('.');
                           fillSouthWall('.');
                           fillWestWall('#');
                           break;
         case CROSS :      fillNorthWall('.');
                           fillEastWall('.');
                           fillSouthWall('.');
                           fillWestWall('.');
                           break;
      }
      setDrawingPanel();
   }
   
   private void fillWestWall(char c)
   {
      for(int i = 0; i < roomTemplate.getHeight(); i++)
         roomTemplate.set(0, i, c, false, false);
   }
   private void fillEastWall(char c)
   {
      for(int i = 0; i < roomTemplate.getHeight(); i++)
         roomTemplate.set(roomTemplate.getWidth() - 1, i, c, false, false);
   }
   private void fillNorthWall(char c)
   {
      for(int i = 0; i < roomTemplate.getWidth(); i++)
         roomTemplate.set(i, 0, c, false, false);
   }
   private void fillSouthWall(char c)
   {
      for(int i = 0; i < roomTemplate.getWidth(); i++)
         roomTemplate.set(i, roomTemplate.getWidth() - 1, c, false, false);
   }
   
   public static final void main(String[] args)
   {
      ToolRoomTemplateWorkshopMain frame = new ToolRoomTemplateWorkshopMain();
   }
}