package TheInfernalManor.Map;

import TheInfernalManor.Engine.*;

public class MapGrid implements MapConstants
{
	private int width;
	private int height;
	private GridNode[][] nodeMap;
	private RoomTemplateDeck deck;
	private double connectivity;
   private GridNode oobNode;
   private RoomTemplate[][] templateMap;
   private double minRatio;


	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public GridNode[][] getNodeMap(){return nodeMap;}
	public RoomTemplateDeck getDeck(){return deck;}
	public double getConnectivity(){return connectivity;}
   public RoomTemplate[][] getTemplateMap(){return templateMap;}
   public double getMinRatio(){return minRatio;}


	public void setDeck(RoomTemplateDeck d){deck = d;}
	public void setConnectivity(double c){connectivity = c;}


   public MapGrid(int w, int h, double c, RoomTemplateDeck d, double mr)
   {
      width = w;
      height = h;
      connectivity = c;
      deck = d;
      minRatio = Math.min(1.0, mr);
      oobNode = new GridNode();
      oobNode.north = ConnectionStatus.MUST_NOT;
      oobNode.south = ConnectionStatus.MUST_NOT;
      oobNode.east = ConnectionStatus.MUST_NOT;
      oobNode.west = ConnectionStatus.MUST_NOT;
      do
      {
         generateBlankNodeMap();
         templateMap = new RoomTemplate[width][height];
         populateNodeMap();
         populateTemplateMap();
      }
      while(getRoomRatio() < minRatio);
   }
   
   public MapGrid(int w, int h, double c, RoomTemplateDeck d)
   {
      this(w, h, c, d, 0.0);
   }
   
   public double getRoomRatio()
   {
      int max = width * height;
      int actual = 0;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
         if(nodeMap[x][y].hasConnections())
            actual++;
      return (double)actual / (double)max;
   }
   
   private void generateBlankNodeMap()
   {
      nodeMap = new GridNode[width][height];
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
         nodeMap[x][y] = new GridNode();
   }
   
   private GridNode getNode(int x, int y)
   {
      if(x < 0 || x >= width ||
         y < 0 || y >= height)
         return oobNode;
      return nodeMap[x][y];
   }
   
   public void populateNodeMap()
   {
      fillNode(width / 2, height / 2);
      fillRemainingNodes();
   }
   
