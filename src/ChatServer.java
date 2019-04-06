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
            ServerSocket ss = new ServerSocket(8888);
            while (true) {
                Socket socket = ss.accept();
                System.out.println("a client connected");
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String readUTF = dis.readUTF();
                System.out.println(readUTF);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
