package com.scanMi.colormap;

import com.scanMi.pixel.Pixel;

public class Mapping {

	private String message;
	private String key;
	
	public Mapping(String key, String message){
		this.message = message;
		this.key = key;
	}

	public String getKey(){
		return key;
	}
	
	public String getMessage(){
		return message;
	}
	
	
}
