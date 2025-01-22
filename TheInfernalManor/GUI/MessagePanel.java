package TheInfernalManor.GUI;

import java.util.*;

public class MessagePanel
{
   private static Vector<String> messageList = new Vector<String>();
   private static Vector<Integer> colorList = new Vector<Integer>();
   private static final int MAX_MESSAGES = 60;
   
   
   public static void addMessage(String str, int color)
   {
      messageList.insertElementAt(str, 0);
      colorList.insertElementAt(color, 0);
      while(messageList.size() > MAX_MESSAGES)
         messageList.removeElementAt(MAX_MESSAGES - 1);
      while(colorList.size() > MAX_MESSAGES)
         colorList.removeElementAt(MAX_MESSAGES - 1);
   }
   
   public static String getString(int index)
   {
      if(messageList.size() < index)
         return messageList.elementAt(index);
      return "";
   }
   
   public static int getColor(int index)
   {
      if(colorList.size() < index)
         return colorList.elementAt(index);
      return GUIConstants.WHITE;
   }
   
   public static void clear()
   {
      messageList.clear();
      colorList.clear();
   }
}