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
		if (!(br.readLine().trim().isEmpty())){
			bw.newLine();
			bw.append(record[0] + " " + record[1] + " Ja - nav --/--/----");
		} else {
			bw.append(record[0] + " " + record[1] + " Ja - nav --/--/----"); 
		}
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
	public void deleteLine(int line) throws IOException{
		File newFile = new File(filePath + "/CopyOf" + fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
		String []txt = getText(file);
		for (int i = 0; i < txt.length-1; i++){
			if (i == line){
				continue;
			} else if (i == txt.length-2){
				bw.write(txt[i].trim());
				break;
			} else {
				bw.write(txt[i].trim() + "^");
				bw.newLine();
			}
		}
		bw.close();
		lineCount--;
		pasteFileText(newFile);
		newFile.delete();
	}
	public void replaceLineWord(int line, String prevText, String newText) throws IOException{
		File newFile = new File(filePath + "/CopyOf" + fileName);
		BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
		String []txt = getText(file);
		int counter = 0;
		boolean taken = false;
		for (String a: txt){
			String []check = a.split("\\s");
			for (int i = 0; i < check.length; i++){
				if (i == check.length-1 && 
					prevText.equals(check[i]) && line == counter && taken == false){
					bw.write(newText + "^");
					taken = true;
					break;
				} else if (i == check.length-1){
					bw.write(check[i] + "^");
					break;
				}
				if (prevText.equals(check[i]) && line == counter && taken == false){
					bw.write(newText + " ");
					taken = true;
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
	public String getLineWord(int line, int column) throws IOException{
		String [][]matrix = getSplitText(file);
		return matrix[column][line];
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
	public String[][] getSplitText(File file) throws IOException{
		String []vec = getText(file);
		String []text = getText(file); 
		String [][]matrix = new String[columns][lineCount];
		for (int i = 0; i < lineCount; i++){
			String split = text[i];
			String []splited = split.split("\\s");
			for (int j = 0; j < columns; j++){
				matrix[j][i] = splited[j];
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
			if (add.isEmpty()){
				continue;
			}
			String []vec = add.split("\\s");
			System.out.printf("|%-5d |%-15s |%-30s |%-9s |%-13s |%-19s |%-17s |\n",
				inc, vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
			inc++;
		}
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		br.close();
	}
	public void outputLine(int line) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		System.out.println("|ID    |AUTORS          |NOSAUKUMS                      |UZ_VIETAS |LASITAJA_KODS |GRAMATAS_NEMEJS     |ATGRIESANAS_DATUMS|");
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		String add = "";
		int inc = 1;
		while((add = br.readLine()) != null){
			if (add.isEmpty()){
				continue;
			}
			String []vec = add.split("\\s");
			if (inc == line){
				System.out.printf("|%-5d |%-15s |%-30s |%-9s |%-13s |%-19s |%-17s |\n",
					inc, vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
			}
			inc++;
		}
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		br.close();
	}
	public void outputLines(int []lines) throws IOException{
		if (lines == null){
			System.out.println("Nav datu ko attelot");
			return;
		}
		BufferedReader br = new BufferedReader(new FileReader(file));
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		System.out.println("|ID    |AUTORS          |NOSAUKUMS                      |UZ_VIETAS |LASITAJA_KODS |GRAMATAS_NEMEJS     |ATGRIESANAS_DATUMS|");
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		String add = "";
		int inc = 1, i = 0;
		while((add = br.readLine()) != null){
			if (add.isEmpty()){
				continue;
			}
			String []vec = add.split("\\s");
			if (i < lines.length && inc == lines[i]){
				System.out.printf("|%-5d |%-15s |%-30s |%-9s |%-13s |%-19s |%-17s |\n",
					inc, vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
				i++;
			}
			inc++;
		}
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		br.close();
	}
	public void getAvailableBooks() throws IOException{
		String [][]matrix = getSplitText(file);
		int counter = 0;
		for (int i = 0; i < matrix[2].length; i++){
			if(matrix[2][i].equals("Ja")){
				counter++;
			}
		}
		System.out.println("Paslaik ir pieejamas " + counter + " gramatas:");
		BufferedReader br = new BufferedReader(new FileReader(file));
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		System.out.println("|ID    |AUTORS          |NOSAUKUMS                      |UZ_VIETAS |LASITAJA_KODS |GRAMATAS_NEMEJS     |ATGRIESANAS_DATUMS|");
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		String add = "";
		int searchedLines[] = getSearchedColumnLines("Ja", 2);
		counter = 0;
		while((add = br.readLine()) != null){
			String []vec = add.split("\\s");
			if (vec[2].equals("Ja")){
				System.out.printf("|%-5d |%-15s |%-30s |%-9s |%-13s |%-19s |%-17s |\n",
					searchedLines[counter], vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
				counter++;
			}
		}
		System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
		br.close();

	}
	public int[] getSearchedColumnLines(String name, int column) throws IOException{
		int lines[] = null;
		int count = 0, mainCount = 0;
		char[] letters = name.toCharArray();
		for(int i = 0; i < lineCount; i++){
			String word = getLineWord(i, column);
			for (int j = 0; j < letters.length; j++){
				if (word.charAt(j) == letters[j]){
					count++;
				}
			}
			if (count == letters.length){
				mainCount++;
			}
			count = 0;
		}
		if (mainCount == 0){
			return null;
		}
		lines = new int[mainCount];
		mainCount = 0;

		for(int i = 0; i < lineCount; i++){
			String word = getLineWord(i, column);
			for (int j = 0; j < letters.length; j++){
				if (word.charAt(j) == letters[j]){
					count++;
				}
			}
			if (count == letters.length){
				lines[mainCount] = i+1;
				mainCount++;
			}
			count = 0;
		}
		return lines;
	}
	public boolean checkAvailableBook(int id) throws IOException{
		String [][]matrix = getSplitText(file);
		if(matrix[2][id].equals("Ja")){
			return true;
		}
		return false;
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