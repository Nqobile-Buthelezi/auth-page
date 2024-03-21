package za.co.bangoma.auth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import java.sql.Connection;


public class UserController {

    public static void deleteAllUserData() {
        try (Connection connection = Database.getConnection()) {
            String deleteSQL = "DELETE FROM user_accounts";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("All data deleted successfully, " + rowsDeleted + " rows deleted.");
                } else {
                    System.out.println("There is no data to delete in the user_accounts table.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new BadRequestResponse("Failed to execute the SQL command.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BadRequestResponse("Could not access the database to delete user data.");
        }
    }

    public static void create(@NotNull Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String email = ctx.formParam("email");

        // Basic validation
        if (username == null || password == null || email == null ||
            username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            throw new BadRequestResponse("Missing username, password, or email");
        }

        // Check if user_accounts table exists if not create it
        Database.createUserTable();

        try (Connection conn = Database.getConnection()) {
            String insertSQL = "INSERT INTO user_accounts (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
            throw new BadRequestResponse("Failed to create user");
        }

        System.out.println("Signed up successfully!");
        ctx.status(201); // Created
        ctx.redirect("/");
    }

    public static User loginDatabase(String username, String password) {
        User user = null;
        String query = "SELECT * FROM user_accounts WHERE username=? AND password=?";
        
        try (Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                user = new User(username, password, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }

    public static void authenticate(@NotNull Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new BadRequestResponse("Username or password is missing.");
        }

        // Check if user_accounts table exists if not create it
        Database.createUserTable();
        
        User user = loginDatabase(username, password);
        
        if (user != null) {
            // Authentication successful
            System.out.println("Logged in successfully!");
            ctx.status(200); // Created
            ctx.redirect("/");
        } else {
            // Authentication failed
            throw new BadRequestResponse("Invalid username or password.");
        }
    }

}
