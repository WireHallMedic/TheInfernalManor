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
   
	private Restriction restriction;


	public Restriction getRestriction(){return restriction;}


	public void setRestriction(Restriction r){restriction = r;}

   
   public Relic(String n)
   {
      super(n, RELIC_ICON, WHITE);
      restriction = null;
   }
   
   public boolean conflictsWith(Relic that)
   {
      return this.restriction != null && this.restriction == that.restriction;
   }
}