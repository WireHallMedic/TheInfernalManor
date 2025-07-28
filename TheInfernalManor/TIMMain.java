package TheInfernalManor;

import TheInfernalManor.GUI.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Item.*;
import java.awt.*;


public class TIMMain
{
   public static void main(String[] args)
   {
      TIMFrame frame = new TIMFrame();
      GameState gameState = new GameState();
      int a = new Color(154, 205, 50).brighter().getRGB();
      int b = new Color(154, 205, 50).getRGB();
      int c = GUITools.getAverageColor(a, b);
      System.out.println(GUITools.getAverageColor(c, b) + "");
   }
}