package TheInfernalManor.Map;

import TheInfernalManor.Actor.*;

public class ZoneMap
{
	private int width;
	private int height;
	private String name;
	private boolean[][] lowPassMap;
	private boolean[][] highPassMap;
	private boolean[][] transparentMap;
	private MapCell[][] tileMap;
   private MapCell oobTile;


	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public String getName(){return name;}
	public boolean[][] getLowPassMap(){return lowPassMap;}
	public boolean[][] getHighPassMap(){return highPassMap;}
	public boolean[][] getTransparentMap(){return transparentMap;}
	public MapCell[][] getTileMap(){return tileMap;}


	public void setName(String n){name = n;}
   public void setOOBTile(MapCell o){oobTile = o;}


   public ZoneMap(int w, int h)
   {
      setSize(w, h);
   }
   
   public void setSize(int w, int h)
   {
      width = w;
      height = h;
      lowPassMap = new boolean[w][h];
      highPassMap = new boolean[w][h];
      transparentMap = new boolean[w][h];
      tileMap = new MapCell[w][h];
      oobTile = new MapCell(MapCellBase.WALL);
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
         setTile(x, y, new MapCell(MapCellBase.WALL));
   }
   
   public boolean isInBounds(int x, int y)
   {
      return x >= 0 && x < width &&
         y >= 0 && y < height;
   }
   
   public boolean isLowPassable(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y].isLowPassable();
      return oobTile.isLowPassable();
   }
   
   public boolean isHighPassable(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y].isHighPassable();
      return oobTile.isHighPassable();
   }
   
   public boolean isTransparent(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y].isTransparent();
      return oobTile.isTransparent();
   }
   
   public void setTile(int x, int y, MapCell cell)
   {
      if(isInBounds(x, y))
      {
         tileMap[x][y] = cell;
         lowPassMap[x][y] = cell.isLowPassable();
         highPassMap[x][y] = cell.isHighPassable();
         transparentMap[x][y] = cell.isTransparent();
      }
   }
   
   public void updateMaps(int x, int y)
   {
      lowPassMap[x][y] = getTile(x, y).isLowPassable();
      highPassMap[x][y] = getTile(x, y).isHighPassable();
      transparentMap[x][y] = getTile(x, y).isTransparent();
   }
   
   public MapCell getTile(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y];
      return oobTile;
   }
   
   public boolean canStep(int x, int y, Actor a)
   {
      return getTile(x, y).isLowPassable();
   }
   
   public void doToggle(int x, int y)
   {
      if(getTile(x, y) instanceof ToggleTile)
      {
         ToggleTile tt = (ToggleTile)getTile(x, y);
         tt.toggle();
         updateMaps(x, y);
      }
   }
}