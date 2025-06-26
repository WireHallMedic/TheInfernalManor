package TheInfernalManor.Map;

import StrictCurses.*;

public class ToggleTile extends MapCell
{
	private MapCell stateA;
	private MapCell stateB;
	private boolean toggleState;
	private boolean oneUseOnly;


	public MapCell getStateA(){return stateA;}
	public MapCell getStateB(){return stateB;}
	public boolean isToggleState(){return toggleState;}
	public boolean isOneUseOnly(){return oneUseOnly;}


	public void setStateA(MapCell s){stateA = s;}
	public void setStateB(MapCell s){stateB = s;}
	public void setToggleState(boolean t){toggleState = t;}
	public void setOneUseOnly(boolean o){oneUseOnly = o;}
   
   
   public ToggleTile(MapCell a, MapCell b)
   {
      super(MapCellBase.WALL);
      stateA = a;
      stateB = b;
      toggleState = true;
      oneUseOnly = false;
   }
   
   public MapCell getCurState()
   {
      if(toggleState)
         return stateA;
      return stateB;
   }
   
   public void toggle()
   {
      // no effect for one-use-only that have been used already
      if(!toggleState && oneUseOnly)
         return;
      toggleState = !toggleState;
   }
   
   @Override public int getIconIndex(){return getCurState().getIconIndex();}
	@Override public int getFGColor(){return getCurState().getFGColor();}
	@Override public int getBGColor(){return getCurState().getBGColor();}
	@Override public boolean isLowPassable(){return getCurState().isLowPassable();}
	@Override public boolean isHighPassable(){return getCurState().isHighPassable();}
	@Override public boolean isTransparent(){return getCurState().isTransparent();}
	@Override public MapCellBase getBase(){return getCurState().getBase();}
   
	
   @Override
	public void setFGColor(int f)
   {
      stateA.setFGColor(f);
      stateB.setFGColor(f);
   }
   
   @Override
	public void setBGColor(int b)
   {
      stateA.setBGColor(b);
      stateB.setBGColor(b);
   }
   
   @Override
   public void setColors(int fg, int bg)
   {
      setFGColor(fg);
      setBGColor(bg);
   }
}