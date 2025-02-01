package TheInfernalManor.GUI;

import TheInfernalManor.Map.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Ability.*;
import TheInfernalManor.Engine.*;
import StrictCurses.*;
import WidlerSuite.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MapPanel extends SCPanel implements GUIConstants, SCConstants
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
            // mapCell
            setTile(x, y, map.getTile(x + xOffset, y + yOffset));
            
            // decoration
            ForegroundObject fo = map.getDecoration(x + xOffset, y + yOffset);
            if(fo != null)
               setTile(x, y, fo);
               
            // item
            if(map.isItemAt(x + xOffset, y + yOffset))
               setTile(x, y, map.getItemAt(x + xOffset, y + yOffset));
         }
         Vector<Actor> actorList = GameState.getActorList();
         if(actorList != null)
         {
            // actor
            for(int i = 0; i < actorList.size(); i++)
               setTile(actorList.elementAt(i), player);
         }
         
         // pending ability shape
         boolean reticleInShape = true;
         if(AdventurePanel.getMode() == AdventurePanel.RANGED_TARGET_MODE)
         {
            int x = AdventurePanel.getTargetX();
            int y = AdventurePanel.getTargetY();
            int reticleColor = SELECTED_COLOR;
            setTileBG(x - xOffset, y - yOffset, reticleColor);   
            Ability pendingAbility = AdventurePanel.getPendingAbility();
            int range = pendingAbility.getRange();
            if(range == AbilityConstants.USE_WEAPON_RANGE)
               range = player.getWeapon().getRange();
               
            Coord origin = new Coord(player.getXLocation(), player.getYLocation());
            Coord target = new Coord(AdventurePanel.getTargetX(), AdventurePanel.getTargetY());
            int len = pendingAbility.getRange();
            if(len == Ability.USE_WEAPON_RANGE)
               len = player.getWeapon().getRange();
            Vector<Coord> shape = new Vector<Coord>();
            switch(pendingAbility.getShape())
            {
               case AbilityConstants.EffectShape.POINT :
                  shape = EngineTools.getPointTarget(origin, target, len); break;
               case AbilityConstants.EffectShape.BEAM :
                  shape = EngineTools.getLineTargets(origin, target, len); break;
               case AbilityConstants.EffectShape.CONE :
                  shape = EngineTools.getConeTargets(origin, target, len, pendingAbility.getRadius()); break;
               case AbilityConstants.EffectShape.BLAST :
                  shape = EngineTools.getBlastTargets(origin, target, len, pendingAbility.getRadius()); break;
            }
            reticleInShape = false;
            Coord reticleLoc = new Coord(x, y);
            for(Coord c : shape)
            {
               setTileBG(c.x - xOffset, c.y - yOffset, reticleColor);
               if(c.equals(reticleLoc))
                  reticleInShape = true;
            }
         }
         
         // targeting reticle
         if(AdventurePanel.getMode() == AdventurePanel.LOOK_MODE || AdventurePanel.getMode() == AdventurePanel.RANGED_TARGET_MODE)
         {
            int x = AdventurePanel.getTargetX();
            int y = AdventurePanel.getTargetY();
            if(reticleInShape)
               setTileBG(x - xOffset, y - yOffset, RETICLE_COLOR);
            else
               setTileBG(x - xOffset, y - yOffset, RED);
         }
         
         // visual effects
         Vector<VisualEffect> veList = AnimationManager.getLockList();
         for(int j = 0; j < 2; j++)
         {
            if(j == 1)
               veList = AnimationManager.getNonlockList();
            for(int i = 0; i < veList.size(); i++)
            {
               VisualEffect ve = veList.elementAt(i);
               if(ve.hasIconList())
                  setTileIndex(ve.getXLocation() - xOffset, ve.getYLocation() - yOffset, ve.getIcon());
               if(ve.hasFGList())
                  setTileFG(ve.getXLocation() - xOffset, ve.getYLocation() - yOffset, ve.getFG());
               if(ve.hasBGList())
                  setTileBG(ve.getXLocation() - xOffset, ve.getYLocation() - yOffset, ve.getBG());
            }
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