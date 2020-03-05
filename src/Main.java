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
		Database db = null;
		Scanner sc = new Scanner(System.in);
		int entered = -1;
		boolean exit = false;
		System.out.println("Lauris Melderis 14 191RDB078");
		System.out.println("\nINFORMATIVA SISTEMA BIBLIOTEKAI\n");
		System.out.println("PIEEJAMAS DATUBAZES:\n" + fileList());
		db = listFilesAndOpenDatabase(txtFileName, br, db);
		
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
				break;
				case 8:
					db = openNewDatabase(br, txtFileName, db);
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
		System.out.println(" 2 --> Pievienot jaunu vienumu");
		System.out.println(" 3 --> Dzest vienumus");
		System.out.println(" 4 --> Rediget vienumus");
		System.out.println(" 5 --> Kartot vienumus"); //Kartot pec autora un gr. nos.
		System.out.println(" 6 --> Meklet vienumus pec autora");
		System.out.println(" 7 --> Pieejamais gramatu skaits");
		System.out.println(" 8 --> Atvert jaunu datubazi");
		System.out.println(" 0 --> Iziet no programmas");
		System.out.println();
	}
	public static Database listFilesAndOpenDatabase(String txtFileName, BufferedReader br, Database db) throws IOException{
		System.out.print("Izvelieties datubazi (piem. database): ");
		txtFileName = br.readLine();
		db = new Database(txtFileName + ".txt");
		if (db.streaming == true){
			System.out.println("DATUBAZE - " + txtFileName + ".txt - TIKKA ATVERTA");
			db.outputTable();
			return db;
		}
		return null;
	}
	public static String fileList(){
		String list = "";
		File []files = new File("textFiles/").listFiles();
		for (int i = 0; i < files.length;i++){
			if(files[i].isFile()){
				list += files[i].getName() + " ";
			}
		}
		return list;
	}
	public static Database openNewDatabase(BufferedReader br, String txtFileName, Database db)throws IOException{
		System.out.println("Pieejamas datubazes:\n" + fileList());
		System.out.print("Datubazes nosaukums: ");
		txtFileName = br.readLine();
		File check = new File("textFiles/" + txtFileName + ".txt");
		if (check.exists()){
			if (db != null){
				db.closeStream();
			}
			System.out.println("DATUBAZE - " + txtFileName + ".txt - TIKKA ATVERTA");
			db = new Database(txtFileName + ".txt");
			db.outputTable();
			return db;
		} else {
			System.out.println("Datubaze neeksiste!");
			return null;
		}		
	}
}