package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Ability.*;

public class CharacterCreationPanel extends TIMPanel implements GUIConstants, SCConstants
{
   private String nameStr;
   private boolean writingName;
   private Vector<Ability> abilityList;
   private boolean[] selectedList;
   private int listIndex;
   private static final int NAME_ROW = 3;
   private static final int ABILITY_STARTING_ROW = 6;
   
   public CharacterCreationPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Character Creation ");
      writeLine(1, 3, "Escape to go back, any other key to start game.");
      nameStr = "";
      writingName = true;
      listIndex = 0;
      setAbilityList();
      selectedList = new boolean[abilityList.size()];
   }
   
   @Override
   public void paint(Graphics g)
   {
      clearScreen();
      String str = "Enter character name: " + nameStr;
      writeLine(1, NAME_ROW, str);
      
      if(writingName)
      {
         if(AnimationManager.getMediumBlink())
            setTileIndex(1 + str.length(), NAME_ROW, '_');
         else
            setTileIndex(1 + str.length(), NAME_ROW, ' ');
      }
      else
      {  
         writeLine(1, ABILITY_STARTING_ROW - 1, "Select two starting abilities:");
         for(int i = 0; i < abilityList.size(); i++)
            writeLine(4, ABILITY_STARTING_ROW + i, abilityList.elementAt(i).getName());
         for(int i = 0; i < abilityList.size(); i++)
         {
            int iconIndex;
            if(i == listIndex)
            {
               if(selectedList[i])
                  iconIndex = INVERSE_DOT_TILE;
               else
                  iconIndex = INVERSE_RING_TILE;
            }
            else
            {
               if(selectedList[i])
                  iconIndex = DOT_TILE;
               else
                  iconIndex = RING_TILE;
            }
            setTileIndex(2, ABILITY_STARTING_ROW + i, iconIndex);
         }
      }
      super.paint(g);
   }
   
   private void clearScreen()
   {
      fillTile(1, NAME_ROW, TILES_WIDE - 2, TILES_TALL - 2, ' ', WHITE, BLACK);
   }
   
   private void setAbilityList()
   {
      abilityList = new Vector<Ability>();
      for(Attack attack : AttackFactory.getStartingAttacks())
         abilityList.add(attack);
   }
   
   @Override
   public void keyTyped(KeyEvent ke)
   {
      if(writingName)
      {
         if(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)
         {
            if(nameStr.length() > 0)
               nameStr = nameStr.substring(0, nameStr.length() - 1);
         }
         else if(ke.getKeyChar() == KeyEvent.VK_ENTER)
         {
            if(nameStr.length() > 0)
               writingName = false;
         }
         else
            nameStr += ke.getKeyChar();
      }
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      if(!writingName)
      {
         switch(ke.getKeyCode())
         {
            case KeyEvent.VK_ESCAPE:   parentFrame.setVisiblePanel("NewGamePanel");
                                       break;
            case KeyEvent.VK_UP:       ;
            case KeyEvent.VK_NUMPAD8:  decrementIndex();
                                       break;
            case KeyEvent.VK_DOWN:     ;
            case KeyEvent.VK_NUMPAD2:  incrementIndex();
                                       break;
            case KeyEvent.VK_ENTER:    selectedList[listIndex] = !selectedList[listIndex];
                                       checkIfDone();
                                       break;
         }
         if(ke.getKeyCode() == KeyEvent.VK_ESCAPE)
         {
            
         }
         else
         {
//             GameState.setTestValues();
//             parentFrame.setVisiblePanel("ManagementPanel");
         }
      }
   }
   
   private void incrementIndex()
   {
      listIndex++;
      if(listIndex >= abilityList.size())
         listIndex = 0;
   }
   
   private void decrementIndex()
   {
      listIndex--;
      if(listIndex < 0)
         listIndex = abilityList.size() - 1;
   }
   
   private void checkIfDone()
   {
      int count = 0;
      for(boolean val : selectedList)
         if(val)
            count++;
      if(count > 1)
      {
         Actor player = ActorFactory.getNewPlayer();
         player.setName(nameStr);
         for(int i = 0; i < selectedList.length; i++)
            if(selectedList[i])
               player.addAbility(abilityList.elementAt(i));
         GameState.setPlayerCharacter(player);
         parentFrame.setVisiblePanel("ManagementPanel");
      }
   }
}