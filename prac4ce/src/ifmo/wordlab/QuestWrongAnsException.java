package ifmo.wordlab;

public class QuestWrongAnsException extends Exception{
	private static final long serialVersionUID = 1L;

	public QuestWrongAnsException(){
		super("Неверный ответ");
	}
}
