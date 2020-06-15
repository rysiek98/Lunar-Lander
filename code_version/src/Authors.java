import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Klasa tworząca okno z informacją o autorach*/

public class Authors  extends JFrame implements WindowLocation {

    /** Konstruktor klasy Authors
     * @param Loc tablica 4 wartości Integer [współrzędna x, współrzędna y, szerokość okna, wysokość okna]
     * */
    public Authors(int Loc[]) {
        JPanel Header = createHeader();
        JPanel Content = createContent();
        JPanel Footer = createFooter(this);
        BorderLayout borderLayout = new BorderLayout(5,5);
        setLayout(borderLayout);
        add(Header, BorderLayout.NORTH);
        getContentPane().add(Content, BorderLayout.CENTER);
        add(Footer, BorderLayout.SOUTH);
        pack();
        setSize(new Dimension(300, 200));
        setVisible(true);
        setLocation(WindowLocation.setLoc(Loc, this));
    }

    /** Metoda prywatna pomagająca stworzyć stopkę */
    private JPanel createFooter(JFrame frame) {
        JPanel panel = new JPanel();
        JButton BackButton = new JButton("Wróć");
        panel.add(BackButton);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        return panel;
    }

    /** Metoda prywatna pomagająca stworzyć wyświetlaną zawartość */
    private JPanel createContent() {
        JPanel panel = new JPanel();
        JLabel Author1 = new JLabel("Michał Ryszka, ");
        JLabel Author2 = new JLabel("Wojciech Kowalski");
        JLabel Place = new JLabel("Politechnika Warszawska 2020");
        Font font2 = new Font("Arial", Font.ITALIC,15);
        Author1.setFont(font2);
        Author2.setFont(font2);
        Place.setFont(font2);
        panel.add(Author1);
        panel.add(Author2);
        panel.add(Place);
        return panel;
    }

    /** Metoda prywatna pomagająca stworzyć nagłówek */
    private JPanel createHeader() {
        JPanel panel = new JPanel();
        JLabel Header = new JLabel("Autorzy");
        Header.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD+Font.ITALIC,20);
        Header.setFont(font);
        panel.add(Header);
        return panel;
    }

}
