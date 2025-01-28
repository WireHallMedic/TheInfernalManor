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
   private static Vector<VisualEffect> lockList = new Vector<VisualEffect>();
   private static Vector<VisualEffect> nonlockList = new Vector<VisualEffect>();
   private static Vector<VisualEffect> actorVEList = new Vector<VisualEffect>();
   private int tickIndex;


	public static boolean getSlowBlink(){return slowBlink;}
	public static boolean getMediumBlink(){return mediumBlink;}
	public static boolean getFastBlink(){return fastBlink;}
   public static Vector<VisualEffect> getLockList(){return lockList;}
   public static Vector<VisualEffect> getNonlockList(){return nonlockList;}
   public static Vector<VisualEffect> getActorVEList(){return actorVEList;}
   
   
   public AnimationManager()
   {
      slowBlink = false;
      mediumBlink = false;
      fastBlink = false;
      tickIndex = 0;
   }
   
   public void kick()
   {
      tickIndex++;
      if(tickIndex == MAX_TICKS)
         tickIndex = 0;
      
      if(tickIndex % 24 == 0)
         slowBlink = !slowBlink;
      
      if(tickIndex % 12 == 0)
         mediumBlink = !mediumBlink;
      
      if(tickIndex % 6 == 0)
         fastBlink = !fastBlink;
         
      for(int j = 0; j < 3; j++)
      {
         Vector<VisualEffect> veList = lockList;
         if(j == 1)
            veList = nonlockList;
         if(j == 2)
            veList = actorVEList;
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
   
   public static boolean hasBlockingVisualEffect()
   {
      return lockList.size() > 0;
   }
}