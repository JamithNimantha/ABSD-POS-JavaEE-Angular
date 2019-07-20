package webapp;

import com.google.gson.Gson;
import webapp.common.RestCheck;
import webapp.dto.ItemDTO;
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

@WebServlet(name = "Item")
public class Item extends HttpServlet {
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
            ItemDTO dto = gson.fromJson(sb.toString(), ItemDTO.class);
            System.out.println(sb.toString());
            System.out.println(dto);
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO T_ITEM VALUES (?,?,?,?,?)");
            statement.setString(1,dto.getCode());
            statement.setString(2,dto.getName());
            statement.setString(3,dto.getDescription());
            statement.setDouble(4,dto.getPrice());
            statement.setDouble(5,dto.getQty());

            int rst = statement.executeUpdate();
            RestCheck.checkResult(gson,writer,rst);

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
            ArrayList<ItemDTO> artrayList = new ArrayList<>();
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM T_ITEM");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ItemDTO dto = new ItemDTO();
                dto.setCode(resultSet.getString(1));
                dto.setName(resultSet.getString(2));
                dto.setDescription(resultSet.getString(3));
                dto.setPrice(resultSet.getDouble(4));
                dto.setQty(resultSet.getInt(4));
                artrayList.add(dto);
            }

            Gson gson = new Gson();
            String s = gson.toJson(artrayList);
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
            ItemDTO dto = gson.fromJson(sb.toString(), ItemDTO.class);
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE T_ITEM SET NAME=?,DESCRIPTION=?,UNIT_PRICE=?,QTY_ON_HAND=? WHERE CODE=?");
            statement.setString(1,dto.getName());
            statement.setString(2,dto.getDescription());
            statement.setDouble(3,dto.getPrice());
            statement.setDouble(4,dto.getQty());
            statement.setString(5,dto.getCode());

            int rst = statement.executeUpdate();
            RestCheck.checkResult(gson,writer,rst);

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

        String code = req.getParameter("code");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM T_ITEM WHERE CODE=?");
            statement.setString(1,code);

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
