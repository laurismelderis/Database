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
	private File file = null;
	public Boolean streaming = false;
	public String filePath = "textFiles/";
	public String fileList = "";
	public String fileName = "";
	public Database(){
		fileList();
	}
	public void init(String txtName){
		fileName = txtName;
		file = new File(filePath + fileName);
		openStream(txtName);
	}
	public void fileList(){
		String list = "";
		File []files = new File(filePath).listFiles();
		for (int i = 0; i < files.length;i++){
			if(files[i].isFile()){
				list += files[i].getName() + " ";
			}
		}
		fileList = list;
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
	public void writeText(String text) throws Exception{
		FileWriter fw = new FileWriter(file);
		fw.write(text);
		fw.flush();
	}
	public void deleteText(String text) throws Exception{
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
	public void openStream(String txtName){
		try{
			this.br = new BufferedReader(new FileReader(file));
			streaming = true;
		} catch (Exception e){
			streaming = false;
			System.out.println("File open stream error");
		}
		System.out.println("Stream started");
	}
	public void closeStream() throws Exception{
		if (this.br != null){
			this.br.close();
		}
	}
}