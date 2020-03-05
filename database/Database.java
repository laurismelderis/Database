package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileSystems;

public class Database{
	private BufferedReader br = null;
	public File file = null;
	public boolean streaming = false;
	public String filePath = "textFiles/";
	public String fileName = "";
	public Database(String txtName) throws IOException{
		fileName = txtName;
		file = new File(filePath + fileName);
		if (file.exists()){
			openStream();
		} else {
			closeStream();
			System.out.println("Datu baze neeksiste!");
			return;
		}
		if (checkText() == false){
			closeStream();
			System.out.println("Datu baze neatbilst formatam vai ir tuksa!");
			return;
		}
	}
	
	public String readText() throws IOException{
		String text = "", add = " ";
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((add = br.readLine()) != null){
			text = text + add + "$";
		}
		if (text.equals("")){
			return "The file is empty!";
		}
		return text;
	}
	public void writeText(String text) throws IOException{
		FileWriter fw = new FileWriter(file);
		fw.write(text);
		fw.flush();
	}
	public void deleteText(String text) throws IOException{
		File newFile = new File(filePath + "/CopyOf" + fileName);
		FileWriter fw = new FileWriter(newFile);
		String []words = getText(readText());
		for (String a: words){
			System.out.println("-"+a+"-");
			if (a != text){
				fw.write(a);
				continue;
			}
			System.out.println("found");
		}
		fw.flush();
		System.out.println("Done");
	}
	public String[] getText(String text){
		String[] words = text.split("\\s");
		return words;
	}
	public void openStream(){
		try{
			br = new BufferedReader(new FileReader(file));
			streaming = true;
		} catch (Exception e){
			streaming = false;
			System.out.println("File open stream error");
		}
	}
	public void closeStream() throws IOException{
		if (br != null){
			br.close();
		}
		streaming = false;
	}
	public void outputTable() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		System.out.println("|ID    |AUTORS          |NOSAUKUMS                      |UZ_VIETAS |LASITAJA_KODS |GRAMATAS_NEMEJS     |ATGRIESANAS_DATUMS|");
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		String add = "";
		while((add = br.readLine()) != null){
			String []vec = add.split("\\s");
			System.out.printf("|%-5s |%-15s |%-30s |%-9s |%-13s |%-19s |%-17s |\n",
				vec[0], vec[1], vec[2], vec[3], vec[4], vec[5], vec[6]);
		}
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		br.close();
	}
	public boolean checkText() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String add = br.readLine();
		String []vec = add.split("\\s");
		if (vec.length == 7){
			return true;
		}
		return false;
	}
	public void output(String []arr){
		for (String a: arr){
			System.out.print(a);
		}
		System.out.println();
	}
}