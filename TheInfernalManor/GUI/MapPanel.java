package TheInfernalManor.GUI;

import TheInfernalManor.Map.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import StrictCurses.*;
import java.awt.*;
import java.awt.event.*;

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
         for(int x = 0; x < MAP_PANEL_SIZE; x++)
         for(int y = 0; y < MAP_PANEL_SIZE; y++)
         {
            setTile(x, y, map.getTile(x, y));
         }
      }
      else
      {
         fillTile(0, 0, MAP_PANEL_SIZE, MAP_PANEL_SIZE, ' ', WHITE, BLACK);
         writeLine(0, 0, "No map found.");
      }
      super.paint(g);
   }
   
   private void setTile(int x, int y, MapCell mapCell)
   {
      setTile(x, y, mapCell.getIconIndex(), mapCell.getFGColor(), mapCell.getBGColor());
   }
}