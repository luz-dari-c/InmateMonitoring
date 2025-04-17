package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.AlphaComposite;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class BootstrapButton extends JButton {
    // Paleta de colores principal (#b4b4c3 - RGB: 180,180,195)
    private Color normalColor = new Color(180, 180, 195);  // Color base solicitado
    private Color hoverColor = new Color(160, 160, 180);   // Versión más oscura para hover
    private Color pressedColor = new Color(140, 140, 165); // Más oscuro para pressed
    private Color rippleColor = new Color(200, 200, 220, 120); // Ripple más claro
    private Color textColor = new Color(50, 50, 60);       // Texto oscuro para contraste
    
    // Configuración de diseño
    private int borderRadius = 8; // Bordes ligeramente redondeados
    private float hoverScale = 1.04f; // Efecto de crecimiento al hover
    private float currentScale = 1.0f;
    private Point ripplePoint;
    private float rippleSize = 0;
    private float rippleAlpha = 0;
    private boolean pressed = false;

    public BootstrapButton() {
        super();
        setContentAreaFilled(false);
        setForeground(textColor);
        setBorder(new EmptyBorder(10, 16, 10, 16)); // Padding adecuado
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false); // Eliminar borde de enfoque por defecto
        
        // Animaciones
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animateHover(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                animateHover(false);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                ripplePoint = e.getPoint();
                animateRipple();
            }
        });
    }
    
    private void animateHover(boolean enter) {
        new Thread(() -> {
            float targetScale = enter ? hoverScale : 1.0f;
            float step = enter ? 0.01f : -0.01f;
            
            while ((enter && currentScale < targetScale) || (!enter && currentScale > targetScale)) {
                currentScale += step;
                repaint();
                try { Thread.sleep(10); } catch (Exception ex) {}
            }
        }).start();
    }
    
    private void animateRipple() {
        new Thread(() -> {
            rippleSize = 0;
            rippleAlpha = 0.7f;
            
            while (rippleAlpha > 0) {
                rippleSize += 6;
                rippleAlpha -= 0.04f;
                repaint();
                try { Thread.sleep(10); } catch (Exception ex) {}
            }
            pressed = false;
        }).start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Transformación de escala para efecto hover
        int width = getWidth();
        int height = getHeight();
        g2.translate(width*(1-currentScale)/2, height*(1-currentScale)/2);
        g2.scale(currentScale, currentScale);
        
        // Fondo del botón
        if (pressed) {
            g2.setColor(pressedColor);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(normalColor);
        }
        
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, borderRadius, borderRadius));
        
        // Efecto ripple
        if (ripplePoint != null && rippleAlpha > 0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, rippleAlpha));
            g2.setColor(rippleColor);
            g2.fillOval(
                (int)(ripplePoint.x - rippleSize/2),
                (int)(ripplePoint.y - rippleSize/2),
                (int)rippleSize,
                (int)rippleSize
            );
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        
        g2.dispose();
        super.paintComponent(g);
    }
    
    // Métodos de personalización
    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        repaint();
    }
    
    public void setButtonColors(Color normal, Color hover, Color pressed) {
        this.normalColor = normal;
        this.hoverColor = hover;
        this.pressedColor = pressed;
        repaint();
    }
    
    public void setRippleColor(Color color) {
        this.rippleColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 120);
        repaint();
    }
    
    public void setTextColor(Color color) {
        this.textColor = color;
        setForeground(color);
        repaint();
    }
    
    public void setHoverScale(float scale) {
        this.hoverScale = scale;
    }
    
    // Variante con sombra (opcional)
    public void enableShadow(boolean enable) {
        if (enable) {
            setBorder(new EmptyBorder(8, 14, 12, 14)); // Más padding inferior para sombra
        } else {
            setBorder(new EmptyBorder(10, 16, 10, 16));
        }
    }
}