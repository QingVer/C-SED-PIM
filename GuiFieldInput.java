import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Georgew on 13/04/2016.
 */
@SuppressWarnings("serial")
public class GuiFieldInput extends JFrame implements ActionListener, ComponentListener {
    //Booleans
    boolean ready;
    boolean maxSizeReached;
    Thread thread;
    //Colours
    Color correct = new Color(255, 255, 255);
    Color wrong = new Color(255, 214, 207);
    //variables
    JPanel center;
    JScrollPane centerScroll;
    ArrayList<JTextComponent> stringFields = new ArrayList<>();
    ArrayList<String> stringAnswers = new ArrayList<>();
    ArrayList<JTextField> intFields = new ArrayList<>();
    ArrayList<String> intAnswers = new ArrayList<>();
    ArrayList<String> stringQuestions;
    ArrayList<String> intQuestions;

    JPanel north;
    JPanel south;
    JButton doneButton;
    JButton cancelButton;

    /**
     * Constructs a basic GUI with field inputs. Makes a field for each element in
     * each arrayList, stringQuestion fields have to take a string, int.. take int inputs
     *
     * Adding /0 to the end of a stringQuestion allows multiple line input.
     * @param title
     * The title displayed at the top of the GUI
     * @param stringQuestions
     * The field name's to display and get input for.
     * @param intQuestions
     * The field name's to display and get int inputs for.
     */
    public GuiFieldInput(String title, ArrayList<String> stringQuestions, ArrayList<String> intQuestions) {
        super(title);
        //setsUp GUI
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.stringQuestions = stringQuestions;
        this.intQuestions = intQuestions;
        thread = Thread.currentThread();
        maxSizeReached = false;
        //Top
        north = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel(this.getTitle());
        titleLabel.setFont(new Font("Lucida", Font.BOLD, 24));
        Image image = DynOSor.getLogo();
        titleLabel.setIcon(new ImageIcon(image.getScaledInstance(75,75,Image.SCALE_DEFAULT)));
        titleLabel.setFont(new Font("Lucida",Font.BOLD,24));
        titleLabel.setText(title);
        north.add(titleLabel);
        //Center
        center = new JPanel();
        center.setMinimumSize(new Dimension(500, 0));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setAlignmentX(LEFT_ALIGNMENT);
        centerScroll = new JScrollPane(center);
        centerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //Center Components
        for (int i = 0; i < stringQuestions.size(); i++) {
            JLabel label = new JLabel(stringQuestions.get(i));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            center.add(label);
            if (stringQuestions.get(i).contains("\0")) {
                JTextArea field = new JTextArea();
                field.addComponentListener(this);
                stringFields.add(field);
                field.setAlignmentX(Component.LEFT_ALIGNMENT);
                center.add(field);
            } else {
                JTextField field = new JTextField();
                field.addActionListener(this);
                field.addComponentListener(this);
                stringFields.add(field);
                field.setAlignmentX(Component.LEFT_ALIGNMENT);
                center.add(field);
            }
            stringAnswers.add("");
        }
        for (int i = 0; i < intQuestions.size(); i++) {
            JLabel label = new JLabel();
            if (intQuestions.get(i).contains("\0")) {
                label.setText(intQuestions.get(i) + " (Float)");
            } else {
                label.setText(intQuestions.get(i) + " (Number)");
            }
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            center.add(label);
            JTextField field = new JTextField();
            field.addActionListener(this);
            field.setActionCommand("int" + i);
            intFields.add(field);
            center.add(field);
            intAnswers.add("");
        }


        //Buttons
        south = new JPanel();
        south.setLayout(new FlowLayout());
        doneButton = new JButton("Done");
        doneButton.addActionListener(this);
        south.add(doneButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        south.add(cancelButton);

        ready = false;
        //Setup Main Frame
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    setLayout(new BorderLayout());
                    add(north, BorderLayout.PAGE_START);
                    add(centerScroll, BorderLayout.CENTER);
                    add(south, BorderLayout.PAGE_END);
                    setVisible(true);
                    setMaximumSize(new Dimension(500, 500));
                    setAlwaysOnTop(true);
                    pack();
                    requestFocus();
                    setAlwaysOnTop(false);
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls the main constructor, then adds the given answers into
     * the respective JTextFields/Areas
     * @param title
     * The title to be displayed
     * @param title
     * The title displayed at the top of the GUI
     * @param stringQuestions
     * The field name's to display and get input for.
     * @param stringAnswers
     * The field content to add.
     * @param intQuestions
     * The field name's to display and get int inputs for.
     * @param intAnswers
     * The field content to be added.
     */
    public GuiFieldInput(String title, ArrayList<String> stringQuestions, ArrayList<String> stringAnswers, ArrayList<String> intQuestions, ArrayList<String> intAnswers) {
        this(title, stringQuestions, intQuestions);
        assert stringQuestions.size() == stringAnswers.size() && intQuestions.size() == intAnswers.size();

        for (int i = 0; i < stringAnswers.size(); i++) {
            stringFields.get(i).setText(stringAnswers.get(i));
        }
        for (int i = 0; i < intAnswers.size(); i++) {
            intFields.get(i).setText(intAnswers.get(i));
        }
    }

    /**
     * checks the content of the respective field!
     * @param id
     * Field ID to check.
     * @return
     * true or false depending on if the content is correct.
     */
    protected boolean getIntInput(int id) {
        String input = intFields.get(id).getText();
        if (intQuestions.get(id).contains("\0")) {
            try {
                Float.parseFloat(input);
                intAnswers.set(id, input);
                intFields.get(id).setBackground(correct);
                return true;
            } catch (NumberFormatException ex) {
                intFields.get(id).setBackground(wrong);
                return false;
            }
        } else {
            try {
                Long.parseLong(input);
                intAnswers.set(id, input);
                intFields.get(id).setBackground(correct);
                return true;
            } catch (NumberFormatException ex) {
                intFields.get(id).setBackground(wrong);
                return false;
            }
        }
    }

    /**
     * checks the content of the respective field!
     * @param id
     * Field ID to check.
     * @return
     * true or false depending on if the content is correct.
     */
    protected boolean getStringInput(int id) {
        String input = stringFields.get(id).getText();
        if (input.equals("")) {
            stringFields.get(id).setBackground(wrong);
            return false;
        } else {
            stringAnswers.set(id, input);
            stringFields.get(id).setBackground(correct);
            return true;
        }
    }

    /**
     * Waits for the user to input all data before returning
     * @throws QuitException
     * If the users clicks cancel.
     */
    public void getInput() throws QuitException {
        while (!ready) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                dispose();
                throw new QuitException();
            }
        }
    }

