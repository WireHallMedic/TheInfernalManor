package TheInfernalManor.AI;

import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import java.util.*;
import WidlerSuite.*;

public class ActorMemory implements ActorConstants
{
	private Vector<MemoryObject> memoryList;
	private Actor self;
	private int duration;


	public Vector<MemoryObject> getMemoryList(){return memoryList;}
	public Actor getSelf(){return self;}
	public int getDuration(){return duration;}


	public void setMemoryList(Vector<MemoryObject> m){memoryList = m;}
	public void setSelf(Actor s){self = s;}
	public void setDuration(int d){duration = d;}
   
   
   public ActorMemory(Actor a)
   {
      self = a;
      memoryList = new Vector<MemoryObject>();
      duration = DEFAULT_MEMORY_DURATION;
   }
   
   public int getIndex(Actor a)
   {
      for(int i = 0; i < memoryList.size(); i++)
      {
         if(memoryList.elementAt(i).getActor() == a)
            return i;
      }
      return -1;
   }
   
   // call at beginning of turn, after updating enemyFoV
   public void update()
   {
      Vector<Actor> actorList = GameState.getActorList();
      for(int i = 0; i < actorList.size(); i++)
         if(self.canSee(actorList.elementAt(i)) && actorList.elementAt(i) != self)
            noteActor(actorList.elementAt(i));
   }
   
   // call at end of turn
   public void increment()
   {
      for(int i = 0; i < memoryList.size(); i++)
      {
         memoryList.elementAt(i).increment();
         if(memoryList.elementAt(i).getTurnsSinceSeen() >= duration)
         {
            memoryList.removeElementAt(i);
            i--;
         }
      }
   }
   
   
   public void noteActor(Actor a, boolean tellFriends)
   {
      for(int i = 0; i < memoryList.size(); i++)
      {
         if(memoryList.elementAt(i).getActor() == a)
         {
            memoryList.elementAt(i).refresh();
            if(tellFriends && self.isEnemy(a))
               alertFriends(a);
            return;
         }
      }
      memoryList.add(new MemoryObject(a));
   }
   public void noteActor(Actor a){noteActor(a, true);}
   
   
   public void alertFriends(Actor target)
   {
      for(int i = 0; i < memoryList.size(); i++)
      {
         Actor a = memoryList.elementAt(i).getActor();
         if(self.isFriend(a) && self.canSee(a))
         {
            a.getAI().getMemory().noteActor(target, false);
         }
      }
   }
   
   
   public void clear()
   {
      memoryList.clear();
   }
}