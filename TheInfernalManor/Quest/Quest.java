package TheInfernalManor.Quest;

import java.util.*;
import TheInfernalManor.Map.*;
import TheInfernalManor.Actor.*;

public class Quest
{
	private Vector<ZoneMap> zoneList;
	private Vector<ActorList> actorList;
	private String name;
	private String description;


	public Vector<ZoneMap> getZoneList(){return zoneList;}
	public Vector<ActorList> getActorList(){return actorList;}
	public String getName(){return name;}
	public String getDescription(){return description;}


	public void setZoneList(Vector<ZoneMap> z){zoneList = z;}
	public void setActorList(Vector<ActorList> a){actorList = a;}
	public void setName(String n){name = n;}
	public void setDescription(String d){description = d;}

   public Quest()
   {
      zoneList = new Vector<ZoneMap>();
      actorList = new Vector<ActorList>();
      name = "Unnamed Quest";
      description = "No description.";
   }
   
   public void add(ZoneMap m, Vector<Actor> a)
   {
      zoneList.add(m);
      actorList.add(new ActorList(a));
   }
   
   public int size()
   {
      return zoneList.size();
   }
   
   public ZoneMap getZoneMap(int i)
   {
      return zoneList.elementAt(i);
   }
   
   public Vector<Actor> getActorList(int i)
   {
      return actorList.elementAt(i).list;
   }
   
   public void setActorList(int i, Vector<Actor> a)
   {
      actorList.elementAt(i).list = a;
   }

   
   // wrapper class because we can't do a vector of vectors
   private class ActorList
   {
      public Vector<Actor> list;
      
      private ActorList()
      {
         list = new Vector<Actor>();
      }
      
      private ActorList(Vector<Actor> a)
      {
         this();
         list = a;
      }
   }
}