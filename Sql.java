import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.Connection;


public class Sql {

	private static Connection connect;

	public static String  check_if_user_exist (String  id) {
		String answer = "";
		String sqlExist = "select * from student where student.id = ?";
		try {
			PreparedStatement pst = connect.prepareStatement(sqlExist);

			pst.setString(1, id);

			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				answer += "id:  " + resultSet.getString(1) + "      name:  " + resultSet.getString(2) + "       phone:  " + resultSet.getString(3);
			}
			if (answer.isEmpty()) {
				answer = "student doesn't exist!";
			}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return answer;


		}
		public static void delete_statement(String id){

			String sqldelete = "delete from student where id = ?";

			try {
				PreparedStatement pst = connect.prepareStatement(sqldelete);

				pst.setString(1, id);

				pst.execute();


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public static void update_statement(String name, String id,String phone){

			String sqlupdate = "UPDATE student SET name=? , phone = ?  WHERE id =? ";

			try {
				PreparedStatement pst = connect.prepareStatement(sqlupdate);

				pst.setString(1, name);
				pst.setString(2, phone);
				pst.setString(3, id);
				pst.executeUpdate();


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public static void insert_statement( String id , String name , String phone){

			String sqlInsert = "insert into student (id,name, phone) values (?,?,?)";

			try {
				PreparedStatement pst = connect.prepareStatement(sqlInsert);
				pst.setString(1, id);
				pst.setString(2, name);
				pst.setString(3, phone);

				pst.execute();



			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public static double select_query()
		{
			String str="";
			try {
				PreparedStatement statement = connect.prepareStatement("select average(studentsalary) from student ");

				ResultSet result = statement.executeQuery();

				while(result.next())
				{

					str += result.getString(1) + result.getString(2);

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Double.parseDouble(str);
		}

		public static void connection()
		{

			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Works");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static void ConectingToSQL ()
		{
			connection();
			String host = "jdbc:mysql://localhost:3306/studentsdb";
			String username = "root";
			String password = "1234";

			try {
				connect = (Connection) DriverManager.getConnection(host, username, password);
				System.out.println("work");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}


	}
