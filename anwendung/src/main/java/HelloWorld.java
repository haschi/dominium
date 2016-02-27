import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebServlet(value = "/hello")
public class HelloWorld extends HttpServlet {

    @Override
    public final void service(final ServletRequest request, final ServletResponse response) throws IOException {
        response.getWriter().println("Hello World");

    }
}
