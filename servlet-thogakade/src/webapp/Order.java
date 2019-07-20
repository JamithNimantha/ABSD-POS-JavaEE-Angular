package webapp;

import com.google.gson.Gson;
import webapp.dto.CustomerDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "Order")
public class Order extends HttpServlet {
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        if (dataSource==null) {
            dataSource = (DataSource) getServletContext().getAttribute("dbPool");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oId = request.getParameter("oId");
        String custId = request.getParameter("custId");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO T_ORDER VALUES (?,?,?)");
            statement.setString(1,oId);
            statement.setString(2,custId);
            statement.setDate(3, Date.valueOf(LocalDate.now()));

            int rst = statement.executeUpdate();


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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            ArrayList<CustomerDTO> arrayList = new ArrayList<>();
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM T_ORDER");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerDTO dto = new CustomerDTO();
                dto.setId(resultSet.getString(1));
                dto.setName(resultSet.getString(2));
                dto.setAddress(resultSet.getString(3));
                dto.setSalary(resultSet.getDouble(4));
                arrayList.add(dto);
            }

            Gson gson = new Gson();
            String s = gson.toJson(arrayList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(s);

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
}
