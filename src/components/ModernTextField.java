package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ModernTextField extends JTextField {
    private Color borderColor = new Color(200, 200, 210);
    private Color focusColor = new Color(100, 150, 255);
    private Color bgColor = new Color(250, 250, 255);
    private int borderRadius = 8;
    private String placeholder = "";

    public ModernTextField() {
        setOpaque(false);
        setBorder(new EmptyBorder(10, 15, 10, 15));
        setBackground(bgColor);
        setForeground(new Color(60, 60, 70));
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        
        // Borde
        if (hasFocus()) {
            g2.setColor(focusColor);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, borderRadius, borderRadius);
            
            // Efecto de sombra interior
            g2.setColor(new Color(focusColor.getRed(), focusColor.getGreen(), focusColor.getBlue(), 30));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        } else {
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, borderRadius, borderRadius);
        }
        
        g2.dispose();
        super.paintComponent(g);
        
        // Placeholder
        if (getText().isEmpty() && !placeholder.isEmpty() && !hasFocus()) {
            g2 = (Graphics2D) g.create();
            g2.setColor(new Color(150, 150, 160));
            g2.setFont(getFont().deriveFont(getFont().getStyle() | java.awt.Font.ITALIC));
            Insets insets = getInsets();
            g2.drawString(placeholder, insets.left, getHeight()/2 + getFont().getSize()/2 - 2);
            g2.dispose();
        }
    }

    // Métodos de personalización
    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }
    
    public void setFocusColor(Color color) {
        this.focusColor = color;
        repaint();
    }
    
    public void setPlaceholder(String text) {
        this.placeholder = text;
        repaint();
    }
    
    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        repaint();
    }
}