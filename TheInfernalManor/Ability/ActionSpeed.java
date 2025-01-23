package TheInfernalManor.Ability;

public enum ActionSpeed
{
   INSTANT  ("Instant", 0),
   FAST     ("Fast", 1),
   NORMAL   ("Normal", 2),
   SLOW     ("Slow", 4);
   
   public String name;
   public int ticks;
   
   private ActionSpeed(String n, int t)
   {
      name = n;
      ticks = t;
   }
   
   public ActionSpeed faster()
   {
      switch(this)
      {
         case SLOW :    return NORMAL;
         case NORMAL :  return FAST;
         default :      return this;
      }
   }
   
   public ActionSpeed slower()
   {
      switch(this)
      {
         case FAST :    return NORMAL;
         case NORMAL :  return SLOW;
         default :      return this;
      }
   }
   
   public static ActionSpeed fastest(ActionSpeed... asList)
   {
      ActionSpeed fastest = null;
      for(ActionSpeed curAS : asList)
      {
         if(fastest == null || curAS.ticks < fastest.ticks)
            fastest = curAS;
      }
      return fastest;
   }
   
   public static ActionSpeed slowest(ActionSpeed... asList)
   {
      ActionSpeed slowest = null;
      for(ActionSpeed curAS : asList)
      {
         if(slowest == null || curAS.ticks > slowest.ticks)
            slowest = curAS;
      }
      return slowest;
   }
}