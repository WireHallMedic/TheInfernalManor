package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Actor.*;
import WidlerSuite.*;
import java.util.*;

public class ZoneMap implements GUIConstants
{
	private int width;
	private int height;
	private String name;
	private boolean[][] lowPassMap;
	private boolean[][] highPassMap;
	private boolean[][] transparentMap;
	private MapCell[][] tileMap;
   private int[][] lastSeenMap;
   private int[][] lastSeenColorMap;
   private Item[][] itemMap;
   private ForegroundObject[][] decorationMap;
   private MapCell oobTile;
   private MapCell defaultLastSeenTile;
   private Vector<TIMRoom> roomList;
   private Coord entranceLoc;
   private Coord exitLoc;


	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public String getName(){return name;}
	public boolean[][] getLowPassMap(){return lowPassMap;}
	public boolean[][] getHighPassMap(){return highPassMap;}
	public boolean[][] getTransparentMap(){return transparentMap;}
   public Item[][] getItemMap(){return itemMap;}
   public ForegroundObject[][] getDecorationMap(){return decorationMap;}
	public MapCell[][] getTileMap(){return tileMap;}
   public int[][] getLastSeenMap(){return lastSeenMap;}
   public int[][] getLastSeenColorMap(){return lastSeenColorMap;}
   public Vector<TIMRoom> getRoomList(){return roomList;}
   public Coord getEntranceLoc(){return new Coord(entranceLoc);}
   public Coord getExitLoc(){return new Coord(exitLoc);}


	public void setName(String n){name = n;}
   public void setOOBTile(MapCell o){oobTile = o;}
   public void setRoomList(Vector<TIMRoom> rl){roomList = rl;}


   public ZoneMap(int w, int h)
   {
      setSize(w, h);
      roomList = new Vector<TIMRoom>();
   }
   
