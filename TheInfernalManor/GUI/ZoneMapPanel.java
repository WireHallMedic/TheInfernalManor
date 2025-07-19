package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Map.*;
import WidlerSuite.*;

public class ZoneMapPanel extends TIMPanel implements GUIConstants
{
   public ZoneMapPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame, (int)(TILES_WIDE * 1.5), (int)(TILES_TALL * 3.0));
      GUITools.drawSimpleBorder(this);
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      switch(ke.getKeyCode())
      {
         case KeyEvent.VK_ESCAPE :
         case KeyEvent.VK_M :
         case KeyEvent.VK_SPACE :   parentFrame.returnToMainPanel();
      }
   }
   
   @Override
   public void paint(Graphics g)
   {
      ZoneMap z = GameState.getCurZone();
      Actor player = GameState.getPlayerCharacter();
      if(z != null && player != null)
      {
         int w = getTilesWide() - 2;
         int h = getTilesTall() - 2;
         Coord center = GameState.getPlayerCharacter().getLocation();
         center.x -= w / 2;
         center.y -= h / 2;
         for(int x = 0; x < w; x++)
         for(int y = 0; y < h; y++)
         {
            int c = z.getLastSeen(x + center.x, y + center.y);
            MapCell cell = z.getTile(x + center.x, y + center.y);
            if(c == 0)
               setTile(x + 1, y + 1, c, WHITE, BLACK);
            else
               setTile(x + 1, y + 1, c, z.getLastSeenColor(x + center.x, y + center.y), cell.getBGColor());
         }
         if(AnimationManager.getMediumBlink())
         {
            setTileIndex((w / 2) + 1, (h / 2) + 1, player.getIconIndex());
            setTileFG((w / 2) + 1, (h / 2) + 1,  player.getColor());
         }
      }
      super.paint(g);
   }
}