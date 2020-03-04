package src;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

import database.*;

public class Main {
	public static void main(String []args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String txtFileName = "";
		Scanner sc = new Scanner(System.in);
		Database db = new Database();
		int entered = -1;
		boolean exit = false;
		System.out.println("Lauris Melderis 14 191RDB078");
		System.out.println("\nINFORMATIVA SISTEMA BIBLIOTEKAI\n");
		System.out.println("PIEEJAMAS DATUBAZES:\n" + db.fileList);
		System.out.print("Izvelieties datubazi (piem. database): ");
		try {
			txtFileName = br.readLine();
		} catch (IOException e){
			System.out.println("input-output error");
			return;
		}
		db.init(txtFileName + ".txt");
		if (!db.file.exists()){
			System.out.println("Datubaze neeksiste");
			return;
		}
		System.out.println("DATUBAZE - " + txtFileName + ".txt - TIKKA ATVERTA");
		db.outputTable();
		do {
			System.out.println("\nIzvelieties attiecigo numuru, lai izpilditu aprakstito komandu:");
			mainMenu();
			System.out.print("Ievade: ");
			if (sc.hasNextInt()){
				entered = sc.nextInt();
			} else {
				System.out.println("input-output error");
				return;
			}
			switch(entered){
				case 1:
					db.outputTable();
				break;
				case 2:
				break;
				case 3:
				break;
				case 4:
				break;
				case 5:
				break;
				case 6:
				break;
				case 7:
					System.out.println("Pieejamas datubazes:\n" + db.fileList);
					System.out.print("Datubazes nosaukums: ");
					try {
						txtFileName = br.readLine();
					} catch (IOException e){
						System.out.println("input-output error");
					}
					File check = new File(db.filePath + txtFileName);
					if (check.exists()){
						db.closeStream();
						db.init(txtFileName + ".txt");
						db.outputTable();
					} else {
						System.out.println("Datubaze neeksiste!");
					}
					
				break;
				case 0:
					System.out.println("Visu labu!");
					exit = true;
				break;
			}
		} while(exit == false);
	}
	public static void output(String []arr){
		for (String a: arr){
			System.out.print(a);
		}
		System.out.println();
	}
	public static void mainMenu(){
		System.out.println();
		System.out.println(" 1 --> Apskatit datubazi");
		System.out.println(" 2 --> Dzest vienumus");
		System.out.println(" 3 --> Rediget vienumus");
		System.out.println(" 4 --> Kartot vienumus");
		System.out.println(" 5 --> Meklet vienumus");
		System.out.println(" 6 --> Pieejamais gramatu skaits");
		System.out.println(" 7 --> Atvert jaunu datubazi");
		System.out.println(" 0 --> Iziet no programmas");
		System.out.println();
	}
}