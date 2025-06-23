package TheInfernalManor.Map;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import WidlerSuite.*;


public class TIMRoomTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }


   @Test public void testClearConnections() 
   {
      int width = 4;
      int height = 6;
      ZoneMap z = new ZoneMap(width, height);
      for(int x = 1; x < width - 1; x++)
         for(int y = 1; y < height - 1; y++)
         {
            z.setTile(x, y, MapCellFactory.getMapCell(MapCellBase.CLEAR));
         }
      TIMRoom room = new TIMRoom();
      room.origin = new Coord(0, 0);
      room.size = new Coord(width, height);
      room.setConnections(z);
      Assert.assertFalse("No northern connection.", room.connectsNorth);
      Assert.assertFalse("No eastern connection.", room.connectsEast);
      Assert.assertFalse("No southern connection.", room.connectsSouth);
      Assert.assertFalse("No western connection.", room.connectsWest);
      Assert.assertEquals("Zero connections.", 0, room.numOfConnections);
      Assert.assertFalse("Not a terminal.", room.isTerminal());
      
      // connect north
      for(int x = 1; x < width - 1; x++)
      {
         z.setTile(x, 0, MapCellFactory.getMapCell(MapCellBase.CLEAR));
      }
      room.setConnections(z);
      Assert.assertTrue("Northern connection.", room.connectsNorth);
      Assert.assertFalse("No eastern connection.", room.connectsEast);
      Assert.assertFalse("No southern connection.", room.connectsSouth);
      Assert.assertFalse("No western connection.", room.connectsWest);
      Assert.assertEquals("One connection.", 1, room.numOfConnections);
      Assert.assertTrue("Is a terminal.", room.isTerminal());
      
      // connect south
      for(int x = 1; x < width - 1; x++)
      {
         z.setTile(x, height - 1, MapCellFactory.getMapCell(MapCellBase.CLEAR));
      }
      room.setConnections(z);
      Assert.assertTrue("Northern connection.", room.connectsNorth);
      Assert.assertFalse("No eastern connection.", room.connectsEast);
      Assert.assertTrue("Southern connection.", room.connectsSouth);
      Assert.assertFalse("No western connection.", room.connectsWest);
      Assert.assertEquals("Two connections.", 2, room.numOfConnections);
      Assert.assertFalse("Not a terminal.", room.isTerminal());
      
      // connect west
      for(int y = 1; y < height - 1; y++)
      {
         z.setTile(0, y, MapCellFactory.getMapCell(MapCellBase.CLEAR));
      }
      room.setConnections(z);
      Assert.assertTrue("Northern connection.", room.connectsNorth);
      Assert.assertFalse("No eastern connection.", room.connectsEast);
      Assert.assertTrue("Southern connection.", room.connectsSouth);
      Assert.assertTrue("Western connection.", room.connectsWest);
      Assert.assertEquals("Three connections.", 3, room.numOfConnections);
      Assert.assertFalse("Not a terminal.", room.isTerminal());
      
      // connect east
      for(int y = 1; y < height - 1; y++)
      {
         z.setTile(width - 1, y, MapCellFactory.getMapCell(MapCellBase.CLEAR));
      }
      room.setConnections(z);
      Assert.assertTrue("Northern connection.", room.connectsNorth);
      Assert.assertTrue("Eastern connection.", room.connectsEast);
      Assert.assertTrue("Southern connection.", room.connectsSouth);
      Assert.assertTrue("Western connection.", room.connectsWest);
      Assert.assertEquals("Four connections.", 4, room.numOfConnections);
      Assert.assertFalse("Not a terminal.", room.isTerminal());
   }


   @Test public void testDoorConnections() 
   {
      int width = 4;
      int height = 6;
      ZoneMap z = new ZoneMap(width, height);
      for(int x = 1; x < width - 1; x++)
         for(int y = 1; y < height - 1; y++)
         {
            z.setTile(x, y, MapCellFactory.getMapCell(MapCellBase.CLEAR));
         }
      TIMRoom room = new TIMRoom();
      room.origin = new Coord(0, 0);
      room.size = new Coord(width, height);
      room.setConnections(z);
      Assert.assertFalse("No northern connection.", room.connectsNorth);
      Assert.assertFalse("No eastern connection.", room.connectsEast);
      Assert.assertFalse("No southern connection.", room.connectsSouth);
      Assert.assertFalse("No western connection.", room.connectsWest);
      Assert.assertEquals("Zero connections.", 0, room.numOfConnections);
      Assert.assertFalse("Not a terminal.", room.isTerminal());
      
      // connect north
      z.setTile(width / 2, 0, MapCellFactory.getDoor());
      room.setConnections(z);
      Assert.assertTrue("Northern connection.", room.connectsNorth);
      Assert.assertFalse("No eastern connection.", room.connectsEast);
      Assert.assertFalse("No southern connection.", room.connectsSouth);
      Assert.assertFalse("No western connection.", room.connectsWest);
      Assert.assertEquals("One connection.", 1, room.numOfConnections);
      Assert.assertTrue("Is a terminal.", room.isTerminal());
      
      // connect south
      z.setTile(width / 2, height - 1, MapCellFactory.getDoor());
      room.setConnections(z);
      Assert.assertTrue("Northern connection.", room.connectsNorth);
      Assert.assertFalse("No eastern connection.", room.connectsEast);
      Assert.assertTrue("Southern connection.", room.connectsSouth);
      Assert.assertFalse("No western connection.", room.connectsWest);
      Assert.assertEquals("Two connections.", 2, room.numOfConnections);
      Assert.assertFalse("Not a terminal.", room.isTerminal());
      
      // connect west
      z.setTile(0, height / 2, MapCellFactory.getDoor());
      room.setConnections(z);
      Assert.assertTrue("Northern connection.", room.connectsNorth);
      Assert.assertFalse("No eastern connection.", room.connectsEast);
      Assert.assertTrue("Southern connection.", room.connectsSouth);
      Assert.assertTrue("Western connection.", room.connectsWest);
      Assert.assertEquals("Three connections.", 3, room.numOfConnections);
      Assert.assertFalse("Not a terminal.", room.isTerminal());
      
      // connect east
      z.setTile(width - 1, height / 2, MapCellFactory.getDoor());
      room.setConnections(z);
      Assert.assertTrue("Northern connection.", room.connectsNorth);
      Assert.assertTrue("Eastern connection.", room.connectsEast);
      Assert.assertTrue("Southern connection.", room.connectsSouth);
      Assert.assertTrue("Western connection.", room.connectsWest);
      Assert.assertEquals("Four connections.", 4, room.numOfConnections);
      Assert.assertFalse("Not a terminal.", room.isTerminal());
   }


   @Test public void testOverlapping() 
   {
      TIMRoom room1 = new TIMRoom();
      room1.origin = new Coord(0, 0);
      room1.size = new Coord(5, 5);
      TIMRoom room2 = new TIMRoom();
      room2.origin = new Coord(10, 10);
      room2.size = new Coord(5, 5);
      
      // no x or y overlap
      Assert.assertFalse("Check non-overlapping a to b.", room1.overlaps(room2));
      Assert.assertFalse("Check non-overlapping b to a.", room2.overlaps(room1));
      
      // x overlap, not touching
      room2.origin.x = 4;
      Assert.assertFalse("Check non-overlapping a to b.", room1.overlaps(room2));
      Assert.assertFalse("Check non-overlapping b to a.", room2.overlaps(room1));
      
      // xy overlap, touching
      room2.origin.y = 4;
      Assert.assertTrue("Check overlapping a to b.", room1.overlaps(room2));
      Assert.assertTrue("Check overlapping b to a.", room2.overlaps(room1));
      
      // x overlap, not touching
      room2.origin.x = 10;
      Assert.assertFalse("Check non-overlapping a to b.", room1.overlaps(room2));
      Assert.assertFalse("Check non-overlapping b to a.", room2.overlaps(room1));
   }

}