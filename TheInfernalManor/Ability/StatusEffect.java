package TheInfernalManor.Ability;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Item.*;
import java.util.*;

public class StatusEffect extends EquippableItem implements GUIConstants, AbilityConstants
{
   public enum OngoingEffect
   {
      HEALING           ("Healing", false),
      GREATER_HEALING   ("Greater Healing", false),
      FLEET             ("Fleet", false),
      HASTE             ("Hasted", false),
      RECHARGING        ("Recharging", false),
      SLUGGISH          ("Sluggish", true),
      SLOW              ("Slowed", true),
      BURNING           ("Burning", true),
      POISONED          ("Poisoned", true);
      
      public String name;
      public boolean isHarmful;
      
      private OngoingEffect(String n, boolean h)
      {
         name = n;
         isHarmful = h;
      }
   }
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

}