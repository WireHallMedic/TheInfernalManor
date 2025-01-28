package TheInfernalManor.GUI;

import TheInfernalManor.Actor.*;


public class ActorVisualEffect extends VisualEffect
{
	private Actor actor;


	public Actor getActor(){return actor;}


	public void setActor(Actor a){actor = a;}


   public ActorVisualEffect(Actor a, int[] iconArr, int[] fgArr)
   {
      super(iconArr, fgArr, null);
      actor = a;
      actor.setVisualEffect(this);
   }
   
   @Override
   public void increment()
   {
      super.increment();
      if(isExpired())
         actor.setVisualEffect(null);
   }
}
   