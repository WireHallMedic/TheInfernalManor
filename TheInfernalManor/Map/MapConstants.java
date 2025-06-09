
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
}