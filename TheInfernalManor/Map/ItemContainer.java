package TheInfernalManor.Map;

import java.util.*;
import StrictCurses.*;
import TheInfernalManor.Item.*;

// class for things like barrels and crates
public class ItemContainer extends MapCell implements ItemDropper
{
   private Vector<Item> itemList;
   
   public ItemContainer(int index, boolean lowPass, boolean highPass, boolean trans)
   {
      super(index, lowPass, highPass, trans);
      setBreakable(true);
      itemList = new Vector<Item>();
   }
   
   public ItemContainer(MapCellBase base)
   {
      this(base.iconIndex, base.lowPassable, base.highPassable, base.transparent);
      setBase(base);
   }
   
   
   public Vector<Item> takeItems()
   {
      Vector<Item> temp = itemList;
      itemList = new Vector<Item>();
      return temp;
   }
   public Vector<Item> getItems(){return itemList;}
   public void setItems(Vector<Item> list){itemList = list;};
   public void addItem(Item i){itemList.add(i);}
}