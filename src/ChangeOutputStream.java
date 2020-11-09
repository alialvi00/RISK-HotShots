
import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class ChangeOutputStream extends OutputStream {

    private JTextArea consoleUpdate;
    private StringBuilder a;
    //private String text;

    public ChangeOutputStream(JTextArea consoleUpdate){
        a = new StringBuilder();
        this.consoleUpdate = consoleUpdate;
    }
    @Override
    public void write(int b) {

        if(b == '\n'){
            final String text = a.toString();

            SwingUtilities.invokeLater(() -> consoleUpdate.append(text));

            a.setLength(0);
        }
        a.append((char) b);
    }
}