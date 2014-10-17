package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Studyroom.DAOs.RoomstatusService;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/Roomstatus.api" })
public class RoomstatusApi extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	public enum ACTION {
		doChangeStatus, // 改變狀態。不論原本是什麼狀態 open->close 或 close->open
		doOpen, //
		doClose;//
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RoomstatusApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void AccessFilter(HttpServletRequest request) throws AccessException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			ACTION action = ACTION.valueOf(request.getParameter("action"));
			switch (action) {
			case doChangeStatus:
				Date date = Date.valueOf(request.getParameter("date"));
				new RoomstatusService().doChangeStatus(date);
				return;
			case doClose:
				date = Date.valueOf(request.getParameter("date"));
				new RoomstatusService().doOpen(date);
				break;
			case doOpen:
				date = Date.valueOf(request.getParameter("date"));
				new RoomstatusService().doClose(date);
				break;
			default:
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(e);
		}
	}

}
