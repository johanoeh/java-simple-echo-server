
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

/**
 * @author johan
 * date : 2018-08-27
 * Implements a very basic TCP server that echos recieved message
 * to all connected clients.
 */

public class EchoServer implements Runnable {

  ServerSocket serverSocket;

  // Printwriter wraps the the client sockets outputstream
  private List<PrintWriter> recipients;
  private int port;

  public EchoServer(int port) throws IOException{
    this.port = port;
    serverSocket = new ServerSocket(port);
    recipients = new ArrayList<>();

  }

  @Override
  public void run(){
    System.out.println("Running server on port: "+ port);
    while(true){
      try {
	// blocking call to serversocket
        accept(serverSocket.accept());
      } catch (IOException ex){
	ex.printStackTrace();
      }
    }

  }

  private void accept(Socket socket) throws IOException {

    // create a printwriter for client sockets
    recipients.add(new PrintWriter(socket.getOutputStream(),true));
    // create a BufferedReader for the sockets inputstrem
    BufferedReader br = 
    new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

    /*create a new Thread to listen to what client sends*/
    Thread recieverThread = new Thread(new Runnable() {

      @Override
      public void run(){
        String line = null;
	try{

            while((line = br.readLine()) != null){
	    // send message to all connected clients
              for(PrintWriter printWriter : recipients){
                printWriter.println(line);
                printWriter.flush();
              }
            }

           } catch (IOException ex){
	     ex.printStackTrace(); 
           }
      }

    });
    // starts the new thread
    recieverThread.start();
  }
}
