package TheInfernalManor.GUI;

import java.util.*;
import StrictCurses.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Map.*;
import WidlerSuite.Coord;

public class InfoPanel extends TIMPanel implements GUIConstants
{
   public static final int CHARACTER_SUMMARY_PANEL_X_ORIGIN = AdventurePanel.CHARACTER_SUMMARY_PANEL_X_ORIGIN;
   public static final int ENVIRONMENT_PANEL_X_ORIGIN = AdventurePanel.ENVIRONMENT_PANEL_X_ORIGIN;
   public static final int SIDE_PANEL_WIDTH = AdventurePanel.ENVIRONMENT_PANEL_WIDTH;
   public static final int SIDE_PANEL_HEIGHT = AdventurePanel.MESSAGE_PANEL_Y_ORIGIN - 2;
   public static final int Y_ORIGIN = 1;
   
   public InfoPanel(SCTilePalette x1y2TilePalette, TIMFrame pFrame)
   {
      super(x1y2TilePalette, pFrame);
   }
   
   public void set()
   {
      if(GameState.getPlayerCharacter() == null)
         return;
      setCharacterSummaryPanel();
      setEnvironmentPanel();
    }
    
    private void setCharacterSummaryPanel()
    {
      Actor player = GameState.getPlayerCharacter();
      // draw name
      writeLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN, player.getName());
      
