package TheInfernalManor.Map;

import java.util.*;
import StrictCurses.*;
import TheInfernalManor.Item.*;

// class for things like barrels and crates, partial parent class of chests
public class Chest extends ToggleTile implements ItemDropper
{
   private Vector<Item> itemList;
   
   public Chest()
   {
      super(new MapCell(MapCellBase.CHEST_CLOSED), new MapCell(MapCellBase.CHEST_OPEN));
      setOneUseOnly(true);
      setBrokenForm(new MapCell(MapCellBase.ROUGH));
      itemList = new Vector<Item>();
   }
   
   public Vector<Item> getItems(){return itemList;}
   public void setItems(Vector<Item> list){itemList = list;}
   public void addItem(Item i){itemList.add(i);}
}