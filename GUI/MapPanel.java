package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public class MapPanel extends SCPanel implements GUIConstants
{
   public MapPanel(SCTilePalette tilePalette)
   {
      super(tilePalette, MAP_PANEL_SIZE, MAP_PANEL_SIZE);
      for(int x = 0; x < getTilesWide(); x++)
      for(int y = 0; y < getTilesTall(); y++)
         setTileIndex(x, y, 'X');
      writeLine(0, 0, "MapPanel");
   }
}