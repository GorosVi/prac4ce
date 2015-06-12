package ifmo.opendata;

import ifmo.opendata.items.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class DataWorkerServlet extends HttpServlet{
	private ifmo.opendata.items.Container dataContainer = null;
	
	private void printRestriction(Restriction cRestr, HttpServletResponse resp, int columns) throws IOException{
		resp.getWriter().println("<tr>");
		if (columns > 0) 
			resp.getWriter().println("<td>"+cRestr.getRestrType().getName()+"</td>");
		if (columns > 7) 
			resp.getWriter().println("<td>"+cRestr.getDisposalNum()+"</td>");
		if (columns > 1) 
			resp.getWriter().println("<td><a onclick=\"openDataUpdate(\'wiev=restriction&param="+cRestr.getCode()+"\')\" href=\"#files\">"
					+ cRestr.getAddress()+"</a></td>");
		if (columns > 2) 
			resp.getWriter().println("<td><a onclick=\"openDataUpdate(\'wiev=district&param="+cRestr.getDistrict().getName()+"\')\" href=\"#files\">"
			                         + cRestr.getDistrict().getName() +"</a></td>");
		if (columns > 3){
			Date cDate = cRestr.getDateStart();
			SimpleDateFormat restDF = new SimpleDateFormat("dd.MM.yyyy");
			resp.getWriter().println("<td><a onclick=\"openDataUpdate(\'select=date&param="+restDF.format(cDate)+"\')\" href=\"#files\">"
					+ restDF.format(cDate)+"</a></td>");
		}
		if (columns > 4){
			Date cDate = cRestr.getDateEnd();
			SimpleDateFormat restDF = new SimpleDateFormat("dd.MM.yyyy");
			resp.getWriter().println("<td><a onclick=\"openDataUpdate(\'select=date&param="+restDF.format(cDate)+"\')\" href=\"#files\">"
					+ restDF.format(cDate)+"</a></td>");
		}
		if (columns > 5 && cRestr.getContractor()!=null&& cRestr.getContractor().getName()!=null) 
			resp.getWriter().println("<td><a onclick=\"openDataUpdate(\'wiev=company&param="+cRestr.getContractor().getINN()+"\')\" href=\"#files\">"
					+ cRestr.getContractor().getName() +"</a></td>");
		if (columns > 6 && cRestr.getConsumer()!=null&& cRestr.getConsumer().getName()!=null) 
			resp.getWriter().println("<td><a onclick=\"openDataUpdate(\'wiev=company&param="+cRestr.getConsumer().getINN()+"\')\" href=\"#files\">"
				+ cRestr.getConsumer().getName() +"</a></td>");
		if (columns > 8) 
				resp.getWriter().println("<td>"+cRestr.getCode() +"</td>");
			resp.getWriter().println("</tr>");
		}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");	
		
		if (dataContainer == null){
			dataContainer = new ifmo.opendata.items.Container("data/data.csv");
		}
		// SELECT command
		String selBy = req.getParameter("select");		
		if (selBy != null){
			if (selBy.equals("address")){
				String param = req.getParameter("param");
				if (param != null){
					resp.getWriter().println("<h3>Активные объекты по адресу "+param+":</h3>");
					resp.getWriter().println("<table><tbody><tr><td>Тип&nbsp;ограничения</td><td>Адрес&nbsp;производства&nbsp;работ</td>"
							+ "    <td>Район</td><td>Дата начала работ</td><td>Дата окончания работ</td><td>Подрядчик</td></tr>");
					ArrayList<Restriction> restList = dataContainer.getRestrictionList();
					for (int i = 0; i < restList.size(); i++)
						if (restList.get(i).getAddress().contains(param))
							printRestriction(restList.get(i), resp, 6);
				}
			}
			if (selBy.equals("date")){
				String param = req.getParameter("param");
				if (param != null){
					SimpleDateFormat restDF = new SimpleDateFormat("dd.MM.yyyy");
					try{
						Date restDate= restDF.parse(param);
						
						resp.getWriter().println("<h3>Активные объекты на дату "+restDF.format(restDate)+":</h3>");
						resp.getWriter().println("<table><tbody><tr><td>Тип&nbsp;ограничения</td><td>Адрес&nbsp;производства&nbsp;работ</td>"
								+ "    <td>Район</td><td>Дата начала работ</td><td>Дата окончания работ</td><td>Подрядчик</td></tr>");
						ArrayList<Restriction> restList = dataContainer.getRestrictionList();
						for (int i = 0; i < restList.size(); i++)
							if (restList.get(i).getDateStart().compareTo(restDate)<=0 && restList.get(i).getDateEnd().compareTo(restDate)>=0)
								printRestriction(restList.get(i), resp, 6);
					} catch (Exception e){
						resp.getWriter().println("Ошибка: некорректный формат даты.");
					}
				}
			}
			if (selBy.equals("district")){
				String param = req.getParameter("param");
				if (param != null){
					int index = dataContainer.findDistrict(param);
					if (index >= 0){
						District wrkDistr = dataContainer.getDistrictList().get(index);
						resp.getWriter().println("<h3>Район: "+wrkDistr.getName()+"</h3>");
						resp.getWriter().println("<p>Информация об оганичениях:</p> <table><tbody>"
								+ "<tr><td>Тип&nbsp;ограничения</td><td>Адрес&nbsp;производства&nbsp;работ</td>"
								+ "    <td>Район</td><td>Дата начала работ</td><td>Дата окончания работ</td><td>Подрядчик</td></tr>");
						for (int i = 0; i < wrkDistr.getRestList().size(); i++)
							printRestriction(wrkDistr.getRestList().get(i), resp, 6);
						resp.getWriter().println("</tbody></table>");
					} else
						resp.getWriter().println("Ошибка: района нет в списке.");
				}
			}
			if (selBy.equals("company")){
				String param = req.getParameter("param");
				if (param != null){
					int index;
					try{
						long lparam = Long.parseLong(param);
						index = dataContainer.findCompany(lparam);
					} catch (Exception e){
						resp.getWriter().println("Error: invalid number format.");
						return;
					}
					if (index >= 0){
						Company wrkCom= dataContainer.getCompanyList().get(index);
						resp.getWriter().println("<h3>Компания: "+wrkCom.getName()+"</h3>");
						resp.getWriter().println("<h3>Как подрядчик</h3>");
						resp.getWriter().println("<table><tbody>"
								+ "<tr><td>Тип&nbsp;ограничения</td><td>Адрес&nbsp;производства&nbsp;работ</td>"
								+ "    <td>Район</td><td>Дата начала работ</td><td>Дата окончания работ</td><td>Подрядчик</td>"
								+ "    <td>Заказчик</td></tr>");
						for (int i = 0; i < wrkCom.getAsContractorList().size(); i++)
							printRestriction(wrkCom.getAsContractorList().get(i), resp, 7);
						resp.getWriter().println("</tbody></table>");
						resp.getWriter().println("<h3>Как заказчик</h3>");
						resp.getWriter().println("<table><tbody>"
								+ "<tr><td>Тип&nbsp;ограничения</td><td>Адрес&nbsp;производства&nbsp;работ</td>"
								+ "    <td>Район</td><td>Дата начала работ</td><td>Дата окончания работ</td><td>Подрядчик</td>"
								+ "    <td>Заказчик</td></tr>");
						for (int i = 0; i < wrkCom.getAsCustomerList().size(); i++)
							printRestriction(wrkCom.getAsCustomerList().get(i), resp, 7);
						resp.getWriter().println("</tbody></table>");
					} else
						resp.getWriter().println("Ошибка: компании нет в списке.");
				}
			}
		}

		// WIEV command
		String wievBy = req.getParameter("wiev");		
		if (wievBy != null){
			if (wievBy.equals("company")){
				String param = req.getParameter("param");
				if (param != null){
					int index;
					try{
						long lparam = Long.parseLong(param);
						index = dataContainer.findCompany(lparam);
					} catch (Exception e){
						resp.getWriter().println("Error: invalid number format.");
						return;
					}
					if (index >= 0){
						Company wrkCom= dataContainer.getCompanyList().get(index);
						resp.getWriter().println("<h3>Компания: "+wrkCom.getName()+"</h3>");
						resp.getWriter().println("ИНН: "+wrkCom.getINN()+"<br>");
						resp.getWriter().println("Всего объектов: "+(wrkCom.getAsContractorList().size()+wrkCom.getAsCustomerList().size())+"<br>");
						resp.getWriter().println("<a  onclick=\"openDataUpdate(\'select=company&param="+wrkCom.getINN()+"\')\" href=\"#files\">"
								+ "Список объектов </a>");
					} else
						resp.getWriter().println("Ошибка: компании нет в списке.");
				}
			}
			if (wievBy.equals("district")){
				String param = req.getParameter("param");
				if (param != null){
					int index = dataContainer.findDistrict(param);
					if (index >= 0){
						District wrkDistr = dataContainer.getDistrictList().get(index);

						resp.getWriter().println("<h3>Район: "+wrkDistr.getName()+"</h3>");
						resp.getWriter().println("Всего объектов: "+ wrkDistr.getRestList().size() +"<br>");
						resp.getWriter().println("<a  onclick=\"openDataUpdate(\'select=district&param="+wrkDistr.getName()+"\')\" href=\"#files\">"
								+ "Список объектов </a>");
					} else
						resp.getWriter().println("Ошибка: района нет в списке.");
				}
			}
			if (wievBy.equals("restriction")){
				String param = req.getParameter("param");
				if (param != null){
					int index;
					try{
						int iparam = Integer.parseInt(param);
						index = dataContainer.findRestriction(iparam);
					} catch (Exception e){
						resp.getWriter().println("Error: invalid number format.");
						return;
					}
					if (index >= 0){
						resp.getWriter().println("<h3>Информация об ограничении</h3> <table><tbody>"
								+ "<tr><td>Тип&nbsp;ограничения</td><td>Номер распоряжения</td><td>Адрес&nbsp;производства&nbsp;работ</td>"
								+ "    <td>Район</td><td>Дата начала работ</td><td>Дата окончания работ</td><td>Подрядчик</td>"
								+ "    <td>Заказчик</td><td>Код ограничения</td></tr>");
						Restriction wrkRestriction= dataContainer.getRestrictionList().get(index);
						printRestriction(wrkRestriction, resp, 9);
						resp.getWriter().println("</tbody></table>");
					} else
						resp.getWriter().println("Ошибка: ограничения нет в списке.");
				}
			}
		}
		
		// LIST command
		String listBy = req.getParameter("list");		
		if (listBy != null){
			if (listBy.equals("company")){
				ArrayList<Company> comList = dataContainer.getCompanyList();
				for (int i = 0; i < comList.size(); i++)
					resp.getWriter().println("<a  onclick=\"openDataUpdate(\'wiev=company&param="
							+comList.get(i).getINN() +"\')\" href=\"#files\">"+ comList.get(i).getName() +
							"</a><br>");
			}
			if (listBy.equals("district")){
				ArrayList<District> distList = dataContainer.getDistrictList();
				for (int i = 0; i < distList.size(); i++)
					resp.getWriter().println("<a  onclick=\"openDataUpdate(\'wiev=district&param="
							+distList.get(i).getName() +"\')\" href=\"#files\">"+ distList.get(i).getName() +
							"</a><br>");
			}
			if (listBy.equals("restriction")){
			ArrayList<Restriction> restList = dataContainer.getRestrictionList();
				resp.getWriter().println("<table><tbody><tr><td>Тип&nbsp;ограничения</td><td>Адрес&nbsp;производства&nbsp;работ</td>"
						+ "    <td>Район</td><td>Дата начала работ</td><td>Дата окончания работ</td><td>Подрядчик</td></tr>");
				for (int i = 0; i < restList.size(); i++)
					printRestriction(restList.get(i), resp, 6);
				resp.getWriter().println("</tbody></table>");
			}
		}
		
		// Logging
		if (req.getParameter("log") != null)
			resp.getWriter().println(dataContainer.getLog().replaceAll("\n", "<br>"));
	}

}