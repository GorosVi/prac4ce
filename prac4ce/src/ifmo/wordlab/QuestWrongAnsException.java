package ifmo.wordlab;

public class QuestWrongAnsException extends Exception{
	public QuestWrongAnsException(){
		super("Неверный ответ");
	}
}
