import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authors  extends JFrame {

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
        setLocation(setLoc(Loc));

    }

    private JPanel createFooter(JFrame frame) {
        JPanel panel = new JPanel();
        JButton BackButton = new JButton("Wroc");
        panel.add(BackButton);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        return panel;
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        JLabel Author1 = new JLabel("Michal Ryszka, ");
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

    private JPanel createHeader() {
        JPanel panel = new JPanel();
        JLabel Header = new JLabel("Autorzy");
        Header.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD+Font.ITALIC,20);
        Header.setFont(font);
        panel.add(Header);
        return panel;
    }

    private Point setLoc(int[] Loc) {
        Point result = new Point();
        Loc[2] = Loc[2]/2 - (this.getSize().width/2);
        Loc[3] = Loc[3]/2 - (this.getSize().height/2);
        result.x = Loc[0] + Loc[2];
        result.y = Loc[1] + Loc[3];
        return result;
    }
}
