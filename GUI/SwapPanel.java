package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.event.*;

public interface SwapPanel
{
   public String getPanelName();
   public void setVisible(boolean v);
   public void setSize(int w, int h);
   public void setLocation(int x, int y);
}