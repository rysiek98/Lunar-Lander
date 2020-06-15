import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/** Klasa wyświetlająca okno z tabelą wyników */
public class ResultsTable extends JFrame implements WindowLocation{

    ArrayList<String> results;

    /** Konstruktor klasy ResultsTable
     * @param Loc tablica 4 wartości Integer [współrzędna x, współrzędna y, szerokość okna, wysokość okna]
     * */
    public ResultsTable(int[] Loc){


        JLabel Label = new JLabel("Tabela wyników");
        Label.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD + Font.ITALIC, 20);
        Label.setFont(font);
        loadData();
        pack();
        setSize(new Dimension(570, 240));
        setLocation(WindowLocation.setLoc(Loc, this));
        setVisible(true);
        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (int i = 0; i < results.size(); i++) {
            l1.addElement(results.get(i));
        }
        JList<String> list = new JList<>(l1);
        list.setVisibleRowCount(7);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Font font2 = new Font("Arial", Font.BOLD + Font.ITALIC, 12);
        list.setFont(font2);
        list.setFixedCellWidth(510);
        list.setForeground(Color.DARK_GRAY);

        setLayout(new FlowLayout());
        add(Label);
        add(new JScrollPane(list));
        JButton BackButton = new JButton("Wróć");
        add(BackButton);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    /** Metoda pobierająca wyniki do tablicy a następnie wyświetlająca je*/
    private void loadData() {
        try {
            if (Client.Online()) {
                results = Client.getResultData();

            } else {
                results = new ArrayList<String>();
                BufferedReader br = new BufferedReader(new FileReader("TabelaWynikow.txt"));
                String textLine = br.readLine();
                do {
                    System.out.println(textLine);
                    results.add(textLine);
                    textLine = br.readLine();
                } while (textLine != null);
                br.close();
            }

        } catch (Exception e) {
            System.err.println("loadData() exception: " + e);

        }
    }
}