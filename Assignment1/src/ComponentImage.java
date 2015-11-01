import java.awt.Color;
import java.util.Random;
import edu.princeton.cs.introcs.Picture;
/*************************************************************************
 * Compilation: java ConnectedComponentImage.java
 * 
 * The <tt>ConnectedComponentImage</tt> class
 * <p>
 * A class that deals with image processing.
 * 
 * 
 * @author James Power 20067779
 *************************************************************************/

public class ComponentImage 
{
	private int width;
	private int height;
	private Picture pic;
	private int[] id;     // parent link (site indexed)   
	private int[] sz;     // size of component for roots (site indexed)   
	private int count;    // number of components 
	
	//displays an image from a hardcoded location onto the screen
    public ComponentImage(String fileLocation)
    {
    	Picture pic = new Picture("images/shapes.bmp");
    	
    }
    //Takes an image from a hardcoded location and processes the image into black and white
    //it then displays the image in black and white
    public Picture	binaryComponentImage()
    {
    	for (int x = 0; x < width; x++) 
    	{
   		    for (int y = 0; y < height; y++) 
   		    {
   		        Color color = pic.get(x, y);
   		        Color gray = Luminance.toGray(color);
   		        pic.set(x, y, gray);
   		    }
   		    pic.show();
    	}	
    return pic;	
    }
    //Takes an image from a hardcoded location and processes the image
    //Using the binaryComponentMethod changes the color of the white pixels to blue 
    //and the black pixels into a randomly generated color.
    public Picture	colourComponentImage()
    {
    	//creates a random generator from the random class
    	Random rand = new Random();
    		
    	//colors are separated into red green and blue,
    	//you can create a new random color by creating random primary colors(Red,Green,Blue)
    	float r = rand.nextFloat();
    	float g = rand.nextFloat();
    	float b = rand.nextFloat();
    	
    	Picture pic = new Picture("images/shapes.bmp");
    	int rows = width;
    	int cols = height;
    	count = rows*cols;
    	int[] picArray = new int[rows * cols];
    	binaryComponentImage();
    	for (int y = 0; y < height; y++) 
    	{
    		for (int x = 0; x < width; x++) 
    	    {
    			int location = x + y*width;
                Color color = pic.get(x, y);
                {
                	if(color.equals(Color.BLACK))
    	        	{
                		//creates a random color
                		Color randomColor = new Color(r, g, b);
                		//changes the color of the pixel from black to the randomly generated color
                		color = randomColor;
    	        	}
                	else if (color.equals(Color.WHITE))
                	{
                		//sets all the white pixels to the same color blue
                		color = Color.BLUE;
                	}
                }
    	    }
    	pic.show();
    	}
    return pic;
    }  	

    
                	
                	
    //Using the binary method it checks the image for black pixels
    //the black pixels are unioned together using the WeightedQuickUF algorithm
    //each time there's a union the value of count is decremented
    //count represents the number of components in the image
    public int countComponents()
    {
    	Picture pic = new Picture("images/shapes.bmp");
    	int rows = width;
    	int cols = height;
    	count = rows*cols;
    	
        //sets up a 2D array the size of the picture
    	int[] picArray = new int[rows * cols];
    	//runs binary method which converts all pixels in array to either black or white
    	binaryComponentImage();
    	//starts at (0,0) in the array. 
    	//checks if the pixels y value if within the pictures height
    	//increments the y value of pixel
    	for (int y = 0; y < height; y++) 
    	{
    	    //starts at (0,0) in the array.
    		//checks if the pixels x value is within the pictures width.
    		//increments the x value of pixel
    		for (int x = 0; x < width; x++) 
    	    {
    			//current pixel (location) is given by pixels x value plus its y value multiplied by the width value of the array.
    	        int location = x + y*width;
    	        //gets the color of the current pixel. Because of the binarization it should only be black or white.
    	        Color color = pic.get(x, y);
    	        {
    	        	//If the color of the pixel is black
    	        	if(color.equals(Color.BLACK))
    	        	{
    	        		//gets the color of the pixel to the right of the current pixel (y+1) 
    	        		Color nextPicColor = pic.get(x, y+1);
    	        		{
    	        			//checks if the pixel beside the current pixel (y+1) is also black
    	        			//and pixels y value is less than or equal to the pictures width
    	        			if(nextPicColor.equals(Color.BLACK) && y+1 <= width)
    	        			{
    	        				//if the nextPicColor is black
    	        				//union both pixels using weighted quick union
    	        				//Make smaller root point point to larger one.
    	        			    if (sz[x] < sz[y])
    	        			    {
    	        				    id[x] = y;
    	        				    sz[y] += sz[x];
    	        			    }
    	        			    else
    	        			    {
    	        				    id[y] = x; sz[x] += sz[y];
    	        			    }
    	        			    //Decrements the value of count by one because we have unioned two pixels together.
    	        			    count--;
    	        			}
    	        		}
    	        	}
    	        }
    	    }  
    	}    
    	
    return count;	
    }
    	
    //Gets an image and runs the binary method on it. 
    //Checks the adjacents of black pixels
    //if white then that signals the outer edges of the components
    //to display the component boundary the white boundary pixels are changed to red simulating a red box around the components.
    public Picture	highlightComponentImage()
    {
    	Picture pic = new Picture("images/shapes.bmp");
    	int rows = width;
    	int cols = height;
    	int[] picArray = new int[rows * cols];
    	binaryComponentImage();
    	
    	for (int y = 0; y < height; y++) 
    	{
    		for (int x = 0; x < width; x++) 
    	    {
    			Color color = pic.get(x, y);
    	        {
    	        	if(color.equals(Color.BLACK))
    	        	{
    	        		//Gets color of pixel (x,y+1) 
    	        		Color YPLUSPicColor = pic.get(x, y+1);
    	        		//Gets color of pixel (x,y-1) 
    	        		Color YMINUSPicColor = pic.get(x, y-1);
    	        		//Gets color of pixel (x+1,y)
    	        		Color XPLUSPicColor = pic.get(x+1, y);
    	        		//Gets color of pixel (x-1,y)
    	        		Color XMINUSPicColor = pic.get(x-1, y);
    	        		{
    	        			//Check if the color of pixel y+1 is white
    	        			//and if its within the pictures width
    	        			if (YPLUSPicColor.equals(Color.WHITE)&& y+1 <= width)
    	        			{
    	        				//sets the pixel color to red
    	        				YPLUSPicColor = Color.RED;
    	        			}
    	        			//Check if the color of pixel y-1 is white
    	        			//and if its within the pictures width
    	        			if (YMINUSPicColor.equals(Color.WHITE)&& y-1 >= 0)
    	        		    {
    	        		    	//sets the pixel color to red
    	        				YMINUSPicColor = Color.RED;
    	        		    }
    	        			//Check if the color of pixel x+1 is white
    	        			//and if its within the pictures height
    	        			if (XPLUSPicColor.equals(Color.WHITE)&& x+1 <= height)
    	        			{
    	        				//sets the pixel color to red
    	        				XPLUSPicColor = Color.RED;
    	        			}
    	        			//Check if the color of pixel x-1 is white
    	        			//and if its within the pictures height
    	        			if (XMINUSPicColor.equals(Color.WHITE)&& x-1 >= 0)
    	        		    {
    	        				//sets the pixel color to red
    	        				XMINUSPicColor = Color.RED;
    	        		    }
    	        		}
    	        	}
    	        }
    	    }
    	pic.show();
    	}
    return pic;
    }
}    
    	
    
