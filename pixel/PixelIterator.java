package com.scanMi.pixel;

import com.scanMi.constants.Constants;
import com.scanMi.frame.Frame;

public class PixelIterator {

	private Frame frame;
	private int x = 0;
	private int y = 10;

	public PixelIterator(Frame frame) {
		this.frame = frame;
	}

	public boolean hasNext() {

		if (y + 1 > frame.getHeight() - Constants.MARGIN - 2)
			return false;
		else
			return true;
	}

	public Pixel next() {

		if (hasNext() == false)
			throw new RuntimeException("Next Pixel Does Not Exist");
		if (x < frame.getWidth()
		// - Constants.MARGIN - 1
				&& y < frame.getHeight() - Constants.MARGIN - 1)
			x++;
		if (x >= frame.getWidth()
		// - Constants.MARGIN - 1
				&& y <= frame.getHeight() - Constants.MARGIN - 1) {
			y += Constants.MARGIN;
			x = 0;
		}
		return frame.getPixel(x, y);
	}

	public Pixel nextSearch() {

		if (hasNext() == false)
			throw new RuntimeException("Next Pixel Does Not Exist");
		if (x <= frame.getWidth() - Constants.MARGIN - 1
				&& y <= frame.getHeight() - Constants.MARGIN - 1)
			x++;
		if (x >= frame.getWidth() - Constants.MARGIN - 1
				&& y <= frame.getHeight() - Constants.MARGIN - 1) {
			y+=Constants.MARGIN;
			x = 1;
		}
		return frame.getPixel(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void skipX() {
		nextSearch();
		x += Constants.MARGIN-1;
	}

	public void skipY() {
		y += Constants.MARGIN;
		if (y >= frame.getHeight()) {
			y = frame.getHeight() - 1;
		}

	}

}
