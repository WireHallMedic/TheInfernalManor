package TheInfernalManor.Map;

import TheInfernalManor.Engine.*;

public class GridOfMapGrids implements MapConstants
{
	private int width;
	private int height;
   private int lowerWidth;
   private int lowerHeight;
	private RoomTemplateDeck deck;
	private double connectivity;
	private double lowerConnectivity;
   private double minRatio;
   private double lowerMinRatio;
   private MapGrid upperGrid;
   private MapGrid[][] lowerGridArr;
   private boolean maximizeLowerConnections;


	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public RoomTemplateDeck getDeck(){return deck;}
	public double getConnectivity(){return connectivity;}
   public double getUpperMinRatio(){return minRatio;}
   public int getLowerWidth(){return lowerWidth;}
   public int getLowerHeight(){return lowerHeight;}
   public MapGrid getUpperGrid(){return upperGrid;}


	public void setDeck(RoomTemplateDeck d){deck = d;}
	public void setUpperConnectivity(double c){connectivity = c;}
   public void setLowerConnectivity(double c){lowerConnectivity = c;}
   public void setUpperMinRatio(double mr){minRatio = mr;}
   public void setLowerMinRatio(double mr){minRatio = mr;}
   public void setLowerWidth(int w){lowerWidth = w;}
   public void setLowerHeight(int h){lowerHeight = h;}


   public GridOfMapGrids(int w, int h, double c, RoomTemplateDeck d, double mr)
   {
      width = w;
      height = h;
      connectivity = c;
      lowerConnectivity = .5;
      deck = d;
      minRatio = mr;
      lowerMinRatio = .5;
      lowerWidth = 3;
      lowerHeight = 3;
      maximizeLowerConnections = false;
      rollUpper();
   }
   
   public void maximizeConnections()
   {
      upperGrid.maximizeConnections();
      rollLowers();
   }
   
   public void maximizeLowerConnections()
   {
      maximizeLowerConnections = true;
      rollLowers();
   }
   
   public void rollUpper()
   {
      upperGrid = new MapGrid(width, height, connectivity, deck, minRatio);
      rollLowers();
   }
   
   public void rollLowers()
   {
      lowerGridArr = new MapGrid[width][height];
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         lowerGridArr[x][y] = new MapGrid(lowerWidth, lowerHeight, lowerConnectivity, deck, lowerMinRatio);
         if(upperGrid.getTemplateMap()[x][y].getConnectionType() == ConnectionType.ISOLATED)
            lowerGridArr[x][y].closeAll();
         if(maximizeLowerConnections)
            lowerGridArr[x][y].maximizeConnections();
      }
   }
   
   public void populateTemplateMaps()
   {
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         lowerGridArr[x][y].populateTemplateMap();
      }
   }
   
   public MapGrid getLowerGrid(int x, int y)
   {
      return lowerGridArr[x][y];
   }
}