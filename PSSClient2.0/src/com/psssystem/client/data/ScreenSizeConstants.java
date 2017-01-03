package com.psssystem.client.data;

import java.awt.Toolkit;

public class ScreenSizeConstants {
	public final static int SCREEN_WIDTH = Toolkit.getDefaultToolkit()
			.getScreenSize().width;
	public final static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit()
			.getScreenSize().height;
	public final static int WIDTH = SCREEN_WIDTH * 6 / 7;
	public final static int HEIGHT = SCREEN_HEIGHT * 3 / 4;
	public final static int LEFT= SCREEN_WIDTH/14;
	public final static int TOP=SCREEN_HEIGHT/8;
	
	private ScreenSizeConstants(){
		
	}
}
