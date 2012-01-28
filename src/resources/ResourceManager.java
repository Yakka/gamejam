/* @author william */

package resources;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ResourceManager
{
    /// CLASS NAMESPACE CONSTANTS
	private static final String RESDIR = "data/";
	private static final String PNG = ".png";
    private static final String SNAKE = "snake";
    private static final String RED_MARBLE = "red_marble";

    /// CLASS NAMESPACE VARIABLES
    private static ResourceManager instance = null;

    /// CLASS NAMESPACE FUNCTIONS
    public static ResourceManager getInstance()
    {
        if(instance == null)
            instance = new ResourceManager();
        return instance;
    }

    /// ATTRIBUTES
    private HashMap<String, Animation> animations 
    					= new HashMap<String, Animation>();


    /// METHODS
    
    // creation
    private ResourceManager()
    {
    		// Snakes
    		addAnimation("snake", 2, 300);
			// Marbles
			addAnimation("red_marble", 1, 300);
    }

    // query
    public Animation getAnimation(String animation_name)
    {
        return animations.get(animation_name);
    }
    
    // addition
    protected void addAnimation(String name, int n_frames, int frame_duration)
    {
		try
		{
			Animation new_anim = new Animation();
			
			// Load and add each frame
	    	Image frame_i;
	    	for(int i = 0; i < n_frames; i++)
	    	{
	    		frame_i = new Image(RESDIR+name+i+PNG);
	    		new_anim.addFrame(frame_i, frame_duration);
	    	}
	    	
	    	// Save the animation
	    	animations.put(name, new_anim);
		}
		catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    }
    
}
