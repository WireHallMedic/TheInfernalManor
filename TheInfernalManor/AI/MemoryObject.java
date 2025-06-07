package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;
import WidlerSuite.*;

public class MemoryObject
{
	private Actor actor;
	private int turnsSinceSeen;


	public Actor getActor(){return actor;}
	public int getTurnsSinceSeen(){return turnsSinceSeen;}


	public void setActor(Actor a){actor = a;}
	public void setTurnsSinceSeen(int t){turnsSinceSeen = t;}

   
   public MemoryObject(Actor a)
   {
      actor = a;
      turnsSinceSeen = 0;
   }
   
   public void increment()
   {
      turnsSinceSeen++;
   }
   
   public void refresh()
   {
      turnsSinceSeen = 0;
   }
}