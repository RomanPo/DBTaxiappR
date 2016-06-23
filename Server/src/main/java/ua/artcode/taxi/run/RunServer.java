package ua.artcode.taxi.run;

import com.google.gson.Gson;
import ua.artcode.taxi.dao.AppDB;
import ua.artcode.taxi.dao.OrderDaoInnerDbImpl;
import ua.artcode.taxi.dao.UserDaoInnerDbImpl;
import ua.artcode.taxi.service.UserService;
import ua.artcode.taxi.service.UserServiceImpl;
import ua.artcode.taxi.service.ValidatorImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by serhii on 05.06.16.
 */
public class RunServer {



    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Gson gson = new Gson();
        AppDB appDB = new AppDB();
        UserService userService = new UserServiceImpl(
                new UserDaoInnerDbImpl(appDB),
                new OrderDaoInnerDbImpl(appDB),
                new ValidatorImpl(appDB));

        while(true){
            // waiting for new client
            Socket clientSocket = serverSocket.accept();


            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            printWriter.println("Hello form server" + getMenu());
            printWriter.flush();

            while(true){
                String line = null;
                StringBuilder sb = new StringBuilder();
                while((line = bf.readLine()) != null){
                    sb.append(line).append("\n");
                }

                System.out.println(sb.toString());

                String requestBody = sb.toString();

            }


        }



    }

    public static String getMenu(){

        return "1. Add user \n" + "2. Exit\n";

    }


}
