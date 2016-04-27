
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Creates a basic GUI with buttons with given
 * options. The user can select a button which is
 * then returned.
 * Use to get single selections from users.
 * @author George Andrews
 * @version 1.0
 */
public class GuiButtonInput extends JFrame implements ActionListener, ComponentListener{
    //Variables
    private JPanel north;
    private JScrollPane centerScroll;
    private JPanel center;
    private JPanel south;
    private String buttonPressed;
    private Thread thread;
    private JLabel titleLabel;
    private boolean dispose;

    /**
     * Builds the gui with a button for each String in
     * options.
     * @param title
     * The title displayed at the top of the GUI
     * @param options
     * The list of strings to display on buttons
     */
    public GuiButtonInput(String title, ArrayList<String> options){
        super(title);
        dispose = true;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonPressed = "";
        thread = Thread.currentThread();

        //Setup north
        north = new JPanel();
        titleLabel = new JLabel();
        Image image = DynOSor.getLogo();
        titleLabel.setIcon(new ImageIcon(image.getScaledInstance(75,75,Image.SCALE_DEFAULT)));
        titleLabel.setFont(new Font("Lucida",Font.BOLD,24));
        titleLabel.setText(title);
        north.add(titleLabel);

        //Setup Center
        center = new JPanel();
        centerScroll = new JScrollPane(center);
        centerScroll.addComponentListener(this);
        centerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        centerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerScroll.setMaximumSize(new Dimension(500,500));
        center.setLayout(new GridLayout(0,1));
        for(int i = 0; i < options.size(); i++){
            JButton button = new JButton(options.get(i).replace("\n"," "));
            button.addActionListener(this);
            center.add(button);
        }

        //Setup South
        south = new JPanel(new FlowLayout());
        JButton button = new JButton("Cancel");
        button.addActionListener(this);
        south.add(button);

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    setLayout(new BorderLayout());
                    add(north, BorderLayout.PAGE_START);
                    add(centerScroll, BorderLayout.CENTER);
                    add(south,BorderLayout.PAGE_END);
                    setVisible(true);
                    setAlwaysOnTop(true);
                    setMinimumSize(new Dimension(200, 0));
                    setMaximumSize(new Dimension(800,800));
                    pack();
                    setAlwaysOnTop(false);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls the main constructor. Then add a descriptive JTextArea
     * below each button containing the respective text from optionsDetails
     * @param title
     * The title to display
     * @param options
     * The Buttons to create
     * @param optionsDetails
     * The text to display below the buttons.
     */
    public GuiButtonInput(String title, ArrayList<String> options, ArrayList<String> optionsDetails){
        this(title,options);
        assert options.size() == optionsDetails.size();
        center.removeAll();
        center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
            for(int i = 0; i < options.size(); i++){
                JButton button = new JButton(options.get(i).replace("\n"," "));
                button.addActionListener(this);
                button.setAlignmentX(Component.LEFT_ALIGNMENT);
                center.add(button);
                JTextArea textArea = new JTextArea(optionsDetails.get(i));
                textArea.setEditable(false);
                textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                center.add(textArea);
                center.repaint();
            }
        pack();
    }

    /**
     * Waits for the user to click a button then
     * returns which button was clicked. Then calls dispose.
     * @return
     * The text on the button clicked.
     * @throws QuitException
     * If the player clicks cancel.
     */
    public String getInput() throws QuitException{
        while(buttonPressed.equals("")){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                throw new QuitException();
            }
        }
        return buttonPressed;
    }

    /**
     * Changes the label text at the top of the interface.
     * @param title
     * What to set the label text to.
     */
    public void setTitle(String title){
        titleLabel.setText(title);
        pack();
        pack();
    }

    /**
     * Changes if the frame is disposed after a selection
     * is made.
     * @param dispose
     * True - frame gets disposed.
     */
    public void setDispose(boolean dispose){
        this.dispose = dispose;
    }

    /**
     * Sets the button pressed to nothing so
     * another selection can be made.
     */
    public void resetButtonPressed(){
        buttonPressed = "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Cancel")){
            thread.interrupt();
        } else {
            buttonPressed = e.getActionCommand();
        }
        if(dispose) {
            dispose();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(centerScroll.getHeight() > 800){
            centerScroll.setPreferredSize(new Dimension(300,800));
        }
        pack();
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
