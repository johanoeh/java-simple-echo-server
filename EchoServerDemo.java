
import java.io.IOException;

/**
 * 
 * @author johan
 * Date 2018-08-27
 * Program displays a simple TCP server that Echoes input from a client to
 * all connected clients.
 * Can be tested using : telnet ip port
 *
 */


/**
 * Driver class for the EchoServer
 */
public class EchoServerDemo{

  public static void main(String [] args){

    Integer portNum = null;

    // check if there is arguments
    if(args.length == 0){
      System.err.println("Provide portnumber as arguments");
      return;
     } else {
       // try and parse users argument
       try{
            portNum = Integer.parseInt(args[0]);
          } catch(NumberFormatException ex) { 
            System.err.println("Invalid input provide an integer as argument");
	    return;
          }
    }

   // creates a EchoServer Runnable and runs it in a new Thread as a server would block the mainthread listening for input.
    try{

        EchoServer echoServer = new EchoServer(portNum);
        Thread serverThread = new Thread(echoServer);
        serverThread.start();

    } catch(IOException ex){

        ex.printStackTrace();

    }
  }

}
