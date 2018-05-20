package pl.Code;

import java.io.*;
import java.util.*;

import pl.RBTree.RBT;

public class Main {

	RBT drzewo = new RBT();
	
	
	public void odczyt(String plik, RBT drzewo) throws IOException {

		File file = new File(plik + ".txt");

		FileReader fr = new FileReader(file);
		Scanner scan = new Scanner(fr);
		
		String str[];
		int i = 1;
		while (scan.hasNextLine()) {
			str = scan.nextLine().split(" |\\,|\\.|\\-|\\!");
			for (int j = 0; j < str.length; j++) {
				
				drzewo.add(str[j], i);
			}
			i++;
		}

		fr.close();

	}

	
	

	public static void main(String[] args) throws IOException {
		
		RBT d = new RBT();
		Main m = new Main();
		
		m.odczyt("test1", d);
		System.out.println(d.toString());

	}

}
