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
      return size(ct.ordinal());
   }
   
   public int size(int ct)
   {
      return typeList[ct].list.size();
   }
   
   public void add(RoomTemplate rt)
   {
      typeList[rt.getConnectionType().ordinal()].list.add(rt);
   }
   
   public RoomTemplate get(ConnectionType ct, int i)
   {
      if(i < size(ct))
         return typeList[ct.ordinal()].list.elementAt(i);
      return null;
   }
   
   public RoomTemplate get(int ct, int i)
   {
      return get(ConnectionType.values()[ct], i);
   }
   
   public int[] getIndex(RoomTemplate rt)
   {
      int[] returnVal = {-1, -1};
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         for(int j = 0; j < size(ConnectionType.values()[i]); j++)
         {
            if(get(ConnectionType.values()[i], j) == rt)
            {
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
                  j--;
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
   
   public Vector<String> serialize()
   {
      Vector<String> outList = new Vector<String>();
      outList.add("@HEADER\n");
      outList.add("@BODY\n");
      
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         for(int j = 0; j < size(ConnectionType.values()[i]); j++)
         {
            Vector<String> rtStrList = get(i, j).serialize();
            for(String str : rtStrList)
               outList.add(str + "\n");
         }
         outList.add("\n");
      }
      return outList;
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