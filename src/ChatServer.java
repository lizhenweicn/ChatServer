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
        new ChatServer().launchSerer();
    }

    /**
     * 启动服务器程序
     */
    private void launchSerer() {

        //  服务器端的 ServerSocket
        ServerSocket serverSocket = null;

        //  客户端的 Socket
        Socket socket = null;

        //  网络数据输入流
        DataInputStream dis = null;

        boolean serverStarted = false;
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("ServerSocket Start");
            serverStarted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (serverStarted) {
                boolean clientStarted = false;
                socket = serverSocket.accept();
                System.out.println("a client connected");
                clientStarted = true;
                dis = new DataInputStream(socket.getInputStream());
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
        } catch (Exception e) {
            System.out.println("Client is closed");
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

}
