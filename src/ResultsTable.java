import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/** KLASY WYŚWIETLAJĄCA TABELE WYNIKÓW */
public class ResultsTable extends JFrame {

    ArrayList<String> results;

    public ResultsTable(int[] Loc){


        JLabel Label = new JLabel("Tabela wyników");
        Label.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD + Font.ITALIC, 20);
        Label.setFont(font);
        loadData();
        pack();
        setSize(new Dimension(570, 240));
        setLocation(setLoc(Loc));
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

    private Point setLoc(int[] Loc) {
        Point result = new Point();
        Loc[2] = Loc[2] / 2 - (this.getSize().width / 2);
        Loc[3] = Loc[3] / 2 - (this.getSize().height / 2);
        result.x = Loc[0] + Loc[2];
        result.y = Loc[1] + Loc[3];
        return result;
    }

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