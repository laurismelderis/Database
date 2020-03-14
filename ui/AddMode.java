package ui;

import java.io.BufferedReader;
import java.io.IOException;

import database.Database;

public class AddMode {
	Database db = null;
	BufferedReader br = null;
	public AddMode(Database db, BufferedReader br){
		this.db = db;
		this.br = br;
	}
	public void run() throws IOException{
		if (db == null){
			System.out.println("Datubaze nav atverta, lai atvertu to, ievadiet ciparu 8 !");
			return;
		}
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
			record += param.trim().replace(' ', '_');
			run = false;
		}
		db.addRecord(record);
		System.out.println("Gramata - " + record.replaceAll("_"," ") + " - tika pievienota datubazei");
	}
}