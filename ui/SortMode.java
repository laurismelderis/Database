package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import database.Database;

public class SortMode{
	Database db = null;
	BufferedReader br = null;
	public SortMode(Database db, BufferedReader br){
		this.db = db;
		this.br = br;
	}
	public void run() throws IOException{
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
}