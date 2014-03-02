package com.scanMi.colormap;

import java.util.Scanner;

public class ColorMap {

	private Mapping[] map;
	private int currentPos = 0;

	public ColorMap() {

		map = new Mapping[28];

		map[0] = new Mapping("RG RG RG ", "start_codon");
		map[1] = new Mapping("GB GB GB ", "stop_codon");
		map[2] = new Mapping("R R R R R ", "A");
		map[3] = new Mapping("R R R R G ", "B");
		map[4] = new Mapping("R R R R B ", "C");

		map[5] = new Mapping("R R R G R ", "D");
		map[6] = new Mapping("R R R G G ", "E");
		map[7] = new Mapping("R R R G B ", "F");

		map[8] = new Mapping("R R R B R ", "G");
		map[9] = new Mapping("R R R B G ", "H");
		map[10] = new Mapping("R R R B B ", "I");

		map[11] = new Mapping("R R G R R ", "J");
		map[12] = new Mapping("R R G R G ", "K");
		map[13] = new Mapping("R R G R B ", "L");

		map[14] = new Mapping("R R G G R ", "M");
		map[15] = new Mapping("R R G G B ", "N");
		map[16] = new Mapping("R R G G G ", "O");

		map[17] = new Mapping("R R G B R ", "P");
		map[18] = new Mapping("R R G B B ", "Q");
		map[19] = new Mapping("R R G B G ", "R");

		map[20] = new Mapping("R R B R R ", "S");
		map[21] = new Mapping("R R B R B ", "T");
		map[22] = new Mapping("R R B R G ", "U");

		map[23] = new Mapping("R R B G R ", "V");
		map[24] = new Mapping("R R B G B ", "W");
		map[25] = new Mapping("R R B G G ", "X");

		map[26] = new Mapping("R R B B R ", "Y");
		map[27] = new Mapping("R R B B B ", "Z");

	}

	public String mapTo(String key) throws MappingNotFoundException {

		for (int i = 0; i < map.length; i++) {
			if (key.equals(map[i].getKey())) {
				return map[i].getMessage();
			}
		}

		throw new MappingNotFoundException();
	}

	public String stringFilter(String message) throws MappingNotFoundException {
		System.out.println("Message:" + message);	

		
		Scanner scanner = new Scanner(message);
		int length = message.length();
		String finalMessage = "";
		String temp = scanner.next() + " " + scanner.next() + " "
				+ scanner.next() + " ";
		length = length - 9;
		if (!mapTo(temp).equals("start_codon")) {
			throw new RuntimeException("No start codon");
		}
		
		while(length > 9){
			
		temp = scanner.next() + " " + scanner.next() + " " + scanner.next()
					+ " " + scanner.next() + " " + scanner.next() + " ";
			finalMessage+= mapTo(temp);
			length = length - 10;
		}
		
		
		temp = scanner.next() + " " + scanner.next() + " "
				+ scanner.next() + " ";
		if (!mapTo(temp).equals("start_codon")) {
			throw new RuntimeException("No stop codon");
		}
		
		return finalMessage;
	}

	public String reverseMapTo(String message) throws MappingNotFoundException {
		for (int i = 0; i < map.length; i++) {
			if (message.equals(map[i].getMessage())) {
				return map[i].getKey();
			}
		}
		throw new MappingNotFoundException();
	}

	public void addMapping(String key, String mapTo) {
		map[currentPos] = new Mapping(key, mapTo);
	}

	public boolean isFull() {
		return currentPos == map.length - 1;
	}
}
