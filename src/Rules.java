import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/** KLASA WYŚWIETLAJĄCA ZASADY GRY */
public class Rules extends JFrame {

    public Rules(int[] Loc){
        JPanel Header = createHeader();
        JPanel Content = createContent();

        add(Header, BorderLayout.NORTH);
        getContentPane().add(Content, BorderLayout.CENTER);

        pack();
        setSize(new Dimension(480, 280));
        setLocation(setLoc(Loc));
        setVisible(true);
    }

    private Point setLoc(int[] Loc) {
        Point result = new Point();
        Loc[2] = Loc[2]/2 - (this.getSize().width/2);
        Loc[3] = Loc[3]/2 - (this.getSize().height/2);
        result.x = Loc[0] + Loc[2];
        result.y = Loc[1] + Loc[3];
        return result;
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        Font font2 = new Font("Arial", Font.ITALIC,15);
        JTextArea area = new JTextArea("Używaj strzałek aby poruszć się lądownikiem i wylądować nim na jednym z czerwonych lądowisk. " +
                                "Strzałki w prawo i w lewo pozwolą Ci zmienić kierunek lotu, a dzięki strzałce w góre zwiększysz swoją wysokość lub zmniejszysz prędkość opadania "+
                                "Grawitacja będzie ściągać Cię do dołu, a paliwa wystarczy tylko na kilka imulsów. " +
                               "Mniejsze lądowisko jest lepiej punktowane. Uważaj na meteoryty!!!");
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(font2);
        area.setColumns(30);
        area.setBackground(new Color(0,0,0,0));
        area.setOpaque(true);
        area.setEditable(false);
        area.setHighlighter(null);
        panel.add(area);
        JButton BackButton = new JButton("Do gry!");
        panel.add(BackButton);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        return panel;
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel();
        JLabel Header = new JLabel("Zasady");
        Header.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD+Font.ITALIC,25);
        Header.setFont(font);
        panel.add(Header);
        return panel;
    }

}
