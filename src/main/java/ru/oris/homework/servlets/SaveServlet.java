package ru.oris.homework.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/save")
public class SaveServlet extends HttpServlet {

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = "D8a4U_8mU";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Oris_HW";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int countFromDb = 1;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int currentUsers = resultSet.getInt(1);
                if (currentUsers > 0) {
                    countFromDb = currentUsers;
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("count", countFromDb);
        request.getRequestDispatcher("/jsp/save.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] names = request.getParameterValues("name");
        String[] surnames = request.getParameterValues("surname");
        String[] ages = request.getParameterValues("age");
        String[] cities = request.getParameterValues("city");
        String[] countries = request.getParameterValues("country");
        String[] emails = request.getParameterValues("email");

        if (names != null) {
            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String sql = "INSERT INTO users (name, surname, age, city, country, email) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                for (int i = 0; i < names.length; i++) {
                    preparedStatement.setString(1, names[i]);
                    preparedStatement.setString(2, surnames[i]);
                    preparedStatement.setInt(3, Integer.parseInt(ages[i]));
                    preparedStatement.setString(4, cities[i]);
                    preparedStatement.setString(5, countries[i]);
                    preparedStatement.setString(6, emails[i]);
                    preparedStatement.executeUpdate();
                }
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        response.sendRedirect("/html/success.html");
    }
}
