import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.scene.control.ListView;

// The server class starts here and is set up with a port so that other clients are able to
// connect to the server if they have the right port number
// all the game aspects that run behind the GUI are started here and run here
// so that anything that a client presses while playing the game is told to the server
// here to receive.
public class Server{

    int count = 1;
    int port;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;
    GameLogic game = new GameLogic();
    int catLives = 0;
    Server(Consumer<Serializable> call, int port){
        this.port = port;
        callback = call;
        server = new TheServer();
        server.start();
    }

    // This class inside the server starts up a thread for a client to use
    // allowing multiple threads to be connected to the game
    // with this one instance when it goes through here makes sure what number client it
    // based on how many are connected and who connected first
    //using a try and catch in the case that the port doesn't connect right
    public class TheServer extends Thread{
        public void run() {

            try(ServerSocket mysocket = new ServerSocket(port);){
                System.out.println("Server is waiting for a client!");

                while(true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("client has connected to server: " + "client #" + count);
                    clients.add(c);
                    c.start();
                    count++;
                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }

    // The client thread class is here inside the server and
    // the sets up the class so that the server and client are able
    // to communicate each other's data

    class ClientThread extends Thread{

        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;
        String cat;
        String gameWord;

        ArrayList<Integer> indexNums = new ArrayList<>();
        // connection is started and the number of client is assigned
        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        // this function is used to update any changes based on the client
        // in the case they press any buttons they are sent out here with the
        // outcome of pressing the button based on the scene that they are on
        public void updateClient(GameData data) {
            try {
                out.writeObject(data);
            }
            catch(Exception e) {
            }
        }

        // we make sure that the connection stays true so that when the
        // game data is updated from the client we check the instances that are sent
        // back and run the function according to what part of the game data we are
        // checking Because here we check if the player won overall or a round and the same for if they
        // lose a game or round.
        public void run(){
            try {                    //establish the connection between the client and the server
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
                game.initCategories();
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            while(true) {
                try {
                    GameData data = (GameData) in.readObject();
                    if (data.restart){      //player chose to play again
                        game.initCategories();
                        callback.accept("Client# " + count + " has chosen to play again");
                        data.restart = false;
                    }

                    cat = data.userChosenCategory;      // get the category the user chose

                    if (cat.equals("Animals")){         //this will be the category lives the user has to choose a word
                        catLives = data.categoryAnimalsLives;
                    }
                    else if (cat.equals("Brands")){
                        catLives = data.categoryBrandsLives;
                    }
                    else if (cat.equals("Movies")){
                        catLives = data.categoryMoviesLives;
                    }
                    else{
                        catLives = 5;
                    }

                    if (data.catButtonClicked){     //they chose a category
                        gameWord = game.getRandomWord(cat, catLives);
                        data.gameShowcaseWord = game.initShowcaseWord(gameWord);        //get the word and blank it out
                        callback.accept("This is the category chosen by client: #" + count + " " +  cat);
                        System.out.println(gameWord);
                        System.out.println(data.gameShowcaseWord);
                    }

                    if (data.enterButtonClicked){       //they started the game and now entered a character
                        if (game.checkUserGuess(gameWord, data.UserGuess, data.gameShowcaseWord)){      //they got the guess right
                            data.gameShowcaseWord = game.checkUserGuess2(gameWord, data.UserGuess, data.gameShowcaseWord, indexNums);
                            callback.accept("this is the character that client #" + count + " guessed: " + data.UserGuess + ", They were right these are the locations " + indexNums.toString());
                            indexNums.clear();
                            System.out.println(data.gameShowcaseWord);
                            if (game.checkUserWon(gameWord, data.gameShowcaseWord)){      //they guessed the word and got it right
                                data.gameShowcaseWord = "";
                                data.wordGuessLives = 6;
                                data.enterButtonClicked = false;        //resetting everything for the next game
                                data.roundWon = true;
                                game.setCatWin(cat, data);
                                callback.accept("Client #" + count + " Won the round for " + cat);
                            }
                            data.userGuessRight = true;
                            game.checkUserWonGame(data);
                            if (data.userWin){          //they beat the game
                                callback.accept("Client #" + count + " has won the game");
                            }
                        }
                        else{       //they guessed incorrectly
                            callback.accept("this is the character that client #" + count + " guessed: " + data.UserGuess + ", They were wrong");
                            data.wordGuessLives = data.wordGuessLives - 1;
                            if (game.checkUserLost(data)){      //lost the round
                                data.gameShowcaseWord = "";
                                data.wordGuessLives = 6;
                                data.enterButtonClicked = false;
                                data.roundLost = true;
                                game.decreaseLives(cat, data);
                                callback.accept("Client #" + count + " Lost the round for " + cat);
                            }
                            data.userGuessRight = false;
                            game.checkUserLostGame(data);
                            if (data.userLost){             //lost the game
                                callback.accept("Client #" + count + " has Lost the game");
                            }
                        }
                    }
                    updateClient(data);     // send the new state of the game
                }
                catch(Exception e) {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + ", They probaly closed the game....closing down!");
                    clients.remove(this);
                    e.printStackTrace();
                    break;
                }
            }
        }//end of run
    }//end of client thread
}