   private void fillRemainingNodes()
   {
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         GridNode curNode = getNode(x, y);
         if(curNode.north == ConnectionStatus.UNDEFINED)
            curNode.north = ConnectionStatus.MUST_NOT;
         if(curNode.south == ConnectionStatus.UNDEFINED)
            curNode.south = ConnectionStatus.MUST_NOT;
         if(curNode.east == ConnectionStatus.UNDEFINED)
            curNode.east = ConnectionStatus.MUST_NOT;
         if(curNode.west == ConnectionStatus.UNDEFINED)
            curNode.west = ConnectionStatus.MUST_NOT;
      }
   }
   
   public void populateTemplateMap()
   {
      GridNode curNode;
      RoomTemplate curRT;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         curNode = getNode(x, y);
         curRT = deck.getRandom(curNode.getConnectionType());
         curRT.resolveRandomTiles();
         rotateToMatch(curNode, curRT);
         templateMap[x][y] = curRT;
      }
   }
   
   
   private void fillNode(int x, int y)
   {
      GridNode thisNode = getNode(x, y);
      GridNode thatNode = null;
      ConnectionStatus connection = null;
      boolean recurseNorth = false;
      boolean recurseSouth = false;
      boolean recurseEast = false;
      boolean recurseWest = false;
      // north
      thatNode = getNode(x, y - 1);
      if(thisNode.north == ConnectionStatus.UNDEFINED)
      {
         if(thatNode.south != ConnectionStatus.UNDEFINED)
         {
            thisNode.north = thatNode.south;
         }
         else
         {
            if(roll())
            {
               connection = ConnectionStatus.MUST;
               recurseNorth = true;
            }
            else
               connection = ConnectionStatus.MUST_NOT;
            thisNode.north = connection;
            thatNode.south = connection;
         }
      }
      // south
      thatNode = getNode(x, y + 1);
      if(thisNode.south == ConnectionStatus.UNDEFINED)
      {
         if(thatNode.north != ConnectionStatus.UNDEFINED)
         {
            thisNode.south = thatNode.south;
         }
         else
         {
            if(roll())
            {
               connection = ConnectionStatus.MUST;
               recurseSouth = true;
            }
            else
               connection = ConnectionStatus.MUST_NOT;
            thisNode.south = connection;
            thatNode.north = connection;
         }
      }
      // west
      thatNode = getNode(x - 1, y);
      if(thisNode.west == ConnectionStatus.UNDEFINED)
      {
         if(thatNode.east != ConnectionStatus.UNDEFINED)
         {
            thisNode.west = thatNode.east;
         }
         else
         {
            if(roll())
            {
               connection = ConnectionStatus.MUST;
               recurseWest = true;
            }
            else
               connection = ConnectionStatus.MUST_NOT;
            thisNode.west = connection;
            thatNode.east = connection;
         }
      }
      // east
      thatNode = getNode(x + 1, y);
      if(thisNode.east == ConnectionStatus.UNDEFINED)
      {
         if(thatNode.west != ConnectionStatus.UNDEFINED)
         {
            thisNode.east = thatNode.west;
         }
         else
         {
            if(roll())
            {
               connection = ConnectionStatus.MUST;
               recurseEast = true;
            }
            else
               connection = ConnectionStatus.MUST_NOT;
            thisNode.east = connection;
            thatNode.west = connection;
         }
      }
      if(recurseNorth) fillNode(x, y - 1);
      if(recurseSouth) fillNode(x, y + 1);
      if(recurseEast) fillNode(x + 1, y);
      if(recurseWest) fillNode(x - 1, y);
   }
   
   private boolean roll()
   {
      return RNG.nextDouble() <= connectivity;
   }
   
   private void rotateToMatch(GridNode node, RoomTemplate template)
   {
      while(!node.matches(template))
         template.rotate();
   }
   
   private class GridNode
   {
      public ConnectionStatus north = ConnectionStatus.UNDEFINED;
      public ConnectionStatus south = ConnectionStatus.UNDEFINED;
      public ConnectionStatus east = ConnectionStatus.UNDEFINED;
      public ConnectionStatus west = ConnectionStatus.UNDEFINED;
      
      public boolean hasConnections()
      {
         return north == ConnectionStatus.MUST ||
                south == ConnectionStatus.MUST ||
                east == ConnectionStatus.MUST ||
                west == ConnectionStatus.MUST;
      }
      
      public void setUndefinedTo(ConnectionStatus newStatus)
      {
         if(north == ConnectionStatus.UNDEFINED)
            north = newStatus;
         if(south == ConnectionStatus.UNDEFINED)
            south = newStatus;
         if(east == ConnectionStatus.UNDEFINED)
            east = newStatus;
         if(west == ConnectionStatus.UNDEFINED)
            west = newStatus;
      }
      
      public ConnectionType getConnectionType()
      {
         int exits = 0;
         if(north == ConnectionStatus.MUST) exits++;
         if(south == ConnectionStatus.MUST) exits++;
         if(east == ConnectionStatus.MUST) exits++;
         if(west == ConnectionStatus.MUST) exits++;
         
         if(exits == 0)
         {
            return ConnectionType.ISOLATED;
         }
         if(exits == 1)
         {
            return ConnectionType.TERMINAL;
         }
         if(exits == 3)
         {
            return ConnectionType.TEE;
         }
         if(exits == 4)
         {
            return ConnectionType.CROSS;
         }
         // exits == 2
         if((north == ConnectionStatus.MUST && south== ConnectionStatus.MUST) || 
         (east == ConnectionStatus.MUST && west== ConnectionStatus.MUST))
         {
            return ConnectionType.STRAIGHT;
         }
         return ConnectionType.ELBOW;
      }
      
      public boolean isComplete()
      {
         return north != ConnectionStatus.UNDEFINED &&
                south != ConnectionStatus.UNDEFINED &&
                east != ConnectionStatus.UNDEFINED &&
                west != ConnectionStatus.UNDEFINED;
      }
      
      public boolean matches(RoomTemplate rt)
      {
         boolean n = north == ConnectionStatus.MUST;
         boolean s = south == ConnectionStatus.MUST;
         boolean e = east == ConnectionStatus.MUST;
         boolean w = west == ConnectionStatus.MUST;
         
         return n == rt.connectsNorth() &&
                s == rt.connectsSouth() &&
                e == rt.connectsEast() &&
                w == rt.connectsWest();
      }
   }
}