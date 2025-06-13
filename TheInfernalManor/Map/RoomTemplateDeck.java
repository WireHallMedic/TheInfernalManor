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
   
   public int length()
   {
      return ConnectionType.values().length;
   }
   
   public int size(ConnectionType ct)
   {
      return typeList[ct.ordinal()].list.size();
   }
   
   public void add(RoomTemplate rt)
   {
      typeList[rt.getConnectionType().ordinal()].list.add(rt);
   }
   
   public RoomTemplate get(ConnectionType ct, int i)
   {
      if(size(ct) > i)
         return typeList[ct.ordinal()].list.elementAt(i);
      return null;
   }
   
   public int[] getIndex(RoomTemplate rt)
   {
      int[] returnVal = {-1, -1};
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         for(int j = 0; j < size(ConnectionType.values()[i]); j++)
         {
            if(rt.getConnectionType() != ConnectionType.values()[i])
            {
               if(get(ConnectionType.values()[i], j) == rt)
               returnVal[0] = i;
               returnVal[1] = j;
               return returnVal;
            }
         }
      }
      return returnVal;
   }
   
   public void sort()
   {
      for(int reps = 0; reps < 2; reps++)
      {
         for(int i = 0; i < ConnectionType.values().length; i++)
         {
            for(int j = 0; j < size(ConnectionType.values()[i]); j++)
            {
               RoomTemplate rt = get(ConnectionType.values()[i], j);
               if(rt.getConnectionType() != ConnectionType.values()[i])
               {
                  typeList[ConnectionType.values()[i].ordinal()].list.removeElement(rt);
                  add(rt);
               }
            }
         }
      }
   }
   
   // targeted sort for efficency
   public void sort(RoomTemplate target)
   {
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         for(int j = 0; j < size(ConnectionType.values()[i]); j++)
         {
            RoomTemplate rt = get(ConnectionType.values()[i], j);
            if(target == rt &&
               rt.getConnectionType() != ConnectionType.values()[i])
            {
               typeList[ConnectionType.values()[i].ordinal()].list.removeElement(rt);
               add(rt);
               return;
            }
         }
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