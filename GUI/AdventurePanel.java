package TheInfernalManor.GUI;

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
   
   public void keyPressed(KeyEvent ke)
   {
      parentFrame.setVisiblePanel("SplashPanel");
   }
   public void keyTyped(KeyEvent ke){}
   public void keyReleased(KeyEvent ke){}
   
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
}