package TheInfernalManor.GUI;

import TheInfernalManor.Engine.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Map.*;
import StrictCurses.*;
import java.awt.event.*;
import javax.swing.*;

public class AdventurePanel extends JPanel implements GUIConstants, ComponentListener, SwapPanel, KeyListener
{
   private MapPanel mapPanel;
   private TIMPanel infoPanel;
   private TIMFrame parentFrame;
   
   public String getPanelName(){return this.getClass().getSimpleName();}
   
   public AdventurePanel(SCTilePalette x1y2TilePalette, SCTilePalette x1y1TilePalette, TIMFrame pFrame)
   {
      super();
      setLayout(null);
      parentFrame = pFrame;
      infoPanel = new TIMPanel(x1y2TilePalette, pFrame);
      mapPanel = new MapPanel(x1y1TilePalette);
      mapPanel.setVisible(true);
      add(mapPanel);
      infoPanel.setSize(getWidth(), getHeight());
      infoPanel.setLocation(0, 0);
      add(infoPanel);
      
      setBorder();
      addComponentListener(this);
      setMapPanel();
   }
   
   private void setBorder()
   {
      int w = infoPanel.getTilesWide();
      int h = infoPanel.getTilesTall();
      boolean[][] borderArr = new boolean[w][h];
      for(int x = 0; x < w; x++)
      {
         borderArr[x][0] = true;
         borderArr[x][h - 1] = true;
      }
      for(int y = 0; y < h; y++)
      {
         borderArr[0][y] = true;
         borderArr[w - 1][y] = true;
      }
      for(int x = 1; x < (MAP_PANEL_SIZE * 2) + 1; x++)
      {
         borderArr[x][MAP_PANEL_SIZE + 1] = true;
      }
      for(int y = 1; y < h; y++)
      {
         borderArr[(MAP_PANEL_SIZE * 2) + 1][y] = true;
      }
      GUITools.applyBorderTileArray(borderArr, infoPanel);
   }
   
   // listener for basePanel
   public void componentHidden(ComponentEvent e){}
   public void componentMoved(ComponentEvent e){}
   public void componentShown(ComponentEvent e){}
   public void componentResized(ComponentEvent e)
   {
      resize();
   }
   
   public void resize()
   {
      infoPanel.setSize(getWidth(), getHeight());
      setMapPanel();
   }
   
   private void setMapPanel()
   {
      int displayWidth = infoPanel.getWidth() - (infoPanel.getImageXInset() * 2);
      int displayHeight = infoPanel.getHeight() - (infoPanel.getImageYInset() * 2);
      double trueTileWidth = (double)displayWidth / infoPanel.getTilesWide();
      double trueTileHeight = (double)displayHeight / infoPanel.getTilesTall();
      int xInset = (int)(infoPanel.getImageXInset() + trueTileWidth);
      int yInset = (int)(infoPanel.getImageYInset() + trueTileHeight);
      int width = (int)(trueTileWidth * MAP_PANEL_SIZE * 2);
      int height = (int)(trueTileHeight * MAP_PANEL_SIZE);
      mapPanel.setLocation(xInset, yInset);
      mapPanel.setSize(width, height);
   }
   
   private void directionPressed(Direction dir)
   {
      int newX = GameState.getPlayerCharacter().getXLocation() + dir.x;
      int newY = GameState.getPlayerCharacter().getYLocation() + dir.y;
      GameState.getPlayerCharacter().setLocation(newX, newY);
   }
   
   public void keyPressed(KeyEvent ke)
   {
      switch(ke.getKeyCode())
      {
         // directions
         case KeyEvent.VK_UP :      
         case KeyEvent.VK_NUMPAD8 : directionPressed(Direction.NORTH); break;
         case KeyEvent.VK_NUMPAD9 : directionPressed(Direction.NORTHEAST); break;
         case KeyEvent.VK_RIGHT :  
         case KeyEvent.VK_NUMPAD6 : directionPressed(Direction.EAST); break;
         case KeyEvent.VK_NUMPAD3 : directionPressed(Direction.SOUTHEAST); break;
         case KeyEvent.VK_DOWN :  
         case KeyEvent.VK_NUMPAD2 : directionPressed(Direction.SOUTH); break;
         case KeyEvent.VK_NUMPAD1 : directionPressed(Direction.SOUTHWEST); break;
         case KeyEvent.VK_LEFT :  
         case KeyEvent.VK_NUMPAD4 : directionPressed(Direction.WEST); break;
         case KeyEvent.VK_NUMPAD7 : directionPressed(Direction.NORTHWEST); break;
         case KeyEvent.VK_PERIOD :  
         case KeyEvent.VK_NUMPAD5 : directionPressed(Direction.ORIGIN); break;
         
         // change screen
         case KeyEvent.VK_I :       parentFrame.setVisiblePanel("InventoryPanel"); break;
         case KeyEvent.VK_C :       parentFrame.setVisiblePanel("CharacterPanel"); break;
         case KeyEvent.VK_H :       parentFrame.setVisiblePanel("HelpPanel"); break;
      }
   }
   public void keyTyped(KeyEvent ke){}
   public void keyReleased(KeyEvent ke){}
}