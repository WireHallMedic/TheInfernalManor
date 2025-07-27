package TheInfernalManor.Map;

import StrictCurses.*;

public class Exit extends MapCell
{
   private ZoneMap targetZone;


	public ZoneMap getTargetZone(){return targetZone;}


	public void setTargetZone(ZoneMap t){targetZone = t;}


   public Exit(MapCellBase base)
   {
      super(base);
      if(base != MapCellBase.ENTRANCE &&
         base != MapCellBase.EXIT)
         throw new Error("Bad mapCellBase for exit constructor.");
      targetZone = null;
   }
   
   public Exit()
   {
      this(MapCellBase.EXIT);
   }
}