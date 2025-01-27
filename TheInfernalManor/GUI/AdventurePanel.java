package TheInfernalManor.GUI;

import TheInfernalManor.Engine.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.AI.*;
import StrictCurses.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdventurePanel extends JPanel implements GUIConstants, ComponentListener, SwapPanel, KeyListener
{
   private MapPanel mapPanel;
   private InfoPanel infoPanel;
   private TIMFrame parentFrame;
   private int mode;
   private int targetX;
   private int targetY;
   private static int MESSAGE_PANEL_HEIGHT = TILES_TALL - 3 - MAP_PANEL_SIZE;
   private static int MESSAGE_PANEL_WIDTH = TILES_WIDE - 2;
   private static int MESSAGE_PANEL_X_ORIGIN = 1;
   private static int MESSAGE_PANEL_Y_ORIGIN = MAP_PANEL_SIZE + 2;
   private static int NORMAL_MODE = 0;
   private static int ADJACENT_TARGET_MODE = 1;
   private static int RANGED_TARGET_MODE = 2;
   private static int LOOK_MODE = 3;
   
   public String getPanelName(){return this.getClass().getSimpleName();}
   
   public AdventurePanel(SCTilePalette x1y2TilePalette, SCTilePalette x1y1TilePalette, TIMFrame pFrame)
   {
      super();
      setLayout(null);
      parentFrame = pFrame;
      mode = NORMAL_MODE;
      infoPanel = new InfoPanel(x1y2TilePalette, pFrame);
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
      // full border
      GUITools.setBorderBox(0, 0, w, h, borderArr);
      // mapPanel border
      GUITools.setBorderBox(0, 0, (MAP_PANEL_SIZE * 2) + 2, MAP_PANEL_SIZE + 2, borderArr);
      // messagePanel border
      GUITools.setBorderBox(MESSAGE_PANEL_X_ORIGIN - 1, MESSAGE_PANEL_Y_ORIGIN - 1, 
         MESSAGE_PANEL_WIDTH + 2, MESSAGE_PANEL_HEIGHT + 2, borderArr);
//       for(int x = 0; x < w; x++)
//       {
//          borderArr[x][0] = true;
//          borderArr[x][h - 1] = true;
//          borderArr[x][MAP_PANEL_SIZE + 1] = true;
//       }
//       for(int y = 0; y < h; y++)
//       {
//          borderArr[0][y] = true;
//          borderArr[w - 1][y] = true;
//       }
//       for(int y = 1; y < h; y++)
//       {
//          borderArr[(MAP_PANEL_SIZE * 2) + 1][y] = true;
//       }
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
   
   @Override
   public void paint(Graphics g)
   {
      infoPanel.set();
      setMessagePanel();
      super.paint(g);
   }
   
   @Override
   public void setVisible(boolean v)
   {
      if(v)
         GameState.setGameMode(EngineConstants.ADVENTURE_MODE);
      super.setVisible(v);
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
   
   private void setMessagePanel()
   {
      for(int i = 0; i < MESSAGE_PANEL_HEIGHT; i++)
      {
         String str = MessagePanel.getString(i);
         int fg = MessagePanel.getColor(i);
         for(int j = 0; j < MESSAGE_PANEL_WIDTH; j++)
         {
            if(j < str.length())
               infoPanel.setTile(MESSAGE_PANEL_X_ORIGIN + j, MESSAGE_PANEL_Y_ORIGIN + i, str.charAt(j), fg, BLACK);
            else
               infoPanel.setTile(MESSAGE_PANEL_X_ORIGIN + j, MESSAGE_PANEL_Y_ORIGIN + i, ' ', WHITE, BLACK);
         }
      }
   }
   
   private void directionPressed(Direction dir)
   {
      ActionPlan ap = new ActionPlan(ActionType.CONTEXTUAL, dir);
      GameState.getPlayerCharacter().getAI().setPendingAction(ap);
   }
   
   private boolean playerStandingOnItem()
   {
      ZoneMap map = GameState.getCurZone();
      int x = GameState.getPlayerCharacter().getXLocation();
      int y = GameState.getPlayerCharacter().getYLocation();
      return map.isItemAt(x, y);
   }
   
   public void keyPressed(KeyEvent ke)
   {
      Actor player = GameState.getPlayerCharacter();
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
         
         // specific actions
         case KeyEvent.VK_G :       if(playerStandingOnItem())
                                    {
                                       if(player.getInventory().hasRoom())
                                          player.getAI().setPendingAction(new ActionPlan(ActionType.PICK_UP, Direction.ORIGIN));
                                       else
                                          MessagePanel.addMessage("Your inventory is full.");
                                    }
                                    else
                                    {
                                       MessagePanel.addMessage("Nothing to pick up here.");
                                    }
                                    break;
         case KeyEvent.VK_ESCAPE :  if(mode != NORMAL_MODE)
                                       mode = NORMAL_MODE;
                                    break;
         
         // change screen
         case KeyEvent.VK_P :       parentFrame.setVisiblePanel("PreferencesPanel"); break;
         case KeyEvent.VK_I :       parentFrame.setVisiblePanel("InventoryPanel"); break;
         case KeyEvent.VK_C :       parentFrame.setVisiblePanel("CharacterPanel"); break;
         case KeyEvent.VK_H :       parentFrame.setVisiblePanel("HelpPanel"); break;
         
         // testing
         case KeyEvent.VK_BACK_QUOTE : player.applyCombatDamage(1, Ability.PHYSICAL);
                                       break;
      }
   }
   public void keyTyped(KeyEvent ke){}
   public void keyReleased(KeyEvent ke){}
}