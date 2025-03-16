import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Sprite implements Displayable{
    protected Image image;
    //Position modélisée par x et y
    protected double x;
    protected double y;
    //Taille modélisée par width et height
    protected double width;
    protected double height;

    public Sprite (Image image, double x, double y, double width, double height) {
        super();

        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

 @Override
 public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
 }
}
