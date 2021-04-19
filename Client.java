import java.net.*;
import java.io.*;
public class Client {
    Socket socket;

    BufferedReader br;
    PrintWriter out;
    public Client()
    {
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            StartReading();
            StartWriting();
            
        } catch (Exception e) {
            // TODO: handle exception
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
                            System.out.println("Server Terminated the chat");
                            socket.close();
                            break;
                        }

                        System.out.println("Server : "+msg);
                    
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

    public static void main(String[] args) {
        System.out.println("this is client.... going to start client");
        new Client();
    }
}
