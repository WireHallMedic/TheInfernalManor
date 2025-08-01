package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class OffHand extends EquippableItem implements GUIConstants
{
   public OffHand(String n)
   {
      super(n, OFFHAND_ICON, WHITE);
   }
   
   public OffHand(OffHand that)
   {
      super(that);
   }
   
   public boolean equals(OffHand that)
   {
      return super.equals(that);
   }
   
   public static int numOfSerializedComponents()
   {
      return EquippableItem.numOfSerializedComponents() + 0;
   }
   
   public String serialize()
   {
      String str = super.serialize();
      str = str.replace("EQUIPPABLE_ITEM@", "OFF_HAND@");
      return str;
   }
   
   public void deserialize(String str)
   {
      super.deserialize(str);
   }
   
   public void setTestingValues()
   {
      super.setTestingValues();
   }
}