      // draw block info
      writeLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 1, "Guard  [        ]");
      fillTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 1, 17, 1, WHITE);
      fillTileBG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8, Y_ORIGIN + 1, 8, 1, RED);
      int[] bar = GUITools.getBar(player.getCurGuard(), player.getMaxGuard(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8 + i, Y_ORIGIN + 1, bar[i]);
      
      // draw health info
      writeLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 2, "Health [        ]");
      fillTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 2, 17, 1, YELLOW);
      fillTileBG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8, Y_ORIGIN + 2, 8, 1, RED);
      bar = GUITools.getBar(player.getCurHealth(), player.getMaxHealth(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8 + i, Y_ORIGIN + 2, bar[i]);
      
      // draw energy info
      writeLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 3, "Energy [        ]");
      fillTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 3, 17, 1, BLUE);
      fillTileBG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8, Y_ORIGIN + 3, 8, 1, RED);
      bar = GUITools.getBar(player.getCurEnergy(), player.getMaxEnergy(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + 8 + i, Y_ORIGIN + 3, bar[i]);
      
      // draw status effect info
      Vector<StatusEffect> seList = player.getSEList();
      for(int i = 0; i < SIDE_PANEL_WIDTH; i++)
      {
         if(i < seList.size())
         {
            StatusEffect se = seList.elementAt(i);
            setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + i, Y_ORIGIN + 4, se.getIconIndex());
            setTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + i, Y_ORIGIN + 4, se.getColor());
         }
         else
         {
            setTileIndex(CHARACTER_SUMMARY_PANEL_X_ORIGIN + i, Y_ORIGIN + 4, ' ');
            setTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN + i, Y_ORIGIN + 4, WHITE);
         }
      }
      // abilities
      for(int i = 0; i < 10; i++)
      {
         int keyVal = i + 1;
         if(keyVal == 10)
            keyVal = 0;
         String str = "<No Ability>";
         Ability ability = player.getAbility(i);
         if(ability != null)
            str = ability.getName();
         overwriteLine(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 5 + i, String.format("[%d]: %s", keyVal, str), SIDE_PANEL_WIDTH);
         if(player.canUseAbility(i))
            fillTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 5 + i, SIDE_PANEL_WIDTH, 1, WHITE);
         else
         {
            fillTileFG(CHARACTER_SUMMARY_PANEL_X_ORIGIN, Y_ORIGIN + 5 + i, SIDE_PANEL_WIDTH, 1,  GREY);
         }
      }
    }
    
    private void setEnvironmentPanel()
    {
      // clear first
      fillTile(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN, SIDE_PANEL_WIDTH, SIDE_PANEL_HEIGHT, ' ', WHITE, BLACK);
      String modeStr = "Unknown mode";
      switch(AdventurePanel.getMode())
      {
         case AdventurePanel.NORMAL_MODE:          modeStr = "Normal Mode"; break;
         case AdventurePanel.ADJACENT_TARGET_MODE: modeStr = "Targeting Adjacent"; break;
         case AdventurePanel.RANGED_TARGET_MODE:   modeStr = "Ranged Targeting"; break;
         case AdventurePanel.LOOK_MODE:            modeStr = "Looking"; break;
      }
      int writeLine = 0;
      if(AdventurePanel.getMode() == AdventurePanel.NORMAL_MODE)
      {
         Vector<Actor> aList = GameState.getActorList();
         for(Actor a : aList)
         {
            if(GameState.playerCanSee(a))
               if(a != GameState.getPlayerCharacter())
                  writeLine += showActorSummary(writeLine, a);
         }
      }
      else //looking, adj targeting, ranged targeting
      {
         overwriteLine(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN, modeStr, SIDE_PANEL_WIDTH);
         overwriteLine(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + 1, "Escape to Cancel", SIDE_PANEL_WIDTH);
         writeLine = 3;
         Coord targetLoc = AdventurePanel.getTarget();
         
         if(GameState.playerCanSee(targetLoc))
         {
            Actor actor = GameState.getActorAt(targetLoc);
            Item item = GameState.getCurZone().getItemAt(targetLoc);
            MapCell mapCell = GameState.getCurZone().getTile(targetLoc);
            ForegroundObject groundObj = GameState.getCurZone().getCorpse(targetLoc);
            // actor
            if(actor != null)
            {
               writeLine += 1 + showActorSummary(writeLine, actor);
            }
            // item
            if(item != null)
            {
               setTileIndex(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + writeLine, item.getIconIndex());
               setTileFG(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + writeLine, item.getColor());
               writeLine += 1 + writeBox(ENVIRONMENT_PANEL_X_ORIGIN + 2, Y_ORIGIN + writeLine, SIDE_PANEL_WIDTH - 3, SIDE_PANEL_HEIGHT - writeLine, item.getName());
            }
            // ground object
            if(groundObj != null)
            {
               setTileIndex(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + writeLine, groundObj.getIconIndex());
               setTileFG(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + writeLine, groundObj.getColor());
               writeLine += 1 + writeBox(ENVIRONMENT_PANEL_X_ORIGIN + 2, Y_ORIGIN + writeLine, SIDE_PANEL_WIDTH - 3, SIDE_PANEL_HEIGHT - writeLine, groundObj.getName());
            }
            // map tile
            setTile(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + writeLine, mapCell.getIconIndex(), mapCell.getFGColor(), mapCell.getBGColor());
            overwriteLine(ENVIRONMENT_PANEL_X_ORIGIN + 1, Y_ORIGIN + writeLine, " " + mapCell.getBase().toString(), SIDE_PANEL_WIDTH - 2);
            writeLine++;
            overwriteLine(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + writeLine, "", SIDE_PANEL_WIDTH);
            writeLine += 2;
         }
         else // out of view
         {
            overwriteLine(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + writeLine, "Out of View", SIDE_PANEL_WIDTH);
            writeLine++;
         }

      }
    }
    
    // returns number of rows taken to write the summary
    private int showActorSummary(int startRow, Actor a)
    {
      int linesUsed = 0;
      String barStr1 = "  [        ]";
      String barStr2 = "[        ]";
      int barLen = barStr1.length();
      // icon and name
      setTileIndex(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + startRow, a.getIconIndex());
      setTileFG(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + startRow, a.getColor());
      overwriteLine(ENVIRONMENT_PANEL_X_ORIGIN + 1, Y_ORIGIN + startRow, " " + a.getName(), SIDE_PANEL_WIDTH - 2);
      linesUsed++;
      
      // draw block info
      writeLine(ENVIRONMENT_PANEL_X_ORIGIN, Y_ORIGIN + startRow + 1, barStr1);
      fillTileFG(ENVIRONMENT_PANEL_X_ORIGIN + 3, Y_ORIGIN + startRow + 1, 8, 1, WHITE);
      fillTileBG(ENVIRONMENT_PANEL_X_ORIGIN + 3, Y_ORIGIN + startRow + 1, 8, 1, RED);
      int[] bar = GUITools.getBar(a.getCurGuard(), a.getMaxGuard(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(ENVIRONMENT_PANEL_X_ORIGIN + 3 + i, Y_ORIGIN + startRow + 1, bar[i]);
      
      // draw health info
      writeLine(ENVIRONMENT_PANEL_X_ORIGIN + barLen, Y_ORIGIN + startRow + 1, barStr2);
      fillTileFG(ENVIRONMENT_PANEL_X_ORIGIN + barLen + 1, Y_ORIGIN + startRow + 1, 8, 1, YELLOW);
      fillTileBG(ENVIRONMENT_PANEL_X_ORIGIN + barLen + 1, Y_ORIGIN + startRow + 1, 8, 1, RED);
      bar = GUITools.getBar(a.getCurHealth(), a.getMaxHealth(), 8);
      for(int i = 0; i < bar.length; i++)
         setTileIndex(ENVIRONMENT_PANEL_X_ORIGIN + barLen + 1 + i, Y_ORIGIN + startRow + 1, bar[i]);
      linesUsed++;
      
      if(a.getSEList().size() > 0)
      {
         for(int x = 0; x < a.getSEList().size(); x++)
         {
            StatusEffect se = a.getSEList().elementAt(x);
            setTile(ENVIRONMENT_PANEL_X_ORIGIN + x, Y_ORIGIN + startRow + 2, se.getIconIndex(), se.getColor(), BLACK);
         }
         linesUsed++;
      }
      
      return linesUsed;
    }
}