package com.scanMi.frame;

import java.io.File;

import com.scanMi.pixel.Pixel;


public interface Frame {
	int getWidth();
	int getHeight();
	Pixel getPixel(int x, int y);
	void setPixel(int x, int y, Pixel p);
	String getTitle();
	void setTitle(String new_title);
	boolean equals(Frame f);
	String toString();
	Pixel getAverage();
	IndirectFrame crop(int x, int y, int width, int height);
	void copyImage(File file);
}
