package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;

public class MapCell implements GUIConstants
{
	private int iconIndex;
	private int fgColor;
	private int bgColor;
	private boolean lowPassable;
	private boolean highPassable;
	private boolean transparent;


	public int getIconIndex(){return iconIndex;}
	public int getFGColor(){return fgColor;}
	public int getBGColor(){return bgColor;}
	public boolean isLowPassable(){return lowPassable;}
	public boolean isHighPassable(){return highPassable;}
	public boolean isTransparent(){return transparent;}


	public void setIconIndex(int i){iconIndex = i;}
	public void setFGColor(int f){fgColor = f;}
	public void setBGColor(int b){bgColor = b;}
	public void setLowPassable(boolean l){lowPassable = l;}
	public void setHighPassable(boolean h){highPassable = h;}
	public void setTransparent(boolean t){transparent = t;}

   public MapCell(int index, boolean lowPass, boolean highPass, boolean trans)
   {
      iconIndex = index;
      fgColor = WHITE;
      bgColor = BLACK;
      lowPassable = lowPass;
      highPassable = highPass;
      transparent = trans;
   }
}