package per.map.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.map.serviceimpl.mapServiceImpl;

@WebServlet(name="getsite",urlPatterns="/action/coordinate")
public class mapAction extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7028961484560867554L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		mapServiceImpl impl = new mapServiceImpl();
		String json = impl.getMapCoordinate();
		resp.getWriter().println(json);
	}
}
