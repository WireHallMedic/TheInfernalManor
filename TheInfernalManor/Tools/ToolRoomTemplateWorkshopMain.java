
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

public class ToolRoomTemplateWorkshopMain extends JFrame implements ActionListener, MouseListener, MouseMotionListener, 
                                                                    MapConstants, KeyListener
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
   private JButton fillAllB;
   private JLabel currentlySelectedL;
   private JLabel typeCountL;
   private JButton[] connectionButtonArr;
   private JLabel completeL;
   private JLabel uniformL;
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
   private JButton dupeRoomB;
   private JButton newDeckB;
   private JButton deleteRoomB;
   private JButton nextConnTypeB;
   private JButton prevConnTypeB;
   private JButton nextRoomB;
   private JButton prevRoomB;
   private JButton addWidthB;
   private JButton reduceWidthB;
   private JButton addHeightB;
   private JButton reduceHeightB;
   private int roomWidth;
   private int roomHeight;
   private String fileName;
   private boolean mouseButtonDown;
   private javax.swing.Timer timer;
   
   public ToolRoomTemplateWorkshopMain()
   {
      super();
      setSize(1400, 1000);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setTitle("Room Template Workshop");
      selectedChar = '.';
      fileName = "";
      deck = new RoomTemplateDeck();
      mouseButtonDown = false;
      roomWidth = 11;
      roomHeight = 11;
      roomTemplate = new RoomTemplate(roomWidth, roomHeight);
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
      resetDrawingPanel();
      
      setDrawingButtons();
      setConnectionButtons();
      setControlButtons();
      setUnfocusable(controlPanel);
      setUnfocusable(mapPanel);
      this.addKeyListener(this);
      setVisible(true);
      
      timer = new javax.swing.Timer(1000 / 25, this);
      timer.start();
      
      connectionButtonArr[0].doClick();
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
      subPanel.setLayout(new GridLayout(1, 3));
      newRoomB = new JButton("New Room");
      newRoomB.addActionListener(this);
      subPanel.add(newRoomB);
      dupeRoomB = new JButton("Duplicate Room");
      dupeRoomB.addActionListener(this);
      subPanel.add(dupeRoomB);
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
      newDeckB = new JButton("New Deck");
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
      anonPanel.setLayout(new GridLayout(7, 1));
      controlSubpanel1.add(anonPanel);
      JPanel[] anonSubpanel = new JPanel[7];
      for(int i = 0; i < anonSubpanel.length; i++)
      {
         anonSubpanel[i] = new JPanel();
         if(i < 3)
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
      fillAllB = new JButton("Fill All with Selected");
      fillAllB.addActionListener(this);
      anonSubpanel[curIndex].add(fillAllB);
      completeL = new JLabel("Complete Deck: ");
      anonSubpanel[curIndex].add(completeL);
      uniformL = new JLabel("Uniform Squares: ");
      anonSubpanel[curIndex].add(uniformL);
      curIndex++;
      
      typeCountL = new JLabel("Type Count: ");
      anonSubpanel[curIndex].add(typeCountL);
      curIndex++;
      
      anonSubpanel[curIndex].setLayout(new GridLayout(1, 4));
      prevConnTypeB = new JButton((char)8592 + " Prev Type");
      prevConnTypeB.addActionListener(this);
      anonSubpanel[curIndex].add(prevConnTypeB);
      nextConnTypeB = new JButton((char)8594 + " Next Type");
      nextConnTypeB.addActionListener(this);
      anonSubpanel[curIndex].add(nextConnTypeB);
      prevRoomB = new JButton((char)8593 + " Prev Room");
      prevRoomB.addActionListener(this);
      anonSubpanel[curIndex].add(prevRoomB);
      nextRoomB = new JButton((char)8595 + " Next Room");
      nextRoomB.addActionListener(this);
      anonSubpanel[curIndex].add(nextRoomB);
      curIndex++;
      
      anonSubpanel[curIndex].setLayout(new GridLayout(1, 4));
      addWidthB = new JButton("+Width");
      addWidthB.addActionListener(this);
      anonSubpanel[curIndex].add(addWidthB);
      reduceWidthB = new JButton("-Width");
      reduceWidthB.addActionListener(this);
      anonSubpanel[curIndex].add(reduceWidthB);
      addHeightB = new JButton("+Height");
      addHeightB.addActionListener(this);
      anonSubpanel[curIndex].add(addHeightB);
      reduceHeightB = new JButton("-Height");
      reduceHeightB.addActionListener(this);
      anonSubpanel[curIndex].add(reduceHeightB);
      curIndex++;
      
      anonSubpanel[curIndex].setLayout(new GridLayout(1, 4));
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
      String str = "Currently Selected: %c (%s)";
      currentlySelectedL.setText(String.format(str, selectedChar, RoomTemplateCellMapping.deserialize(selectedChar).name));
      roomTemplate.setConnectionType();
      deck.sort(roomTemplate);
      setLocationValues();
      setCountLabel();
      ConnectionType ct = ConnectionType.values()[curConnectionType];
      String curStr = "Current Room: %s (%d/%d)";
      currentRoomL.setText(String.format(curStr, ct.name, curConnectionIndex + 1, deck.size(ct)));
      curStr = "Complete Deck: " + ((deck.isComplete()) ? "True" : "False");
      completeL.setText(curStr);
      curStr = "Uniform Squares: " + ((deck.isUniformSquareSize()) ? "True" : "False");
      uniformL.setText(curStr);
   }
   
   private void setLocationValues()
   {
      int[] indexes = deck.getIndex(roomTemplate);
      curConnectionType = indexes[0];
      curConnectionIndex = indexes[1];
   }
   
   public void setDrawingPanel()
   {
      for(int x = 0; x < roomWidth; x++)
      for(int y = 0; y < roomHeight; y++)
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
   
   private void fillAll()
   {
      boolean iR = iRB.isSelected();
      boolean dR = dRB.isSelected();
      for(int x = 0; x < roomWidth; x++)
      for(int y = 0; y < roomHeight; y++)
      {
         roomTemplate.set(x, y, selectedChar, iR, dR);
      }
   }
   
   private void mouseClickedInDrawingPanel(MouseEvent me)
   {
      int[] mouseLoc = drawingPanel.getMouseLocTile();
      if(!roomTemplate.isInBounds(mouseLoc[0], mouseLoc[1]))
         return;
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
      else
      {
         boolean iR = iRB.isSelected();
         boolean dR = dRB.isSelected();
         roomTemplate.set(mouseLoc[0], mouseLoc[1], selectedChar, iR, dR);
      }
   }
   
   private void pressCharButton(char c)
   {
      for(int i = 0; i < RoomTemplateCellMapping.values().length; i++)
      {
         if(c == charArr[i])
            charButtonArr[i].doClick();
      }
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
         roomTemplate.set(i, roomTemplate.getHeight() - 1, c, false, false);
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
   
   private void resetDrawingPanel()
   {
      if(drawingPanel != null)
      {
         mapPanel.remove(drawingPanel);
         drawingPanel.removeMouseListener(this);
         drawingPanel.removeMouseMotionListener(this);
      }
      drawingPanel = new SCPanel(palette, roomWidth, roomHeight);
      mapPanel.add(drawingPanel);
      mapPanel.revalidate();
      drawingPanel.addMouseListener(this);
      drawingPanel.addMouseMotionListener(this);
      mapPanel.repaint();
   }
   
   private void save()
   {
      String saveLoc = "";
      JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
         "TIM Template Deck ", "ttd");
      chooser.setFileFilter(filter);
      if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
      {
         saveLoc = chooser.getSelectedFile().getAbsolutePath();
         if(saveLoc == null || saveLoc.equals(""))
         {
            JOptionPane.showMessageDialog(this, "File name cannot be blank.","Save Attempt Aborted",  JOptionPane.ERROR_MESSAGE);
            return;
         }
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
				outFile.println(str);
			}
			
			outFile.close();
		}
		catch(Exception ex){System.out.println("Exception while saving: " + ex.toString());}
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
            roomHeight = deck.getFirstRoom().getWidth();
            roomHeight = deck.getFirstRoom().getHeight();
            setCurrentRoom(deck.getFirstRoom());
   		}
   		catch(Exception ex){System.out.println("Exception while loading: " + ex.toString());}
      }
   }
   
   public void deleteRoom(RoomTemplate target)
   {
      if(deck.getCount() < 2)
      {
         JOptionPane.showMessageDialog(this,"Cannot delete the last room.",  "Delete Attempt Aborted", JOptionPane.ERROR_MESSAGE);
         return;
      }
      int[] newTarget = deck.getIndex(target);
      // not first in type, use previous
      if(newTarget[1] > 0)
      {
         newTarget[1]--;
      }
      // only one in type, grab first after deletion
      else if(deck.size(newTarget[0]) == 1)
      {
         newTarget = null;
      }
      // first in type, more exist; use next (same index after deletion)
      ;
      
      deck.remove(target);
      if(newTarget == null)
         setCurrentRoom(deck.getFirstRoom());
      else
         setCurrentRoom(deck.get(newTarget[0], newTarget[1]));
   }
   
   public void setCurrentRoom(RoomTemplate rt)
   {
      roomTemplate = rt;
      if(rt.getWidth() != roomWidth ||
         rt.getHeight() != roomHeight)
      {
         roomWidth = rt.getWidth();
         roomHeight = rt.getHeight();
         resetDrawingPanel();
      }
   }
   
   public void newDeck()
   {
      RoomTemplateDeck newDeck = new RoomTemplateDeck();
      RoomTemplate newRoomTemplate = new RoomTemplate(roomWidth, roomHeight);
      newDeck.add(newRoomTemplate);
      deck = newDeck;
      resetDrawingPanel();
      setCurrentRoom(newRoomTemplate);
      fileName = "";
   }
   
   public boolean getConfirmation(String message, String label)
   {
      int selection = JOptionPane.showConfirmDialog(this, message, label, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
      return selection == JOptionPane.OK_OPTION;
   }
   
   // recursive method to make everything unfocusable
   private void setUnfocusable(Component c)
   {
      c.setFocusable(false);
      if(c instanceof Container)
      {
         Container c2 = (Container)c;
         for(Component subComponent : c2.getComponents())
            setUnfocusable(subComponent);
      }
   }
   
   // listeners
   public void mouseClicked(MouseEvent me){mouseClickedInDrawingPanel(me);}
   public void mousePressed(MouseEvent me){}
   public void mouseReleased(MouseEvent me){}
   public void mouseEntered(MouseEvent me){}
   public void mouseExited(MouseEvent me){}
   
   public void mouseMoved(MouseEvent me){}
   public void mouseDragged(MouseEvent me)
   {
      drawingPanel.mouseMoved(me); // because mouseDragged prevents mouseMoved, which SCPanel needs to update mouseloc
      mouseClickedInDrawingPanel(me);
   }
   
   public void keyPressed(KeyEvent ke)
   {
      switch(ke.getKeyCode())
      {
         case KeyEvent.VK_UP:    prevRoomB.doClick();
                                 break;
         case KeyEvent.VK_DOWN:  nextRoomB.doClick();
                                 break;
         case KeyEvent.VK_LEFT:  prevConnTypeB.doClick();
                                 break;
         case KeyEvent.VK_RIGHT: nextConnTypeB.doClick();
                                 break;
      }
   }
   
   public void keyReleased(KeyEvent ke){}
   public void keyTyped(KeyEvent ke){}
   
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == timer)
      {
         if(drawingPanel != null)
         {
            updateCurrentLabels();
            setDrawingPanel();
            this.repaint();
            return;
         }
      }
      
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
      if(ae.getSource() == fillAllB)
      {
         if(getConfirmation("Overwrite ALL tiles in this room with '" + selectedChar + "'?", "Fill All"))
            fillAll();
      }
      if(ae.getSource() == newRoomB)
      {
         RoomTemplate newRT = new RoomTemplate(roomWidth, roomHeight);
         deck.add(newRT);
         setCurrentRoom(newRT);
      }
      if(ae.getSource() == dupeRoomB)
      {
         RoomTemplate newRT = new RoomTemplate(roomTemplate);
         deck.add(newRT);
         setCurrentRoom(newRT);
      }
      if(ae.getSource() == deleteRoomB)
      {
         if(getConfirmation("Delete this room?", "Delete Room"))
            deleteRoom(roomTemplate);
      }
      if(ae.getSource() == saveB){save();}
      if(ae.getSource() == loadB){load();}
      if(ae.getSource() == newDeckB)
      {
         if(getConfirmation("This will discard any unsaved progress.", "Create New Deck"))
            newDeck();
      }
      
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
         setCurrentRoom(deck.get(newType, newRoom));
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
         setCurrentRoom(deck.get(newType, newRoom));
      }
      if(ae.getSource() == nextRoomB)
      {
         int newRoom = curConnectionIndex + 1;
         if(newRoom >= deck.size(curConnectionType))
            newRoom = 0;
         setCurrentRoom(deck.get(curConnectionType, newRoom));
      }
      if(ae.getSource() == prevRoomB)
      {
         int newRoom = curConnectionIndex - 1;
         if(newRoom < 0)
            newRoom = deck.size(curConnectionType) - 1;
         setCurrentRoom(deck.get(curConnectionType, newRoom));
      }
      if(ae.getSource() == addWidthB)
      {
         roomTemplate.setSize(roomWidth + 1, roomHeight);
         setCurrentRoom(roomTemplate);
      }
      if(ae.getSource() == reduceWidthB)
      {
         roomTemplate.setSize(Math.max(1, roomWidth - 1), roomHeight);
         setCurrentRoom(roomTemplate);
      }
      if(ae.getSource() == addHeightB)
      {
         roomTemplate.setSize(roomWidth, roomHeight + 1);
         setCurrentRoom(roomTemplate);
      }
      if(ae.getSource() == reduceHeightB)
      {
         roomTemplate.setSize(roomWidth, Math.max(1, roomHeight - 1));
         setCurrentRoom(roomTemplate);
      }
   }
   
   public static final void main(String[] args)
   {
      ToolRoomTemplateWorkshopMain frame = new ToolRoomTemplateWorkshopMain();
   }
}