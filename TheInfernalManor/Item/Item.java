package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;
import java.util.*;

public class Item extends ForegroundObject implements GUIConstants
{
	private String description;


	public String getDescription(){return description;}


	public void setDescription(String d){description = d;}

   public Item(String name, int icon, int color)
   {
      super(name, icon, color);
      description = "No Description.";
   }
   
   public Item(Item that)
   {
      super(that);
      this.description = that.description;
   }
   
   public int numOfSerializedComponents()
   {
      return super.numOfSerializedComponents() + 1;
   }
   
   public String serialize()
   {
      String str = super.serialize();
      str += getSerializationString(description);
      return str;
   }
}