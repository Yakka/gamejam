package navigation;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import resources.ResourceManager;

import utility.IDynamic;

public class Loading extends Scene
{
	/// ATTRIBUTES
	Image background;
	
	/// METHODS
	public Loading()
	{
		background = ResourceManager.getInstance().getImage("menu_loading"); 
	}

	@Override
	public void draw(Graphics g)
	{
		background.draw(0, 0, size.x, size.y);
	}

	@Override
	public Scene getNextScene()
	{
		return new Title();
	}

	@Override
	public Rtn update()
	{
		ResourceManager rm = ResourceManager.getInstance();
		
		rm.load();
		rm.getMusic("music_menu").loop();
		
		return IDynamic.Rtn.CHANGE_SCENE;
	}

}
