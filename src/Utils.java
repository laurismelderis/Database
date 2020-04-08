package src;

public class Utils{
	public static boolean isNumeric(String text){
		char []vec = text.toCharArray();
		for (char a: vec){
			if (Character.isDigit(a)){
				return true;
			}
		}
		return false;
	}
	public static boolean containsSpaces(String text){
		char []vec = text.toCharArray();
		for (char a: vec){
			if (a == ' ') {
				return true;
			}
		}
		return false;
	}
	public static boolean bookNameContainsSpaces(String text){
		char []vec = text.toCharArray();
		for (int i = 0; i < vec.length; i++){
			if(vec[i] == ' ' && vec[i] == vec[i+1]){
				return true;
			} else if (Character.isLetterOrDigit(vec[i]) == false && vec[i] != ' '){
				return true;
			}
		}
		return false;
	}
	public static boolean checkCodeFormat(String text){
		char []vec = text.toCharArray();
		if(Character.isDigit(vec[0]) && Character.isDigit(vec[1]) &&
		   Character.isDigit(vec[2]) && Character.isLetter(vec[3]) &&
		   Character.isLetter(vec[4]) && Character.isLetter(vec[5]) &&
		   Character.isDigit(vec[6]) && Character.isDigit(vec[7]) &&
		   Character.isDigit(vec[8])){
			return true;
		}
		return false;
	}
	public static boolean checkDateFormat(String text){
		char []vec = text.toCharArray();
		if(Character.isDigit(vec[0]) && Character.isDigit(vec[1]) &&
		   vec[2] == '/' && Character.isDigit(vec[3]) &&
		   Character.isDigit(vec[4]) && vec[5] == '/' &&
		   Character.isDigit(vec[6]) && Character.isDigit(vec[7]) &&
		   Character.isDigit(vec[8]) && Character.isDigit(vec[9])){
			return true;
		}
		return false;
	}
}