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
	public int lineCount = 0;
	public int columns = 6;
	public Database(String txtName) throws IOException{
		fileName = txtName + ".txt";
		file = new File(filePath + fileName);
		if (file.exists()){
			openStream();
			lineCount = getLineCount();
		} else {
			closeStream();
			System.out.println("Datu baze neeksiste!");
			return;
		}
		if (checkFormat() == false){
			closeStream();
			System.out.println("Datu baze neatbilst formatam vai ir tuksa!");
			streaming = false;
			return;
		}
	}
	
	public void addRecord(String newRecord) throws IOException{
		String []record = newRecord.split("\\s");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		BufferedReader br = new BufferedReader(new FileReader(file));
		record[0] = record[0].substring(0, 1).toUpperCase() + record[0].substring(1, 2)
		+ record[0].substring(2, 3).toUpperCase() + record[0].substring(3);
		record[1] = record[1].substring(0, 1).toUpperCase() + record[1].substring(1);
		bw.newLine();
		bw.append(record[0] + " " + record[1] + " Ja - - -");
		bw.close();
		lineCount++;
	}
	public String readText() throws IOException{
		String text = "", add = " ";
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((add = br.readLine()) != null){
			text = text + add + "$";
		}
		if (text.equals("")){
			br.close();
			return "The file is empty!";
		}
		br.close();
		return text;
	}
	public void sortTextArr(String veids) throws IOException{
		String []vec = getText(file);
		String []sakartots = null;
		Sort sr = new Sort();
		switch(veids){
			case "autors":
				sakartots = sr.quicksort(vec.clone(), 0);
				arrayToFileFormat(sakartots);
				outputTable();
				System.out.println("Tabula tikka sakartota pec autora uzvarda!");
			break;
			case "gramata":
				sakartots = sr.quicksort(vec.clone(), 1);
				arrayToFileFormat(sakartots);
				outputTable();
				System.out.println("Tabula tikka sakartota pec gramatas nosaukuma!");
			break;
			default:
				System.out.println("Kartosanas veids pec " + veids + " nepastav");
		}
	}
	public void replaceWord(int line, String prevText, String newText) throws IOException{
		File newFile = new File(filePath + "/CopyOf" + fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
		String []txt = getText(file);
		int counter = 0;
		for (String a: txt){
			String []check = a.split("\\s");
			for (int i = 0; i < check.length; i++){
				if (i == check.length-1){
					bw.write(check[i] + "^");
					break;
				}
				if (prevText.equals(check[i]) && line == counter){
					bw.write(newText + " ");
				} else {
					bw.write(check[i] + " ");
				}
			}
			bw.newLine();
			counter++;
		}
		bw.close();
		pasteFileText(newFile);
		newFile.delete();
	}
	public void arrayToFileFormat(String[] arr) throws IOException{
		File newFile = new File(filePath + "/CopyOf" + fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
		for (String a: arr){
			bw.write(a + "^");
			bw.newLine();
		}
		bw.close();
		pasteFileText(newFile);
		newFile.delete();
	}
	public void pasteFileText(File prevFile) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		String []text = getText(prevFile);
		for (String a: text){
			String check[] = a.split("\\s"); 
			for (int i = 0; i < check.length; i++){
				if ((check[i].charAt(check[i].length()-1)) == '^'){
					for (int j = 0; j < check[i].length() -1;j++) {
						bw.write(check[i].charAt(j));
					}
					bw.newLine();
					break;
				}
				bw.write(check[i] + " ");
			}
		}
		bw.close();

	}
	public String[] getText(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		if (lineCount <= 0){
			br.close();
			return null;
		}
		String []vec = new String[lineCount];
		for (int i = 0; i < vec.length; i++){
			vec[i] = br.readLine();
		}
		br.close();
		return vec;
	}
	public String[][] getSplitText() throws IOException{
		String []vec = getText(file);
		String []text = getText(file); 
		String [][]matrix = new String[lineCount][columns]; 
		for (int i = 0; i < matrix.length; i++){
			String split = text[i];
			String []splited = split.split("\\s");
			for (int j = 0; j < matrix[i].length; j++){
				matrix[i][j] = splited[j];
			}
		}
		return matrix;
	}
	public void outputTable() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		System.out.println("|ID    |AUTORS          |NOSAUKUMS                      |UZ_VIETAS |LASITAJA_KODS |GRAMATAS_NEMEJS     |ATGRIESANAS_DATUMS|");
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		String add = "";
		int inc = 1;
		while((add = br.readLine()) != null){
			String []vec = add.split("\\s");
			System.out.printf("|%-5d |%-15s |%-30s |%-9s |%-13s |%-19s |%-17s |\n",
				inc, vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
			inc++;
		}
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		br.close();
	}
	public boolean checkFormat() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String add = "";
		while ((add = br.readLine()) != null){
			String []vec = add.split("\\s");
			if (vec.length != columns){
				System.out.println("EEEE");
				br.close();
				return false;
			}
		}
		br.close();
		return true;
	}
	public int getLineCount() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		int count = 0;
		while(br.readLine() != null){
			count++;
		}
		br.close();
		return count;
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
	public void outputArr(String []arr){
		for (String a: arr){
			System.out.println(a);
		}
		System.out.println();
	}
}