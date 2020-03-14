package ui;

import java.io.BufferedReader;
import java.io.IOException;

import database.Database;
import src.Utils;

public class EditMode{
	Database db = null;
	BufferedReader br = null;
	public EditMode(Database db, BufferedReader br){
		this.db = db;
		this.br = br;
	}
	public void run() throws IOException{
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
	public int editRecordEntry(Database db) throws IOException{
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
	public void editRecordAuthor(Database db, int line, int column) throws IOException{
		System.out.println("Autora varda un uzvarda maksimalais simbolu daudzums ir 15!");
		System.out.print("Ievadiet jauno autora varda pirmo burtu un uzvardu (piem. L.Ozols): ");
		String edit = "";
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
		} else if (Utils.isNumeric(edit)){
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
	public void editRecordBookName(Database db, int line, int column) throws IOException{
		System.out.println("Gramatas nosaukuma maksimalais simbolu daudzums ir 30!");
		System.out.print("Ievadiet jauno autora varda pirmo burtu un uzvardu (piem. Svina garsa): ");
		String edit = "";
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
		} else if (Utils.containsSpaces(edit) == false){
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
	public void editRecordBookTakerCode(Database db, int line, int column) throws IOException{
		System.out.println("Lasitaja koda maksimalais simbolu daudzums ir 9!");
		System.out.println("Ja velaties attirit so lauku, ievadiet: tukss");
		System.out.print("Ievadiet jauno autora varda pirmo burtu un uzvardu (piem. 191RDB001): ");
		String edit = "";
		try{
			edit = br.readLine();
		} catch(Exception e){
			System.out.println("Nepareiza ievade");
			return;
		}
		if (edit.equals("0")){
			return;
		} else if (edit.equals("tukss")){
			edit = "-";
		} else if (edit.length() != 9){
			System.out.println("Lasitaja kods sastav no 9 simboliem, nevis no " + edit.length() + "!");
			return;
		} else if (Utils.checkCodeFormat(edit) == false){
			System.out.println("Jus izmantojat nepareizo formatu!");
			return;
		} else {
			edit = edit.substring(0, 3) + edit.substring(3, 6).toUpperCase() + edit.substring(6);
		}
		String prev = db.getLineWord(line-1, column);
		db.replaceLineWord(line-1, prev, edit);
		if (prev.equals("-")){
			System.out.println("Lasitaja kods ir nomainits uz " + edit +"!");
		} else if (edit.equals("-")){
			System.out.println("Lasitaja kods "+prev+" ir dzests!");
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
	public void editRecordBookTakerName(Database db, int line, int column) throws IOException{
		System.out.println("Gramatas nemeja maksimalais simbolu daudzums ir 13!");
		System.out.println("Ja velaties attirit so lauku, ievadiet: tukss");
		System.out.print("Ievadiet jauno gramatas nemeja vardu (piem. Arkadijs): ");
		String edit = "";
		try{
			edit = br.readLine();
		} catch(Exception e){
			System.out.println("Nepareiza ievade");
			return;
		}
		if (edit.equals("0")){
			return;
		} else if (edit.equals("tukss")){
			edit = "nav";
		} else if (edit.length() > 13){
			System.out.println("Gramatas nemeja varda maksimalais garums ir 13!");
			return;
		} else if (edit.length() < 2){
			System.out.println("Gramatas nemeja varda minimalais garums ir 3!");
			return;
		}else if (Utils.containsSpaces(edit) == false){
			System.out.println("Ievadiet tikai vardu! Atstarpes nedrikst ievietot");
			return;
		} else {
			edit = edit.substring(0, 1).toUpperCase() + edit.substring(1);
		}
		String prev = db.getLineWord(line-1, column);
		db.replaceLineWord(line-1, prev, edit);
		if (prev.equals("nav")){
			System.out.println("Gramatas nemeja vards ir nomainits uz " + edit +"!");
		} else if (edit.equals("nav")){
			System.out.println("Gramatas nemeja vards "+prev+" ir dzests!");
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
	public void editRecordBookTakerDate(Database db, int line, int column) throws IOException{
		System.out.println("Datuma formats ir piemeram 01/01/2020 - diena/menesis/gads!");
		System.out.println("Ja velaties attirit so lauku, ievadiet: tukss");
		System.out.print("Ievadiet jauno atgriesanas datumu (piem. 04/05/2020): ");
		String edit = "";
		try{
			edit = br.readLine();
		} catch(Exception e){
			System.out.println("Nepareiza ievade");
			return;
		}
		if (edit.equals("0")){
			return;
		} else if (edit.equals("tukss")){
			edit = "--/--/----";
		} else if (edit.length() != 10){
			System.out.println("Datumam ir jasastav no 10 simboliem! Jus ievadijat: " + edit.length() + "!");
			return;
		} else if (Utils.checkDateFormat(edit) == false){
			System.out.println("Datums neatbilstam pareizajam formatam");
			return;
		} 
		String prev = db.getLineWord(line-1, column);
		db.replaceLineWord(line-1, prev, edit);

		if (prev.equals("--/--/----")){
			System.out.println("Atgiesanas datums ir nomainits uz " + edit +"!");
		} else if (edit.equals("--/--/----")){
			System.out.println("Atgriesanas datums "+prev+" ir dzests!");
		} else {
			System.out.println("Atgiesanas datums ir nomainits no " + prev + " uz " + edit +"!");
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
}