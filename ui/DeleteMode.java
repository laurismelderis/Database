package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import database.Database;

public class DeleteMode{
	Database db = null;
	BufferedReader br = null;
	public DeleteMode(Database db, BufferedReader br){
		this.db = db;
		this.br = br;
	}
	public void run() throws IOException{
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
			return;
		}
		String lineText = null;
		int line = 0;
		System.out.println("Ja velaties atcelt dzesanu ievadiet 0!");
		System.out.print("Ievadiet datubazes tabulas rindinas ciparu (1 - "+(db.lineCount)+"), lai izdzestu to: ");
		try{
			lineText = br.readLine();
			line = Integer.parseInt(lineText);
		} catch(Exception e) {
			System.out.println("Ievadiet skaitli!");
			return;
		}
		if (line < 0 || line > db.lineCount){
			System.out.println("Ievadiet pieejamo skaitli!");
			return;
		} else if (line == 0){
			return;
		}
		db.outputLine(line);
		System.out.print("Vai jus velaties izdzest " + line + " rindinu no datubazes (y/n)?: ");
		String check = "no";
		check = br.readLine();
		if (check.equals("yes") || check.equals("y") || check.equals("Y") || check.equals("Yes")){
			System.out.println("Gramata "+ db.getLineWord(line-1, 1) + " ar autoru " + db.getLineWord(line-1, 0) + " tikka dzesta!");
			db.deleteLine(line-1);
		} 
	}
}