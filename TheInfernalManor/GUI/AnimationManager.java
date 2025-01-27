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
   private int tickIndex;


	public static boolean getSlowBlink(){return slowBlink;}
	public static boolean getMediumBlink(){return mediumBlink;}
	public static boolean getFastBlink(){return fastBlink;}
   
   
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
   }
}