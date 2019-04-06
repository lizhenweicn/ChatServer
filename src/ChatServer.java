import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
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
        } catch (BindException e) {
            System.out.println("端口已经被使用");
            System.out.println("请退出相关程序并重新启动服务端");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (serverStarted) {
                boolean clientStarted;
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
            }
        } catch (EOFException e) {
            System.out.println("Client is closed");
        } catch (IOException e) {
            e.printStackTrace();
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
