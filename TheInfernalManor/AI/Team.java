package TheInfernalManor.AI;

public enum Team
{
   HERO,
   NEUTRAL,
   ENEMY;
   
   public boolean isEnemy(Team that)
   {
      if(this == that)
         return false;
      if(this == NEUTRAL || that == NEUTRAL)
         return false;
      return true;
   }
   
   public boolean isFriend(Team that)
   {
      return this == that;
   }
}