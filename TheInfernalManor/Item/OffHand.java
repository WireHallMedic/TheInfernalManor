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
   
   public int numOfSerializedComponents()
   {
      return super.numOfSerializedComponents() + 0;
   }
   
   public String serialize()
   {
      String str = super.serialize();
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