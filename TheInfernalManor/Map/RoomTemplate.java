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
   private RoomTemplateCellMapping[][] mappingTable;
   private boolean[][] independentlyRandomTable;
   private boolean[][] dependentlyRandomTable;
   
   public RoomTemplate(int w, int h)
   {
      setSizes(w, h);
   }
   
   public void setSizes(int w, int h)
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
      
      }
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
   // 
//    public String serialize(int x, int y)
//    {
//       int val = baseTable[x][y].iconIndex;
//       if(independentlyRandomTable[x][y])
//          val += IR_FLAG;
//       else if(dependentlyRandomTable[x][y])
//          val += DR_FLAG;
//       return "" + val;
//    }
//    
//    public void deserialize(int x, int y, String str)
//    {
//       int val = Integer.parseInt(str);
//       if(val >= DR_FLAG)
//       {
//          val -= DR_FLAG;
//          dependentlyRandomTable[x][y] = true;
//       }
//       else
//          dependentlyRandomTable[x][y] = false;
//       if(val >= IR_FLAG)
//       {
//          val -= IR_FLAG;
//          independentlyRandomTable[x][y] = true;
//       }
//       else
//          independentlyRandomTable[x][y] = false;
//       MapCellBase base = null;
//       for(int i = 0; i < MapCellBase.values().size(); i++)
//          if(MapCellBase.values()[i].iconIndex == val)
//             base = MapCellBase.values()[i];
//       
//    }
//    
   public static void main(String[] args)
   {
      System.out.println(MapCellBase.CLEAR.iconIndex + "");
   }
}