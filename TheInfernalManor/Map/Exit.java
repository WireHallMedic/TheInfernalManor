package TheInfernalManor.Map;

import StrictCurses.*;

public class Exit extends MapCell implements MapConstants
{
   private int targetZone;


	public int getTargetZone(){return targetZone;}


	public void setTargetZone(int t){targetZone = t;}


   public Exit(MapCellBase base)
   {
      super(base);
      if(base != MapCellBase.ENTRANCE &&
         base != MapCellBase.EXIT)
         throw new Error("Bad mapCellBase for exit constructor.");
      targetZone = UNDEFINED_EXIT;
   }
   
   public Exit()
   {
      this(MapCellBase.EXIT);
   }
}