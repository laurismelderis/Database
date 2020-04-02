package src;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

import database.Database;
import ui.*;

public class Main {
	private static int fileCount = 0;
	private static boolean failed = false;
	public static void main(String []args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String txtFileName = "";
		Database db = null;
		boolean exit = false;
		int entered = 0;
		String files = fileList();
		if (fileCount == 0){
			System.out.println("Paslaik nav neviena datubaze izveidota!");
			System.out.println("Izveidojiet jaunu!");
			createNewDatabase(br);
		}
		if (failed){
			return;
		}
		System.out.println("Lauris Melderis 14 191RDB078");
		System.out.println("\nINFORMATIVA SISTEMA BIBLIOTEKAI\n");
		System.out.println("PIEEJAMAS DATUBAZES:\n" + fileList());
		db = listFilesAndOpenDatabase(txtFileName, br, db);
		do {
			if (entered >= 0 && entered <= 8){
				System.out.println("\nIzvelieties attiecigo numuru, lai izpilditu aprakstito komandu:");
				mainMenu();
			} else {
				System.out.println("\nNepareiza ievade!");
				System.out.println("\nIzvelieties attiecigo numuru, lai izpilditu aprakstito komandu:");
				mainMenu();
			}
			entered = enterNumber(br);
			switch(entered){
				case 1:
					openDatabaseTable(db);
				break;
				case 2:
					AddMode addMode = new AddMode(db, br);
					addMode.run();
				break;
				case 3:
					DeleteMode deleteMode = new DeleteMode(db, br);
					deleteMode.run();
				break;
				case 4:
					EditMode editMode = new EditMode(db, br);
					editMode.run();
				break;
				case 5:
					SortMode sortMode = new SortMode(db, br);
					sortMode.run();
				break;
				case 6:
					SearchMode searchMode = new SearchMode(db, br);
					searchMode.run();
				break;
				case 7:
					bookCount(db);
				break;
				case 8:
					db = openNewDatabase(br, txtFileName, db);
				break;
				case 9:
					createNewDatabase(br);
				break;
				case 0:
					exit = exitProgram();
				break;
			}
		} while(exit == false);
	}
	public static void mainMenu(){
		System.out.println();
		System.out.println(" 1 --> Apskatit datubazi");
		System.out.println(" 2 --> Pievienot jaunu vienumu");
		System.out.println(" 3 --> Dzest vienumus");
		System.out.println(" 4 --> Rediget vienumus");
		System.out.println(" 5 --> Kartot vienumus");
		System.out.println(" 6 --> Meklet vienumus pec autora");
		System.out.println(" 7 --> Pieejamais gramatu skaits");
		System.out.println(" 8 --> Atvert jaunu datubazi");
		System.out.println(" 9 --> Izveidot jaunu datubazi");
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
			fileCount++;
		}
		return list;
	}
	public static int enterNumber(BufferedReader br) throws IOException{
		System.out.print("Ievade: ");
		String str = br.readLine();
		int entered = -1;
		try {
			entered = Integer.parseInt(str);
		} catch (Exception e){
			System.out.println("Ievadiet veselu skaitli!");
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
	//7
	public static void bookCount(Database db) throws IOException{
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
			return;
		}
		db.getAvailableBooks();
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

	//9
	public static void createNewDatabase(BufferedReader br)throws IOException{
		System.out.print("Jaunas datubazes nosaukums: ");
		String newFileName = br.readLine();
		if (newFileName.isEmpty()){
			System.out.println("Jus neko neievadijat!");
			failed = true;
			return;
		}
		File newFile = new File("textFiles/" + newFileName + ".txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, true));
		bw.write("AUTORS GRAMATA Ne - - -");
		bw.close();
		System.out.println("Datubaze " + newFileName + ".txt izveidota!\n");
	}
	//0
	public static boolean exitProgram(){
		System.out.println("Visu labu!");
		return true;
	}
}