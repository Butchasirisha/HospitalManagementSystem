package hospitalmanagementsystem;

import java.sql.*;
import java.util.Scanner;

public class hospitalmanagementsystem {
   private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final  String user="root";
    private static final String password="pass1234";

    public static void main(String[] args) {
        try
        {
           Class.forName("com.mysql.cj.jdbc.driver") ;
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url,user,password);
            Patient p=new Patient(connection,sc);
            Doctor d=new Doctor(connection);
            while(true)
            {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patients");
                System.out.println("3.View Doctors");
                System.out.println("4.Book Appointment");System.out.println("5.Exit");
                System.out.println("Enter Your Choice");
                int choice=sc.nextInt();
                switch(choice)
                {
                    case 1:
                        p.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        p.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        d.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        BookAppointment(p,d,connection,sc);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice!!");

                }



            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public static void BookAppointment(Patient p,Doctor d,Connection connection,Scanner scanner){
        System.out.println("enter patient id");
        int pid= scanner.nextInt();
        System.out.println("enter doctor id");
        int did=scanner.nextInt();
        System.out.println("enter appointment date");
        String app= scanner.next();
        if(p.getPatientById(pid) && d.getDoctorsById(did))
        {
            if(checkAvailability(did,app,connection))
            {
                String query="insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
                try{
                    PreparedStatement ps=connection.prepareStatement(query);
                    ps.setInt(1,pid);
                    ps.setInt(2,did);
                    ps.setString(3,app);
                    int rowsaffected=ps.executeUpdate();
                    if(rowsaffected>0)
                    {
                        System.out.println("Appointment booked");
                    }
                    else {
                        System.out.println("failed to book appointment");
                    }

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("doctor not available");
            }
        }
        else {
            System.out.println("person or doctor does not exist");
        }
    }
    public static boolean checkAvailability(int did,String app,Connection connection){
        String query="select count(*) from appointments where doctor_id=? appointment_date=?";
        try{
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,did);
            ps.setString(2,app);
            ResultSet res=ps.executeQuery();
            if(res.next())
            {
                int count=res.getInt(1);
                if(count==0){
                    return true;
                }
                else {
                    return false;
                }
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
