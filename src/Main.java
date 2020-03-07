package src;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

import database.*;

public class Main {
	public static void main(String []args) throws Exception{
		Database db = new Database("database");
		editRecord(db);
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// String txtFileName = "";
		// Database db = null;
		// boolean exit = false;
		// System.out.println("Lauris Melderis 14 191RDB078");
		// System.out.println("\nINFORMATIVA SISTEMA BIBLIOTEKAI\n");
		// System.out.println("PIEEJAMAS DATUBAZES:\n" + fileList());
		// db = listFilesAndOpenDatabase(txtFileName, br, db);
		// do {
		// 	System.out.println("\nIzvelieties attiecigo numuru, lai izpilditu aprakstito komandu:");
		// 	mainMenu();
		// 	int entered = enterNumber();
		// 	switch(entered){
		// 		case 1:
		// 			openDatabaseTable(db);
		// 		break;
		// 		case 2:
		// 			addRecord(db);
		// 		break;
		// 		case 3:
		// 			deleteLine(db);
		// 		break;
		// 		case 4:
		// 			editRecord(db);
		// 		break;
		// 		case 5:
		// 			sort(db);
		// 		break;
		// 		case 6:
		// 		break;
		// 		case 7:
		// 			bookCount(db);
		// 		break;
		// 		case 8:
		// 			db = openNewDatabase(br, txtFileName, db);
		// 		break;
		// 		case 9:
		// 			db = openNewDatabase(br, txtFileName, db);
		// 		break;
		// 		case 0:
		// 			exit = exitProgram();
		// 		break;
		// 	}
		// } while(exit == false);
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
		System.out.println(" 4 --> Rediget vienumus--");
		System.out.println(" 5 --> Kartot vienumus"); //Kartot pec autora un gr. nos.
		System.out.println(" 6 --> Meklet vienumus pec autora---");
		System.out.println(" 7 --> Pieejamais gramatu skaits");
		System.out.println(" 8 --> Atvert jaunu datubazi");
		System.out.println(" 9 --> Iznemt gramatu---");
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
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
			return;
		} 
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
	
	//3
	public static void deleteLine(Database db) throws IOException{
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String lineText = null;
		int line = 0;
		System.out.print("Ievadiet datubazes tabulas rindinas ciparu (1 - "+(db.lineCount)+"), lai izdzestu to: ");
		try{
			lineText = br.readLine();
			line = Integer.parseInt(lineText);
		} catch(Exception e) {
			System.out.println("Ievadiet skaitli!");
			return;
		}
		if (line <= 0 || line > db.lineCount){
			System.out.println("Ievadiet pieejamo skaitli!");
			return;
		}
		System.out.print("Vai jus velaties izdzest " + line + " rindinu no datubazes (y/n)?: ");
		String check = "no";
		check = br.readLine();
		if (check.equals("yes") || check.equals("y") || check.equals("Y") || check.equals("Yes")){
			db.deleteLine(line-1);
		}
	}
	
