package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;
import WidlerSuite.SpiralSearch;
import java.util.*;

public class RoomTemplate implements MapConstants
{   
   private int width;
   private int height;
   private RoomTemplateCellMapping[][] mappingTable;
   private boolean[][] independentlyRandomTable;
   private boolean[][] dependentlyRandomTable;
   private ConnectionType connectionType;
   
   public int getWidth(){return width;}
   public int getHeight(){return height;}
   public ConnectionType getConnectionType(){return connectionType;}
   
   public RoomTemplate(int w, int h)
   {
      setSize(w, h);
      clear();
      setConnectionType();
   }
   
   public RoomTemplate(Vector<String> data)
   {
      this(1, 1);
      deserialize(data);
      setConnectionType();
   }
   
   public RoomTemplate(RoomTemplate that)
   {
      setSize(that.getWidth(), that.getHeight());
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         this.mappingTable[x][y] = that.mappingTable[x][y];
         this.independentlyRandomTable[x][y] = that.independentlyRandomTable[x][y];
         this.dependentlyRandomTable[x][y] = that.dependentlyRandomTable[x][y];
      }
      setConnectionType();
   }
   
   public void clear()
   {
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
         set(x, y, '.', false, false);
   }
   
   public RoomTemplateCellMapping getCellMapping(int x, int y)
   {
      if(isInBounds(x, y))
         return mappingTable[x][y];
      return null;
   }
   
   public char getChar(int x, int y)
   {
      if(isInBounds(x, y))
         return getCellMapping(x, y).character;
      return (char)0;
   }
   
   public boolean isIndependentlyRandom(int x, int y)
   {
      if(isInBounds(x, y))
         return independentlyRandomTable[x][y];
      return false;
   }
   
   public boolean isDependentlyRandom(int x, int y)
   {
      if(isInBounds(x, y))
         return dependentlyRandomTable[x][y];
      return false;
   }
   
   public void setSize(int w, int h)
   {
      width = w;
      height = h;
      mappingTable = new RoomTemplateCellMapping[w][h];
      independentlyRandomTable = new boolean[w][h];
      dependentlyRandomTable = new boolean[w][h];
      for(int x = 0; x < w; x++)
      for(int y = 0; y < h; y++)
      {
         mappingTable[x][y] = RoomTemplateCellMapping.CLEAR;
      }
   }
   
   public boolean isInBounds(int x, int y)
   {
      return x >= 0 &&
             x < width &&
             y >= 0 && 
             y < height;
   }
   
   public void set(int x, int y, char c, boolean ir, boolean dr)
   {
      if(isInBounds(x, y))
      {
         setChar(x, y, c);
         setIR(x, y, ir);
         setDR(x, y, dr);
      }
   }
   
   public void set(int x, int y, char c)
   {
      set(x, y, c, false, false);
   }
   
   private void setChar(int x, int y, char c)
   {
      RoomTemplateCellMapping cm = RoomTemplateCellMapping.deserialize(c);
      if(cm != null)
         mappingTable[x][y] = cm;
   }
   
   private void setIR(int x, int y, boolean ir)
   {
      independentlyRandomTable[x][y] = ir;
      if(ir)
         dependentlyRandomTable[x][y] = false;
   }
   
   private void setDR(int x, int y, boolean dr)
   {
      dependentlyRandomTable[x][y] = dr;
      if(dr)
         independentlyRandomTable[x][y] = false;
   }
   
   public void deserialize(int x, int y, char mod, char val)
   {
      boolean ir = false;
      boolean dr = false;
      if(mod == RoomTemplateCellMapping.INDEPENDENTLY_RANDOM)
         ir = true;
      if(mod == RoomTemplateCellMapping.DEPENDENTLY_RANDOM)
         dr = true;
      set(x, y, val, ir, dr);
   }
   
   public String serialize(int x, int y)
   {
      if(isInBounds(x, y))
      {
         String prefix = " ";
         if(independentlyRandomTable[x][y])
            prefix = "" + RoomTemplateCellMapping.INDEPENDENTLY_RANDOM;
         if(dependentlyRandomTable[x][y])
            prefix = "" + RoomTemplateCellMapping.DEPENDENTLY_RANDOM;
         return prefix + mappingTable[x][y].character;
      }
      return null;
   }
   
   public Vector<String> serialize()
   {
      Vector<String> output = new Vector<String>();
      for(int y = 0; y < height; y++)
      {
         String str = "";
         for(int x = 0; x < width; x++)
            str = str + serialize(x, y);
         output.add(str);
      }
      return output;
   }
   
   public void deserialize(Vector<String> input)
   {
      int w = input.elementAt(0).length() / 2;
      int h = input.size();
      setSize(w, h);
      char val;
      char mod;
      for(int y = 0; y < height; y++)
      for(int x = 0; x < width; x++)
      {
         mod = input.elementAt(y).charAt(x * 2);
         val = input.elementAt(y).charAt((x * 2) + 1);
         deserialize(x, y, mod, val);
      }
   }
   
   public boolean connectsNorth()
   {
      for(int x = 1; x < width - 1; x++)
      {
         if(mappingTable[x][0].mapCellBase.pathingPassable)
            return true;
      }
      return false;
   }
   
   public boolean connectsSouth()
   {
      for(int x = 1; x < width - 1; x++)
      {
         if(mappingTable[x][height - 1].mapCellBase.pathingPassable)
            return true;
      }
      return false;
   }
   
   public boolean connectsEast()
   {
      for(int y = 1; y < height - 1; y++)
      {
         if(mappingTable[width - 1][y].mapCellBase.pathingPassable)
            return true;
      }
      return false;
   }
   
   public boolean connectsWest()
   {
      for(int y = 1; y < height - 1; y++)
      {
         if(mappingTable[0][y].mapCellBase.pathingPassable)
            return true;
      }
      return false;
   }
   
   public void setConnectionType()
   {
      boolean north = connectsNorth();
      boolean east = connectsEast();
      boolean south = connectsSouth();
      boolean west = connectsWest();
      
      int exits = 0;
      if(north) exits++;
      if(south) exits++;
      if(east) exits++;
      if(west) exits++;
      
      if(exits == 0)
      {
         connectionType = ConnectionType.ISOLATED;
         return;
      }
      if(exits == 1)
      {
         connectionType = ConnectionType.TERMINAL;
         return;
      }
      if(exits == 3)
      {
         connectionType = ConnectionType.TEE;
         return;
      }
      if(exits == 4)
      {
         connectionType = ConnectionType.CROSS;
         return;
      }
      // exits == 2
      if((north && south) || (east && west))
      {
         connectionType = ConnectionType.STRAIGHT;
         return;
      }
      connectionType = ConnectionType.ELBOW;
   }
   
   
   // rotates a quarter turn clockwise
   public void rotate()
   {
      RoomTemplateCellMapping[][] newMappingTable  = new RoomTemplateCellMapping[width][height];
      boolean[][] newIRTable = new boolean[width][height];
      boolean[][] newDRTable = new boolean[width][height];
      int w = height - 1;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         newMappingTable[x][y] = mappingTable[y][w - x];
         newIRTable[x][y] = independentlyRandomTable[y][w - x];
         newDRTable[x][y] = dependentlyRandomTable[y][w - x];
      }
      mappingTable = newMappingTable;
      independentlyRandomTable = newIRTable;
      dependentlyRandomTable = newDRTable;
   }
   
   public void mirrorX()
   {
      RoomTemplateCellMapping[][] newMappingTable  = new RoomTemplateCellMapping[width][height];
      boolean[][] newIRTable = new boolean[width][height];
      boolean[][] newDRTable = new boolean[width][height];
      int w = width - 1;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         newMappingTable[x][y] = mappingTable[w - x][y];
         newIRTable[x][y] = independentlyRandomTable[w - x][y];
         newDRTable[x][y] = dependentlyRandomTable[w - x][y];
      }
      mappingTable = newMappingTable;
      independentlyRandomTable = newIRTable;
      dependentlyRandomTable = newDRTable;
   }
   
   public void mirrorY()
   {
      RoomTemplateCellMapping[][] newMappingTable  = new RoomTemplateCellMapping[width][height];
      boolean[][] newIRTable = new boolean[width][height];
      boolean[][] newDRTable = new boolean[width][height];
      int w = height - 1;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         newMappingTable[x][y] = mappingTable[x][w - y];
         newIRTable[x][y] = independentlyRandomTable[x][w - y];
         newDRTable[x][y] = dependentlyRandomTable[x][w - y];
      }
      mappingTable = newMappingTable;
      independentlyRandomTable = newIRTable;
      dependentlyRandomTable = newDRTable;
   }
   
   // returns a copy of the template, resolving randomness
   public RoomTemplate resolveRandomTiles()
   {
      RoomTemplate newRT = new RoomTemplate(this);
      boolean dependentRoll = RNG.nextBoolean();
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         if(independentlyRandomTable[x][y])
         {
            newRT.independentlyRandomTable[x][y] = false;
            if(RNG.nextBoolean())
               newRT.mappingTable[x][y] = RoomTemplateCellMapping.CLEAR;
         }
         if(dependentlyRandomTable[x][y])
         {
            newRT.dependentlyRandomTable[x][y] = false;
            if(dependentRoll)
               newRT.mappingTable[x][y] = RoomTemplateCellMapping.CLEAR;
         }
      }
      return newRT;
   }
   
   // debug method
   public void print()
   {
      Vector<String> out = this.serialize();
      for(int i = 0; i < out.elementAt(0).length() + 2; i++)
         System.out.print("X");
      System.out.println("");
      for(int i = 0; i < out.size(); i++)
         System.out.println("X" + out.elementAt(i) + "X");
      for(int i = 0; i < out.elementAt(0).length() + 2; i++)
         System.out.print("X");
      System.out.println("");
   }
}