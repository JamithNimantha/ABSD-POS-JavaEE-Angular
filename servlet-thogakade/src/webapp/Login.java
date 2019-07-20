package webapp;

import com.google.gson.Gson;
import webapp.dto.UserDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Login")
public class Login extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        dataSource = (DataSource) getServletContext().getAttribute("dbPool");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        PrintWriter writer = response.getWriter();
        Connection connection = null;
        PreparedStatement statement = null;



        try {
            StringBuilder sb = new StringBuilder();
            String sw;
            while ((sw = request.getReader().readLine()) != null) {
                sb.append(sw);
            }
            UserDTO userDTO = gson.fromJson(sb.toString(), UserDTO.class);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT PASSWORD FROM T_USER WHERE USER_NAME=?");
            statement.setString(1, userDTO.getUser());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String rstPass = resultSet.getString(1);
                if (userDTO.getPass().equals(rstPass)) {
                    request.getSession().setAttribute("isLogged", true);
                    String s = gson.toJson(true);
                    writer.write(s);
                } else {
                    String s = gson.toJson(false);
                    writer.write(s);
                }
            } else {
                String s = gson.toJson(false);
                writer.write(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }


}
