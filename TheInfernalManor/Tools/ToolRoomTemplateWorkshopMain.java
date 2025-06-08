
package TheInfernalManor.Tools;

import javax.swing.*;
import java.awt.*;
import TheInfernalManor.GUI.*;

public class ToolRoomTemplateWorkshopMain extends JFrame
{
   private JPanel mapPanel;
   private JPanel controlPanel;
   
   public ToolRoomTemplateWorkshopMain()
   {
      super();
      setSize(1400, 1000);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setTitle("Room Template Workshop");
      setLayout(new GridLayout(1, 2));
      mapPanel = new JPanel();
      mapPanel.setBackground(Color.BLUE);
      add(mapPanel);
      controlPanel = new JPanel();
      controlPanel.setBackground(Color.RED);
      add(controlPanel);
      setVisible(true);
   }
   
   public static final void main(String[] args)
   {
      ToolRoomTemplateWorkshopMain frame = new ToolRoomTemplateWorkshopMain();
   }
}