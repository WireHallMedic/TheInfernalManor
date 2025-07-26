package TheInfernalManor.GUI;

import TheInfernalManor.Map.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;
import StrictCurses.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AnimationManager implements GUIConstants
{
   public static final int MAX_TICKS = 24;
   private static boolean slowBlink;
	private static boolean mediumBlink;
	private static boolean fastBlink;
   private static double pulseIndex;
   private static boolean pulseDir;
   private static Vector<VisualEffect> lockList = new Vector<VisualEffect>();
   private static Vector<VisualEffect> nonlockList = new Vector<VisualEffect>();
   private static Vector<VisualEffect> actorVEList = new Vector<VisualEffect>();
   private static Vector<VisualEffect> groundEffectList = new Vector<VisualEffect>();
   private int tickIndex;


	public static boolean getSlowBlink(){return slowBlink;}
	public static boolean getMediumBlink(){return mediumBlink;}
	public static boolean getFastBlink(){return fastBlink;}
   public static Vector<VisualEffect> getLockList(){return lockList;}
   public static Vector<VisualEffect> getNonlockList(){return nonlockList;}
   public static Vector<VisualEffect> getActorVEList(){return actorVEList;}
   public static Vector<VisualEffect> getGroundEffectList(){return groundEffectList;}
   
   
   public AnimationManager()
   {
      slowBlink = false;
      mediumBlink = false;
      fastBlink = false;
      pulseIndex = 0.0;
      pulseDir = true;
      tickIndex = 0;
   }
   
   public void kick()
   {
      tickIndex++;
      if(tickIndex == MAX_TICKS)
      {
         tickIndex = 0;
         pulseDir = !pulseDir;
      }
      
      if(tickIndex % MAX_TICKS == 0)
         slowBlink = !slowBlink;
      
      if(tickIndex % (MAX_TICKS / 2) == 0)
         mediumBlink = !mediumBlink;
      
      if(tickIndex % (MAX_TICKS / 4) == 0)
         fastBlink = !fastBlink;
      
      if(pulseDir)
         pulseIndex = (1.0 / MAX_TICKS) * tickIndex;
      else
         pulseIndex = 1.0 - ((1.0 / MAX_TICKS) * tickIndex);
         
      for(int j = 0; j < 4; j++)
      {
         Vector<VisualEffect> veList = lockList;
         if(j == 1)
            veList = nonlockList;
         if(j == 2)
            veList = actorVEList;
         if(j == 3)
            veList = groundEffectList;
         for(int i = 0; i < veList.size(); i++)
         {
            veList.elementAt(i).increment();
            if(veList.elementAt(i).isExpired())
            {
               veList.removeElementAt(i);
               i--;
            }
         }
      }
   }
   
   public static void add(VisualEffect ve)
   {
      if(ve instanceof ActorVisualEffect)
         actorVEList.add(ve);
      else
         nonlockList.add(ve);
   }
   
   public static void addLocking(VisualEffect ve)
   {
      lockList.add(ve);
   }
   
   public static void addGroundEffect(VisualEffect ve)
   {
      groundEffectList.add(ve);
   }
   
   public static boolean hasBlockingVisualEffect()
   {
      return lockList.size() > 0;
   }
   
   public static int getPulseIndex(int len)
   {
      return Math.min(len - 1, (int)(len * pulseIndex));
   }
}