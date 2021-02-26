package util;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class ScreenDimension {
	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	static final int width = gd.getDisplayMode().getWidth(); // retrieves the width of the screen;
	static final int height = gd.getDisplayMode().getHeight(); // retrieves the height of the screen;

	public static int getScreenWidth() {
		return width;
	}

	public static int getScreenHeight() {
		return height;
	}
}
