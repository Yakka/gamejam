package system;

import math.IVect;
import navigation.Loading;
import navigation.Scene;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import resources.ResourceManager;

import utility.IDynamic;
 
public class Game extends BasicGame 
{
	/// CONSTANTS
	private static final int MAX_FPS = 30;
	
	/// ATTRIBUTES
	private IVect size;
	private Scene current_scene;
	
	/// METHODS
	
	// creation
	public Game()
	{
		// Window title
		super("Quetzalcoatl");
	}

	// framework
	@Override
	public void init(GameContainer container) throws SlickException
	{
		// Display setup
		container.setSmoothDeltas(true);
        container.setVSync(true);
        container.setTargetFrameRate(MAX_FPS);
        container.setIcon(ResourceManager.ICON);
        container.setShowFPS(false);
        container.setMouseGrabbed(true);
        
		// Start control manager
		ControlManager.createInstance(container.getInput());

		// Get the true size of the window (may not be what was requested)
		size = new IVect(container.getWidth(), container.getHeight());
		Scene.init(size.FVect());
		current_scene = new Loading();	
	}

	@Override
	public void controllerButtonPressed(int controller, int button)
	{
		ControlManager.getInstance().controllerButtonPressed(controller, button);
	}
	
	@Override
	public void controllerButtonReleased(int controller, int button)
	{
		ControlManager.getInstance().controllerButtonReleased(controller, button);
	}
	
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException
	{
		// Update all the scene currently being displayed
		IDynamic.Rtn update_rtn = current_scene.update();
		switch(update_rtn)
		{
			case CHANGE_SCENE:
				current_scene = current_scene.getNextScene();
				ControlManager.getInstance().clearInputQueue();
				System.gc();
				break;
			
			case EXIT_GAME:
				container.exit();
				break;
			
			default:
				break;
		}
		
		// Forget events that were received this step
		ControlManager.getInstance().forgetEvents();
//		if(ControlManager.getInstance().getDevice() == ControlManager.Device.PS3_TURNTABLE)
//			for(int i = 0; i < 20; i ++)
//				if (container.getInput().isControlPressed(i, ControlManager.getInstance().getDeviceIndex()))
//					System.out.println("Key pressed ! #"+i);

	}
	
	/**
	 * Check if the wheel has moved.
	 * @param container
	 * @param delta
	 */
	public void scratchCheck(GameContainer container, int delta)
	{
		if(container.getInput().isControllerDown(ControlManager.getInstance().getDeviceIndex()))
		{
		}
		if(container.getInput().isControllerUp(ControlManager.getInstance().getDeviceIndex()))
		{
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException
	{
		
		// Draw the contents of the scene
		current_scene.draw(g);
	}
	
	public void keyPressed(int key, char c)
	{
		ControlManager.getInstance().anyKeyEvent();
	}
	
	public void mouseWheelMoved(int delta) 
	{
		ControlManager.getInstance().wheelEvent(delta);
	}
}
