package kms.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kms.dao.subjectDAO;
import kms.model.subject;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ListSubjectController
 */
@WebServlet("/ListSubjectController")
public class ListSubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListSubjectController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("login.jsp?msg=sessionExpired");
            return;
        }

        String role = (String) session.getAttribute("role");

        // Fetch all subjects
        List<subject> subjectList = subjectDAO.getAllSubjects();
        request.setAttribute("subjectList", subjectList);
        
        String targetPage = "";

        if ("admin".equalsIgnoreCase(role)) {
            targetPage = "listSubject.jsp";
        } else if ("parent".equalsIgnoreCase(role)) {
            targetPage = "listSubjectParent.jsp";
        } else if ("teacher".equalsIgnoreCase(role)) {
            targetPage = "listSubjectTeacher.jsp";
        } else {
            response.sendRedirect("login.jsp?msg=unauthorized");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
        dispatcher.forward(request, response);
    }

}
