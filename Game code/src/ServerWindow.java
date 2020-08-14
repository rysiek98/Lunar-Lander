import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


/** Klasa odpowiedzialna za wyświetlanie okna serwera */
public class ServerWindow extends JFrame {

    DefaultListModel<String> listModel = new DefaultListModel<>();
    Server server;
    JFrame frame;
    JList<String> list;

    public ServerWindow(Server S){

        frame = this;
        server = S;
        JLabel Label = new JLabel("Serwer");
        Label.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD + Font.ITALIC, 20);
        Label.setFont(font);
        setSize(new Dimension(500, 240));
        setVisible(true);
        list = new JList<>(listModel);
        list.setVisibleRowCount(7);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Font font2 = new Font("Arial", Font.BOLD + Font.ITALIC, 12);
        list.setFont(font2);
        list.setFixedCellWidth(460);
        list.setForeground(Color.DARK_GRAY);

        setLayout(new FlowLayout());
        add(Label);
        add(new JScrollPane(list));
        JButton BackButton = new JButton("Zakończ");
        add(BackButton);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    server.stopServer();
                }catch (IOException err) {
                    System.err.println("ServerWindow exception: " + err);
                }
                dispose();
            }
        });
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server.stopServer();
                }catch (IOException err) {
                    System.err.println("ServerWindow exception: " + err);
                }
                dispose();
            }
        });
    }

    /** Metoda aktualizuje informacje o działaniach serwera */
    public void loadData(String x) {
        listModel.addElement(x);
        list.ensureIndexIsVisible(listModel.getSize()-1);
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }
}