package TheInfernalManor.Engine;

import java.util.*;

public class RNG
{
   private static java.util.Random random = new java.util.Random(System.currentTimeMillis());
   
   public static void setSeed(long s){random.setSeed(s);}
   
   public static double nextDouble(){return random.nextDouble();}
   public static int nextInt(){return random.nextInt();}
   public static int nextInt(int bound){return random.nextInt(bound);}
}