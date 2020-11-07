import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class ChangeOutputStream extends OutputStream {

    private JTextArea consoleUpdate;
    private StringBuilder a;
    private String text;

    public ChangeOutputStream(JTextArea consoleUpdate){
        this.consoleUpdate = consoleUpdate;
    }
    @Override
    public void write(int b) throws IOException {

        if(b == '\n'){
            text = a.toString();

            SwingUtilities.invokeLater(new Runnable() {
                public void run()
                {
                    consoleUpdate.append(text);
                }
            });

            a.setLength(0);
        }
        a.append((char) b);
    }
}
