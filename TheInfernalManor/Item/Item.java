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
   
   public boolean equals(Item that)
   {
      return super.equals(that) &&
         this.description.equals(that.description);
   }
   
   public int numOfSerializedComponents()
   {
      return super.numOfSerializedComponents() + 1;
   }
   
   public String serialize()
   {
      String str = super.serialize();
      str = str.replace("FOREGROUND_OBJECT[", "");
      str = str.replace("]", "");
      str += getSerializationString(description);
      str = "ITEM[" + str + "]";
      return str;
   }
   
   public void deserialize(String str)
   {
      super.deserialize(str);
      String[] strList = getDeserializationArray(str);
      int startingIndex = super.numOfSerializedComponents();
      description = strList[startingIndex];
   }
   
   public void setTestingValues()
   {
      super.setTestingValues();
   }
}