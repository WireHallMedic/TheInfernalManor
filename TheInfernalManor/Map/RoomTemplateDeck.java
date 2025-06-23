package TheInfernalManor.Map;

import java.util.*;
import TheInfernalManor.Engine.*;

public class RoomTemplateDeck implements MapConstants
{
   private RTCollection[] typeList;
   private boolean uniformSquareSize;
   private boolean updateF;
   
   public RoomTemplateDeck()
   {
      typeList = new RTCollection[ConnectionType.values().length];
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         typeList[i] = new RTCollection(ConnectionType.values()[i]);
      }
      uniformSquareSize = false;
      updateF = true;
   }
   
   public boolean isUniformSquareSize()
   {
      if(updateF)
      {
         updateF = false;
         uniformSquareSize = true;
         RoomTemplate rt = getFirstRoom();
         int w = rt.getWidth();
         int h = rt.getHeight();
         if(w != h)
            uniformSquareSize = false;
         else
         {
            for(ConnectionType ct : ConnectionType.values())
            {
               for(int i = 0; i < typeList[ct.ordinal()].list.size() && uniformSquareSize; i++)
               {
                  rt = get(ct, i);
                  if(rt.getWidth() != w ||
                     rt.getHeight() != h)
                  {
                     uniformSquareSize = false;
                  }
               }
            }
         }
      }
      return uniformSquareSize;
   }
   
   public RoomTemplateDeck(Vector<String> strList)
   {
      this();
      deserialize(strList);
   }
   
   // returns true if there is at least one template of each type
   public boolean isComplete()
   {
      for(int i = 0; i < length(); i++)
         if(size(i) == 0)
            return false;
      return true;
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
   
   public int getCount()
   {
      int count = 0;
      for(int i = 0; i < typeList.length; i++)
         count += typeList[i].list.size();
      return count;
   }
   
   public void add(RoomTemplate rt)
   {
      typeList[rt.getConnectionType().ordinal()].list.add(rt);
   }
   
   public void remove(RoomTemplate rt)
   {
      typeList[rt.getConnectionType().ordinal()].list.remove(rt);
   }
   
   public RoomTemplate getRandom(ConnectionType ct)
   {
      return getRandom(ct.ordinal());
   }
   
   public RoomTemplate getRandom(int ct)
   {
      int roll = RNG.nextInt(size(ct));
      RoomTemplate rt = new RoomTemplate(get(ct, roll));
      if(RNG.nextBoolean())
         rt.mirrorX();
      if(RNG.nextBoolean())
         rt.mirrorY();
      return rt;
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
   
   public RoomTemplate getFirstRoom()
   {
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         for(int j = 0; j < size(ConnectionType.values()[i]); j++)
         {
            return get(ConnectionType.values()[i], j);
         }
      }
      return null;
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
      outList.add("@HEADER");
      outList.add("@BODY");
      
      for(int i = 0; i < ConnectionType.values().length; i++)
      {
         for(int j = 0; j < size(ConnectionType.values()[i]); j++)
         {
            Vector<String> rtStrList = get(i, j).serialize();
            for(String str : rtStrList)
               outList.add(str);
            outList.add("");
         }
      }
      outList.add("@END");
      return outList;
   }
   
   /* reads a list of strings.
      Anything before @HEADER is a comment
      Anything between @HEADER and @BODY is part of the header
      Anything after @BODY is either part of a roomTemplate, or a space between them
      Anything after @END is ignored
   */
   public void deserialize(Vector<String> strList)
   {
      Vector<String> rowList = new Vector<String>();
      boolean inHeader = false;
      boolean inBody = false;
      boolean inRoom = false;
      for(String curStr : strList)
      {
         // pre-header
         if(!inHeader && !inBody)
         {
            if(curStr.equals("@HEADER"))
               inHeader = true;
         }
         
         // header
         if(inHeader)
         {
            if(curStr.equals("@BODY"))
            {
               inHeader = false;
               inBody = true;
            }
         }
         
         // body
         if(inBody)
         {
            if(isRoomLine(curStr))
            {
               // new room
               if(!inRoom)
               {
                  inRoom = true;
               }
               rowList.add(curStr);
            }
            else
            {
               // end of room
               if(inRoom)
               {
                  inRoom = false;
                  add(new RoomTemplate(rowList));
                  rowList = new Vector<String>();
               }
            }
            if(curStr.equals("@END"))
            {
               return;
            }
         }
      }
   }
   
   private boolean isRoomLine(String str)
   {
      return str.length() > 0 && str.charAt(0) != '@';
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