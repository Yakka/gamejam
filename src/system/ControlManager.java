package system;

import java.util.ArrayList;

import model.GameObject;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;


public class ControlManager
{
	// / CLASS NAMESPACE VARIABLES
	private static ControlManager instance = null;
	private final static int axisIndex = 4;

	// / CLASS NAMESPACE FUNCTIONS
	public static void createInstance(Input init_input)
	{
		instance = new ControlManager(init_input);
	}
	
	public static ControlManager getInstance()
	{
		if(instance == null)
			System.out.println("ControlManager has not neen initialised!");
		return instance;
	}

	// / ATTRIBUTES
	private enum Device 
	{
		PS3Turntable, XBOXTurntable, keyboard;
	}
	private Device device = Device.keyboard;
	private Input input;
	private int wheel_direction = 0;
	private int deviceIndex = -1;
	private String deviceName;
	private ArrayList<Controller> controllers;

	/// METHODS
	
	// creation
	public ControlManager(Input init_input)
	{
		// Initialize variables
		input = init_input;
		controllers = new ArrayList<Controller>();
		checkDevice();
		
		
		//Controllers test
		try
		{
			input.initControllers();
			//TODO : do it better
			//Check if turntable is connected :
			for (int i=0;i<controllers.size();i++)
				if(((Controller) controllers.get(i)).getName().compareTo("Guitar Hero5 for PlayStation (R) 3") == 0) 
					System.out.println("Turntable connected.");
					
					//Get the number of axis
					//System.out.println("Number of axis : "+container.getInput().getAxisCount(2)); //4
		}
		catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// query
	public int getSnakeDelta(GameObject.Colour snake_colour)
	{
		switch(device)
		{
			case keyboard :
				switch(snake_colour)
				{
					case BLUE:
						return (input.isKeyDown(Input.KEY_W)) ? arrowToDelta() : 0;
					case RED:
						return (input.isKeyDown(Input.KEY_X)) ? arrowToDelta() : 0;
					case GREEN:
						return (input.isKeyDown(Input.KEY_C)) ? arrowToDelta() : 0;
					default:
						return 0;
		
				}
			case PS3Turntable :
				switch(snake_colour)
				{
					case BLUE:
						return (input.isButtonPressed(4, deviceIndex)) ? arrowToDelta() : 0;
					case RED://TODO
						return (input.isButtonPressed(6, deviceIndex)) ? arrowToDelta() : 0;
					case GREEN://TODO
						return (input.isButtonPressed(5, deviceIndex)) ? arrowToDelta() : 0;
					default:
						return 0;
		
				}
			default :
				return 0;
		}
	}
	
	private int arrowToDelta()
	{
		//return wheel_direction;
		switch(device)
		{
			case PS3Turntable :
				if(input.getAxisValue(2, 0) < 0)
					return -1;
				else if(input.getAxisValue(2, 0) > 0)
					return 1;
				else
					return 0;
			default :
				if(input.isKeyDown(Input.KEY_UP))
					return -1;
				else if(input.isKeyDown(Input.KEY_DOWN))
					return 1;
				else
					return 0;
		}
	}

	public void wheelEvent(int delta)
	{
		// Event generated by Game instance
		System.out.println(delta);
		wheel_direction = (int) Math.signum(delta);	
	}
	
	public void forgetEvents()
	{
		// Forget the mouse wheel direction the update after it occurs
		wheel_direction = 0;
	}
	
	//Check if the player play with a turntable or a keyboard
	private void checkDevice()
	{
		String tamp;
		controllers = new ArrayList<Controller>();
		for (int i = 0; i < Controllers.getControllerCount(); i++) 
		{
			Controller controller = Controllers.getController(i);
			if ((controller.getButtonCount() >= 3) && (controller.getButtonCount() < 20))
			{
				controllers.add(controller);
			}
		}
		for (int i=0;i<controllers.size();i++)
		{
			tamp = ((Controller) controllers.get(i)).getName();
			if (tamp.compareTo("Guitar Hero5 for PlayStation (R) 3") == 0) 
			{
				deviceName = "Guitar Hero5 for PlayStation (R) 3";
				deviceIndex = ((Controller) controllers.get(i)).getIndex();
				device = Device.PS3Turntable;
			}
			//TODO : find the good string for the xbox device
//			if(tamp.compareTo("Guitar Hero5 for Xbox360 ??? (R) 3") == 0)
//			{
//				deviceName = "Guitar Hero5 for Xbox360 ??? (R) 3";
//				deviceIndex = ((Controller) controllers.get(i)).getIndex();	
//				device = Device.XBOXTurntable;
//			}
				
		}
	}

	public int getDeviceIndex() {
		return deviceIndex;
	}

	
	
	

}
