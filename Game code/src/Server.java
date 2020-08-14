import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/** Klasa serwera */

public class Server {
    static ArrayList<String> results;
    static private ArrayList<String> levels;
    ServerWindow serverWindow;
    boolean flag;
    Socket socket;
    ServerSocket serverSocket;
    InputStream is;
    OutputStream os;


    public static void main(String[] args) throws Exception{
        new Server();
    }

    Server() throws Exception{
        flag = true;
        serverWindow = new ServerWindow(this);
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localHost.getHostAddress() = " + localHost.getHostAddress());
        serverWindow.loadData("localHost.getHostAddress() = " + localHost.getHostAddress());
        System.out.println("localHost.getHostName() = " + localHost.getHostName());
        serverWindow.loadData("localHost.getHostName() = " + localHost.getHostName());

        try {
            serverSocket = new ServerSocket(LoadingLevel.readPort("Game code/serverConfig.txt"));
            levels = LoadingLevel.readLevelsName("Game code/serverConfig.txt");
        } catch (Exception e) {
            System.err.println("Create server socket: " + e);
            return;
        }

        while (flag) {
            try {
                socket = serverSocket.accept();
                System.out.println("TEST");
                is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String fromClient = br.readLine();
                System.out.println("From client: [" + fromClient + "]");
                serverWindow.loadData("From client: [" + fromClient + "]");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                switch(fromClient) {

                    case "Login":
                        pw.println("Logged");
                        break;

                    case "Ping":
                        pw.println("Ping");
                        break;

                    case "sendResults":
                        pw.print("OK");
                        pw.println();
                        try {
                            System.out.println(LoadingLevel.readPathsTo("ResultsTable",LoadingLevel.pathToServerConfigFile));
                            results = LoadingLevel.readResults(LoadingLevel.readPathsTo("ResultsTable",LoadingLevel.pathToServerConfigFile));
                        }catch (Exception e) {
                            System.out.println("Server: BRAK BAZY WYNIKOW!");
                            serverWindow.loadData("Server: BRAK BAZY WYNIKOW!");
                            pw.println("NOT FOUND");
                            System.err.println("Server exception: " + e);
                        }
                        int rsize = results.size();

                        for (int i = 0; i < rsize; i++){
                            fromClient = br.readLine();
                            System.out.println("From client: [" + fromClient + "]");
                            serverWindow.loadData("From client: [" + fromClient + "]");
                            System.out.println(results.get(i));
                            serverWindow.loadData(results.get(i));
                            pw.println(results.get(i));
                        }
                        System.out.println("Server: END");
                        serverWindow.loadData("Server: END");
                        pw.print("END");
                        pw.println();

                        break;

                    case "giveLevel":
                        pw.print("OK");
                        pw.println();
                        fromClient = br.readLine();
                        System.out.println("From client: [" + fromClient + "]");
                        serverWindow.loadData("From client: [" + fromClient + "]");
                        if(levels.contains(fromClient)) {
                            String[] table = LoadingLevel.readLevel(fromClient);
                            for (int i = 0; i < table.length; i++) {
                                System.out.println("Server: "+table[i]);
                                serverWindow.loadData("Server: "+table[i]);
                                pw.println(table[i]);
                                fromClient = br.readLine();
                                System.out.println("From client: [" + fromClient + "]");
                                serverWindow.loadData("From client: [" + fromClient + "]");
                            }
                            System.out.println("Server: END");
                            serverWindow.loadData("Server: END");
                            pw.print("END");
                            pw.println();
                        }else{
                            System.out.println("Server: BRAK ZADANEGO POZIOMU!");
                            serverWindow.loadData("Server: BRAK ZADANEGO POZIOMU!");
                            pw.println("NOT FOUND");
                        }
                        break;

                    case "saveResults":
                        String name, diffLevel;
                        int points;
                        pw.println("Send Name");
                        name = br.readLine();
                        pw.println("Send Points");
                        points = Integer.parseInt(br.readLine());
                        pw.println("Send Difficult level");
                        diffLevel = br.readLine();
                        SaveResults.saveResult(name,points, diffLevel);
                        break;

                    case "sendImage":
                        pw.print("OK");
                        pw.println();
                        fromClient = br.readLine();
                        System.out.println("From client: [" + fromClient + "]");
                        serverWindow.loadData("From client: [" + fromClient + "]");
                        String[] text = fromClient.split("\\.");
                        BufferedImage image = ImageIO.read(new File(LoadingLevel.readPathsTo("img",LoadingLevel.pathToServerConfigFile)+fromClient));
                        ImageIO.write(image, text[1], baos);
                        byte[] size = ByteBuffer.allocate(4).putInt(baos.size()).array();
                        os.write(size);
                        os.write(baos.toByteArray());
                        os.flush();

                        break;

                    case "giveLevelList":
                        for(int i=0; i<levels.size(); i++) {
                            pw.print(levels.get(i));
                            pw.println();
                            System.out.println("Server: "+levels.get(i));
                            serverWindow.loadData("Server: "+levels.get(i));
                            fromClient = br.readLine();
                            System.out.println("From client: [" + fromClient + "]");
                            serverWindow.loadData("From client: [" + fromClient + "]");
                        }
                        System.out.println("Server: END");
                        serverWindow.loadData("Server: END");
                        pw.print("END");
                        pw.println();
                        break;

                    default:
                        pw.println("Server ERROR");
                        System.out.print("Server error!");
                        serverWindow.loadData("Server error!");
                }

                socket.close();
            } catch (Exception e) {
                System.err.println("Server exception: " + e);
                serverWindow.loadData("Server while exception: " + e);
            }
        }
    }

    /** Metoda kończy działanie serwera */
    public void stopServer() throws IOException {

         flag = false;
        try {
            if (os != null)
                os.close();
            if (is != null)
                is.close();
        } catch (IOException e) {
            System.err.println("Server stopServer exception: " + e);
        }
        
        if (!serverSocket.isClosed())
            serverSocket.close();
        }

}
