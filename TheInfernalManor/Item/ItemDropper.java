package TheInfernalManor.Item;

import java.util.*;

public interface ItemDropper
{
   public Vector<Item> getItems();
   public Vector<Item> takeItems();
   public void setItems(Vector<Item> list);
   public void addItem(Item i);
}