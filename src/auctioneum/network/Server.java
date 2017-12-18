package auctioneum.network;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;



public class Server {

    /** The node in which the server is running **/
    private Node owner;

    /** Service type **/
    private ServiceType serviceType;

    /** Socket running on the owner node**/
    private ServerSocket serverSocket;

    /** Client connection **/
    private Socket clientSocket;

    /** The port that the server listens to **/
    private int listeningPort;

    /** Whether to stop the server **/
    private boolean stop;


    public Server(Node owner, ServiceType serviceType){
        try {
            this.owner = owner;
            this.serviceType = serviceType;
            switch (serviceType){
                case TRANSACTIONS:{
                    this.listeningPort = this.owner.getTransactionsPort();
                    break;
                }
                case VALIDATIONS:{
                    this.listeningPort = this.owner.getValidationsPort();
                    break;
                }
            }
            this.listeningPort = owner.getValidationsPort();
            this.stop = false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Starts running the server on the specified port.
     */
    public void start(){
        try {
            this.serverSocket = new ServerSocket(listeningPort);
            while (!stop) {
                this.clientSocket = serverSocket.accept();
                System.out.println("\nConnected with : "+clientSocket.getInetAddress()+" at "+new Date());
                Thread service;
                switch (this.serviceType){
                    case TRANSACTIONS:{
                        service = new Thread(new ValidationHandler(this.owner, this.clientSocket));
                        break;
                    }
                    case VALIDATIONS: {
                        service = new Thread(new TransactionHandler(this.owner,this.clientSocket));
                        break;
                    }
                    default:{
                        service = new Thread(new ValidationHandler(this.owner,this.clientSocket));
                        break;
                    }
                }
                service.start();

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.stop();
        }
    }

    /**
     * Terminates the server.
     */
    public void stop(){
        try {
            this.stop = true;
            this.serverSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Service Types
     */
    public enum ServiceType{
        TRANSACTIONS,
        VALIDATIONS
    }



}

