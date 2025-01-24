package TheInfernalManor.GUI;

import TheInfernalManor.Map.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import StrictCurses.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MapPanel extends SCPanel implements GUIConstants
{
   public MapPanel(SCTilePalette tilePalette)
   {
      super(tilePalette, MAP_PANEL_SIZE, MAP_PANEL_SIZE);
   }
   
   @Override
   public void paint(Graphics g)
   {
      ZoneMap map = GameState.getCurZone();
      Actor player = GameState.getPlayerCharacter();
      if(map != null && player != null)
      {
         int xOffset = player.getXLocation() - (MAP_PANEL_SIZE / 2);
         int yOffset = player.getYLocation() - (MAP_PANEL_SIZE / 2);
         for(int x = 0; x < MAP_PANEL_SIZE; x++)
         for(int y = 0; y < MAP_PANEL_SIZE; y++)
         {
            setTile(x, y, map.getTile(x + xOffset, y + yOffset));
            if(map.isItemAt(x + xOffset, y + yOffset))
               setTile(x, y, map.getItemAt(x + xOffset, y + yOffset));
         }
         Vector<Actor> actorList = GameState.getActorList();
         if(actorList != null)
         {
            for(int i = 0; i < actorList.size(); i++)
               setTile(actorList.elementAt(i), player);
         }
      }
      else
      {
         fillTile(0, 0, MAP_PANEL_SIZE, MAP_PANEL_SIZE, ' ', WHITE, BLACK);
         if(map == null)
            writeLine(0, 0, "No map found.");
         if(player == null)
            writeLine(0, 1, "No player character found.");
      }
      super.paint(g);
   }
   
   private void setTile(int x, int y, MapCell mapCell)
   {
      setTile(x, y, mapCell.getIconIndex(), mapCell.getFGColor(), mapCell.getBGColor());
   }
   
   private void setTile(Actor curActor, Actor player)
   {
      int x = (MAP_PANEL_SIZE / 2) + curActor.getXLocation() - player.getXLocation();
      int y = (MAP_PANEL_SIZE / 2) + curActor.getYLocation() - player.getYLocation();
      setTile(x, y, curActor);
   }
   
   private void setTile(int x, int y, ForegroundObject obj)
   {
      setTileIndex(x, y, obj.getIconIndex());
      setTileFG(x, y, obj.getColor());
   }
   
   public boolean isInBounds(int x, int y)
   {
      return x >= 0 && x < MAP_PANEL_SIZE &&
             y >= 0 && y < MAP_PANEL_SIZE;
   }
   
   public boolean isInBounds(Actor a)
   {
      return isInBounds(a.getXLocation(), a.getYLocation());
   }
}