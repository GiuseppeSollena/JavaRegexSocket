package it.regex;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class inputClient {
    public inputClient() {
    }

    public static void main(String[] args) throws IOException {
        InetAddress addr;
        if (args.length == 0) {
            addr = InetAddress.getByName((String)null);
        } else {
            addr = InetAddress.getByName(args[0]);
        }

        Socket socket = null;
        BufferedReader in = null;
        BufferedReader stdIn = null;
        PrintWriter out = null;

        try {
            socket = new Socket(addr, 1050);
            System.out.println("EchoClient: started");
            System.out.println("Client Socket: " + String.valueOf(socket));
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            out = new PrintWriter(bw, true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                System.out.println("Inserisci dato con formato tipo:valore");
                String str = stdIn.readLine();
                if (str.equals("STOP")) {
                    out.println(str);
                    break;
                }

                out.println(str);
                System.out.println("RISPOSTA DEL SERVER: ");
                String serverResp = in.readLine();
                System.out.println(serverResp);
            }
        } catch (UnknownHostException var11) {
            System.err.println("Don’t know about host " + String.valueOf(addr));
            System.exit(1);
        } catch (IOException var12) {
            System.err.println("Couldn’t get I/O for the connection to: " + String.valueOf(addr));
            System.exit(1);
        }

        System.out.println("EchoClient: closing...");
        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}
