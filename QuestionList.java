import java.util.Random;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionList {
	
	private ArrayList<Question> questList;		//menyimpan banyaknya pertanyaan
	private int size; 										//ukuran list

	public QuestionList() {
		this.size = 0;
		this.questList = new ArrayList<>(); 	//langsung dibuat maksimalnya 10
		addQuestion("Txt/questions.txt");
	}
	
	private void addQuestion(String fileName) { 
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			this.size = Integer.parseInt(reader.readLine());
			for (int i = 0; i < this.size; i++) {
				String quest = reader.readLine();
				String answer = reader.readLine();
				this.questList.add(new Question(quest, answer));
			}
		}	
		catch (IOException | NumberFormatException e) {
			System.err.println("error reading questions from file "+fileName);
			e.printStackTrace();
		}
	}
	
	public Question getRandomQuestion() {
		Random rand = new Random();	
		int i = rand.nextInt(20);
		
		return questList.get(i);
	}
}
