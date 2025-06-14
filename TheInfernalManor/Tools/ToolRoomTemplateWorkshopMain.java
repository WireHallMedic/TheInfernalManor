
package TheInfernalManor.Tools;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.*;
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
   private int curConnectionType;
   private int curConnectionIndex;
   private char[] charArr;
   private JButton[] charButtonArr;
   private JLabel currentlySelectedL;
   private JLabel typeCountL;
   private JButton[] connectionButtonArr;
   private JLabel currentRoomL;
   private JRadioButton setB;
   private JRadioButton iRB;
   private JRadioButton dRB;
   private char selectedChar;
   private static final int SET_COLOR = Color.BLACK.getRGB();
   private static final int I_R_COLOR = Color.BLUE.darker().getRGB();
   private static final int D_R_COLOR = Color.RED.darker().getRGB();
   private RoomTemplate curRoomTemplate;
   private RoomTemplateDeck deck;
   private JButton saveB;
   private JButton loadB;
   private JButton newRoomB;
   private JButton newDeckB;
   private JButton deleteRoomB;
   private JButton nextConnTypeB;
   private JButton prevConnTypeB;
   private JButton nextRoomB;
   private JButton prevRoomB;
   private int roomSize;
   private String fileName;
   
   public ToolRoomTemplateWorkshopMain()
   {
      super();
      setSize(1400, 1000);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setTitle("Room Template Workshop");
      selectedChar = '.';
      fileName = "";
      deck = new RoomTemplateDeck();
      roomSize = 21;
      roomTemplate = new RoomTemplate(roomSize, roomSize);
      deck.add(roomTemplate);
      setLocationValues();
      
      setLayout(new GridLayout(1, 2));
      
      mapPanel = new JPanel();
      mapPanel.setLayout(new GridLayout(1, 1));
      add(mapPanel);
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(3, 1));
      add(controlPanel);
      
      controlSubpanel1 = new JPanel();
      controlSubpanel1.setLayout(new GridLayout(1, 1));
      controlPanel.add(controlSubpanel1);
      controlSubpanel2 = new JPanel();
      controlSubpanel2.setLayout(new GridLayout(1, 1));
      controlPanel.add(controlSubpanel2);
      controlSubpanel3 = new JPanel();
      controlSubpanel3.setLayout(new GridLayout(1, 1));
      controlPanel.add(controlSubpanel3);
      
      palette = new SCTilePalette("WidlerTiles_16x16.png", 16, 16);
      drawingPanel = new SCPanel(palette, roomSize, roomSize);
      drawingPanel.addMouseListener(this);
      mapPanel.add(drawingPanel);
      
      setDrawingButtons();
      setConnectionButtons();
      setControlButtons();
      setDrawingPanel();
      updateCurrentLabels();
      setVisible(true);
      
   }
   
   private void setControlButtons()
   {
      newRoomB = new JButton("New Room");
      deleteRoomB = new JButton("Delete Room");
      saveB = new JButton("Save");
      loadB = new JButton("Load");
      newDeckB = new JButton("New Set");
      
      JPanel anonPanel = new JPanel();
      anonPanel.setLayout(new GridLayout(6, 1));
      
      JPanel subPanel = new JPanel();
      subPanel.setLayout(new GridLayout(1, 2));
      newRoomB = new JButton("New Room");
      newRoomB.addActionListener(this);
      subPanel.add(newRoomB);
      deleteRoomB = new JButton("Delete Room");
      deleteRoomB.addActionListener(this);
      subPanel.add(deleteRoomB);
      anonPanel.add(subPanel);
      
      subPanel = new JPanel();
      subPanel.setLayout(new GridLayout(1, 3));
      saveB = new JButton("Save");
      saveB.addActionListener(this);
      subPanel.add(saveB);
      loadB = new JButton("Load");
      loadB.addActionListener(this);
      subPanel.add(loadB);
      newDeckB = new JButton("New Group");
      newDeckB.addActionListener(this);
      subPanel.add(newDeckB);
      anonPanel.add(subPanel);
      
      controlSubpanel3.add(anonPanel);
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
      anonPanel.setLayout(new GridLayout(6, 1));
      controlSubpanel1.add(anonPanel);
      JPanel[] anonSubpanel = new JPanel[6];
      for(int i = 0; i < 6; i++)
      {
         anonSubpanel[i] = new JPanel();
         if(i < (len + 2) / 3)
            anonSubpanel[i].setLayout(new GridLayout(1, 3));
         else
            anonSubpanel[i].setLayout(new GridLayout(1, 1));
         anonPanel.add(anonSubpanel[i]);
      }
      for(int i = 0; i < len; i++)
      {
         String str = ConnectionType.values()[i].name;
         connectionButtonArr[i] = new JButton(str);
         connectionButtonArr[i].addActionListener(this);
         anonSubpanel[i / 3].add(connectionButtonArr[i]);
      }
      int curIndex = len / 3;
      typeCountL = new JLabel("Type Count: ");
      anonSubpanel[curIndex].add(typeCountL);
      curIndex++;
      
      anonSubpanel[curIndex].setLayout(new GridLayout(1, 4));
      prevConnTypeB = new JButton("Prev Type");
      prevConnTypeB.addActionListener(this);
      anonSubpanel[curIndex].add(prevConnTypeB);
      nextConnTypeB = new JButton("Next Type");
      nextConnTypeB.addActionListener(this);
      anonSubpanel[curIndex].add(nextConnTypeB);
      prevRoomB = new JButton("Prev Room");
      prevRoomB.addActionListener(this);
      anonSubpanel[curIndex].add(prevRoomB);
      nextRoomB = new JButton("Next Room");
      nextRoomB.addActionListener(this);
      anonSubpanel[curIndex].add(nextRoomB);
      curIndex++;
      
      currentRoomL = new JLabel("Connection Type: ");
      anonSubpanel[curIndex].add(currentRoomL);
      curIndex++;
      
      while(curIndex < 6)
      {
         anonSubpanel[curIndex].add(new JPanel());
         curIndex++;
      }
   }
   
   private void updateCurrentLabels()
   {
      currentlySelectedL.setText("Currently Selected: " + selectedChar);
      roomTemplate.setConnectionType();
      deck.sort(roomTemplate);
      setLocationValues();
      setCountLabel();
      ConnectionType ct = ConnectionType.values()[curConnectionType];
      String curStr = "Current Room: %s (%d/%d)";
      currentRoomL.setText(String.format(curStr, ct.name, curConnectionIndex + 1, deck.size(ct)));
   }
   
   private void setLocationValues()
   {
      int[] indexes = deck.getIndex(roomTemplate);
      curConnectionType = indexes[0];
      curConnectionIndex = indexes[1];
   }
   
   public void setDrawingPanel()
   {
      for(int x = 0; x < roomSize; x++)
      for(int y = 0; y < roomSize; y++)
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
      if(ae.getSource() == newRoomB)
      {
         roomTemplate = new RoomTemplate(roomSize, roomSize);
         deck.add(roomTemplate);
         setDrawingPanel();
      }
      if(ae.getSource() == saveB){save();}
      if(ae.getSource() == loadB){load();}
      if(ae.getSource() == newDeckB){}
      if(ae.getSource() == deleteRoomB){}
      
      
      if(ae.getSource() == nextConnTypeB)
      {
         int newRoom = curConnectionIndex;
         int newType = curConnectionType;
         do
         {
            newType++;
            if(newType >= ConnectionType.values().length)
               newType = 0;
         }
         while(deck.size(newType) == 0);
         if(newRoom >= deck.size(newType))
            newRoom = deck.size(newType) - 1;
         roomTemplate = deck.get(newType, newRoom);
         setDrawingPanel();
      }
      if(ae.getSource() == prevConnTypeB)
      {
         int newRoom = curConnectionIndex;
         int newType = curConnectionType;
         do
         {
            newType--;
            if(newType < 0)
               newType = ConnectionType.values().length - 1;
         }
         while(deck.size(newType) == 0);
         if(newRoom >= deck.size(newType))
            newRoom = deck.size(newType) - 1;
         roomTemplate = deck.get(newType, newRoom);
         setDrawingPanel();
      }
      if(ae.getSource() == nextRoomB)
      {
         int newRoom = curConnectionIndex + 1;
         if(newRoom >= deck.size(curConnectionType))
            newRoom = 0;
         roomTemplate = deck.get(curConnectionType, newRoom);
         setDrawingPanel();
      }
      if(ae.getSource() == prevRoomB)
      {
         int newRoom = curConnectionIndex - 1;
         if(newRoom < 0)
            newRoom = deck.size(curConnectionType) - 1;
         roomTemplate = deck.get(curConnectionType, newRoom);
         setDrawingPanel();
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
   
   private void setCountLabel()
   {
      String output = "";
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         ConnectionType ct = ConnectionType.values()[i];
         output += ct.name + ": " + deck.size(ct);
         if(i != ConnectionType.values().length - 1)
            output += ", ";
      }
      typeCountL.setText("Type Count: " + output);
   }
   
   private void save()
   {
      String saveLoc = "";
      saveLoc = JOptionPane.showInputDialog(this, "Enter file name: ", fileName);
      if(saveLoc == null || saveLoc.equals(""))
      {
         JOptionPane.showMessageDialog(this, "Save Attempt Aborted", "File name cannot be blank.", JOptionPane.ERROR_MESSAGE);
         return;
      }
      fileName = saveLoc;
      if(!fileName.contains(".ttd"))
         fileName = fileName + ".ttd";
      
      PrintWriter outFile;
		Vector<String> saveStringList = deck.serialize();
		try
		{
			outFile = new PrintWriter(fileName);
		
			for(String str : saveStringList)
			{
				outFile.print(str);
			}
			
			outFile.close();
		}
		catch(Exception ex){System.out.println("Exception: " + ex.toString());}
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
         fileName = chooser.getSelectedFile().getAbsolutePath();
   		Vector<String> saveString = new Vector<String>();
   		try
   		{
   			Scanner inFile = new Scanner(new FileReader(fileName));
   			while(inFile.hasNext())
   				saveString.add(inFile.nextLine().replace("\n", ""));
   			inFile.close();
            deck = new RoomTemplateDeck(saveString);
            roomTemplate = deck.getFirstRoom();
            setDrawingPanel();
            updateCurrentLabels();
   		}
   		catch(Exception ex){System.out.println("Exception: " + ex.toString());}
      }
   }
   
   public static final void main(String[] args)
   {
      ToolRoomTemplateWorkshopMain frame = new ToolRoomTemplateWorkshopMain();
   }
}