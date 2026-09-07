import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

//The client class allows us to be able to create a client connections here with a server naming it
// socketclient. We use data as a way to be able to get messages and things that the server sends.
// Overall with the client class we make the connection as a thread and as the game is running with the
// connection to the server we have instances where we check with if statements what the server sends
// to know what methods to use.
public class Client extends Thread{

    int port;
    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;
    GameData data = new GameData();
    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call,int port){
        this.port = port;
        callback = call;
    }

    //Here we establish the connection between the client and the server
    // where we check if the port number is current and make sure we enter using sockets
    public void run() {
        try {
            socketClient= new Socket("127.0.0.1", port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
            callback.accept(data);          //start the second screen, and hence begin the game
        }
        catch(Exception e) {
            System.out.println("Client did not connect");
        }

        while(true) {
            try {
                data = (GameData) in.readObject();
                callback.accept(data);
            }
            catch(Exception e) {}
        }
    }

    // this function send is made so that when the game wants to send a value to the server we can do that
    // by just calling it instead of using the code over and over
    public void send(GameData gData) {
        try {
            out.writeObject(gData);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Object was not sent back");
        }
    }

    //This takes in a lambda expression to update elements in a different scene
    void setCallBack(Consumer<Serializable> call){
        this.callback = call;
    }

}

