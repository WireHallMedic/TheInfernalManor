package TheInfernalManor.Map;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;


public class RoomTemplateTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() 
   {
   }


   
   @Test public void serializationAndDeserializationTest() 
   {
      int width = 10;
      int height = 10;
      RoomTemplate rt = new RoomTemplate(width, height);
      int len = RoomTemplateCellMapping.values().length;
      for(int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
         char c = RoomTemplateCellMapping.values()[(x + y) % len].character;
         boolean ir = x == 3;
         boolean dr = y == 5;
         rt.set(x, y, c, ir, dr);
      }
      RoomTemplate rt2 = new RoomTemplate(rt.serialize());
      Vector<String> a = rt.serialize();
      Vector<String> b = rt2.serialize();
      boolean problem = false;
      
      for(int y = 0; y < height; y++)
      for(int x = 0; x < width * 2; x++)
         if(a.elementAt(y).charAt(x) != b.elementAt(y).charAt(x))
            problem = true;
   
      Assert.assertFalse("Serializing and deserializing result in good copy.", problem);
   }
}
