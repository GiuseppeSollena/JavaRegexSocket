package it.regex;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validatorServer {
    public static final int PORT = 1000;

    public validatorServer() {
    }

    public static boolean validazione(String value, String regExp) {
        Pattern pattern = Pattern.compile(regExp, 2);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static String Switchy(String str) {
        String[] patt = str.split(":");
        String str1;
        switch (patt[0]) {
            case "CAP":
                if (validazione(patt[1], "^[0-9]{5}$")) {
                    str1 = "CAP CORRETTO";
                } else {
                    str1 = "CAP SBAGLIATO";
                }
                break;
            case "EMAIL":
                if (validazione(patt[1], "^[a-zA-Z0-9_.]+[+]?[a-zA-Z0-9]+[@]{1}[a-z0-9]+[\\.][a-z]+$")) {
                    str1 = "EMAIL CORRETTA";
                } else {
                    str1 = "EMAIL SBAGLIATA";
                }
                break;
            case "CF":
                if (validazione(patt[1], "^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$")) {
                    str1 = "CF CORRETTO";
                } else {
                    str1 = "CF SBAGLIATO";
                }
                break;
            case "IBAN":
                if (validazione(patt[1], "^IT\\d{2}[A-Z]\\d{10}\\d{12}$")) {
                    str1 = "IBAN CORRETTO";
                } else {
                    str1 = "IBAN SBAGLIATO";
                }
                break;
            default:
                str1 = "INSERIMENTO ERRATO";
        }

        return str1;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1000);
        System.out.println("EchoServer: connessione iniziata ");
        System.out.println("info Server Socket: " + String.valueOf(serverSocket));
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            clientSocket = serverSocket.accept();
            System.out.println("Connection accepted: " + String.valueOf(clientSocket));
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            in = new BufferedReader(isr);
            OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            out = new PrintWriter(bw, true);

            while(true) {
                String str = in.readLine();
                if (str.equals("STOP")) {
                    System.out.println("Client ha  scritto STOP!");
                    break;
                }

                System.out.println("Stringa ricevuta: " + str);
                String str1 = Switchy(str);
                out.println(str1);
            }
        } catch (IOException var10) {
            System.err.println("Accept failed");
            System.exit(1);
        }

        System.out.println("EchoServer: closing...");
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
