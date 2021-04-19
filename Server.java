import java.net.*;
import java.io.*;
class Server
{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    //constructor
    public Server()
    {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting....");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            StartReading();
            StartWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public void StartReading()
    {
        // thread -read krke deta rahega
        Runnable r1 = ()->{
            System.out.println("reader Started");
            try{
                while(true)
                {

                        String msg = br.readLine();
                        if(msg.equals("exit")){
                            System.out.println("Client Terminated the chat");
                            socket.close();
                            break;
                        }
                        System.out.println("Client : "+msg);
                    
                }
            } catch(Exception e){
                //e.printStackTrace();
                System.out.println("connection is closed");

            }
        };
        new Thread(r1).start();

    }
    public void StartWriting()
    {
        //thread - data user lega and send karega client tak
        Runnable r2 = ()->{
            System.out.println("writer started....");
            try{
                while(!socket.isClosed())
                {
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                        String content = br1.readLine();
                        out.println(content);
                        out.flush();

                        if(content.equals("exit")){
                            socket.close();
                            break;
                        }

                }
                //System.out.println("connection is closed");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();

    }
    public static void main(String [] args) {
        System.out.println("this is Server....going to start Server");
        new Server();
    }
}