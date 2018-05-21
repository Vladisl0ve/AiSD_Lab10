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

	public void menu() throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Wybierz co chcesz:\n 1. Odczyt z plika w drzewo\n 2. Pokaz skorowidz\n "
				+ "3. Usunienie elementa drzewa\n 4. Przechodzenie po drzewu\n " + "0. Koniec");

		int choise = scan.nextInt();

		switch (choise) {
		case 1:
			odczyt("aforyzm", drzewo);
			System.out.println("Plik jest zapisany do drzewa");
			menu();
			break;

		case 2:
			System.out.println(drzewo.toString());
			menu();
			break;
		case 3:
			System.out.println("Jakie element chcesz usunac?");
			Comparable obj = scan.next();
			drzewo.remove(obj);
			System.out.println("Gotowe");
			menu();
			break;
		case 4:
			drzewo.preOrder();
			drzewo.postOrder();
			drzewo.przejscieWszerz();
			System.out.println("Gotowe");
			menu();
			break;
		case 0:
			System.out.println("Koniec programu...");
			break;

		default:
			System.out.println("Nie ma takiego zagadnienia, sprobuj ponownie");
			menu();
			break;
		}
	}

	public static void main(String[] args) throws IOException {

		Main m = new Main();
		m.menu();

	}

}
