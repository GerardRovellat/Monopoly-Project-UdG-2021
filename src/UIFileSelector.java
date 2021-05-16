import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Gerard Rovellat
 * @file UIFileSelector.java
 * @class UIFileSelector
 * @brief Implementació d'un selector de fitxers gràfic per poder jugar al Monopoly
 */

public class UIFileSelector extends JFrame {

    private String rules_name;///<
    private String board_name;///<
    private boolean status = false;///< Estat de

    public UIFileSelector(){
        setSize(750,750);
        setTitle("MONOPOLY CONFIG");
        setLocationRelativeTo(null);
        startComponents();
    }

    private void startComponents() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(null);
        this.getContentPane().add(panel);
        JLabel rules = new JLabel();
        JLabel board = new JLabel();
        rules.setText("Fitxer de regles:");
        board.setText("Fitxer de tauler:");
        rules.setBounds(50, 20, 100, 30);
        board.setBounds(400, 20, 100, 30);
        panel.add(rules);
        panel.add(board);
        JButton browse_rules_button = new JButton();
        JButton browse_board_button = new JButton();
        browse_rules_button.setText("Browse");
        browse_board_button.setText("Browse");
        browse_rules_button.setBounds(150, 20, 100, 30);
        browse_board_button.setBounds(500, 20, 100, 30);
        panel.add(browse_rules_button);
        panel.add(browse_board_button);
        JTextField rules_field = new JTextField();
        JTextField board_field = new JTextField();
        rules_field.setBounds(50, 60, 300, 30);
        board_field.setBounds(400, 60, 300, 30);
        panel.add(rules_field);
        panel.add(board_field);
        JTextArea rules_text_area = new JTextArea();
        JTextArea board_text_area = new JTextArea();
        JScrollPane scroll_rules = new JScrollPane(rules_text_area);
        JScrollPane scroll_board = new JScrollPane(board_text_area);
        rules_text_area.setEditable(false);
        scroll_rules.setBounds(50, 120, 300, 525);
        scroll_rules.setVisible(true);
        panel.add(scroll_rules);
        board_text_area.setEditable(false);
        scroll_board.setBounds(400, 120, 300, 525);
        scroll_board.setVisible(true);
        panel.add(scroll_board);
        ActionListener browse_rules_action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File rules_file;
                int response = 0;
                JFileChooser rules_file_chooser = new JFileChooser(".");
                response = rules_file_chooser.showOpenDialog(panel);
                if (response == JFileChooser.APPROVE_OPTION) {
                    rules_file = rules_file_chooser.getSelectedFile();
                    rules_name = rules_file.getName();
                    rules_field.setText(rules_file.getPath());
                    try (FileReader fr = new FileReader(rules_file)) {
                        String cadena = "";
                        int valor = fr.read();
                        while (valor != -1) {
                            cadena = cadena + (char) valor;
                            valor = fr.read();
                        }
                        rules_text_area.setText(cadena);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        };
        browse_rules_button.addActionListener(browse_rules_action);
        ActionListener browse_board_action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File board_file;
                int response = 0;
                JFileChooser board_file_chooser = new JFileChooser(".");
                response = board_file_chooser.showOpenDialog(panel);
                if (response == JFileChooser.APPROVE_OPTION) {
                    board_file = board_file_chooser.getSelectedFile();
                    board_name = board_file.getName();
                    board_field.setText(board_file.getPath());
                    try (FileReader fr = new FileReader(board_file)) {
                        String cadena = "";
                        int valor = fr.read();
                        while (valor != -1) {
                            cadena = cadena + (char) valor;
                            valor = fr.read();
                        }
                        board_text_area.setText(cadena);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        };
        browse_board_button.addActionListener(browse_board_action);
        JButton next_button = new JButton();
        next_button.setText("Següent");
        next_button.setBounds(600, 650, 100, 30);
        panel.add(next_button);
        next_button.setEnabled(true);
        ActionListener next_action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                status = true;
            }
        };
        next_button.addActionListener(next_action);
    }

    public String getRulesFileName(){
        return rules_name;
    }

    public String getBoardFileName(){
        return board_name;
    }

    public boolean getStatus(){ return status; }

}
