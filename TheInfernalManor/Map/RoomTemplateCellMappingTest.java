package TheInfernalManor.Map;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class RoomTemplateCellMappingTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }

   @Test public void noConflictingCharactersTest() 
   {
      boolean problem = false;
      int len = RoomTemplateCellMapping.values().length;
      for(int i = 0; i < len; i++)
      for(int j = i + 1; j < len; j++)
         if(RoomTemplateCellMapping.values()[i].character == RoomTemplateCellMapping.values()[j].character)
            problem = true;
      
      Assert.assertFalse("No mappings conflict with each other", problem);
   }

   @Test public void flagCollisionTestIR()
   {
      boolean problem = false;
      int len = RoomTemplateCellMapping.values().length;
      for(int i = 0; i < len; i++)
         if(RoomTemplateCellMapping.values()[i].character == RoomTemplateCellMapping.INDEPENDENTLY_RANDOM)
            problem = true;
      Assert.assertFalse("No mappings conflict with IR flag", problem);
   }

   @Test public void flagCollisionTestDR()
   {
      boolean problem = false;
      int len = RoomTemplateCellMapping.values().length;
      for(int i = 0; i < len; i++)
         if(RoomTemplateCellMapping.values()[i].character == RoomTemplateCellMapping.DEPENDENTLY_RANDOM)
            problem = true;
      Assert.assertFalse("No mappings conflict with DR flag", problem);
   }
}
