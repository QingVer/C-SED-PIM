import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Expands GuiFieldInput. Checks if string fields with /0
 * are in the correct date format.
 *
 * @author George Andrews
 * @version 1.0, Sprint 3
 */
public class GuiDateInput extends GuiFieldInput {
    public GuiDateInput(String title, ArrayList<String> stringQuestions, ArrayList<String> intQuestions){
        super(title, stringQuestions, intQuestions);
    }

    public GuiDateInput(String title, ArrayList<String> stringQuestions, ArrayList<String> stringAnswers, ArrayList<String> intQuestions, ArrayList<String> intAnswers){
        super(title, stringQuestions, stringAnswers, intQuestions, intAnswers);
    }

    /**
     * Checks the inputs for the field with given ID.
     * @param id
     * Field ID to check.
     * @return
     * If the field content was correct.
     */
    protected boolean getStringInput(int id) {
        String input = stringFields.get(id).getText();
        if(!stringQuestions.get(id).contains("dd/MM/yyyy")) {
            return super.getStringInput(id);
        } else {
            try {
                String date =
                        ((new SimpleDateFormat("dd/MM/yyyy HH:mm"))
                                .parse(input)).toString();
                stringFields.get(id).setBackground(correct);
                stringAnswers.set(id, input);
                return true;
            } catch (ParseException e){
                stringFields.get(id).setBackground(wrong);
                return false;
            }
        }
    }
}
