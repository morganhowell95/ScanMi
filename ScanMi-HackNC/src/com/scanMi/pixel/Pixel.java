package com.scanMi.pixel;

import com.scanMi.constants.Constants;

public class Pixel {
	private int red, green, blue;

	public Pixel(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getRed() {
		return red;
	}
	
	public void addRed(){
		red = red + Constants.RGB_OFFSET;
		
		if(red > 255){
			red = 255;
		}
		
		if(red < 0){
			red = 0;
		}
	}

	public int getGreen() {
		return green;
	}
	
	public void addGreen(){
		green = green + Constants.RGB_OFFSET;
		
		if(green > 255){
			green = 255;
		}
		
		if(green < 0){
			green = 0;
		}
	}

	public int getBlue() {
		return blue;
	}
	
	public void addBlue(){
		blue = blue + Constants.RGB_OFFSET;
		
		if(blue > 255){
			blue = 255;
		}
		
		if(blue < 0){
			blue = 0;
		}
	}

	public boolean rgbEquals(Pixel p) {

		if (Math.abs(p.getRed() - red) <= Constants.PIXEL_COMPARE_OFFSET) {
			if (Math.abs(p.getGreen() - green) <= Constants.PIXEL_COMPARE_OFFSET) {
				if (Math.abs(p.getBlue() - blue) <= Constants.PIXEL_COMPARE_OFFSET) {
					return true;
				}
			}
		}

		return false;
	}

	public double getBrightness() {
		return 0.2126 * red + 0.7152 * green + 0.0722 * blue;
	}

	public boolean isWhite() {
		if (getRed() >= 240) {
			if (getGreen() >= 240) {
				if (getBlue() >= 240) {
					return true;
				}
			}
		}
		
		if (getRed() <= 15) {
			if (getGreen() <= 15) {
				if (getBlue() <= 15) {
					return true;
				}
			}
		}
		return false;
	}

	public Pixel offset(int margin) {

		int r = getRed();
		int g = getGreen();
		int b = getBlue();

		r = r + margin;
		if (r < 0) {
			r = 0;
		}

		else if (r > 255) {
			r = 255;
		}

		g = g + margin;
		if (g < 0) {
			g = 0;
		}

		else if (g > 255) {
			g = 255;
		}

		b = b + margin;
		if (b < 0) {
			b = 0;
		}

		else if (b > 255) {
			b = 255;
		}

		return new Pixel(r, g, b);
	}
	
	public String getUp(Pixel previous){
		
		int deltaRed = Math.abs(previous.getRed() - getRed());
		int deltaGreen = Math.abs(previous.getGreen() - getGreen());
		int deltaBlue = Math.abs(previous.getBlue() - getBlue());
		System.out.println(deltaBlue);
		String code="";
		if(deltaRed>=Math.abs(Constants.RGB_OFFSET)){
			code+="R";
		}
		if(deltaGreen>=Math.abs(Constants.RGB_OFFSET)){
			code+="G";
		}
		if(deltaBlue>=Math.abs(Constants.RGB_OFFSET)){
			code+="B";
		}
		if(code.length()>0){
			code+= " ";
		}
		//System.out.println(code);
		return code;
	}

}