package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField {
    private String placeholder;
    private Color placeholderColor = Color.GRAY;
    private Color normalColor = Color.BLACK;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setPlaceholderText(); 
        addFocusListener(new PlaceholderFocusListener());
    }

    private void setPlaceholderText() {
        setText(placeholder);
        setForeground(placeholderColor);
    }

    private void removePlaceholderText() {
        if (getText().equals(placeholder)) {
            setText("");
        }
        setForeground(normalColor);
    }

    @Override
    public String getText() {
        String text = super.getText();
        return text.equals(placeholder) ? "" : text; // Devuelve vacío si es el placeholder
    }

    private class PlaceholderFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            removePlaceholderText();
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (getText().isEmpty()) {
                setPlaceholderText();
            }
        }
    }
}
