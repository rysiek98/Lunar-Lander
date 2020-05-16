import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;


/** KLASA KLIENTA DO KOMUNIKACJI Z SERWEREM */

public class Client extends JFrame {


    private static JTextField ipAddress, port;
    private static boolean mode;

    public Client() {
        JPanel Header = createHeader();
        JPanel Content = createContent();
        JPanel Footer = createFooter();
        BorderLayout borderLayout = new BorderLayout(5,5);
        setLayout(borderLayout);
        add(Header, BorderLayout.NORTH);
        getContentPane().add(Content, BorderLayout.CENTER);
        add(Footer, BorderLayout.SOUTH);
        pack();
        setSize(new Dimension(280, 200));
        setVisible(true);
        setLocationRelativeTo(null);
        mode = false;
    }

    private JPanel createFooter() {
        JPanel panel = new JPanel();
        JButton ExitButton = new JButton("Zamknij");
        JButton LoginButton = new JButton("Zaloguj");
        panel.add(ExitButton);
        panel.add(LoginButton);
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
                App.main();
                setVisible(false);
            }
        });

        return panel;
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        JLabel IP = new JLabel("IP: ");
        JLabel Port = new JLabel("Port: ");
        ipAddress = new JTextField(18);
        port = new JTextField(18);
        Font font2 = new Font("Arial", Font.ITALIC+Font.BOLD,15);
        IP.setFont(font2);
        Port.setFont(font2);
        panel.add(IP);
        panel.add(ipAddress);
        panel.add(Port);
        panel.add(port);
        return panel;
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel();
        JLabel Header = new JLabel("Logowanie do serwera");
        Header.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD+Font.ITALIC,20);
        Header.setFont(font);
        panel.add(Header);
        return panel;
    }

    public static String getIP(){
        return ipAddress.getText();
    }

    public static int getPort(){
        return Integer.parseInt(port.getText());
    }

    /** METODA POZWALAJĄCA NA LOGOWANIE DO SERWERA */
    public static void connectToServer(){

        try {
            Socket socket = new Socket(getIP(), getPort());
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            InputStream is;
            BufferedReader br;
            pw.print("Login");
            pw.println();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            if(br.readLine().equals("Logged")){
                System.out.println("Connect - Online Mode Active");
                mode = true;
            }else{
                System.out.println("Not connected - Offline Mode Active");
            }

           socket.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }
    }

    public static boolean getMode(){
        return mode;
    }

    /** METODA POZWALA POBRAĆ OBRAZEK Z SERWERA*/
    public static BufferedImage getImage(String name){

        try {
            Socket socket = new Socket(getIP(), getPort());
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            InputStream is = socket.getInputStream();
            BufferedReader br;

            pw.print("sendImage");
            pw.println();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            if(br.readLine().equals("OK")) {
                pw.print(name);
                pw.println();
            }
            byte[] sizeB = new byte[4];
            is.read(sizeB);

            int size = ByteBuffer.wrap(sizeB).asIntBuffer().get();

            byte[] imageB = new byte[size];
            is.read(imageB);

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageB));
            socket.close();
            return  image;

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }

        return  null;
    }
    /** METODA POZWALA POBRAĆ PLIK KONFIGURUJĄCY DO DANEGO POZIOMU */
    static ArrayList<String> getLevel(String level){

        ArrayList<String> data = new ArrayList<String>();
        try {
            String textLine;
            Socket socket = new Socket(getIP(), getPort());
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            InputStream is;
            BufferedReader br;

            pw.print("giveLevel");
            pw.println();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            if(br.readLine().equals("OK")) {
                pw.print(level);
                pw.println();
            }
            textLine = br.readLine();

            if(!textLine.equals("NOT FOUND")) {
                do{
                    pw.print("OK");
                    pw.println();
                    is = socket.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is));
                    data.add(textLine);
                    textLine = br.readLine();
                }while(!textLine.equals("END"));
            }else{
                System.out.println("BRAK ŻĄDANEGO POZIOMU!");
                data = null;
            }

            socket.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }
        return  data;
    }

    /** METODA POZWALA NA POBRANIE TABELI WYNIKÓW */
    public static  ArrayList<String> getResultData(){
        ArrayList<String> data = new ArrayList<String>();
        try {
            Socket socket = new Socket(getIP(), getPort());
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            InputStream is;
            BufferedReader br;
            String textLine = null;
            pw.print("sendResults");
            pw.println();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            if(br.readLine().equals("OK")) {
                pw.print("OK");
                pw.println();
            }

            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            textLine = br.readLine();

            if(!textLine.equals("NOT FOUND")) {
                do{
                    pw.print("OK");
                    pw.println();
                    is = socket.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is));
                    data.add(textLine);
                    textLine = br.readLine();
                }while(!textLine.equals("END"));
            }else{
                System.out.println("BRAK BAZY WYNIKOW!");
                data = null;
            }

            socket.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }
        return  data;
    }

    /** METODA WYSYŁA NOWY WYNIK DO SERWERA */
    public static void setResultData(String name, int points, String diffLevel){

        try {
            Socket socket = new Socket(getIP(), getPort());
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            InputStream is;
            BufferedReader br;

            pw.print("saveResults");
            pw.println();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            if(br.readLine().equals("Send Name")){
                pw.print(name);
                pw.println();
            }

            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            if(br.readLine().equals("Send Points")){
                pw.print(points);
                pw.println();
            }
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            if(br.readLine().equals("Send Difficult level"))
            {
                pw.print(diffLevel);
                pw.println();
            }
            socket.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }

    }

    /** METODA POZWALA POBRAĆ LISTE POZIOMÓW*/
    public static ArrayList<String> getLevelList(){
        ArrayList<String> data = new ArrayList<String>();
        String textLine;
        try {
            Socket socket = new Socket(getIP(), getPort());
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            InputStream is;
            BufferedReader br;
            pw.print("giveLevelList");
            pw.println();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            textLine = br.readLine();
            do{
                pw.print("OK");
                pw.println();
                is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                data.add(textLine);
                textLine = br.readLine();
            }while(!textLine.equals("END"));

            socket.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }
        return data;
    }


    public static void main(String[] args) {
        new Client();
    }
}
