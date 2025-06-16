
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
      ROAD     ("Road"), 
      FOREST   ("Forest"),
      FIELD    ("Field"), 
      MOUNTAIN ("Mountain"),
      DUNGEON  ("Dungeon"),
      CAVERN   ("Cavern"),
      CATACOMB ("Catacomb");
      
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