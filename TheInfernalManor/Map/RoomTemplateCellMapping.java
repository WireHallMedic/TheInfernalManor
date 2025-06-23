// maps chars to map cell templates for RoomTemplate

package TheInfernalManor.Map;

public enum RoomTemplateCellMapping
{
   CLEAR             ('.', "Clear", MapCellBase.CLEAR),
   ROUGH             (',', "Rough", MapCellBase.ROUGH),
   WALL              ('#', "Wall", MapCellBase.WALL),
   LOW_WALL          ('=', "Low Wall", MapCellBase.LOW_WALL),
   BARS              (':', "Bars", MapCellBase.BARS),
   SHALLOW_LIQUID    ('-', "Shallow Liquid", MapCellBase.SHALLOW_LIQUID),
   DEEP_LIQUID       ('~', "Deep Liquid", MapCellBase.DEEP_LIQUID),
   CONTAINER         ('0', "Container", MapCellBase.LOW_WALL),
   CHEST             ('c', "Chest", MapCellBase.CHEST_CLOSED),
   METAL_CHEST       ('C', "Metal Chest", MapCellBase.CHEST_CLOSED),
   DOOR              ('|', "Door", MapCellBase.DOOR_CLOSED),
   METAL_DOOR        ('+', "Metal Door", MapCellBase.DOOR_CLOSED),
   TOGGLE            ('!', "Switch", MapCellBase.TOGGLE_UNFLIPPED),
   FEATURE           ('f', "Feature", MapCellBase.CLEAR),
   SPAWN             ('s', "Spawn", MapCellBase.CLEAR),
   CONNECTION        ('X', "Connection", MapCellBase.CLEAR);
   
   public char character;
   public MapCellBase mapCellBase;
   public String name;
   public static final char INDEPENDENTLY_RANDOM = 'i';
   public static final char DEPENDENTLY_RANDOM = 'd';
   
   private RoomTemplateCellMapping(char c, String n, MapCellBase mcb)
   {
      character = c;
      name = n;
      mapCellBase = mcb;
   }
   
   public static MapCellBase getMapCellBaseFromMapping(char c)
   {
      RoomTemplateCellMapping mapping = deserialize(c);
      if(mapping == null)
         return null;
      return mapping.mapCellBase;
   }
   
   public static RoomTemplateCellMapping deserialize(char c)
   {
      for(int i = 0; i < RoomTemplateCellMapping.values().length; i++)
         if(RoomTemplateCellMapping.values()[i].character == c)
            return RoomTemplateCellMapping.values()[i];
      return null;
   }
}