	//4
	public static void editRecord(Database db) throws IOException{
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
			return;
		}
		boolean run = true;
		int line = editRecordEntry(db);
		int column = 0;
		while (run){ 
			if (line == -1){
				return;
			}
			db.outputLine(line);
			System.out.println("Ievadiet operacijas numuru attieciba uz noraditajam komandam!");
			System.out.println("\n1 --> Rediget autoru");
			System.out.println("2 --> Rediget gramatas nosaukumu");
			System.out.println("3 --> Rediget lasitaja kodu");
			System.out.println("4 --> Rediget gramatas nemeju");
			System.out.println("5 --> Rediget atgriesanas datumu");
			System.out.println("6 --> Rediget citu vienumu");
			System.out.println("0 --> Atcelt redigesanu\n");
			System.out.print("Ievade: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String ievade = null;
			try {
				ievade = br.readLine();
				column = Integer.parseInt(ievade);
			} catch (Exception e){
				System.out.println("Ievadiet skaitli!");
				continue;
			}
			if (column != 0){
				System.out.println("Ievadiet 0, ja velaties atcelt operaciju!");
			}
			switch(column){
				case 1:
					editRecordAuthor(db, line, column);
				break;
				case 2:
					editRecordBookName(db, line, column);
				break;
				case 3:
					editRecordBookTakerCode(db, line, column);
				break;
				case 4:
					editRecordBookTakerName(db, line, column);
				break;
				case 5:
					editRecordBookTakerDate(db, line, column);
				break;
				case 6:
					line = editRecordEntry(db);
				break;
				case 0:
					run = false;
				break;
				default:
					System.out.println("Ievadiet pieejamas redigesanas iespejas skaitli!");
			}
		}
	}
	public static int editRecordEntry(Database db) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String ievade = "";
		int line = 0;
		System.out.println("Ja velaties atcelt vienuma redigesanu ievadiet 0 !");
		System.out.println("Ievadiet datubazes tabulas rindinas ciparu (1 - "+(db.lineCount)+"), lai redigetu to:");
		while (true){ 
			System.out.print("Ievade: ");
			try {
				ievade = br.readLine();
				line = Integer.parseInt(ievade);
			} catch (Exception e){
				System.out.println("Ievadiet skaitli!");
				continue;
			}
			if (line == 0){
				return -1;
			} else if (line < 1 || line > db.lineCount){
				System.out.println("Ievadita rinda neeksiste!");
				continue;
			}
			break;
		}
		return line;
	}
	//4th main command options
	//
	// MAKE ONE OPTIMISED METHOD FOR editRecord() OPTIONS
	//
	//1st option
	public static void editRecordAuthor(Database db, int line, int column) throws IOException{
		System.out.println("Autora varda un uzvarda maksimalais simbolu daudzums ir 15!");
		System.out.print("Ievadiet jauno autora varda pirmo burtu un uzvardu (piem. L.Ozols): ");
		String edit = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			edit = br.readLine();
		} catch(Exception e){
			System.out.println("Nepareiza ievade");
			return;
		}
		if (edit.equals("0")){
			return;
		} else if (edit.length() < 3){
			System.out.println("Autora vards un uzvards ir mazs! (Min - 3)");
			return;
		} else if (edit.length() > 15){
			System.out.println("Autora vards un uzvards ir parak gars! (Max - 15)");
			return;
		} else if (isNumeric(edit)){
			System.out.println("Autora vards un uzvards nedrikst but skaitli");
			return;
		}else if (edit.charAt(1) != '.'){
			System.out.println("Jus izmantojat nepareizo formatu. Otraja pozicija obligati jabut punktam!");
			return;
		} 
		String prev = db.getLineWord(line-1, column-1);
		edit = edit = edit.substring(0, 1).toUpperCase() + edit.substring(1, 2)
			+ edit.substring(2, 3).toUpperCase() + edit.substring(3);
		db.replaceLineWord(line-1, prev, edit);
		System.out.println("Autors ir nomainits no " + prev + " uz " + edit +"!");
		return;
	}
	//2nd option
	public static void editRecordBookName(Database db, int line, int column) throws IOException{
		System.out.println("Gramatas nosaukuma maksimalais simbolu daudzums ir 30!");
		System.out.print("Ievadiet jauno autora varda pirmo burtu un uzvardu (piem. Svina garsa): ");
		String edit = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			edit = br.readLine();
		} catch(Exception e){
			System.out.println("Nepareiza ievade");
			return;
		}
		if (edit.equals("0")){
			return;
		} else if (edit.length() < 1){
			System.out.println("Gramatas nosaukums ir mazs! (Min - 1)");
			return;
		} else if (edit.length() > 30){
			System.out.println("Gramatas nosaukums ir parak gars! (Max - 30)");
			return;
		} else if (containsSpaces(edit) == false){
			System.out.println("Jus izmantojat nepareizo formatu un/vai vardu atdala tikai ar vienu atstarpi.");
			return;
		} 
		String prev = db.getLineWord(line-1, column-1);
		edit = edit.substring(0, 1).toUpperCase() + edit.substring(1);
		db.replaceLineWord(line-1, prev, edit.trim().replaceAll(" ", "_"));
		System.out.println("Gramatas nosaukumus ir nomainits no " + prev.replaceAll("_", " ") + " uz " + edit +"!");
		return;
	}
	//3rd option
	public static void editRecordBookTakerCode(Database db, int line, int column) throws IOException{
		System.out.println("Lasitaja koda maksimalais simbolu daudzums ir 9!");
		System.out.print("Ievadiet jauno autora varda pirmo burtu un uzvardu (piem. 191RDB001): ");
		String edit = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			edit = br.readLine();
		} catch(Exception e){
			System.out.println("Nepareiza ievade");
			return;
		}
		if (edit.equals("0")){
			return;
		} else if (edit.length() != 9){
			System.out.println("Lasitaja kods sastav no 9 simboliem, nevis no " + edit.length() + "!");
			return;
		} else if (checkCodeFormat(edit) == false){
			System.out.println("Jus izmantojat nepareizo formatu!");
			return;
		} 
		String prev = db.getLineWord(line-1, column);
		edit = edit.substring(0, 3) + edit.substring(3, 6).toUpperCase() + edit.substring(6);
		db.replaceLineWord(line-1, prev, edit);
		if (prev.equals("-")){
			System.out.println("Lasitaja kods ir nomainits uz " + edit +"!");
		} else {
			System.out.println("Lasitaja kods ir nomainits no " + prev + " uz " + edit +"!");
		}
		if ((db.getLineWord(line-1, 3)).equals("-") == false ||
			(db.getLineWord(line-1, 4)).equals("nav") == false ||
			(db.getLineWord(line-1, 5)).equals("--/--/----") == false){
			db.replaceLineWord(line-1, "Ja", "Ne");
		} else {
			db.replaceLineWord(line-1, "Ne", "Ja");
		}
		return;
	}
	//4th option
	public static void editRecordBookTakerName(Database db, int line, int column) throws IOException{
		System.out.println("Gramatas nemeja maksimalais simbolu daudzums ir 13!");
		System.out.print("Ievadiet jauno gramatas nemeja vardu (piem. Arkadijs): ");
		String edit = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			edit = br.readLine();
		} catch(Exception e){
			System.out.println("Nepareiza ievade");
			return;
		}
		if (edit.equals("0")){
			return;
		} else if (edit.length() > 13){
			System.out.println("Gramatas nemeja varda maksimalais garums ir 13!");
			return;
		} else if (edit.length() < 2){
			System.out.println("Gramatas nemeja varda minimalais garums ir 3!");
			return;
		}else if (containsSpaces(edit) == false){
			System.out.println("Ievadiet tikai vardu! Atstarpes nedrikst ievietot");
			return;
		} 
		String prev = db.getLineWord(line-1, column);
		edit = edit.substring(0, 1).toUpperCase() + edit.substring(1);
		db.replaceLineWord(line-1, prev, edit);
		if (prev.equals("nav")){
			System.out.println("Gramatas nemeja vards ir nomainits uz " + edit +"!");
		} else {
			System.out.println("Gramatas nemeja vards ir nomainits no " + prev + " uz " + edit +"!");
		}
		if ((db.getLineWord(line-1, 3)).equals("-") == false ||
			(db.getLineWord(line-1, 4)).equals("nav") == false ||
			(db.getLineWord(line-1, 5)).equals("--/--/----") == false){
			db.replaceLineWord(line-1, "Ja", "Ne");
		} else {
			db.replaceLineWord(line-1, "Ne", "Ja");
		}
		return;
	}
	//5th option
	public static void editRecordBookTakerDate(Database db, int line, int column) throws IOException{
		System.out.println("Datuma formats ir piemeram 01/01/2020 - diena/menesis/gads!");
		System.out.print("Ievadiet jauno atgriesanas datumu (piem. 04/05/2020): ");
		String edit = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			edit = br.readLine();
		} catch(Exception e){
			System.out.println("Nepareiza ievade");
			return;
		}
		if (edit.equals("0")){
			return;
		} else if (edit.length() != 10){
			System.out.println("Datumam ir jasastav no 10 simboliem! Jus ievadijat: " + edit.length() + "!");
			return;
		} else if (checkDateFormat(edit) == false){
			System.out.println("Datums neatbilstam pareizajam formatam");
			return;
		} 
		String prev = db.getLineWord(line-1, column);
		db.replaceLineWord(line-1, prev, edit);

		if (prev.equals("--/--/----")){
			System.out.println("Atgiesanas datums ir nomainits uz " + edit +"!");
		} else {
			System.out.println("Atgiesanas datums ir nomainits no " + prev + " uz " + edit +"!");
		}
		// if ((db.getLineWord(line-1, 3)).equals("-") == false ||
		// 	(db.getLineWord(line-1, 4)).equals("nav") == false ||
		// 	(db.getLineWord(line-1, 5)).equals("--/--/----") == false){
		// 	db.replaceLineWord(line-1, "Ja", "Ne");
		// } else {
		// 	db.replaceLineWord(line-1, "Ne", "Ja");
		// }
		return;
	}

	//5
	public static void sort(Database db) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String veids = null;
		String prev = veids;
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
			return;
		} 
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
	
	//0
	public static boolean exitProgram(){
		System.out.println("Visu labu!");
		return true;
	}

	//Util methods
	public static boolean isNumeric(String text){
		char []vec = text.toCharArray();
		for (char a: vec){
			if (Character.isDigit(a)){
				return true;
			}
		}
		return false;
	}
	public static boolean containsSpaces(String text){
		char []vec = text.toCharArray();
		for (int i = 0; i < vec.length; i++){
			if(vec[i] == ' ' && vec[i] == vec[i+1]){
				return false;
			} else if (Character.isLetterOrDigit(vec[i]) == false && vec[i] != ' '){
				return false;
			}
		}
		return true;
	}
	public static boolean checkCodeFormat(String text){
		char []vec = text.toCharArray();
		if(Character.isDigit(vec[0]) && Character.isDigit(vec[1]) &&
		   Character.isDigit(vec[2]) && Character.isLetter(vec[3]) &&
		   Character.isLetter(vec[4]) && Character.isLetter(vec[5]) &&
		   Character.isDigit(vec[6]) && Character.isDigit(vec[7]) &&
		   Character.isDigit(vec[8])){
			return true;
		}
		return false;
	}
	public static boolean checkDateFormat(String text){
		char []vec = text.toCharArray();
		System.out.println();
		if(Character.isDigit(vec[0]) && Character.isDigit(vec[1]) &&
		   vec[2] == '/' && Character.isDigit(vec[3]) &&
		   Character.isDigit(vec[4]) && vec[5] == '/' &&
		   Character.isDigit(vec[6]) && Character.isDigit(vec[7]) &&
		   Character.isDigit(vec[8]) && Character.isDigit(vec[9])){
			return true;
		}
		return false;
	}
}