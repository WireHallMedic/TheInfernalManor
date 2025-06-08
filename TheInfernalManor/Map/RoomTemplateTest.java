package TheInfernalManor.Map;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;


public class RoomTemplateTest implements MapConstants
{


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() 
   {
   }
   
   public RoomTemplate getHollowRoom(int size)
   {
      RoomTemplate rt = new RoomTemplate(size, size);
      for(int x = 0; x < size; x++)
      for(int y = 0; y < size; y++)
      {
         rt.set(x, y, '.', false, false);
      }
      for(int x = 0; x < size; x++)
      {
         rt.set(x, 0, '#', false, false);
         rt.set(x, size - 1, '#', false, false);
      }
      for(int y = 0; y < size; y++)
      {
         rt.set(0, y, '#', false, false);
         rt.set(size - 1, y, '#', false, false);
      }
      return rt;
   }


   
   @Test public void serializationAndDeserializationTest() 
   {
      int width = 10;
      int height = 10;
      boolean problem = false;
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
      
      for(int y = 0; y < height; y++)
      for(int x = 0; x < width * 2; x++)
         if(a.elementAt(y).charAt(x) != b.elementAt(y).charAt(x))
            problem = true;
   
      Assert.assertFalse("Serializing and deserializing result in good copy.", problem);
   }


   @Test public void connectionTypeTest() 
   {
      // isolated
      RoomTemplate rt = getHollowRoom(5);
      rt.setConnectionType();
      assertEquals("Recognize isolated room.", ConnectionType.ISOLATED, rt.getConnectionType());
      
      // terminal
      rt = getHollowRoom(5);
      rt.set(0, 2, '.');
      rt.setConnectionType();
      assertEquals("Recognize terminal room.", ConnectionType.TERMINAL, rt.getConnectionType());
      
      // straight
      rt = getHollowRoom(5);
      rt.set(0, 1, '.');
      rt.set(4, 3, '.');
      rt.setConnectionType();
      assertEquals("Recognize straight room.", ConnectionType.STRAIGHT, rt.getConnectionType());
      
      // elbow
      rt = getHollowRoom(5);
      rt.set(2, 0, '.');
      rt.set(0, 2, '.');
      rt.setConnectionType();
      assertEquals("Recognize elbow room.", ConnectionType.ELBOW, rt.getConnectionType());
      
      // tee
      rt = getHollowRoom(5);
      rt.set(2, 0, '.');
      rt.set(0, 2, '.');
      rt.set(4, 2, '.');
      rt.setConnectionType();
      assertEquals("Recognize tee room.", ConnectionType.TEE, rt.getConnectionType());
      
      // cross
      rt = getHollowRoom(5);
      rt.set(2, 0, '.');
      rt.set(0, 2, '.');
      rt.set(4, 2, '.');
      rt.set(2, 4, '.');
      rt.setConnectionType();
      assertEquals("Recognize cross room.", ConnectionType.CROSS, rt.getConnectionType());
   }
}
