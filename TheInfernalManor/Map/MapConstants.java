
package TheInfernalManor.Map;

public interface MapConstants
{
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
   public enum MapTypes
   {
      ROAD     ("Road"),      // gridArea, maximize connections
      FIELD    ("Fields"),     // gridArea, maximize connections
      FOREST   ("Forest"),    // gridArea
      CAVERN   ("Cavern"),    // gridArea
      CATACOMB ("Catacomb"),  // gridOfGrids
      MOUNTAIN ("Mountain"),  // gridOfGrids, maximize lower connections
      BUILDING ("Building"),  // BSP
      DUNGEON  ("Dungeon");   // BSP, rooms less than full size
      
      public String name;
      
      private MapTypes(String n)
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