package TheInfernalManor.Item;

import java.util.*;
import java.io.*;
import TheInfernalManor.GUI.*;
import TheInfernalManor.Actor.*;
import TheInfernalManor.Engine.*;

public class EquipmentAffexFactory implements GUIConstants, ItemConstants
{
   public static AffixBase[] baseList = setBaseList();
   
   private static AffixBase[] setBaseList()
   {
      BufferedReader bReader = EngineTools.getTextReader("MagicAffixes.csv");
      Vector<AffixBase> list = new Vector<AffixBase>();
      try
      {
         bReader.readLine(); // discard header
         String str = bReader.readLine();
         while(str != null)
         {
            list.add(new AffixBase("AffixBase;x;-1;No Description.;" + str));
            str = bReader.readLine();
         }
      }
      catch(Exception ex)
      {
         System.out.println(ex.toString());
      }
      return list.toArray(new AffixBase[list.size()]);
   }
   
   public static AffixBase getWeaponAffix(int level)
   {
      return (AffixBase)EngineTools.roll(getWeaponBases(), level);
   }
   
   public static AffixBase getShieldAffix(int level)
   {
      return (AffixBase)EngineTools.roll(getShieldBases(), level);
   }
   
   public static AffixBase getImplementAffix(int level)
   {
      return (AffixBase)EngineTools.roll(getImplementBases(), level);
   }
   
   public static AffixBase getArmorAffix(int level)
   {
      return (AffixBase)EngineTools.roll(getArmorBases(), level);
   }   
   
   public static AffixBase getRelicAffix(RelicBase rb, int level)
   {
      return (AffixBase)EngineTools.roll(getRelicBases(rb), level);
   }  
   
   public static AffixBase[] getWeaponBases()
   {
      Vector<AffixBase> list = new Vector<AffixBase>();
      fillByCategory(list, "ANY");
      fillByCategory(list, "WEAPON");
      return list.toArray(new AffixBase[list.size()]);
   }
   
   public static AffixBase[] getShieldBases()
   {
      Vector<AffixBase> list = new Vector<AffixBase>();
      fillByCategory(list, "ANY");
      fillByCategory(list, "SHIELD");
      return list.toArray(new AffixBase[list.size()]);
   }
   
   public static AffixBase[] getImplementBases()
   {
      Vector<AffixBase> list = new Vector<AffixBase>();
      fillByCategory(list, "ANY");
      fillByCategory(list, "IMPLEMENT");
      return list.toArray(new AffixBase[list.size()]);
   }
   
   public static AffixBase[] getArmorBases()
   {
      Vector<AffixBase> list = new Vector<AffixBase>();
      fillByCategory(list, "ANY");
      fillByCategory(list, "ARMOR");
      return list.toArray(new AffixBase[list.size()]);
   }
   
   public static AffixBase[] getRelicBases(RelicBase rb)
   {
      Vector<AffixBase> list = new Vector<AffixBase>();
      fillByCategory(list, "ANY");
      fillByCategory(list, "RELIC");
      switch(rb)
      {
         case HELM:        fillByCategory(list, "HELM");
                           break;
         case GLOVES:      fillByCategory(list, "WEAPON");
                           break; 
         case BOOTS:       fillByCategory(list, "BOOTS");
                           break;
         case BRACERS:     fillByCategory(list, "ARMOR");
                           break;
         case JEWELRY:     ; // no extra category for jewelry
                           break;
      }
      return list.toArray(new AffixBase[list.size()]);
   }
   
   public static void fillByCategory(Vector<AffixBase> list, String category)
   {
      for(AffixBase ab : baseList)
         if(ab.isCategory(category))
            list.add(ab);
   }
  
}