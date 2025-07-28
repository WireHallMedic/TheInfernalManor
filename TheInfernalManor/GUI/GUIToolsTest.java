package TheInfernalManor.GUI;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class GUIToolsTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp() {
   }


   @Test public void verifySerialization() {
      for(int i = 0; i < GUIConstants.COLOR_ARRAY.length; i++)
      {
         int a = GUIConstants.COLOR_ARRAY[i];
         int b = GUITools.deserializeColor(GUITools.serializeColor(a));
         Assert.assertEquals("Verify serialization and deserialization of COLOR_ARRAY[" + i + "]", a, b);
      }
   }

   @Test public void testNoDuplicatedColor() {
      for(int i = 0; i < GUIConstants.COLOR_ARRAY.length; i++)
      for(int j = i + 1; j < GUIConstants.COLOR_ARRAY.length; j++)
      {
         Assert.assertNotEquals("COLOR_ARRAY indexes " + i + " and " + j + " are different.",
            GUIConstants.COLOR_ARRAY[i], GUIConstants.COLOR_ARRAY[j]);
      }
   }
}
