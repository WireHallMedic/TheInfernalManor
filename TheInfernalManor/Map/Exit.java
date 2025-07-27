package TheInfernalManor.Map;

import StrictCurses.*;

public class Exit extends MapCell
{
   private ZoneMap targetZone;


	public ZoneMap getTargetZone(){return targetZone;}


	public void setTargetZone(ZoneMap t){targetZone = t;}


   public Exit(MapCellBase base)
   {
      super(MapCellBase.EXIT);
      targetZone = null;
   }
   
   public Exit()
   {
      this(MapCellBase.EXIT);
   }
   
   public static Exit getEntrance()
   {
      return new Exit(MapCellBase.ENTRANCE);
   }
   
   public static Exit getExit()
   {
      return new Exit(MapCellBase.EXIT);
   }
}