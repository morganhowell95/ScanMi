package com.scanMi.frame;

import java.io.File;

import com.scanMi.pixel.Pixel;


public class IndirectFrame implements Frame {
	private Frame frame;
	private int width;
	private int height;
	private int x_offset;
	private int y_offset;
	
	public IndirectFrame(Frame source, int x_offset, int y_offset, int width, int height){
		if (x_offset < 0 || x_offset+width >= frame.getWidth() || y_offset < 0 || y_offset+height >= frame.getHeight()) {
			throw new IllegalArgumentException("x,y coordinates out of bounds");
		}
		
		frame = source;
		this.width = width;
		this.height = height;
		this.x_offset = x_offset;
		this.y_offset = y_offset;	
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Pixel getPixel(int x, int y) {
		if (x_offset+x < x_offset || x_offset+x >= frame.getWidth() || y_offset+y < y_offset || y_offset+y >= frame.getHeight()) {
			throw new RuntimeException("x,y coordinates out of bounds");
		}
		
		return frame.getPixel(x_offset+x, y_offset+y);
	}
	
	public void setPixel(int x, int y, Pixel p) {
		if (x_offset+x < x_offset || x_offset+x >= frame.getWidth() || y_offset+y < y_offset || y_offset+y >= frame.getHeight()) {
			throw new RuntimeException("x,y coordinates out of bounds");
		}
		
		frame.setPixel(x_offset+x, y_offset+y, p);
	}

	public String getTitle() {
		return frame.getTitle();
	}
	
	public void setTitle(String new_title) {
		frame.setTitle(new_title);
	}

	public boolean equals(Frame f) {
		if (f==null) return false;
		if (f.getHeight() != height || f.getWidth() != width) return false;
		
		for (int y = 0; y<height; y++) {
			for (int x = 0; x<width; x++) {
				if (Math.abs(frame.getPixel(x_offset+x, y_offset+y).getBrightness()-f.getPixel(x,y).getBrightness())<=0.01){
					//Continues through valid results that are smaller than a 0.01 difference in brightness
				}	
				else return false;
			}
		}
		
		return true;
	}

	public Pixel getAverage() {
		int sumRed = 0;
		int sumGreen = 0;
		int sumBlue = 0;
		int total = height*width;
		
		for (int y = 0; y<height; y++) {
			for (int x = 0; x<width; x++) {
				sumRed = frame.getPixel(x_offset+x, y_offset+y).getRed();
				sumGreen = frame.getPixel(x_offset+x, y_offset+y).getGreen();
				sumBlue = frame.getPixel(x_offset+x, y_offset+y).getGreen();
			}
		}
		
		return new Pixel(sumRed/total,sumGreen/total,sumBlue/total);
	}

	public IndirectFrame crop(int x, int y, int width, int height){
		return new IndirectFrame(frame,x_offset+x,y_offset+y,width,height);
	}

	@Override
	public void copyImage(File file) {
		// TODO Auto-generated method stub
		
	}
	

}
