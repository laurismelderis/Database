package src;

import java.util.Scanner;

import database.*;

public class Main {
	public static void main(String []args) throws Exception{
		Scanner sc = new Scanner(System.in);
		String txtFileName = "database.txt";
		Database db = new Database();
		db.init(txtFileName);
		String []vardi = db.getText();
		// System.out.println(db.readText(db.file));
		db.deleteText("id");
	}
	public static void output(String []arr){
		for (String a: arr){
			System.out.print(a);
		}
		System.out.println();
	}
}