package TheInfernalManor.Map;

import StrictCurses.*;

public class ToggleTile extends MapCell
{
   private MapCell stateA;
   private MapCell stateB;
   private boolean toggleState;
   
   public ToggleTile(MapCell a, MapCell b)
   {
      super(MapCellBase.WALL);
      stateA = a;
      stateB = b;
      toggleState = true;
   }
   
   public MapCell getCurState()
   {
      if(toggleState)
         return stateA;
      return stateB;
   }
   
   public void toggle()
   {
      toggleState = !toggleState;
   }
   
   @Override public int getIconIndex(){return getCurState().getIconIndex();}
	@Override public int getFGColor(){return getCurState().getFGColor();}
	@Override public int getBGColor(){return getCurState().getBGColor();}
	@Override public boolean isLowPassable(){return getCurState().isLowPassable();}
	@Override public boolean isHighPassable(){return getCurState().isHighPassable();}
	@Override public boolean isTransparent(){return getCurState().isTransparent();}
}