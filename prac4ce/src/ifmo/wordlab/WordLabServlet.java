package ifmo.wordlab;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class WordLabServlet extends HttpServlet {

	private static int fillQuestionsList(ArrayList<Question> qList){
		Question currQestion = null;
		int num = 0;
		if (qList != null)
		{
			currQestion = new Question("Какой же цвет в космосе?","чёрный");
			qList.add(currQestion);
			num++;

			currQestion = new Question("В каком-же году родился Пушкин?",1799);
			qList.add(currQestion);
			num++;

		} else
			System.out.println("Null QuesionsList pointer");
		return num;
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
	
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		
		ArrayList<Question> questList = new ArrayList<Question>();
		int qnum = fillQuestionsList(questList);

		String answer = req.getParameter("answer");
				
		if (answer==null){
			// Give question
			
			int currQnum = (int)Math.round((qnum-1)*Math.random());
			resp.addCookie(new Cookie("questionID",((Integer)currQnum).toString()));
			resp.getWriter().println("<p>"
					+ questList.get(currQnum).questDesc
					+ "</p>\n"
					+ "<form>\n"
					+ "<input type=\"text\" class=\"NumericInput\" id=\"WordAnswer\" placeholder=\"Ответ\">\n"
					+ "<input type=\"button\" id=\"WordAnswerBtn\" value=\"Показать\">\n"
					+ "</form>");
		} else {
			// Check answer
			int currQnum = -1;
			Cookie[] reqCookies = req.getCookies();
			if (reqCookies == null){
				resp.getWriter().println("Error: invalid question ID!");
				return;
			}
			for (int i=reqCookies.length - 1; i>=0; i--)
				if (reqCookies[i].getName().equals("questionID"))
					try{
						currQnum = Integer.parseInt(reqCookies[i].getValue());
					} catch (NumberFormatException e){
						resp.getWriter().println("Error: invalid question ID!");
						return;
					}
				if ((currQnum < 0)&&(currQnum < qnum)){
					resp.getWriter().println("Error: invalid question ID!");
					return;
				}
				
				try {
					questList.get(currQnum).checkAnswer(answer);
					resp.getWriter().println("Таки да");
				} catch (NumberFormatException e){
					resp.getWriter().println("Неверный формат ответа");
				} catch (QuestWrongAnsException e){
					resp.getWriter().println("Ответ неверен");
				}
		}
	}
}
