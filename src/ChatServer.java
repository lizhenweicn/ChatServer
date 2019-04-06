import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhenweiLi
 * @date :2019-04-06 14:32
 * DESC : 聊天程序服务端
 */
public class ChatServer {

    /**
     * 所有客户端集合
     */
    private List<Client> mClientList;

    public static void main(String[] args) {
        new ChatServer().launchServer();
    }

    /**
     * 启动服务器程序
     */
    private void launchServer() {

        //  服务器端的 ServerSocket
        ServerSocket serverSocket = null;

        boolean serverStarted = false;

        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("ServerSocket Start");
            serverStarted = true;
            mClientList = new ArrayList<>();
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
                mClientList.add(client);
                new Thread(client).start();
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

        /**
         * 网络数据输出流
         */
        private DataOutputStream mDos;

        /**
         * 客户端连接状态
         */
        private boolean mClientStarted;

        Client(Socket socket) {
            this.mSocket = socket;
            try {
                this.mDis = new DataInputStream(socket.getInputStream());
                this.mDos = new DataOutputStream(socket.getOutputStream());
                System.out.println("a client connected");
                mClientStarted = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 客户端在线时，才需要推送消息
         *
         * @param message 消息内容
         */
        void send(String message) {
            try {
                mDos.writeUTF(message);
                mDos.flush();
            } catch (SocketException e) {
                mClientList.remove(this);
                System.out.println("对方已下线");
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
                    for (Client client : mClientList) {
                        if (client == this) {
                            continue;
                        }
                        client.send(readUTF);
                    }
                }
            } catch (EOFException e) {
                System.out.println("客户端已关闭");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (mDis != null) {
                        mDis.close();
                    }
                    if (mDos != null) {
                        mDos.close();
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
