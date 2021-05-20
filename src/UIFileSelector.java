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

    private String rules_name;             ///< Ruta fitxer de regles.
    private String board_name;             ///< Ruta fitxer de tauler.
    private boolean status = false;        ///< Estat de selector de fitxers, true si s'ha sel·leccionat false altrament

    /**
     * @brief Constructor de UIFileSelector().
     * @pre \p true
     * @post Crea un sel·lector de fitxers d'entrada.
     */
    public UIFileSelector(){
        setSize(750,750);
        setTitle("MONOPOLY CONFIG");
        setLocationRelativeTo(null);
        startComponents();
    }

    /**
     * @brief Inicialitza i crea tota la finestra.
     * @pre \p true
     * @post Finestra creada amb tota la seva interficie grafica.
     */
    private void startComponents() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(null);
        this.getContentPane().add(panel);
        createLabels(panel);
        JButton browse_rules_button = new JButton();
        JButton browse_board_button = new JButton();
        JButton next_button = new JButton();
        defineButtons(browse_rules_button,browse_board_button,next_button,panel);
        JTextField rules_field = new JTextField();
        JTextField board_field = new JTextField();
        defineTextFields(rules_field,board_field,panel);
        JTextArea rules_text_area = new JTextArea();
        JTextArea board_text_area = new JTextArea();
        defineTextAreas(rules_text_area,board_text_area,panel);
        createBrowseButtonAction(browse_rules_button,rules_field,rules_text_area,"rules",panel);
        createBrowseButtonAction(browse_board_button,board_field,board_text_area,"board",panel);
        createNextButtonAction(next_button);
    }
    /**
     * @brief Getter de nom de fitxer regles \p rules_name.
     * @pre \p true
     * @post el nom de fitxer \p rules_name ha estat tornat.
     */
    public String getRulesFileName(){
        return rules_name;
    }

    /**
     * @brief Getter de nom de fitxer tauler \p board_name.
     * @pre \p true
     * @post el nom de fitxer \p board_name ha estat tornat.
     */
    public String getBoardFileName(){
        return board_name;
    }

    /**
     * @brief Getter de estat de finestra \p status.
     * @pre \p true
     * @post el estat de finestra \p status ha estat tornat.
     */
    public boolean getStatus(){ return status; }

    /**
     * @brief Crea etiquetes i les afegeix a la finestra.
     * @pre \p true
     * @post Etiquetes afegides a la finestra.
     */
    private void createLabels(JPanel panel){
        JLabel rules = new JLabel();
        JLabel board = new JLabel();
        rules.setText("Fitxer de regles:");
        board.setText("Fitxer de tauler:");
        rules.setBounds(50, 20, 100, 30);
        board.setBounds(400, 20, 100, 30);
        panel.add(rules);
        panel.add(board);
    }

    /**
     * @brief Afegeix botos a la finestra.
     * @pre \p true
     * @post Botons definits a la finestra.
     */
    private void defineButtons(JButton browse_rules_button, JButton browse_board_button, JButton next_button, JPanel panel){
        browse_rules_button.setText("Browse");
        browse_board_button.setText("Browse");
        browse_rules_button.setBounds(150, 20, 100, 30);
        browse_board_button.setBounds(500, 20, 100, 30);
        panel.add(browse_rules_button);
        panel.add(browse_board_button);
        next_button.setText("Següent");
        next_button.setBounds(600, 650, 100, 30);
        panel.add(next_button);
        next_button.setEnabled(true);
    }


    /**
     * @brief Afegeix camps de text a la finestra.
     * @pre \p true
     * @post Els camps de text han estat definits.
     */
    private void defineTextFields(JTextField rules_field, JTextField board_field, JPanel panel){
        rules_field.setBounds(50, 60, 300, 30);
        board_field.setBounds(400, 60, 300, 30);
        panel.add(rules_field);
        panel.add(board_field);
    }

    /**
     * @brief Afegeix areas de text a la finestra.
     * @pre \p true
     * @post Les areas de text han estat definides.
     */
    private void defineTextAreas(JTextArea rules_text_area, JTextArea board_text_area , JPanel panel){

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
    }

    /**
     * @brief Crea les accions pels botons browse.
     * @pre \p true
     * @post Accions creades per a cada boto de browse.
     */
    private void createBrowseButtonAction(JButton browse_button, JTextField browse_field, JTextArea browse_textarea, String name_button, JPanel panel){
        ActionListener browse_action = e -> {
            File file;
            int response;
            JFileChooser board_file_chooser = new JFileChooser(".");
            response = board_file_chooser.showOpenDialog(panel);
            if (response == JFileChooser.APPROVE_OPTION) {
                if(name_button.equals("rules")){
                    file = board_file_chooser.getSelectedFile();
                    rules_name = file.getPath();
                }
                else{
                    file = board_file_chooser.getSelectedFile();
                    board_name = file.getPath();
                }
                browse_field.setText(file.getPath());
                try (FileReader fr = new FileReader(file)) {
                    StringBuilder cadena = new StringBuilder();
                    int valor = fr.read();
                    while (valor != -1) {
                        cadena.append((char) valor);
                        valor = fr.read();
                    }
                    browse_textarea.setText(cadena.toString());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        };
        browse_button.addActionListener(browse_action);
    }

    /**
     * @brief Crea les accions pel boto next.
     * @pre \p true
     * @post Accions creades pel boto next.
     */
    private void createNextButtonAction(JButton next_button){
        ActionListener next_action = e -> {
            status = true;
            dispose();

        };
        next_button.addActionListener(next_action);
    }

}
