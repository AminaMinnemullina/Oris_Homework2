package ru.oris.homework.servlets;

import ru.oris.homework.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/console")
public class ConsoleServlet extends HttpServlet {

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
        System.out.println("Результаты:");

        System.out.println("Поиск по городу Казань:");
        List<User> usersByCity = findByCity("Kazan");
        usersByCity.forEach(System.out::println);

        System.out.println("Поиск по стране Россия:");
        List<User> usersByCountry = findByCountry("Russia");
        usersByCountry.forEach(System.out::println);

        System.out.println("Поиск по почте:");
        List<User> usersByEmail = findByEmail("test@mail.ru");
        usersByEmail.forEach(System.out::println);

        PrintWriter writer = response.getWriter();
        writer.println("Done");
    }

    public List<User> findByCity(String city) {
        return findWhere("city", city);
    }

    public List<User> findByCountry(String country) {
        return findWhere("country", country);
    }

    public List<User> findByEmail(String email) {
        return findWhere("email", email);
    }

    private List<User> findWhere(String columnName, String value) {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE " + columnName + " = ?");
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .age(resultSet.getInt("age"))
                        .city(resultSet.getString("city"))
                        .country(resultSet.getString("country"))
                        .email(resultSet.getString("email"))
                        .build());
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}