    /**
     * @return
     * All the string field contents.
     */
    public ArrayList<String> getStringAnswers() {
        return stringAnswers;
    }

    /**
     * @return
     * All the string field contents.
     */
    public ArrayList<String> getIntAnswers() {
        return intAnswers;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().startsWith("int")) {
            int id = Integer.parseInt(e.getActionCommand().charAt(3) + "");
            getIntInput(id);
        } else if (e.getActionCommand().startsWith("String")) {
            int id = Integer.parseInt(e.getActionCommand().charAt(6) + "");
            getStringInput(id);
        } else if (e.getActionCommand().equals("Done")) {
            boolean ready = true;
            for (int i = 0; i < stringFields.size(); i++) {
                if (!getStringInput(i)) {

                    ready = false;
                }
            }
            for (int i = 0; i < intFields.size(); i++) {
                if (!getIntInput(i)) {
                    ready = false;
                }
            }
            if (ready) {
                this.ready = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "One Or More Fields Are Incorrect!");
            }
        } else if (e.getActionCommand().equals("Cancel")) {
            thread.interrupt();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        pack();
            /*if((centerScroll.getHeight() > 700 || centerScroll.getWidth() > 700)&& !maxSizeReached){
                centerScroll.setPreferredSize(new Dimension(700,700));
                centerScroll.repaint();
                maxSizeReached = true;
            }*/
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
