package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import database.Database;
import src.Utils;

public class SearchMode{
	Database db = null;
	BufferedReader br = null;
	public SearchMode(Database db, BufferedReader br){
		this.db = db;
		this.br = br;
	}
	public void run() throws IOException{
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
			return;
		}
		String author = "";
		System.out.println("Ja velaties atgriezties ievadiet 0!");
		System.out.println("Jus varat meklet autoru pec ta varda un/vai uzvarda");
		System.out.print("Ievade: ");
		author = br.readLine();
		if (Utils.isNumeric(author)){
			System.out.println("Nepareiza ievade! Autors nevar saturet skaitlus");
			return;
		} else if (author.isEmpty()){
			System.out.println("Jus ievadijat tuksumu!");
			return;
		}
		System.out.println("Pec ievades - " + author + " tikka atrasti vienumi: ");
		int resultLine = 0;
		author = author.substring(0,1).toUpperCase() + author.substring(1, 2) + 
		author.substring(2, 3).toUpperCase() + author.substring(3).toLowerCase();
		int resultLines[] = db.getSearchedColumnLines(author, 0);
		db.outputLines(resultLines);
	}
}