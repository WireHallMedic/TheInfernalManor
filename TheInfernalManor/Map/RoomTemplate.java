package TheInfernalManor.Map;

import TheInfernalManor.GUI.*;
import WidlerSuite.SpiralSearch;
import java.util.*;

public class RoomTemplate
{
   private int width;
   private int height;
   private RoomTemplateCellMapping[][] mappingTable;
   private boolean[][] independentlyRandomTable;
   private boolean[][] dependentlyRandomTable;
   
   public int getWidth(){return width;}
   public int getHeight(){return height;}
   
   public RoomTemplate(int w, int h)
   {
      setSize(w, h);
   }
   
   public RoomTemplate(Vector<String> data)
   {
      this(1, 1);
      deserialize(data);
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
   
   public static void main(String[] args)
   {
      RoomTemplate rt = new RoomTemplate(5, 5);
      rt.set(1, 1, '0', false, false);
      rt.set(2, 2, '0', true, false);
      rt.set(3, 3, '0', false, true);
      rt.print();
      
      RoomTemplate rt2 = new RoomTemplate(rt.serialize());
      rt2.print();
   }
}