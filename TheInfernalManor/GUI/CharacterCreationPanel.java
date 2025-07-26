package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;

public class CharacterCreationPanel extends TIMPanel implements GUIConstants
{
   private String nameStr;
   private boolean writingName;
   private static final int Y_ORIGIN = 3;
   
   public CharacterCreationPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Character Creation ");
      writeLine(1, 3, "Escape to go back, any other key to start game.");
      nameStr = "";
      writingName = true;
   }
   
   @Override
   public void paint(Graphics g)
   {
      clearScreen();
      String str = "Enter character name: " + nameStr;
      writeLine(1, Y_ORIGIN, str);
      if(writingName)
      {
         if(AnimationManager.getMediumBlink())
            setTileIndex(1 + str.length(), Y_ORIGIN, '_');
         else
            setTileIndex(1 + str.length(), Y_ORIGIN, ' ');
      }
      super.paint(g);
   }
   
   private void clearScreen()
   {
      fillTile(1, Y_ORIGIN, TILES_WIDE - 2, TILES_TALL - 2, ' ', WHITE, BLACK);
   }
   
   @Override
   public void keyTyped(KeyEvent ke)
   {
      if(writingName)
      {
         if(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE && nameStr.length() > 0)
            nameStr = nameStr.substring(0, nameStr.length() - 1);
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
      if(writingName)
      {
      
      }
      else
      {
         if(ke.getKeyCode() == KeyEvent.VK_ESCAPE)
         {
            parentFrame.setVisiblePanel("NewGamePanel");
         }
         else
         {
            GameState.setTestValues();
            parentFrame.setVisiblePanel("ManagementPanel");
         }
      }
   }
}