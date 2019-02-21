import java.util.Random;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//INNER CLASS
	public class Question {
		
		private String quest; 	//menyimpan pertanyaan
		private String ans; 		//menyimpan jawaban	
		
		public Question(String quest, String ans) {
			this.quest = quest;
			this.ans = ans;
		}
		
		//getter atribut
		public String getStringQuestion() {
			return this.quest;
		}
		
		public String getAnsQuestion() {
			return this.ans;
		}

	}
