package ifmo.wordlab;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class WordLabServlet extends HttpServlet {

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
					+ "<form align = \"center\">\n"
					+ "<input type=\"text\" class=\"NumericInput\" id=\"WordAnswer\" placeholder=\"Ответ\">\n"
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
	
	
	private static int fillQuestionsList(ArrayList<Question> qList){
		Question currQestion = null;
		int num = 0;
		if (qList != null)
		{
			currQestion = new Question("Какой же цвет неба в космосе?","чёрный");
			qList.add(currQestion);
			num++;
			
			// (c) Гарднер
			currQestion = new Question("Пассажирский лайнер \"Клара Люксембург\" отошел от берега на 10 км и "
					+ "утонул. Капитан поплыл к берегу со скоростью 6 км/ч. Матросы поплыли к "
					+ "берегу со скоростью 2.4 км/ч, а пассажиры — со скоростью 1.5 км/ч. На сколько "
					+ "раньше, чем матросы, доберется до берега капитан, и на сколько раньше, чем "
					+ "пассажиры, доберутся до берега матросы? ", 2.5);
			qList.add(currQestion);
			num++;
			
			currQestion = new Question("За два часа работы бульдозер разогнал 256 участников "
					+ "несанкционированного митинга. Сколько участников митинга разгонит бульдозер "
					+ "за пять часов, если будет работать с прежней производительностью?", 384);
			qList.add(currQestion);
			num++;
			
			currQestion = new Question("Семья, состоящая из дедушки, бабушки, мамы, папы и четверых детей, "
					+ "получила по талонам кусок колбасы длиной в 20 сантиметров. Детям отрезали по "
					+ "4 см каждому. Остальное поровну разделили между взрослыми. По скольку "
					+ "сантиметров колбасы получил каждый взрослый?", 1);
			qList.add(currQestion);
			num++;

			currQestion = new Question("Генерал-майор с генерал-полковником распили бутылку коньяка пятилетней выдержки. "
					+ "Сколько звездочек можно было насчитать во время этого события?", 13);
			qList.add(currQestion);
			num++;
			
			currQestion = new Question("Во время охоты в заповеднике 9 секретарей обкомов партии убили "
					+ "некоторое количество лосей. Сколько лосей было убито, если известно, что "
					+ "после того, как 5 лучших лосиных рогов было послано в подарок Генеральному "
					+ "секретарю, каждый секретарь обкома получил по рогам?", 14);
			qList.add(currQestion);
			num++;

			currQestion = new Question("Злая колдунья превратилась в Белоснежку и испекла для 7 гномов "
					+ "40 пирожков с гвоздями. Три гнома отказались от угощения, а остальные "
					+ "разделили пирожки поровну и кинули их в колдунью. Половина пирожков, "
					+ "брошенных каждым гномом, попала в колдунью, а другая половина пролетела мимо"
					+ "нее. Сколько пирожков с гвоздями попало в колдунью?", 20);
			qList.add(currQestion);
			num++;

		} else
			System.out.println("Null QuesionsList pointer");
		return num;
	}
}
