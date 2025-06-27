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
   private MapCell brokenForm;
   private MapCellBase base;
   private int paletteVariation;


	public int getIconIndex(){return iconIndex;}
	public int getFGColor(){return fgColor;}
	public int getBGColor(){return bgColor;}
	public boolean isLowPassable(){return lowPassable;}
	public boolean isHighPassable(){return highPassable;}
	public boolean isTransparent(){return transparent;}
   public MapCell getBrokenForm(){return brokenForm;}
   public MapCellBase getBase(){return base;}
   public int getPaletteVariation(){return paletteVariation;}


	public void setIconIndex(int i){iconIndex = i;}
	public void setFGColor(int f){fgColor = f;}
	public void setBGColor(int b){bgColor = b;}
	public void setLowPassable(boolean l){lowPassable = l;}
	public void setHighPassable(boolean h){highPassable = h;}
	public void setTransparent(boolean t){transparent = t;}
   public void setBrokenForm(MapCell bf){brokenForm = bf;}
   public void setBase(MapCellBase b){base = b;}
   public void setPaletteVariation(int v){paletteVariation = v;}


   public MapCell(int index, boolean lowPass, boolean highPass, boolean trans)
   {
      iconIndex = index;
      fgColor = WHITE;
      bgColor = BLACK;
      lowPassable = lowPass;
      highPassable = highPass;
      transparent = trans;
      brokenForm = null;
      base = null;
      paletteVariation = MapPalette.BASE;
   }
   
   
   public MapCell(MapCellBase base)
   {
      this(base.iconIndex, base.lowPassable, base.highPassable, base.transparent);
      setBase(base);
   }
   
   public MapCell(MapCell that)
   {
      this.iconIndex = that.iconIndex;
      this.fgColor = that.fgColor;
      this.bgColor = that.bgColor;
      this.lowPassable = that.lowPassable;
      this.highPassable = that.highPassable;
      this.transparent = that.transparent;
      this.brokenForm = null;
      if(that.brokenForm != null)
         this.brokenForm = new MapCell(that.brokenForm);
      this.base = that.base;
      this.paletteVariation = that.paletteVariation;
   }
   
   public void setColors(int fg, int bg)
   {
      setFGColor(fg);
      setBGColor(bg);
   }
   
   
   public boolean isBreakable()
   {
      return brokenForm != null;
   }
   
   
   public boolean isPermeable()
   {
      return isHighPassable() || isBreakable();
   }
}