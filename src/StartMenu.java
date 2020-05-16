import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;


/** KLASA OKNA USTAWIEŃ GRY */
public class StartMenu  extends JFrame {

    private JTextField SetNick;
    private final JFrame MainWindow;
    private ButtonGroup DifficultGroup, MusicGroup;

    public StartMenu(int[] Loc, JFrame frame) {

        JPanel Header = createHeader();
        JPanel Content = createContent();
        JPanel Footer = createFooter(this);
        BorderLayout borderLayout = new BorderLayout(5,5);
        setLayout(borderLayout);
        add(Header, BorderLayout.NORTH);
        getContentPane().add(Content, BorderLayout.CENTER);
        add(Footer, BorderLayout.SOUTH);
        pack();
        setSize(new Dimension(400, 200));
        setVisible(true);
        setLocation(setLoc(Loc));
        MainWindow = frame;
    }

    private JPanel createFooter(JFrame thisFrame) {
        JPanel panel = new JPanel();
        JButton BackButton = new JButton("Wróć");
        panel.add(BackButton);
        JButton Next = new JButton("Rozpocznij gre!");
        panel.add(Next);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.dispose();
            }
        });
        Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isnotEmpty(SetNick) && difficultLevelChecked(DifficultGroup)){
                    GameWindow(MainWindow);
                    MainWindow.dispose();
                    thisFrame.dispose();
                }
            }
        });


        return panel;
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        JRadioButton Easy = new JRadioButton("Łatwy");
        JRadioButton Medium = new JRadioButton("Średni");
        JRadioButton Hard = new JRadioButton("Trudny");
        JRadioButton MusicOn = new JRadioButton("Włączona");
        JRadioButton MusicOff = new JRadioButton("Wyłączona");
        DifficultGroup = new ButtonGroup();
        DifficultGroup.add(Easy);
        DifficultGroup.add(Medium);
        DifficultGroup.add(Hard);
        MusicGroup = new ButtonGroup();
        MusicGroup.add(MusicOn);
        MusicGroup.add(MusicOff);
        Font font2 = new Font("Arial", Font.BOLD,15);
        Easy.setFont(font2);
        Medium.setFont(font2);
        Hard.setFont(font2);
        JLabel DifficultLabel = new JLabel("Poziom trudności: ");
        DifficultLabel.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD+Font.ITALIC,15);
        DifficultLabel.setFont(font);
        panel.add(DifficultLabel);
        panel.add(Easy);
        panel.add(Medium);
        panel.add(Hard);
        JLabel MusicLabel = new JLabel("Muzyka: ");
        MusicLabel.setForeground(Color.GRAY);
        MusicLabel.setFont(font);
        panel.add(MusicLabel);
        panel.add(MusicOn);
        panel.add(MusicOff);
        JLabel MusicOptions = new JLabel("Sterowanie muzyka podczas gry: klawisz \"M\" ");
        MusicOptions.setFont(font);
        MusicOptions.setForeground(Color.GRAY);
        panel.add(MusicOptions);
        return panel;
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel();
        JLabel Nick = new JLabel("Imie: ");
        Nick.setForeground(Color.GRAY);
        Font font = new Font("Arial", Font.BOLD+Font.ITALIC,15);
        Nick.setFont(font);
        panel.add(Nick);
        SetNick = new JTextField(15);
        SetNick.setForeground(Color.GRAY);
        SetNick.setFont(font);
        panel.add(SetNick);
        SetNick.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SetNick.setBackground(null);
            }
        });
        return panel;
    }

    //OBLICZA POZYCJE OKNA DO WYSRODKOWANIA
    private Point setLoc(int[] Loc) {
        Point result = new Point();
        Loc[2] = Loc[2]/2 - (this.getSize().width/2);
        Loc[3] = Loc[3]/2 - (this.getSize().height/2);
        result.x = Loc[0] + Loc[2];
        result.y = Loc[1] + Loc[3];
        return result;
    }

    //SPRAWDZA CZY NAZWA GRACZA ZOSTALA WPISANA
    public Boolean isnotEmpty(JTextField Nick){
        if(Nick.getText().trim().isEmpty()){
            SetNick.setBackground(new Color(255, 51, 102));
            JOptionPane.showMessageDialog(null, "Prosze wpisać nick gracza");
            return  false;
        }else{
            return  true;
        }
    }

    //SPRAWDZA CZY WYBRANO POZIOM TRUDNOŚCI
    public Boolean difficultLevelChecked(ButtonGroup RG){
        for (Enumeration<AbstractButton> buttons = RG.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Prosze wybrać poziom trudności");
        return false;
    }

    public void GameWindow(JFrame frame) {
        new GameWindow(frame.getLocationOnScreen(), getNick(), getSelectedButtonText(DifficultGroup), getSelectedButtonText(MusicGroup));
    }


    public String getNick(){
        return SetNick.getText();
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

}

