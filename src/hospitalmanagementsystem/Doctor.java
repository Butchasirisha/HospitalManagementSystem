package hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
   private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors() {
        String query = "Select * from doctors";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("DOCTORS");
            System.out.println("|ID  |NAME                    |SPECIALIZATION  | ");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                System.out.printf("|%-4s|%-24s|%-16s|\n",id,name,specialization);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean getDoctorsById(int id) {
        String query = "select * from doctors where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
