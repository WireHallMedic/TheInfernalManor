package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import TheInfernalManor.Actor.*;
import WidlerSuite.SpiralSearch;
import WidlerSuite.Coord;

public class RoomTemplate
{
   private int width;
   private int height;
   private MapCellBase[][] baseTable;
   private boolean[][] independentlyRandomTable;
   private boolean[][] dependentlyRandomTable;
   
   public RoomTemplate(int w, int w)
   {
      setSizes(w, h);
   }
   
   public void setSizes(int w, int h)
   {
      width = w;
      height = h;
      baseTable = new MapCellBase[w][h];
      independentlyRandomTable = new boolean[w][h];
      dependentlyRandomTable = new boolean[w][h];
      for(int x = 0; x < w; x++)
      for(int y = 0; y < h; y++)
      {
      
      }
   }
}