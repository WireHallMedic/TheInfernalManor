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


	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public GridNode[][] getNodeMap(){return nodeMap;}
	public RoomTemplateDeck getDeck(){return deck;}
	public double getConnectivity(){return connectivity;}


	public void setWidth(int w){width = w;}
	public void setHeight(int h){height = h;}
	public void setNodeMap(GridNode[][] n){nodeMap = n;}
	public void setDeck(RoomTemplateDeck d){deck = d;}
	public void setConnectivity(double c){connectivity = c;}


   public MapGrid(int w, int h, double c, RoomTemplateDeck d)
   {
      width = w;
      height = h;
      connectivity = c;
      deck = d;
      oobNode = new GridNode();
      oobNode.north = ConnectionStatus.MUST_NOT;
      oobNode.south = ConnectionStatus.MUST_NOT;
      oobNode.east = ConnectionStatus.MUST_NOT;
      oobNode.west = ConnectionStatus.MUST_NOT;
      generateBlankNodeMap();
      populateNodeMap();
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
         return nodeMap[x][y];
      return oobNode;
   }
   
   public void populateNodeMap()
   {
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
         fillNode(x, y);
   }
   
   private void fillNode(int x, int y)
   {
      GridNode thisNode = getNode(x, y);
      GridNode thatNode = null;
      ConnectionStatus connection = null;
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
               connection = ConnectionStatus.MUST;
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
               connection = ConnectionStatus.MUST;
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
               connection = ConnectionStatus.MUST;
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
               connection = ConnectionStatus.MUST;
            else
               connection = ConnectionStatus.MUST_NOT;
            thisNode.east = connection;
            thatNode.west = connection;
         }
      }
   }
   
   private boolean roll()
   {
      return RNG.nextDouble() <= connectivity;
   }
   
   private void rotateToMatch(GridNode node, RoomTemplate template)
   {
      
   }
   
   private class GridNode
   {
      public ConnectionStatus north = ConnectionStatus.UNDEFINED;
      public ConnectionStatus south = ConnectionStatus.UNDEFINED;
      public ConnectionStatus east = ConnectionStatus.UNDEFINED;
      public ConnectionStatus west = ConnectionStatus.UNDEFINED;
      
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
         
      }
   }
}