import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class socketHandler extends Thread {
	Socket incoming;
	Sql sql;

	socketHandler(Socket _in , Sql sql)
	{
		incoming=_in;
		this.sql = sql ;
	}

	public void run()
	{
		String clientSentence;
		String capitalizedSentence = null;
		int num ;
		int num2;
		try
		{


			ObjectInputStream inFromClient = new ObjectInputStream(incoming.getInputStream());

			DataOutputStream  outToClient =
					new DataOutputStream (incoming.getOutputStream() );

			while(true) {

				try {

					String obj_str = (String) inFromClient.readObject();
					switch (obj_str) {
						case "1" -> {
							Object obj = inFromClient.readObject();
							if ( obj instanceof Student) {
								Student s = (Student) obj;

								sql.insert_statement(s.id, s.name, s.phone);
								outToClient.writeBytes("it was ok everything here\n");
							}
						}
						case "2" -> {
							Student obj = (Student) inFromClient.readObject();
							String answer = sql.check_if_user_exist(obj.id);
							outToClient.writeBytes(answer +"\n");


						}
						case "3" -> {
							Student obj = (Student) inFromClient.readObject();
							outToClient.writeBytes(obj.id + " deleted\n");
							sql.delete_statement(obj.id);

						}
						case "4" -> {
							Student obj = (Student) inFromClient.readObject();
							String answer = sql.check_if_user_exist(obj.id);
							if (answer.equals("student doesn't exist!")) {
								outToClient.writeBytes(answer +"\n");
							} else {
								sql.update_statement(obj.name,obj.id,obj.phone);
								outToClient.writeBytes("student updated\n");
							}
						}
					}


				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					outToClient.writeBytes("it was not ok\n");
					e.printStackTrace();
				}




			}
		}
		catch(IOException e)
		{

		}

	}
}
