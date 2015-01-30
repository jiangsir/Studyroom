package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.jiangsir.Utils.DAOs.UpfileDAO;
import tw.jiangsir.Utils.Objects.Upfile;

/**
 * Servlet implementation class ShowUpfilesServlet
 */
@WebServlet(urlPatterns = { "/DownloadUpfile" })
public class DownloadUpfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	int id = Integer.parseInt(request.getParameter("id"));
	Upfile upfile = new UpfileDAO().getUpfileById(id);
	ServletOutputStream out = null;
	try {
	    out = response.getOutputStream();
	    String filename = new String(
		    upfile.getFilename().getBytes("UTF-8"), "ISO8859_1");
	    response.setHeader("Content-Type", upfile.getFiletype());
	    response.setContentType(upfile.getFiletype());
	    // response.setHeader("Content-Disposition",
	    // "attachment; filename=\""
	    // + filename + "\"");
	    response.setHeader("Content-Disposition", "filename=\"" + filename
		    + "\"");
	    out.write(upfile.getBytes());
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		out.flush();
		out.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
    }

}
