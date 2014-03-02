package com.scanMi.pixel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PixelReturn {
		BufferedImage image;
		ArrayList<ArrayList<Color>> colorArray;

		public PixelReturn(File file) {
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				System.out.println("File Not Found!");
			}
			colorArray = new ArrayList<ArrayList<Color>>();
			for (int i = 0; i < image.getHeight(); i++) {
				colorArray.add(new ArrayList<Color>());
				for (int j = 0; j < image.getWidth(); j++) {
					Color c = new Color(image.getRGB(j, i));
					colorArray.get(i).add(c);
				}
			}
		}

		public ArrayList<ArrayList<Color>> getRGBArray() {
			return colorArray;
		}
	
}
