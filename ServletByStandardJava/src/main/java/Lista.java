import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by wojtek on 2017-06-13.
 */
public class Lista extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<h1> Lista wyników: </h1>");


        Enumeration ListWithValues = request.getParameterNames();
        Enumeration headerNames = request.getHeaderNames();
        out.println("<h3>" + "header names" + "----" + "null" + "</h3>");


        while (ListWithValues.hasMoreElements()) {
            String paramName = (String) ListWithValues.nextElement();
            String[] paramValue = request.getParameterValues(paramName);
            out.println("<h3>" + paramName + "----" + paramValue[0] + "</h3>");

        }


        if (request.getParameterValues("maths") == null)

        {
            out.println("<h3>" + "Chechbox is unchecked" + "</h3>");

        } else

        {
            out.println("<h3>" + "Chechbox is checked" + "</h3>");
        }
        while (headerNames.hasMoreElements()) {
            String paramName = (String) headerNames.nextElement();
            String header = request.getHeader(paramName);
            out.println("<h3>" + paramName + "----" + header + "</h3>");

        }


    }

    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
