import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.introcs.Picture;
/*************************************************************************
 * Compilation: java TestConnectedComponentImage.java
 * 
 * The <tt>TestConnectedComponentImage</tt> JUnitTestCase
 * <p>
 * A JUnitTestCase that tests the ComponentImage java class.
 * 
 * 
 * @author James Power 20067779
 *************************************************************************/

public class TestComponentImage {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//tests that a picture is present from the picture location
	@Test
	public void testGenerateDefaultThumbnail() {
	    Picture pic = new Picture("c:/someFileLocation");
	    assertTrue(pic!= null);
	}

}
