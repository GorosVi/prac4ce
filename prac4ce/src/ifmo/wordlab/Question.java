package ifmo.wordlab;

public class Question{
	public String questDesc = null;
	public Integer questNumAns = null;
	public String questStrAns = null;
	boolean checkAnswer(String ans) throws NumberFormatException, QuestWrongAnsException{
		if (questNumAns != null){
				int ansNum = Integer.parseInt(ans);
				if (ansNum != questNumAns)
					throw new QuestWrongAnsException();
		}
		if (questStrAns != null){
			if (!ans.equals(questStrAns))
				throw new QuestWrongAnsException();
		}
		return true;
	}
	Question(String description, String answer){
		questDesc = new String(description);
		questStrAns = new String(answer);
	}
	Question(String description, Integer answer){
		questDesc = new String(description);
		questNumAns = new Integer(answer);
	}
}
