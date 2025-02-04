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
}