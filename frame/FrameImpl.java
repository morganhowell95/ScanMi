package com.scanMi.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.scanMi.constants.Constants;
import com.scanMi.data.DataEncryption;
import com.scanMi.pixel.Pixel;
import com.scanMi.pixel.PixelReturn;

public class FrameImpl extends JFrame implements Frame {

	protected Pixel pixels[][];
	private int width;
	private int height;
	private String title;

	public FrameImpl(int width, int height, Pixel init_color, String title) {
		super(title);
		if (width < 1 || height < 1) {
			throw new RuntimeException("Illegal dimensions.");
		}

		if (title.equals(null))
			throw new RuntimeException("Illegal String Value: null");

		this.width = width;
		this.height = height;
		this.title = title;

		if (init_color == null)
			throw new RuntimeException("Illegal initial pixel: null");

		pixels = new Pixel[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[y][x] = init_color;
			}
		}

		setSize(width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Pixel getPixel(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new RuntimeException("x,y coordinates out of bounds");
		}

		return pixels[y][x];
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String new_title) {
		if (new_title.equals(null))
			throw new RuntimeException("Illegal String Value: null");
		title = new_title;
	}

	public void setPixel(int x, int y, Pixel p) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			throw new RuntimeException("x,y coordinates out of bounds");
		}

		pixels[y][x] = p;
	}

	public boolean equals(Frame f) {
		if (f == null)
			return false;
		if (f.getHeight() != height || f.getWidth() != width)
			return false;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (pixels[y][x].getBrightness()
						- f.getPixel(x, y).getBrightness() <= 0.01
						&& pixels[y][x].getBrightness()
								- f.getPixel(x, y).getBrightness() >= -0.01) {
					// Continues through valid results that are smaller than a
					// 0.01 difference in brightness
				} else
					return false;
			}
		}

		return true;
	}

	public Pixel getAverage() {
		int sumRed = 0;
		int sumGreen = 0;
		int sumBlue = 0;
		int total = height * width;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sumRed = pixels[y][x].getRed();
				sumGreen = pixels[y][x].getGreen();
				sumBlue = pixels[y][x].getGreen();
			}
		}

		return new Pixel(sumRed / total, sumGreen / total, sumBlue / total);
	}

	@Override
	public String toString() {
		return "Frame: " + title + " (" + width + " x " + height + ")";
	}

	public IndirectFrame crop(int x, int y, int width, int height) {
		return new IndirectFrame(this, x, y, width, height);
	}

	@Override
	public void paint(Graphics g) {

		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[i].length; j++) {
				g.setColor(new Color((int) pixels[i][j].getRed(),
						(int) pixels[i][j].getGreen(), (int) pixels[i][j]
								.getBlue()));
				g.drawLine(j, i, j, i);
			}
		}
	}

	public void copyImage(File file) {
		Color temp;

		ArrayList<ArrayList<Color>> originalImg = new PixelReturn(file)
				.getRGBArray();
		height = originalImg.size();
		width = originalImg.get(0).size();
		pixels = new Pixel[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				temp = originalImg.get(y).get(x);
				setPixel(x,
						y,
						new Pixel(temp.getRed(), temp.getGreen(), 
								temp.getBlue())); // This voids usage for GrayFrame
												// Objects

			}
		}
		setSize(width, height);
		repaint();
	}
	
	public void addBorder(){
		int width_thick = getWidth() / 40;
		int height_thick = getHeight() / 40;
			
		Pixel black = new Pixel(0, 0, 0);
		
		for(int x = 0; x < getWidth(); x++){		
			for(int y = 0; y < getHeight(); y++){
				if(y < height_thick){
					setPixel(x, y, black );
				}
				
				else if(x < width_thick || x > getWidth() - width_thick){
					setPixel(x, y, black);
				}
				
				else if(y > getHeight() - height_thick){
					setPixel(x, y, black);
				}
			}
		}
		
	}
	
	public void threshInit(){
		int topThresh = (int) Math.pow((Constants.MARGIN+2), 2);
		int bottomThresh = (int) Math.pow((Constants.MARGIN-2), 2);
			
	}
	
	public boolean setSubFrameParams(){
		

		int y = getHeight()/2;
		Pixel black = new Pixel(0, 0, 0);
		int count = 0;
		
		for(int x = 0; x < getWidth() -1; x++){
			if(getPixel(x, y).rgbEquals(black)){
				count++;
			}
			
			if(count >= 5){
				Constants.x1 = x;
				break;
			}
		}
		
		count = 0;
		
		for(int x = getWidth() - 1; x > 1; x--){ 
			if(getPixel(x, y).rgbEquals(black)){
				count++;
			}
			
			if(count >= 5){
				Constants.x2 = x;
				break;
			}
		}
		
		count = 0;
		int x = getWidth()/2;
		
		for(y = 0; y < getHeight() -1; y++){
			if(getPixel(x, y).rgbEquals(black)){
				count++;
			}
			
			if(count >= 5){
				Constants.y1 = y;
				break;
			}
		}

		count = 0;
		for(y = getHeight() - 1; y > 1; y--){
			if(getPixel(x, y).rgbEquals(black)){
				count++;
			}
			
			if(count >= 5){
				Constants.y2 = y;
				break;
			}
		}
		
		if(Constants.x1 == -1||
				Constants.x2 == -1||
				Constants.y1 == -1||
				Constants.y2 == -1){
			return false;
		}
		
		return true;
		
	}

	public static void main(String[] args) {
		
		
		FrameImpl frame = new FrameImpl(100, 100, new Pixel(100, 100, 100),
				"ScanMi");
		
		frame.copyImage(new File("appleLogo.jpg"));
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setLocation(350,200);
		Constants.MARGIN = frame.width / 35;
		frame.addBorder();

		DataEncryption encryptor = new DataEncryption(frame);
		encryptor.encrypt("HELLO");
		System.out.println(encryptor.search());
		
	}

}
