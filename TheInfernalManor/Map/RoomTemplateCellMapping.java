// maps chars to map cell templates for RoomTemplate

package TheInfernalManor.Map;

public enum RoomTemplateCellMapping
{
   CLEAR             ('.', MapCellBase.CLEAR),
   ROUGH             (',', MapCellBase.ROUGH),
   WALL              ('#', MapCellBase.WALL),
   LOW_WALL          ('=', MapCellBase.LOW_WALL),
   BARS              (':', MapCellBase.BARS),
   SHALLOW_LIQUID    ('-', MapCellBase.SHALLOW_LIQUID),
   DEEP_LIQUID       ('~', MapCellBase.DEEP_LIQUID),
   CHEST             ('c', MapCellBase.CHEST_CLOSED),
   METAL_CHEST       ('C', MapCellBase.CHEST_CLOSED),
   DOOR              ('|', MapCellBase.DOOR_CLOSED),
   METAL_DOOR        ('+', MapCellBase.DOOR_CLOSED),
   TOGGLE            ('!', MapCellBase.TOGGLE_UNFLIPPED),
   EXIT              ('>', MapCellBase.EXIT);
   
   public char character;
   public MapCellBase mapCellBase;
   public static final char INDEPENDENTLY_RANDOM = 'i';
   public static final char DEPENDENTLY_RANDOM = 'd';
   
   private RoomTemplateCellMapping(char c, MapCellBase mcb)
   {
      character = c;
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