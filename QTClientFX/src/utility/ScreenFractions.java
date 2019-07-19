package utility;

import com.sun.glass.ui.Screen;

/**
 * Contains constants useful for windows sizing
 * 
 * @author Pasquale De Marinis
 * @author Roberto Barile
 * @author Sergio Caputo
 */
public abstract class ScreenFractions {
	public static final double HEIGHT_1 = 0.85;
	public static final double HEIGHT_2 = 0.02;
	public static final double QUARTER_WIDTH = 0.025;
	public static final double SCREEN_HEIGHT = Screen.getMainScreen().getHeight();
	public static final double SCREEN_WIDTH = Screen.getMainScreen().getWidth();
}
