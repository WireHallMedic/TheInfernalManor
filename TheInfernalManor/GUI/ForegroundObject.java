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
}