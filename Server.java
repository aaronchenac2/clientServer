package serCli;

import java.io.*;
import java.net.*;
import java.util.Scanner;


class Server
{
    public static final int PORT = 1212;

    long ID1;

    long ID2;

    String s1;

    String s2;

    PrintStream printStream1;

    PrintStream printStream2;


    public static void main( String args[] ) throws IOException
    {
        new Server().runServer();
    }


    public void runServer() throws IOException
    {
        ServerSocket serverSocket = new ServerSocket( PORT );
        System.out.println( "Server up & ready for connection..." );
        while ( true )
        {
            Socket socket1 = serverSocket.accept();
            Socket socket2 = serverSocket.accept();

            printStream1 = new PrintStream( socket1.getOutputStream() );
            printStream2 = new PrintStream( socket2.getOutputStream() );

            ServerThread client1 = new ServerThread( socket1, printStream1, printStream2, "1" );
            ServerThread client2 = new ServerThread( socket2, printStream1, printStream2, "2" );

            printStream1.println( "hehexd" );
            printStream2.println( "i like pie" );

            client1.start();
            client2.start();
        }
    }


    public class ServerThread extends Thread
    {
        Socket socket;

        PrintStream out1;

        PrintStream out2;


        ServerThread( Socket socket, PrintStream printStream, PrintStream printStream2, String id )
            throws IOException
        {
            this.socket = socket;
            this.setName( id );
            out1 = printStream;
            out2 = printStream2;

            if ( this.getName().equals( "1" ) )
            {
                out1.println( "hi client1 from thread" );
            }
            else if ( this.getName().equals( "2" ) )

            {
                out2.println( "hi client2 from thread" );
            }
        }


        public void run()
        {
            try
            {
                String message = null;
                Scanner scan = new Scanner( socket.getInputStream() );
                System.out.println( "User " + scan.nextLine() + " has connected to the server." );
                while ( ( message = scan.nextLine() ) != null )
                {
                    System.out.println( "Incoming client message: " + message );
                    if ( this.getName().equals( "1" ) )
                    {
                        s1 = message;
                    }
                    else if ( this.getName().equals( "2" ) )
                    {
                        s2 = message;
                    }
                    if ( this.getName().equals( "1" ) )
                    {
                        out2.println( "Server echoing messages: " + s2 );
                    }
                    else if ( this.getName().equals( "2" ) )
                    {
                        out1.println( "Server echoing messages: " + s1 );
                    }
                }

                socket.close();
            }
            catch ( IOException e )
            {

                e.printStackTrace();
            }
        }
    }
}
