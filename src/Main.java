package src;

import java.util.Scanner;

import database.*;

public class Main {
	public static void main(String []args) throws Exception{
		Scanner sc = new Scanner(System.in);
		String txtFileName = "database3";
		Database db = new Database();
		// System.out.println(db.fileList);
		db.init(txtFileName + ".txt");
		String []vardi = db.getText(db.readText());
		System.out.println(db.readText());
		db.deleteText("Sveiki");
	}
	public static void output(String []arr){
		for (String a: arr){
			System.out.print(a);
		}
		System.out.println();
	}
}