package serCli;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client
{
    public static final int PORT = 1212;


    public static void main( String args[] ) throws IOException
    {
        Scanner scan = new Scanner( System.in );
        System.out.println( "Enter name of client" );
        String name = scan.nextLine();
        Socket socket = new Socket( "localhost", PORT );
        PrintStream fileStream = new PrintStream( socket.getOutputStream() );
        fileStream.println( name );
        Scanner scan1 = new Scanner( socket.getInputStream() );
        System.out.println( scan1.nextLine() );
        System.out.println( scan1.nextLine() );
        System.out.println( "Enter a message" );
        while ( true )
        {
            if (scan.hasNextLine())
            {
                String readerInput = scan.nextLine();
                fileStream.println( name + ": " + readerInput );
            }
            if (scan1.hasNextLine())
            {
                System.out.println( scan1.nextLine() );
            }
        }
    }
}
