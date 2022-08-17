import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private ArrayList<ClientHandler> clients;
    private Socket socket;
    public BufferedReader in;
    public PrintWriter writer;
    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) throws IOException {
        this.clients=clients;
        this.socket=socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run(){
        try {
            String msg;

            while ((msg = in.readLine()) != null) {
                if (msg.equalsIgnoreCase( "exit")) {
                    break;
                }
                for (ClientHandler cl : clients) {
                    cl.writer.println(msg);
                    System.out.println(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
