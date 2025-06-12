package TheInfernalManor.Map;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class RoomTemplateDeckTest implements MapConstants {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void testDeckStartsEmpty() 
   {
      RoomTemplateDeck deck = new RoomTemplateDeck();
      
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         ConnectionType conType = ConnectionType.values()[i];
         assertEquals("Count of " + conType.name + " is zero", 0, deck.size(conType));
      }
      //RoomTemplate rt = new RoomTemplate(3, 3);
   }
}
