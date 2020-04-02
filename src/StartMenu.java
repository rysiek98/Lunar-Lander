import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

public class StartMenu  extends JFrame {

    private JTextField SetNick;
    private JFrame MainWindow;
    private ButtonGroup DifficultGroup;

    public StartMenu(int Loc[], JFrame frame) {

        JPanel Header = createHeader();
        JPanel Content = createContent();
        JPanel Footer = createFooter(this);
        BorderLayout borderLayout = new BorderLayout(5,5);
        setLayout(borderLayout);
        add(Header, BorderLayout.NORTH);
        getContentPane().add(Content, BorderLayout.CENTER);
        add(Footer, BorderLayout.SOUTH);
        pack();
        setSize(new Dimension(300, 150));
        setVisible(true);
        setLocation(setLoc(Loc));
        MainWindow = frame;
    }

    private JPanel createFooter(JFrame thisFrame) {
        JPanel panel = new JPanel();
        JButton BackButton = new JButton("Wroc");
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
        JRadioButton Easy = new JRadioButton("Latwy");
        JRadioButton Medium = new JRadioButton("Sredni");
        JRadioButton Hard = new JRadioButton("Trudny");
        DifficultGroup = new ButtonGroup();
        DifficultGroup.add(Easy);
        DifficultGroup.add(Medium);
        DifficultGroup.add(Hard);
        Font font2 = new Font("Arial", Font.BOLD,15);
        Easy.setFont(font2);
        Medium.setFont(font2);
        Hard.setFont(font2);
        panel.add(Easy);
        panel.add(Medium);
        panel.add(Hard);
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

    private Point setLoc(int[] Loc) {
        Point result = new Point();
        Loc[2] = Loc[2]/2 - (this.getSize().width/2);
        Loc[3] = Loc[3]/2 - (this.getSize().height/2);
        result.x = Loc[0] + Loc[2];
        result.y = Loc[1] + Loc[3];
        return result;
    }

    public Boolean isnotEmpty(JTextField Nick){
        if(Nick.getText().trim().isEmpty()){
            SetNick.setBackground(new Color(255, 51, 102));
            JOptionPane.showMessageDialog(null, "Prosze wpisać nick gracza");
            return  false;
        }else{
            return  true;
        }
    }

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

    private void GameWindow(JFrame frame) {
        new GameWindow(frame.getLocationOnScreen());
    }

}

