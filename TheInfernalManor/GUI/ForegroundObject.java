// class for items that appear in map foreground, such as items and actors

package TheInfernalManor.GUI;

public class ForegroundObject implements GUIConstants
{
   
   private String name;
	private int iconIndex;
	private int color;
   
	public String getName(){return name;}
	public int getIconIndex(){return iconIndex;}
	public int getColor(){return color;}
   
	public void setName(String n){name = n;}
	public void setIconIndex(int i){iconIndex = i;}
	public void setColor(int c){color = c;}
   
   public ForegroundObject()
   {
      this("Unknown FGObj", '?', WHITE);
   }
   
   public ForegroundObject(String n, int icon, int c)
   {
      name = n;
      iconIndex = icon;
      color = c;
   }
   
   public ForegroundObject(ForegroundObject that)
   {
      this.name = that.name;
      this.iconIndex = that.iconIndex;
      this.color = that.color;
   }
   
   public String getSerializationString(String str)
   {
      return ",\"" + str + "\"";
   }
   
   public String getSerializationString(int i)
   {
      return ",\"" + i + "\"";
   }
   
   public boolean equals(ForegroundObject that)
   {
      return this.name.equals(that.name) &&
         this.iconIndex == that.iconIndex &&
         this.color == that.color;
   }
   
   public String[] getDeserializationArray(String str)
   {
      String[] strList = str.split(",");
      for(int i = 0; i < strList.length; i++)
         strList[i] = strList[i].replace("\"", "").trim();
      return strList;
   }
   
   public int numOfSerializedComponents()
   {
      return 3;
   }
   
   public String serialize()
   {
      return String.format("\"%s\",\"%s\",\"%d\"", name, "" + iconIndex, color);
   }
   
   public void deserialize(String str)
   {
      String[] strList = getDeserializationArray(str);
      name = strList[0];
      iconIndex = Integer.parseInt(strList[1]);
      color = Integer.parseInt(strList[2]);
   }
   
   public void setTestingValues()
   {
      name = "Testing Object";
      iconIndex = '?';
      color = -1;
   }
}