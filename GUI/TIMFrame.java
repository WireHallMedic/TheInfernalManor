package TheInfernalManor.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import StrictCurses.*;

public class TIMFrame extends JFrame implements SCConstants
{
   private SCTilePalette palette;
   private Vector<SCPanel> panelList;
   
   public TIMFrame()
   {
      super();      
      setSize(1500, 800);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("The Infernal Manor");
      setLayout(null);
      setBackground(new Color(DEFAULT_BG_COLOR));
      
      panelList = new Vector<SCPanel>();
      
      setVisible(true);
   }
}