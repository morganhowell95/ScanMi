package com.scanMi.data;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.scanMi.colormap.ColorMap;
import com.scanMi.colormap.MappingNotFoundException;
import com.scanMi.constants.Constants;
import com.scanMi.frame.Frame;
import com.scanMi.frame.FrameImpl;
import com.scanMi.pixel.Pixel;
import com.scanMi.pixel.PixelIterator;

public class DataEncryption { // Encrypts given string through RGB Manipulation

	private Frame frame;
	private PixelIterator Iter;

	private ArrayList<Pixel> thresh = new ArrayList<Pixel>();

	public int tempx = 0;
	public int tempy = 0;

	public DataEncryption(FrameImpl frame) {
		this.frame = frame;
		Iter = getIterator();
	}

	public String decrypt() {
		return null;
	}

	public Pixel encrypt(String message) { // String is altered to pixel form
		ColorMap map = new ColorMap();
		int pixelsDrawn = 0;
		String rgbCode = "RG RG RG ";

		for (int i = 0; i < message.length(); i++) {
			String currentLetter = "" + message.charAt(i);
			try {
				rgbCode += map.reverseMapTo(currentLetter);
			} catch (MappingNotFoundException e) {
				e.printStackTrace();
			}

		}

		rgbCode += "RG RG RG ";
		System.out.println("message1;" + rgbCode);

		Scanner scanner = new Scanner(rgbCode);

		while (Iter.hasNext()  //raster scan mathces alike pixel families
				&& scanner.hasNext()) {
			Pixel tempP = Iter.next();
			if (hasNeighbor(Constants.MARGIN)) {
				int x = Iter.getX();
				int y = Iter.getY();
				boolean changedPixel = false;
				Pixel p = frame.getPixel(x, y);

				if (!p.isWhite())
				{

					pixelsDrawn++;

					if (pixelsDrawn == 2) {
						tempx = x - Constants.MARGIN / 2;
						tempy = y - Constants.MARGIN / 2;
					}

					String currentLetter = scanner.next();

					for (int i = x - Constants.MARGIN / 2; i < x
							+ Constants.MARGIN / 2; i++) {
						for (int j = y - Constants.MARGIN / 2; j < y
								+ Constants.MARGIN / 2; j++) {

							Pixel newPix = new Pixel(p.getRed(), p.getGreen(),
									p.getBlue());

							if (currentLetter.contains("R")) {
								newPix.addRed();
							}
							if (currentLetter.contains("G")) {
								newPix.addGreen();
							}
							if (currentLetter.contains("B")) {
								newPix.addBlue();
							}

							frame.setPixel(i, j, newPix);
							changedPixel = true;
						}
					}
				}

				if (changedPixel) {
					Iter.skipX();
				}
			}
		}

		return null;
	}

	private boolean hasNeighbor(int margin) { 

		int x = Iter.getX();
		int y = Iter.getY();

		if (x > margin && y > margin && x < frame.getWidth() - margin - 1
				&& y < frame.getHeight() - margin - 1) {
			for (int i = 0; i < margin * 2; i++) {
				for (int j = 0; j < margin * 2; j++) {
					if (!(frame.getPixel(x - margin + i, y - margin + j)
							.rgbEquals(frame.getPixel(x, y)))) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public PixelIterator getIterator() {
		return new PixelIterator(frame);
	}

	public String search() {
		PixelIterator iter = new PixelIterator(frame);
		Pixel currentP = iter.next();
		Pixel previousP = null;
		String mapping = "";
		boolean found = false;

		while (iter.hasNext()) {

			if (found && iter.getX() == 1
					&& iter.getY() <= frame.getHeight() - Constants.MARGIN - 1) {
				found = false;
			}
			previousP = currentP;
			currentP = iter.nextSearch();

			if (!previousP.rgbEquals(currentP) && !previousP.isWhite()
					&& !currentP.isWhite()) {
				thresh = new ArrayList<Pixel>();

				int val = thresholdGrowth(iter.getX(), iter.getY());
				if (marginOfError(val)) {
					mapping += currentP.getUp(previousP);
					iter.skipX();
					found = true;
					System.out.println(mapping);
				}

			}

		}

		ColorMap map = new ColorMap();
		try {
			mapping = map.stringFilter(mapping);
		} catch (MappingNotFoundException e) {
			e.printStackTrace();
		}
		return mapping;
	}

	public int thresholdGrowth(int x, int y) {

		if (x <= 0) {
			return -1;
		}

		if (thresh.size() > Math.pow(Constants.MARGIN, 2)) {
			return -1;
		}

		Pixel p = frame.getPixel(x, y);

		if (p.rgbEquals(frame.getPixel(x - 1, y))) {
			if (addPixel(frame.getPixel(x - 1, y))) {
				thresholdGrowth(x - 1, y);
			}
		}
		if (p.rgbEquals(frame.getPixel(x - 1, y - 1))) {
			if (addPixel(frame.getPixel(x - 1, y - 1))) {
				thresholdGrowth(x - 1, y - 1);
			}
		}
		if (p.rgbEquals(frame.getPixel(x - 1, y + 1))) {
			if (addPixel(frame.getPixel(x - 1, y + 1))) {
				thresholdGrowth(x - 1, y + 1);
			}
		}
		if (p.rgbEquals(frame.getPixel(x, y + 1))) {
			if (addPixel(frame.getPixel(x, y + 1))) {
				thresholdGrowth(x, y + 1);

			}
		}
		if (p.rgbEquals(frame.getPixel(x + 1, y + 1))) {
			if (addPixel(frame.getPixel(x + 1, y + 1))) {
				thresholdGrowth(x + 1, y + 1);
			}
		}
		if (p.rgbEquals(frame.getPixel(x + 1, y))) {
			if (addPixel(frame.getPixel(x + 1, y))) {
				thresholdGrowth(x + 1, y);
			}
		}
		if (p.rgbEquals(frame.getPixel(x + 1, y - 1))) {
			if (addPixel(frame.getPixel(x + 1, y - 1))) {
				thresholdGrowth(x + 1, y - 1);
			}
		}
		if (p.rgbEquals(frame.getPixel(x, y - 1))) {
			if (addPixel(frame.getPixel(x, y - 1))) {
				thresholdGrowth(x, y - 1);
			}
		}

		if (thresh.size() > Math.pow(Constants.MARGIN, 2)) {
			return -1;
		} else {
			return thresh.size();
		}

	}

	private boolean addPixel(Pixel p) {

		if (thresh.contains(p)) {
			return false;

		}

		thresh.add(p);
		return true;

	}

	private boolean marginOfError(int val) {

		Constants.MARGIN_OF_ERROR = (int) Math.pow(Constants.MARGIN, 2);
		return val >= Constants.MARGIN_OF_ERROR;
	}

}
