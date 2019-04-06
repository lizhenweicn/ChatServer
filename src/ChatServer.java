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
                Socket s = ss.accept();
                System.out.println("a client connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
