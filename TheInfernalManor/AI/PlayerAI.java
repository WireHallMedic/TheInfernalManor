package TheInfernalManor.AI;

import TheInfernalManor.Map.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;

public class PlayerAI extends BaseAI
{

   public PlayerAI(Actor s)
   {
      super(s);
      setPlayerControlled(true);
   }
   
   @Override
   public void act()
   {
      if(hasPlan() && pendingAction.size() == 1 && pendingAction.elementAt(0).getActionType() == ActionType.CONTEXTUAL)
         decodeContextualAction();
      super.act();
   }
   
   private void decodeContextualAction()
   {
      ActionType actionType = pendingAction.elementAt(0).getActionType();
      Direction dir = pendingAction.elementAt(0).getDirection();
      
      // delay
      if(dir == Direction.ORIGIN)
      {
         pendingAction.elementAt(0).setActionType(ActionType.DELAY);
         return;
      }
      
      if(dir != null)
      {
         int x = self.getXLocation() + dir.x;
         int y = self.getYLocation() + dir.y;
         
         // step
         if(self.canStep(x, y, GameState.getCurZone()))
         {
            pendingAction.elementAt(0).setActionType(ActionType.STEP);
            return;
         }
         
         // enemy
         if(GameState.isActorAt(x, y))
         {
            pendingAction.elementAt(0).setActionType(ActionType.BASIC_ATTACK);
            return;
         }
         // toggle tile
         else if(GameState.getCurZone().getTile(x, y) instanceof ToggleTile)
         {
            pendingAction.elementAt(0).setActionType(ActionType.USE);
         }
         // impassable tile
         else
         {
            MessagePanel.addMessage("Cannot step there.");
            clearPlan();
         }
      }
   }
}