   public void setSize(int w, int h)
   {
      width = w;
      height = h;
      lowPassMap = new boolean[w][h];
      highPassMap = new boolean[w][h];
      transparentMap = new boolean[w][h];
      tileMap = new MapCell[w][h];
      lastSeenMap = new int[w][h];
      lastSeenColorMap = new int[w][h];
      itemMap = new Item[w][h];
      decorationMap = new ForegroundObject[w][h];
      oobTile = new MapCell(MapCellBase.WALL);
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         setTile(x, y, new MapCell(MapCellBase.WALL));
         itemMap[x][y] = null;
         decorationMap[x][y] = null;
         lastSeenMap[x][y] = 0; // default is 0 instead of ' ' to differentiate
         lastSeenColorMap[x][y] = WHITE;
      }
   }
   
   public boolean isInBounds(int x, int y)
   {
      return x >= 0 && x < width &&
         y >= 0 && y < height;
   }
   public boolean isInBounds(Coord c){return isInBounds(c.x, c.y);}
   
   public boolean isLowPassable(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y].isLowPassable();
      return oobTile.isLowPassable();
   }
   public boolean isLowPassable(Coord c){return isLowPassable(c.x, c.y);}
   
   public boolean isHighPassable(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y].isHighPassable();
      return oobTile.isHighPassable();
   }
   public boolean isHighPassable(Coord c){return isHighPassable(c.x, c.y);}
   
   public boolean isTransparent(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y].isTransparent();
      return oobTile.isTransparent();
   }
   public boolean isTransparent(Coord c){return isTransparent(c.x, c.y);}
   
   public boolean isPermeable(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y].isPermeable();
      return oobTile.isPermeable();
   }
   public boolean isPermeable(Coord c){return isPermeable(c.x, c.y);}
   
   public ForegroundObject getDecoration(int x, int y)
   {
      if(isInBounds(x, y))
         return decorationMap[x][y];
      return null;
   }
   public ForegroundObject getDecoration(Coord c){return getDecoration(c.x, c.y);}
   
   public void setDecoration(int x, int y, ForegroundObject fo)
   {
      if(isInBounds(x, y))
         decorationMap[x][y] = fo;
   }
   
   public boolean isItemAt(int x, int y)
   {
      return getItemAt(x, y) != null;
   }
   public boolean isItemAt(Coord c){return isItemAt(c.x, c.y);}
   
   public Item getItemAt(int x, int y)
   {
      if(isInBounds(x, y))
         return itemMap[x][y];
      return null;
   }
   public Item getItemAt(Coord c){return getItemAt(c.x, c.y);}
   
   public void setItemAt(int x, int y, Item item)
   {
      if(isInBounds(x, y))
         itemMap[x][y] = item;
   }
   
   public void setTile(int x, int y, MapCell cell)
   {
      if(isInBounds(x, y))
      {
         tileMap[x][y] = cell;
         updateMaps(x, y);
      }
   }
   
   public void setLastSeen(int x, int y, int index, int color)
   {
      if(isInBounds(x, y))
      {
         lastSeenMap[x][y] = index;
         lastSeenColorMap[x][y] = color;
      }
   }
   
   public void updateMaps(int x, int y)
   {
      lowPassMap[x][y] = getTile(x, y).isLowPassable();
      highPassMap[x][y] = getTile(x, y).isHighPassable();
      transparentMap[x][y] = getTile(x, y).isTransparent();
      if(getTile(x, y).getBase() == MapCellBase.ENTRANCE)
         entranceLoc = new Coord(x, y);
      if(getTile(x, y).getBase() == MapCellBase.EXIT)
         exitLoc = new Coord(x, y);
   }
   
   public void updateAllMaps()
   {
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
         updateMaps(x, y);
   }
   
   public MapCell getTile(int x, int y)
   {
      if(isInBounds(x, y))
         return tileMap[x][y];
      return oobTile;
   }
   public MapCell getTile(Coord c){return getTile(c.x, c.y);}
   
   public int getLastSeen(int x, int y)
   {
      if(isInBounds(x, y))
         return lastSeenMap[x][y];
      return 0;
   }
   public int getLastSeen(Coord c){return getLastSeen(c.x, c.y);}
   
   public int getLastSeenColor(int x, int y)
   {
      if(isInBounds(x, y))
         return lastSeenColorMap[x][y];
      return WHITE;
   }
   public int getLastSeenColor(Coord c){return getLastSeenColor(c.x, c.y);}
   
   public boolean canStep(int x, int y, Actor a)
   {
      return getTile(x, y).isLowPassable();
   }
   public boolean canStep(Coord c, Actor a){return canStep(c.x, c.y, a);}
   
   public void doToggle(int x, int y)
   {
      if(getTile(x, y) instanceof ToggleTile)
      {
         ToggleTile tt = (ToggleTile)getTile(x, y);
         tt.toggle();
         updateMaps(x, y);
         if(getTile(x, y) instanceof ItemDropper)
            for(Item i : ((ItemDropper)getTile(x, y)).takeItems())
               dropItem(i, x, y);
      }
   }
   
   public void breakTile(int x, int y)
   {
      if(getTile(x, y).isBreakable())
      {
         Vector<Item> dropList = null;
         if(getTile(x, y) instanceof ItemDropper)
            dropList = ((ItemDropper)getTile(x, y)).takeItems();
         setTile(x, y, getTile(x, y).getBrokenForm());
         if(dropList != null)
            for(Item i : dropList)
               dropItem(i, x, y);
      }
   }
   public void breakTile(Coord c){breakTile(c.x, c.y);}
   
   // drops an item as close to the target location as possible
   // returns false if not possible
   public boolean dropItem(Item item, int x, int y)
   {
      Coord c = getItemDropLoc(x, y);
      if(c == null)
         return false;
      itemMap[c.x][c.y] = item;
      return true;
   }
   
   // does a floodfill to find the nearest place to drop an item
   private Coord getItemDropLoc(int startX, int startY)
   {
      int serachSize = 6;
      int xCorner = startX - serachSize;
      int yCorner = startY - serachSize;
      SpiralSearch search = getLowPassSearch(startX, startY, serachSize);
      Coord target = null;
      while(target == null)
      {
         target = search.getNext();
         if(target == null)
            break;
         target.x += xCorner;
         target.y += yCorner;
         if(getItemAt(target.x, target.y) != null ||
            !isLowPassable(target.x, target.y))
            target = null;
      }
      return target;
  }
   
   private SpiralSearch getLowPassSearch(int startX, int startY, int radius)
   {
      int diameter = (radius * 2) + 1;
      boolean[][] passMap = new boolean[diameter][diameter];
      int xCorner = startX - radius;
      int yCorner = startY - radius;
      for(int x = 0; x < diameter; x++)
      for(int y = 0; y < diameter; y++)
         passMap[x][y] = isLowPassable(xCorner + x, yCorner + y);
      return new SpiralSearch(passMap, radius, radius);
   }
   
   public boolean[][] getPathingMap(Actor a, int radius)
   {
      boolean[][] pathMap = new boolean[(radius * 2) + 1][(radius * 2) + 1];
      int xOffset = a.getXLocation() - radius;
      int yOffset = a.getYLocation() - radius;
      boolean ignoreDoors = a.getAI().getUsesDoors();
      for(int x = 0; x < (radius * 2) + 1; x++)
      for(int y = 0; y < (radius * 2) + 1; y++)
      {
         MapCell mc = getTile(x + xOffset, y + yOffset);
         pathMap[x][y] = mc.isLowPassable();
         if(mc instanceof Door && ignoreDoors)
            pathMap[x][y] = true;
      }
      return pathMap;
   }
   
   public void applyPalette(MapPalette palette)
   {
      MapCell cell;
      NoiseChoir choir = new NoiseChoir();
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         cell = getTile(x, y);
         if(cell.getBase() != null)
         {
            int bgColor = palette.getBGColor(cell.getBase(), cell.getPaletteVariation());
            int fgColor = palette.getFGColor(cell.getBase(), cell.getPaletteVariation());
            for(int i = 0; i < COLOR_ARRAY.length; i++)
            {
               if(bgColor == COLOR_ARRAY[i])
                  bgColor = GRADIENT_ARRAY[i][(int)(choir.getValue(.1 * x, .1 * y) * GRADIENT_ARRAY[i].length)];
               if(fgColor == COLOR_ARRAY[i])
                  fgColor = GRADIENT_ARRAY[i][(int)(choir.getValue(.1 * x, .1 * y) * GRADIENT_ARRAY[i].length)];
            }
            cell.setColors(fgColor, bgColor);
         }
      }
   }
   
   public boolean hasChest(TIMRoom r)
   {
      for(int x = 0; x < r.size.x; x++)
      for(int y = 0; y < r.size.y; y++)
         if(getTile(r.origin.x + x, r.origin.y + y) instanceof Chest)
            return true;
      return false;
   }
   
   public boolean hasEntrance(TIMRoom r)
   {
      for(int x = 0; x < r.size.x; x++)
      for(int y = 0; y < r.size.y; y++)
         if(getTile(r.origin.x + x, r.origin.y + y).getBase() == MapCellBase.ENTRANCE)
            return true;
      return false;
   }
   
   public boolean hasExit(TIMRoom r)
   {
      for(int x = 0; x < r.size.x; x++)
      for(int y = 0; y < r.size.y; y++)
         if(getTile(r.origin.x + x, r.origin.y + y).getBase() == MapCellBase.EXIT)
            return true;
      return false;
   }
}