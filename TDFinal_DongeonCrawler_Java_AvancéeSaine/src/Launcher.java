import javax.swing.*;

public class Launcher {
    public static void main(String[] args) throws Exception{
        SwingUtilities.invokeLater(()-> {
            WelcomePage starterPage = new WelcomePage ();
        } );
    }
} 