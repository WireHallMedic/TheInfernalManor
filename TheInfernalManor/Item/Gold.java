package TheInfernalManor.Item;

import TheInfernalManor.GUI.*;

public class Gold extends ForegroundObject
{
	private int value;


	public int getValue(){return value;}


	public void setValue(int v){value = v;}

   public Gold(int v)
   {
      super("Gold", '$', BRIGHT_YELLOW);
      value = v;
   }
   
   public Gold()
   {
      this(0);
   }
   
   @Override
   public String getName()
   {
      return value + " Gold";
   }
   
   public void add(Gold that)
   {
      this.value += that.value;
   }
   
   public void add(int v)
   {
      value += v;
   }
   
   public void subtract(int v)
   {
      value -= v;
   }
}