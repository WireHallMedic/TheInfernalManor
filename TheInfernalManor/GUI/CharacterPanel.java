package TheInfernalManor.GUI;

import StrictCurses.*;
import java.awt.*;
import java.awt.event.*;
import TheInfernalManor.Engine.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Ability.*;

public class CharacterPanel extends TIMPanel implements GUIConstants
{
   private static final int COLUMN_WIDTH = (TILES_WIDE - 2) / 2;
   public CharacterPanel(SCTilePalette tilePalette, TIMFrame pFrame)
   {
      super(tilePalette, pFrame);
      GUITools.drawBorderWithTitle(this, " Character ");
   }
   
   @Override
   public void keyPressed(KeyEvent ke)
   {
      if(ke.getKeyCode() == KeyEvent.VK_ESCAPE || ke.getKeyCode() == KeyEvent.VK_SPACE)
      {
         parentFrame.returnToMainPanel();
      }
   }
   
   @Override
   public void paint(Graphics g)
   {
      Actor a = GameState.getPlayerCharacter();
      if(a != null)
      {
         int lineIndex = 3;
         overwriteLine(2, lineIndex++, a.getName(), COLUMN_WIDTH);
         lineIndex++;
         overwriteLine(2, lineIndex++, String.format("Health:          %d/%d", a.getCurHealth(), a.getMaxHealth()), COLUMN_WIDTH - 1);
         overwriteLine(2, lineIndex++, String.format("Energy:          %d/%d", a.getCurEnergy(), a.getMaxEnergy()), COLUMN_WIDTH - 1);
         overwriteLine(2, lineIndex++, String.format("Guard:           %d/%d", a.getCurGuard(), a.getMaxGuard()), COLUMN_WIDTH - 1);
         lineIndex++;
         overwriteLine(2, lineIndex++, String.format("Move Speed:      %s", a.getMoveSpeed().toString()), COLUMN_WIDTH - 1);
         overwriteLine(2, lineIndex++, String.format("Interact Speed:  %s", a.getInteractSpeed().toString()), COLUMN_WIDTH - 1);
         overwriteLine(2, lineIndex++, String.format("Ability Speed:   %s", a.getAbilitySpeed(ActionSpeed.NORMAL).toString()), COLUMN_WIDTH - 1);
         lineIndex++;
         overwriteLine(2, lineIndex++, String.format("Physical Damage: %d", a.getPhysicalDamage()), COLUMN_WIDTH - 1);
         overwriteLine(2, lineIndex++, String.format("Physical Armor:  %d", a.getPhysicalArmor()), COLUMN_WIDTH - 1);
         overwriteLine(2, lineIndex++, String.format("Magical Damage:  %d", a.getMagicalDamage()), COLUMN_WIDTH - 1);
         overwriteLine(2, lineIndex++, String.format("Magical Armor:   %d", a.getMagicalArmor()), COLUMN_WIDTH - 1);
         overwriteLine(2, lineIndex++, String.format("Vison:           %d", a.getVision()), COLUMN_WIDTH - 1);
         lineIndex++;
         overwriteLine(2, lineIndex++, "Status Effects:", COLUMN_WIDTH);
         overwriteLine(2, lineIndex, "", COLUMN_WIDTH); // clear line as writeBox() does not
         String str = "";
         for(StatusEffect se : a.getSEList())
            str += se.getName() + ", ";
         if(str.length() > 2)
         {
            str = str.substring(0, str.length() - 2);
            lineIndex += writeBox(4, lineIndex, COLUMN_WIDTH - 3, 4, str);
         }
         while(lineIndex < TILES_TALL - 1)
            overwriteLine(2, lineIndex++, "", COLUMN_WIDTH - 1);
      }
      super.paint(g);
   }
}