package ifmo.prac4ce;

import java.io.IOException;

import javax.servlet.http.*;

import java.util.*;




@SuppressWarnings("serial")
public class ListBudget extends HttpServlet {

	private int prevRNGState = 0;

	class ListRecord{
		public int cost;
		public int namelen;
		public String desc;

		public String getDesc(long totalSum){
			String result = "<b>" + this.desc +" (" +
			                        this.cost*100L/totalSum + "%)</b></br>" +
			                        this.cost + "&#8381";
			return result;
		}

		public String getHint(long totalSum){
			String result = " title=\""+ this.desc+"("+
					this.cost*100L/totalSum+"%): "+
					this.cost +"&#8381\"";
			return result;
		}

		public String getColorClass(long totalSum){
			if (prevRNGState < 0)
				prevRNGState = 0;
			int currentNumber = (int)(Math.abs(Math.cos(Math.PI*this.cost/totalSum))*1000000)%10;

			// Preventing color repeat
			if (currentNumber == prevRNGState / 10)
				currentNumber++;
			if (currentNumber == prevRNGState % 10 )
				currentNumber++;
			prevRNGState = prevRNGState % 10 * 10 + currentNumber % 10;

			String result = " class=\"BGcolor"+ currentNumber%10 +"\"";
			return result;
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");


		// Read cells and calculate summary amount
		int cellCount = -1;
		long totalSum = 0;
		ArrayList<ListRecord> workingRows = new ArrayList<ListRecord>();
		try{
			cellCount = Integer.parseInt(req.getParameter("Count"));

			if ((cellCount >= 100)||(cellCount < 0)){
				resp.getWriter().println("Too many rows to create table ;(");
				return;
			}

			for (int i = cellCount; i >= 0; i--){
				ListRecord listItem = new ListRecord();
				listItem.cost = Integer.parseInt(req.getParameter("Sum"+i));
				if (listItem.cost < 0){
					resp.getWriter().println("Negative sums not allowed");
					return;
				}
				totalSum += listItem.cost;

				listItem.desc = (req.getParameter("Desc"+i));
				// Check text to prevent injection
				listItem.desc = listItem.desc.replace('<', '_');
				listItem.desc = listItem.desc.replace('>', '_');
				listItem.namelen = listItem.desc.length() + 1;
				if (!workingRows.add(listItem)){
					resp.getWriter().println("Error in memory allocation");;
					return;
				}
			}
			if (totalSum == 0)
				totalSum = 1;
		}
		catch(NumberFormatException e){
			resp.getWriter().println("Incorrect input value(s)");
			return;
		}


		// Sort cells
		// Java 8
		//workingRows.sort((a, b) -> Integer.compare(a.cost, b.cost));
		// Don't working with AppEngine
		//workingRows.sort(
		//	new Comparator<ListRecord>() {
		//		@Override
		//		public int compare(ListRecord a, ListRecord b) {
		//			return Long.compare(1L*a.cost*a.namelen, 1L*b.cost*b.namelen);
		//		}
		//	}
		//);
		Collections.sort(workingRows,
			new Comparator<ListRecord>() {
				@Override
				public int compare(ListRecord a, ListRecord b) {
					long aw = 1L * a.cost * a.namelen;
					long bw = 1L * b.cost * b.namelen;
					return aw > bw ? 1 : aw == bw ? 0 : -1;
				}
			}
		);


		// Generate <table> content
		int tableDim = (cellCount + 3)/ 2;
		int tableIndex = cellCount;
		float restHorLen = 100;
		float restVerLen = 100;
		float tempValue = 0;
		// Color unduplicator init
		prevRNGState = 0;
		// Echo <table> content
		resp.getWriter().println("<table id=\"BudgeTable\"><tbody>");
		for (int row = tableDim; row > 0; row--){
			resp.getWriter().println("<tr>");
			if ((row < tableDim)&&(row > 1)){
				// Calculating occupied space
				tempValue = 1.0f*workingRows.get(tableIndex).cost*100/totalSum*100/restVerLen;
				restHorLen -= tempValue;

				resp.getWriter().print("<td rowspan=\""+row+"\" width=\""+(int)tempValue+"%\"");
				// Check to output text or hint
				if ((tempValue > 10)&&(restVerLen > 20))
					resp.getWriter().println(workingRows.get(tableIndex  ).getColorClass(totalSum) + ">" +
					                         workingRows.get(tableIndex--).getDesc(totalSum));
				else
					resp.getWriter().println(workingRows.get(tableIndex  ).getHint(totalSum) +
					                         workingRows.get(tableIndex--).getColorClass(totalSum) + ">");
				resp.getWriter().println("</td>");
			}
			if (row > 1){
				// Calculating occupied space
				tempValue = 1.0f*workingRows.get(tableIndex).cost*100/totalSum*100/restHorLen;
				restVerLen -= tempValue;

				resp.getWriter().print("<td colspan=\""+row+"\" height=\""+(int)tempValue+"%\"");
				// Check to output text or hint
				if ((restHorLen > 10)&&(tempValue > 20))
					resp.getWriter().println(workingRows.get(tableIndex  ).getColorClass(totalSum) + ">" +
					                         workingRows.get(tableIndex--).getDesc(totalSum));
				else
					resp.getWriter().println(workingRows.get(tableIndex  ).getHint(totalSum) +
					                         workingRows.get(tableIndex--).getColorClass(totalSum) + ">");
				resp.getWriter().println("</td>");
			}
			if (row == 1)
				if ((cellCount > 0)&&(cellCount % 2 == 0)){
					// Calculating occupied space
					tempValue = 1.0f*workingRows.get(tableIndex).cost*100/totalSum*100/restVerLen;
					restHorLen -= tempValue;

					resp.getWriter().print("<td width=\""+(int)tempValue+"%\"");
					// Check to output text or hint
					if ((tempValue > 10)&&(restVerLen > 20))
						resp.getWriter().println(workingRows.get(tableIndex  ).getColorClass(totalSum) + ">" +
						                         workingRows.get(tableIndex--).getDesc(totalSum));
					else
						resp.getWriter().println(workingRows.get(tableIndex  ).getHint(totalSum) +
						                         workingRows.get(tableIndex--).getColorClass(totalSum) + ">");
					resp.getWriter().println("</td>");


					// Last row - auto completed
					resp.getWriter().print("<td");
					// Check to output text or hint
					if ((restHorLen > 10)&&(restVerLen > 20))
						resp.getWriter().println(workingRows.get(tableIndex  ).getColorClass(totalSum) + ">" +
						                         workingRows.get(tableIndex--).getDesc(totalSum));
					else
						resp.getWriter().println(workingRows.get(tableIndex  ).getHint(totalSum) +
						                         workingRows.get(tableIndex--).getColorClass(totalSum) + ">");
					resp.getWriter().println("</td>");

				} else{
					// Last row - auto completed
					resp.getWriter().print("<td colspan=\"2\"");
					// Check to output text or hint
					if ((restHorLen > 10)&&(restVerLen > 20))
						resp.getWriter().println(workingRows.get(tableIndex  ).getColorClass(totalSum) + ">" +
						                         workingRows.get(tableIndex--).getDesc(totalSum));
					else
						resp.getWriter().println(workingRows.get(tableIndex  ).getHint(totalSum) +
						                         workingRows.get(tableIndex--).getColorClass(totalSum) + ">");
					resp.getWriter().println("</td>");

				};
			resp.getWriter().println("</tr>");
		}
		resp.getWriter().println("</tbody></table>");
	}
}