package managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.RecursiveTask;

public class TCPServer {
    private static final Logger LOGGER = (Logger) LogManager.getLogger("managers.TCPServer");
    public interface TCPExecute { public Object Execute(String s, Object o, String login, String pass); }
    private int port;
    private HashSet<SocketChannel> sessions;
    private ReceivingManager receivingManager = new ReceivingManager();
    private SendingManager sendingManager = new SendingManager();
    private Selector selector;
    private TCPExecute executer;


    public TCPServer(int port, TCPExecute obj) {
        this.port = port;
        executer = obj;
        this.sessions = new HashSet<>();
    }

    public HashSet<SocketChannel> getSessions() {
        return sessions;
    }

    public void close() {
        for (var se: sessions) {
            try {
                se.close();
            } catch (Exception e) {}
        }
    }
    public void start() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            var socketAddress = new InetSocketAddress("localhost", port);
            serverSocketChannel.bind(socketAddress, port);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            LOGGER.info("Server started on :"+port+"...");
            while (true) {
                // blocking, wait for events
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid()) continue;
                    if (key.isAcceptable()) {accept(key);}
                    else if (key.isReadable()) {processingKey(key);}
                    Thread.sleep(200);
                }
            }
        } catch (IOException e) {
            if (e.getMessage().equals("Address already in use: bind")) {
                LOGGER.error("Address already in use");
                port = (port+1)%32767;
                start();
            } else
                LOGGER.error("----------"+e.getMessage()+"------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processingKey(SelectionKey key) {
        receivingManager.read(key, (ObjectInputStream command)->{
            try {
                var cmd = command.readUTF();
                var log = command.readUTF();
                var pas = command.readUTF();
                var obj = command.readObject();
                new Thread(()-> {
                    var ret = executer.Execute(cmd, obj, log, pas);
                    sendingManager.send((SocketChannel) key.channel(), (Object) ret);
                }).start();
            } catch (Exception e) {
                System.out.println(e);
                LOGGER.error(e.getMessage());
                sendingManager.send((SocketChannel) key.channel(), (Object) "503");
            }
        });
    }


    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            LOGGER.info("socket connection accepted:" + channel.socket().getRemoteSocketAddress().toString());
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            sessions.add(channel);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
