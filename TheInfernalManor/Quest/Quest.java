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
   private int level;


	public Vector<ZoneMap> getZoneList(){return zoneList;}
	public Vector<ActorList> getActorList(){return actorList;}
	public String getName(){return name;}
	public String getDescription(){return description;}
   public int getLevel(){return level;}


	public void setZoneList(Vector<ZoneMap> z){zoneList = z;}
	public void setActorList(Vector<ActorList> a){actorList = a;}
	public void setName(String n){name = n;}
	public void setDescription(String d){description = d;}
   public void setLevel(int l){level = l;}

   public Quest()
   {
      this(1);
   }
   
   public Quest(int l)
   {
      zoneList = new Vector<ZoneMap>();
      actorList = new Vector<ActorList>();
      name = "Unnamed Quest";
      description = "No description.";
      level = l;
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
   
   public void connectExits()
   {
      for(int i = 0; i < size(); i++)
      {
         getZoneMap(i).getEntrance().setTargetZone(i - 1);
         getZoneMap(i).getExit().setTargetZone(i + 1);
      }
      getZoneMap(0).getEntrance().setTargetZone(MapConstants.QUEST_ENTRANCE);
      getZoneMap(size() - 1).getExit().setTargetZone(MapConstants.QUEST_EXIT);
   }
   
   public void clean()
   {
      for(ActorList curList : actorList)
         curList.clean();
   }
   
   public static Quest mock()
   {
      Quest q = new Quest(1);
      q.setName("Test Quest");
      ZoneMap map = ZoneMapFactory.generateZoneMap(MapConstants.MapType.FOREST, MapConstants.MapSize.SMALL);
      Vector<Actor> actors = ActorFactory.getPopulation(map, 1);
      q.add(map, actors);
      map = ZoneMapFactory.generateZoneMap(MapConstants.MapType.FOREST, MapConstants.MapSize.SMALL);
      actors = ActorFactory.getPopulation(map, 1);
      q.add(map, actors);
      map = ZoneMapFactory.generateZoneMap(MapConstants.MapType.CAVERN, MapConstants.MapSize.SMALL);
      actors = ActorFactory.getPopulation(map, 1);
      q.add(map, actors);
      q.connectExits();
      return q;
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
      
      public void clean()
      {
         for(int i = 0; i < list.size(); i++)
         {
            if(list.elementAt(i).isDead())
            {
               list.removeElementAt(i);
               i--;
            }
         }
      }
   }
}