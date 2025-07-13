package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class Relic extends EquippableItem implements GUIConstants
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
   
   public Relic(Relic that)
   {
      super(that);
      this.restriction = that.restriction;
   }
   
   public boolean conflictsWith(Relic that)
   {
      if(that == null)
         return false;
      return this.restriction != null && this.restriction == that.restriction;
   }
   
   @Override
   public Vector<String> getSummary()
   {
      Vector<String> strList = super.getSummary();
      if(restriction != null)
      {
         String slotStr = String.format("%s Slot ", restriction.string);
         strList.insertElementAt(slotStr, 0);
      }
      return strList;
   }
   
   public Vector<String> getComparisonSummary(Relic that)
   {
      Vector<String> strList = super.getComparisonSummary(that);
      if(this.restriction != null || that.restriction != null)
      {
         String sizeStr = String.format("%s Slot (%s Slot)", this.restriction.string, that.restriction.string);
         strList.insertElementAt(sizeStr, 0);
      }
      return strList;
   }
   
   public int numOfSerializedComponents()
   {
      return super.numOfSerializedComponents() + 1;
   }
   
   public String serialize()
   {
      String str = super.serialize();
      if(restriction == null)
         str += getSerializationString(-1);
      else
         str += getSerializationString(restriction.ordinal());
      return str;
   }
}