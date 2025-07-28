
package TheInfernalManor.Map;

public interface MapConstants
{
   public static final int QUEST_EXIT = -1;
   public static final int QUEST_ENTRANCE = -2;
   public static final int UNDEFINED_EXIT = -3;
   
   public enum MapSize{SMALL, MEDIUM, LARGE};
   
   public enum ConnectionStatus {MUST, MUST_NOT, UNDEFINED};
   public enum ConnectionType 
   {
      ISOLATED ("Isolated"), 
      TERMINAL ("Terminal"), 
      STRAIGHT ("Straight"), 
      ELBOW    ("Elbow"), 
      TEE      ("Tee"), 
      CROSS    ("Cross");
      
      public String name;
      
      private ConnectionType(String n)
      {
         name = n;
      }
   };
   public enum MapType
   {
      ROAD     ("Road"),      // gridArea, maximize connections
      FIELD    ("Fields"),    // gridArea, maximize connections
      FOREST   ("Forest"),    // gridArea
      CAVERN   ("Cavern"),    // gridArea
      SWAMP    ("Swamp"),     // gridOfGrids
      CATACOMB ("Catacomb"),  // gridOfGrids
      MOUNTAIN ("Mountain"),  // gridOfGrids, maximize lower connections
      BUILDING ("Building"),  // BSP
      VILLAGE  ("Village"),   // BSP, rooms less than full size
      DUNGEON  ("Dungeon");   // BSP, rooms less than full size
      
      public String name;
      
      private MapType(String n)
      {
         name = n;
      }
      
      @Override
      public String toString()
      {
         return name;
      }
   };
}