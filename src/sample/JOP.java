package sample;

import javax.swing.*;

public class JOP {
    public static void msg(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static String input(String message) {
        return JOptionPane.showInputDialog(message);
    }
}
