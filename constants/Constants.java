package com.scanMi.constants;

public class Constants {
	
	public static int MARGIN = 8;  
	public static int MARGIN_OF_ERROR;
	
	
	public static int x1 = -1;
	public static int x2 = -1;
	public static int y1 = -1;
	public static int y2 = -1;
	
	public static final int RGB_OFFSET = -15;
	
	// must happen: PIXEL_COMPARE_OFFSET < abs(RGB_OFFSET-MARGIN)
	public static final int PIXEL_COMPARE_OFFSET = 2;
}
