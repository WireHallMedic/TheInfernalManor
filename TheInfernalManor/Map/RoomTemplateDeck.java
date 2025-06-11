package TheInfernalManor.Map;

import java.util.*;

public class RoomTemplateDeck implements MapConstants
{
   private RTCollection[] typeList;
   
   public RoomTemplateDeck()
   {
      typeList = new RTCollection[ConnectionType.values().length];
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         typeList[i] = new RTCollection(ConnectionType.values()[i]);
      }
   }
   
   
   private class RTCollection
   {
      public ConnectionType type;
      public Vector<RoomTemplate> list;
      
      public RTCollection(ConnectionType t)
      {
         type = t;
         list = new Vector<RoomTemplate>();
      }
   }
}