package TheInfernalManor.GUI;

import WidlerSuite.Coord;

public class VisualEffect implements GUIConstants
{
	private int ticksPerFrame;
   private int ticksElapsed;
	private int curIndex;
   private int maxIndex;
	private int[] iconList;
	private int[] fgList;
	private int[] bgList;
	private int xLoc;
	private int yLoc;


	public int getTicksPerFrame(){return ticksPerFrame;}
	public int[] getIconList(){return iconList;}
	public int[] getFGList(){return fgList;}
	public int[] getBGList(){return bgList;}
	public int getXLocation(){return xLoc;}
	public int getYLocation(){return yLoc;}


	public void setTicksPerFrame(int t){ticksPerFrame = t;}
	public void setIconList(int[] i){iconList = i;}
	public void setFGList(int[] f){fgList = f;}
	public void setBGList(int[] b){bgList = b;}
	public void setXLocation(int x){xLoc = x;}
	public void setYLocation(int y){yLoc = y;}

   
   public VisualEffect(int[] iconArr, int[] fgArr, int[] bgArr)
   {
      curIndex = 0;
      ticksElapsed = 0;
      iconList = iconArr;
      fgList = fgArr;
      bgList = bgArr;
      ticksPerFrame = 2;
      if(iconList != null)
         maxIndex = iconList.length - 1;
      else if(fgList != null)
         maxIndex = fgList.length - 1;
      else if(bgList != null)
         maxIndex = bgList.length - 1;
   }
   
   public int getIcon(){return iconList[curIndex];}
   public int getFG(){return fgList[curIndex];}
   public int getBG(){return bgList[curIndex];}
   
   public boolean isExpired()
   {
      return curIndex > maxIndex;
   }
   
   public boolean hasIconList(){return iconList != null;}
   public boolean hasFGList(){return fgList != null;}
   public boolean hasBGList(){return bgList != null;}
   
   public void increment()
   {
      ticksElapsed++;
      if(ticksElapsed >= ticksPerFrame)
      {
         ticksElapsed = 0;
         curIndex++;
      }
   }
   
   public Coord getLocation()
   {
      return new Coord(xLoc, yLoc);
   }

}