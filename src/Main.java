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
		boolean exit = false;
		System.out.println("Lauris Melderis 14 191RDB078");
		System.out.println("\nINFORMATIVA SISTEMA BIBLIOTEKAI\n");
		System.out.println("PIEEJAMAS DATUBAZES:\n" + fileList());
		db = listFilesAndOpenDatabase(txtFileName, br, db);
		
		do {
			System.out.println("\nIzvelieties attiecigo numuru, lai izpilditu aprakstito komandu:");
			mainMenu();
			int entered = enterNumber();
			switch(entered){
				case 1:
					openDatabaseTable(db);
				break;
				case 2:
					addRecord(db);
				break;
				case 3:
				break;
				case 4:
				break;
				case 5:
					sort(db);
				break;
				case 6:
				break;
				case 7:
				break;
				case 8:
					db = openNewDatabase(br, txtFileName, db);
				break;
				case 0:
					exit = exitProgram();
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
		db = new Database(txtFileName);
		if (db.streaming == true){
			System.out.println("DATUBAZE - " + txtFileName + " - TIKKA ATVERTA");
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
	public static int enterNumber() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Ievade: ");
		String str = br.readLine();
		int entered = -1;
		try {
			entered = Integer.parseInt(str);
		} catch (Exception e){
			System.out.println("Ievadiet skaitli nevis kadu citu simbolu!");
		}
		return entered;
	}
	//1
	public static void openDatabaseTable(Database db) throws IOException{
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
		} else {
			db.outputTable();
		}
	}
	//2
	public static void addRecord(Database db) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Ja velaties atcelt vienuma pievienosanu ievadiet 0 !");
		System.out.println("Autora varda un uzvarda maksimalais garums ir 15 simboli!");
		String record = "";
		boolean run = true;
		String param = "";
		while (run){
			System.out.print("Ievadiet autora varda pirmo burtu un uzvardu (piem. L.Ozols): ");
			param = br.readLine();
			if (param.equals("0")){
				return;
			} else if ((param.charAt(1) != '.') || (param.charAt(2) == ' ')){
				System.out.println("Jus ievadijat nepareizu formatu!");
				System.out.print("Ievadiet, piemeram, L.Talis: ");
				continue;
			} else if (param.length() > 15){
				System.out.println("Jus parsniedzat garumu! Maksimalais garums 15!");
				continue;
			} else if (param.isEmpty()){
				continue;
			} 
			record += param.trim() + " ";
			run = false;
		}
		run = true;
		System.out.println("Gramatas nosaukuma maksimalais garums ir 30 simboli!");
		while (run){
			System.out.print("Ievadiet gramatas nosaukumu (piem. Svina garsa): ");
			param = br.readLine();
			if (param.equals("0")){
				return;
			} else if (param.isEmpty()){
				continue;
			} else if (param.length() > 30){
				System.out.println("Jus parsniedzat garumu! Maksimalais garums 30!");
				continue;
			}
			record += param.trim().replace(' ', '_') + " ";
			run = false;
		}
		System.out.println(record);
		db.addRecord(record);
		System.out.println("Gramata - " + record + "- tika pievienota datubazei");
	}
	//5
	public static void sort(Database db) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String veids = null;
		String prev = veids;
		System.out.println("Kartosanas veidi: ");
		System.out.println("autors gramata");
		boolean check = false;
		System.out.print("Ievadiet kartosanas veidu: ");
		veids = br.readLine();
		switch(veids){
			case "autors":
				db.sortTextArr("autors");
			break;
			case "gramata":
				db.sortTextArr("gramata");
			break;
			default:
				System.out.println("Kartosanas veids nepastav");
		}
	}
	//8
	public static Database openNewDatabase(BufferedReader br, String txtFileName, Database db)throws IOException{
		System.out.println("Pieejamas datubazes:\n" + fileList());
		System.out.print("Datubazes nosaukums: ");
		txtFileName = br.readLine();
		File check = new File("textFiles/" + txtFileName + ".txt");
		if (check.exists()){
			if (db != null){
				db.closeStream();
			}
			db = new Database(txtFileName);
			if (db.streaming == false){
				return null;
			}
			System.out.println("DATUBAZE - " + txtFileName + " - TIKKA ATVERTA");
			db.outputTable();
			return db;
		} else {
			System.out.println("Datubaze neeksiste!");
			return null;
		}		
	}
	//0
	public static boolean exitProgram(){
		System.out.println("Visu labu!");
		return true;
	}
}