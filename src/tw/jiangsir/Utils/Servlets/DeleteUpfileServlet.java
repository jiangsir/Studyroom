package tw.jiangsir.Utils.Servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.DAOs.UpfileDAO;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.Upfile;
import tw.jiangsir.Utils.Objects.User;

/**
 * Servlet implementation class FileUpload
 */
@MultipartConfig
@WebServlet(urlPatterns = { "/DeleteUpfile.do" })
@RoleSetting(allowHigherThen = User.ROLE.ADMIN)
public class DeleteUpfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			new UpfileDAO().delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
		response.sendRedirect(ShowUpfilesServlet.class.getAnnotation(
				WebServlet.class).urlPatterns()[0]);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Part part = request.getPart("photo");
		String header = part.getHeader("Content-Disposition");
		String filename = header.substring(header.indexOf("filename=\"") + 10,
				header.lastIndexOf("\""));
		InputStream in = part.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len;
		byte[] buffer = new byte[1024];
		while ((len = in.read(buffer, 0, buffer.length)) != -1) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.flush();
		out.close();

		Upfile newupfile = new Upfile();
		newupfile.setFilename(filename);
		newupfile.setFiletype(part.getContentType());
		newupfile.setBytes(out.toByteArray());
		try {
			new UpfileDAO().insert(newupfile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()
				+ ShowUpfilesServlet.class.getAnnotation(WebServlet.class)
						.urlPatterns()[0]);
	}
}
