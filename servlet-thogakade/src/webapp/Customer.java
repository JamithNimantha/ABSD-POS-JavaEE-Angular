package webapp;

import com.google.gson.Gson;
import webapp.common.RestCheck;
import webapp.dto.CustomerDTO;
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
import java.util.ArrayList;

@WebServlet(name = "Customer")
public class Customer extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        if (dataSource==null) {
            dataSource = (DataSource) getServletContext().getAttribute("dbPool");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            CustomerDTO dto = gson.fromJson(sb.toString(), CustomerDTO.class);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO T_CUSTOMER VALUES (?,?,?,?)");
            statement.setString(1,dto.getId());
            statement.setString(2,dto.getName());
            statement.setString(3,dto.getAddress());
            statement.setDouble(4,dto.getSalary());

            int rst = statement.executeUpdate();
            RestCheck.checkResult(gson, writer, rst);

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
            statement = connection.prepareStatement("SELECT * FROM T_CUSTOMER");

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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        PrintWriter writer = resp.getWriter();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            StringBuilder sb = new StringBuilder();
            String sw;
            while ((sw = req.getReader().readLine()) != null) {
                sb.append(sw);
            }
            CustomerDTO dto = gson.fromJson(sb.toString(), CustomerDTO.class);
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE T_CUSTOMER SET NAME=?,ADDRESS=?,SALARY=? WHERE ID=?");
            statement.setString(1,dto.getName());
            statement.setString(2,dto.getAddress());
            statement.setDouble(3,dto.getSalary());
            statement.setString(4,dto.getId());


            int rst = statement.executeUpdate();
            RestCheck.checkResult(gson, writer, rst);


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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM T_CUSTOMER WHERE ID=?");
            statement.setString(1,id);

            int rst = statement.executeUpdate();
            if (rst==1){
                resp.getWriter().write(String.valueOf(true));
            }else {
                resp.getWriter().write(String.valueOf(false));
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
}
