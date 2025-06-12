package TheInfernalManor.Map;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class RoomTemplateDeckTest implements MapConstants {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }


   @Test public void testDeckStartsEmpty() 
   {
      RoomTemplateDeck deck = new RoomTemplateDeck();
      
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         ConnectionType conType = ConnectionType.values()[i];
         assertEquals("Count of " + conType.name + " is zero", 0, deck.size(conType));
      }
   }


   @Test public void testAddingRTsIncreasesCount() 
   {
      RoomTemplateDeck deck = new RoomTemplateDeck();
      RoomTemplate rt = new RoomTemplate(3, 3);
      rt.setConnectionType();
      assertEquals("Count of CROSS is zero", 0, deck.size(ConnectionType.CROSS));
      deck.add(rt);
      assertEquals("Count of CROSS is 1", 1, deck.size(ConnectionType.CROSS));
      rt = new RoomTemplate(3, 3);
      assertEquals("Count of TEE is zero", 0, deck.size(ConnectionType.TEE));
      for(int i = 0; i < 3; i++)
         rt.set(0, i, '#');
      rt.setConnectionType();
      deck.add(rt);
      assertEquals("Count of TEE is 1", 1, deck.size(ConnectionType.TEE));
   }


   @Test public void testChangingTypeUpdates() 
   {
      RoomTemplateDeck deck = new RoomTemplateDeck();
      RoomTemplate rt = new RoomTemplate(3, 3);
      rt.setConnectionType();
      assertEquals("Count of CROSS is zero", 0, deck.size(ConnectionType.CROSS));
      assertEquals("Count of TEE is zero", 0, deck.size(ConnectionType.TEE));
      deck.add(rt);
      assertEquals("Count of CROSS is 1", 1, deck.size(ConnectionType.CROSS));
      assertEquals("Count of TEE is zero", 0, deck.size(ConnectionType.TEE));
      for(int i = 0; i < 3; i++)
         rt.set(0, i, '#');
      rt.setConnectionType();
      deck.sort();
      assertEquals("Count of CROSS is zero", 0, deck.size(ConnectionType.CROSS));
      assertEquals("Count of TEE is 1", 1, deck.size(ConnectionType.TEE));
   }
}
