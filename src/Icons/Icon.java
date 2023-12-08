
package Icons;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Icon extends javax.swing.JLabel{
    int x,y;
    private String ruta;
    
    public Icon(JPanel panel, String ruta){
        this.ruta = ruta;
        this.x = panel.getWidth();
        this.y = panel.getHeight();
        this.setSize(x, y);
    }
    
    public void paint(Graphics g){
        ImageIcon img = new ImageIcon(getClass().getResource(ruta));
        g.drawImage(img.getImage(), 0, 0, x, y, null);
    }
}
