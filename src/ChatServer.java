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
                Client client = new Client(serverSocket.accept());
                new Thread(client).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 单独处理消息的线程类
     */
    class Client implements Runnable {

        /**
         * 客户端的 Socket
         */
        private Socket mSocket;

        /**
         * 网络数据输入流
         */
        private DataInputStream mDis;

        boolean mClientStarted;

        Client(Socket socket) {
            this.mSocket = socket;
            try {
                this.mDis = new DataInputStream(socket.getInputStream());
                System.out.println("a client connected");
                mClientStarted = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (mClientStarted) {
                    String readUTF = mDis.readUTF();
                    System.out.println(readUTF);
                    if ("exit".equals(readUTF)) {
                        mClientStarted = false;
                    }
                }
            } catch (EOFException e) {
                System.out.println("Client is closed");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (mDis != null) {
                        mDis.close();
                    }
                    if (mSocket != null) {
                        mSocket.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
