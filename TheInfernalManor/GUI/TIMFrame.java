package TheInfernalManor.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import StrictCurses.*;
import TheInfernalManor.Engine.*;

public class TIMFrame extends JFrame implements SCConstants, ComponentListener, KeyListener, GUIConstants, ActionListener
{
   private SCTilePalette x1y1Palette;
   private SCTilePalette x1y2Palette;
   private Vector<SwapPanel> panelList;
   private JPanel basePanel;
   private SwapPanel curPanel;
   private SwapPanel lastPanel;
   
   private SplashPanel splashPanel;
   private HelpPanel helpPanel;
   private AdventurePanel adventurePanel;
   private PreferencesPanel preferencesPanel;
   private LoadPanel loadPanel;
   private NewGamePanel newGamePanel;
   private CharacterCreationPanel characterCreationPanel;
   private AdvancementPanel advancementPanel;
   private ShopPanel shopPanel;
   private ManagementPanel managementPanel;
   private QuestNegotiationPanel questNegotiationPanel;
   private QuestEndPanel questEndPanel;
   private CharacterPanel characterPanel;
   private InventoryPanel inventoryPanel;
   private QuestInProgressPanel questInProgressPanel;
   private BankPanel bankPanel;
   private GameOverPanel gameOverPanel;
   private AnimationManager animationManager;
   
   private javax.swing.Timer timer;
   
   public TIMFrame()
   {
      super();      
      setSize(100, 100);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("The Infernal Manor");
      setLayout(new GridLayout(1, 1));
      basePanel = new JPanel();
      
      basePanel.setBackground(new Color(DEFAULT_BG_COLOR));
      basePanel.setLayout(null);
      add(basePanel);
      
      x1y1Palette = new SCTilePalette("WidlerTiles_16x16.png", 16, 16);
      x1y2Palette = new SCTilePalette("WidlerTiles_8x16.png", 8, 16);
      
      panelList = new Vector<SwapPanel>();
      
      addKeyListener(this);
      basePanel.addComponentListener(this);
      basePanel.addKeyListener(this);
      
      splashPanel = new SplashPanel(x1y2Palette, this);
      addPanel(splashPanel);
      helpPanel = new HelpPanel(x1y2Palette, this);
      addPanel(helpPanel);
      adventurePanel = new AdventurePanel(x1y2Palette, x1y1Palette, this);
      addPanel(adventurePanel);
      preferencesPanel = new PreferencesPanel(x1y2Palette, this);
      addPanel(preferencesPanel);
      loadPanel = new LoadPanel(x1y2Palette, this);
      addPanel(loadPanel);
      newGamePanel = new NewGamePanel(x1y2Palette, this);
      addPanel(newGamePanel);
      characterCreationPanel = new CharacterCreationPanel(x1y2Palette, this);
      addPanel(characterCreationPanel);
      advancementPanel = new AdvancementPanel(x1y2Palette, this);
      addPanel(advancementPanel);
      shopPanel = new ShopPanel(x1y2Palette, this);
      addPanel(shopPanel);
      managementPanel = new ManagementPanel(x1y2Palette, this);
      addPanel(managementPanel);
      questNegotiationPanel = new QuestNegotiationPanel(x1y2Palette, this);
      addPanel(questNegotiationPanel);
      questEndPanel = new QuestEndPanel(x1y2Palette, this);
      addPanel(questEndPanel);
      characterPanel = new CharacterPanel(x1y2Palette, this);
      addPanel(characterPanel);
      inventoryPanel = new InventoryPanel(x1y2Palette, this);
      addPanel(inventoryPanel);
      questInProgressPanel = new QuestInProgressPanel(x1y2Palette, this);
      addPanel(questInProgressPanel);
      gameOverPanel = new GameOverPanel(x1y2Palette, this);
      addPanel(gameOverPanel);
      bankPanel = new BankPanel(x1y2Palette, this);
      addPanel(bankPanel);
      
      animationManager = new AnimationManager();
      
      setVisible(true);
      snapToPreferredSize();
      setVisiblePanel("SplashPanel");
      GameState.setGameMode(EngineConstants.PREGAME_MODE);
      
      timer = new javax.swing.Timer(1000 / GUIConstants.FRAME_RATE, this);
      timer.start();
   }
   
   public void addPanel(SwapPanel newPanel)
   {
      newPanel.setLocation(0, 0);
      newPanel.setSize(basePanel.getWidth(), basePanel.getHeight());
      newPanel.setVisible(false);
      basePanel.add((Component)newPanel);
      panelList.add(newPanel);
   }
   
   // kicked by timer
   public void actionPerformed(ActionEvent ae)
   {
      animationManager.kick();
      if(curPanel != null)
         ((JPanel)curPanel).repaint();
   }
   
   // listener for basePanel
   public void componentHidden(ComponentEvent e){}
   public void componentMoved(ComponentEvent e){}
   public void componentShown(ComponentEvent e){}
   public void componentResized(ComponentEvent e)
   {
      for(SwapPanel panel : panelList)
      {
         panel.setSize(basePanel.getWidth(), basePanel.getHeight());
      }
   }
   
   public void returnToMainPanel()
   {
      if(GameState.getGameMode() == EngineConstants.PREGAME_MODE)
         setVisiblePanel("SplashPanel");
      if(GameState.getGameMode() == EngineConstants.ADVENTURE_MODE)
         setVisiblePanel("AdventurePanel");
      if(GameState.getGameMode() == EngineConstants.MANAGEMENT_MODE)
         setVisiblePanel("ManagementPanel");
   }
   
   public void returnToLastPanel()
   {
      setVisiblePanel(lastPanel.getPanelName());
   }
   
   public void setVisiblePanel(String panelName)
   {
      boolean foundPanel = false;
      for(SwapPanel panel : panelList)
      {
         if(panel.getPanelName().equals(panelName))
         {
            lastPanel = curPanel;
            panel.setVisible(true);
            curPanel = panel;
            foundPanel = true;
         }
         else
            panel.setVisible(false);
      }
      if(!foundPanel)
      {
         System.out.println("Could not find panel " + panelName);
      }
      repaint();
   }
   
   private void snapToPreferredSize()
   {
      Insets insets = getInsets();
      int newWidth = (TILES_WIDE * 8 * 2) + insets.left + insets.right;
      int newHeight = (TILES_TALL * 16 * 2) + insets.top + insets.bottom;
      setSize(newWidth, newHeight);
   }
   
   public void keyPressed(KeyEvent ke)
   {
      if(curPanel != null)
         ((KeyListener)curPanel).keyPressed(ke);
   }
   public void keyTyped(KeyEvent ke)
   {
      if(curPanel != null)
         ((KeyListener)curPanel).keyTyped(ke);
   }
   public void keyReleased(KeyEvent ke)
   {
      if(curPanel != null)
         ((KeyListener)curPanel).keyReleased(ke);
   }
}