package database;

import java.util.Random;

public class Sort {
	public String[] quicksort(String[] a, int offset){
		int N = a.length;
		String prev = null;
		int median = N/2;
		int left[] = new int[median];
		int right[] = new int[median];
		left[0] = 0;
		right[0] = N - 1;
		int stackpos = 1;
		do {
			stackpos--;
			int I = left[stackpos];
			int r = right[stackpos];
			do {
			int rand = randomNum(I, r);
			String m = a[rand];
			int i = I;
			int j = r;
				do {
					while (a[i].charAt(wordIndex(a[i], offset)) < m.charAt(wordIndex(m, offset))){
						i++;
					}
					while (a[j].charAt(wordIndex(a[j], offset)) > m.charAt(wordIndex(m, offset))){
						j--;
					}
					if (i <= j) {
						prev = a[i];
						a[i] = a[j];
						a[j] = prev;
						i++;
						j--;
					}
				} while(i <= j);
				if (i < r){
					left[stackpos] = i;
					right[stackpos] = r;
					stackpos++;
				}
				r = j;
			} while(I < r);
		} while(stackpos > 0);
		return a;
	}

	public int wordIndex(String line, int elNr){
		if (elNr == 0){
			return 2;
		}
		int idx = 1;
		String []vec = line.split("\\s");
		for(int i = 1; i < line.length(); i++){
			if ((line.charAt(i) == vec[elNr].charAt(0)) && (line.charAt(idx-1) == ' ')){
				break;
			}
			idx++;
		}
		return idx;
	}
	public int randomNum(int minvalue, int maxvalue){
		Random rand = new Random();
		return rand.nextInt((maxvalue+1)-minvalue)+minvalue;
	}
}