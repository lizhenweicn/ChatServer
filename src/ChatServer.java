import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : zhenweiLi
 * @date :2019-04-06 14:32
 * DESC : 聊天程序服务端
 */
public class ChatServer {

    public static void main(String[] args) {
        try {
            boolean serverStarted;
            ServerSocket ss = new ServerSocket(8888);
            serverStarted = true;
            while (serverStarted) {
                boolean clientStarted;
                Socket socket = ss.accept();
                System.out.println("a client connected");
                clientStarted = true;
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                while (clientStarted) {
                    String readUTF = dis.readUTF();
                    System.out.println(readUTF);
                    if ("exit".equals(readUTF)) {
                        clientStarted = false;
                        serverStarted = false;
                    }
                }
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
