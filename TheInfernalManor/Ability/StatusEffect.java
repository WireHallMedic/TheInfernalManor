package TheInfernalManor.Ability;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import java.util.*;

public class StatusEffect extends StatItem implements GUIConstants, AbilityConstants
{
	private int startingDuration;
	private int remainingDuration;
	private Vector<OngoingEffect> effectList;


	public int getStartingDuration(){return startingDuration;}
	public int getRemainingDuration(){return remainingDuration;}
   public Vector<OngoingEffect> getEffectList(){return effectList;}


	public void setStartingDuration(int s){startingDuration = s; remainingDuration = s;}
	public void setRemainingDuration(int r){remainingDuration = r;}
	public void setEffectList(Vector<OngoingEffect> el){effectList = el;}


   public StatusEffect(String name, int icon, int fgColor)
   {
      super(name, icon, fgColor);
      setStartingDuration(MEDIUM_DURATION);
      effectList = new Vector<OngoingEffect>();
   }
   
   public StatusEffect(String name, OngoingEffect oe)
   {
      this(name, oe.iconImage, oe.color);
      effectList.add(oe);
   }
   
   public StatusEffect(StatusEffect that)
   {
      this(that.getName(), that.getIconIndex(), that.getColor());
      this.startingDuration = that.startingDuration;
      this.remainingDuration = that.remainingDuration;
      this.effectList = new Vector<OngoingEffect>();
      for(OngoingEffect oe: that.effectList)
         this.effectList.add(oe);
   }
   
   public boolean hasEffect(OngoingEffect e)
   {
      return effectList.contains(e);
   }
   
   public void addEffect(OngoingEffect e)
   {
      effectList.add(e);
   }
   
   public void increment()
   {
      remainingDuration--;
   }
   
   public boolean isExpired()
   {
      return remainingDuration <= 0;
   }
   
   public boolean ongoingEffectsEqual(StatusEffect that)
   {
      for(OngoingEffect oe : effectList)
         if(!that.hasEffect(oe))
            return false;
      for(OngoingEffect oe : that.getEffectList())
         if(!this.hasEffect(oe))
            return false;
      return true;
   }
   
   public boolean equals(StatusEffect that)
   {
      return super.equals(that) && ongoingEffectsEqual(that);
   }
   
   public void combine(StatusEffect that)
   {
      this.remainingDuration += that.getRemainingDuration();
   }
   
   public boolean isHarmful()
   {
      for(OngoingEffect oe : effectList)
         if(oe.isHarmful)
            return true;
      return super.isHarmful();
   }

}