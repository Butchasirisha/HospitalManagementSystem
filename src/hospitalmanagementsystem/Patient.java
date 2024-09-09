package hospitalmanagementsystem;

import java.sql.*;
import java.util.Scanner;

public class Patient {
 private Connection connection;
  private Scanner scanner;
    public Patient(Connection connection,Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }
    public void  addPatient()
    {
        System.out.println("Enter Patient Name:");
        String name= scanner.next();
        System.out.println("Enter Patient Age:");
        int age= scanner.nextInt();
        System.out.println("Enter Patient Gender");
        String gender= scanner.next();

        try {
            String query = "INSERT INTO patients(name,age,gender) values(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,"siri");
            ps.setInt(2,20);
            ps.setString(3,"female");
            int affected_rows=ps.executeUpdate();
            if(affected_rows>0)
            {
                System.out.println("patient added successfully");
            }
            else{
                System.out.println("failed to add patient");
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public  void viewPatients() {
        String query = "Select * from patients";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("PATIENTS");
            System.out.println("|ID |NAME                    |AGE  |GENDER  | ");
            while (rs.next())
            {
                int id=rs.getInt("id");
                String name=rs.getString("name");
                int age=rs.getInt("age");
                String gender=rs.getString("gender");
                System.out.printf("|%-3s|%-24s|%-5s|%-8s|\n",id,name,age,gender);
            }
        }catch (SQLException e)
        {
          e.printStackTrace();
        }
    }
    public  Boolean getPatientById(int id)
    {
        String query="select * from patients where id=?";
        try{
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            return rs.next();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
