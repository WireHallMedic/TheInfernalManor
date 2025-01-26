package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class Relic extends Item implements GUIConstants
{
   public enum Restriction
   {
      HEAD  ("Head"),
      HANDS ("Hands"),
      FEET  ("Feet"),
      ARMS  ("Arms");
      
      public String string;
      
      private Restriction(String s)
      {
         string = s;
      }
   }
   
   public Relic(String n)
   {
      super(n, RELIC_ICON, WHITE);
   }
}