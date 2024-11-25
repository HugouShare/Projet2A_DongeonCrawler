import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    private boolean isWalking = true;
    static double normalspeed = 5;
    static double acceleration = 15;
    static final double trapDamagePoint = 5;
    static final double maxLifePoint = 100;
    static final double shieldPoint = 20;
    static double playerLifePoint = 100;
    static double speed = normalspeed;
    final int spriteSheetNumberOfColumn = 10;
    protected int timeBetweenFrame = 200;
    private Direction direction = Direction.NORTH;

    public DynamicSprite(Image image,double x,double y,double width,double height){
        super(image,x,y,width,height);
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    private void move(){
    switch (direction){
        case NORTH ->{
            this.y -= speed;
        }
        case SOUTH ->{
            this.y += speed;
        }
        case EAST ->{
            this.x += speed;
        }
        case WEST ->{
            this.x -= speed;
        }
    }
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch (direction) {
            case NORTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed + height/2, super.getHitBox().getWidth(), super.getHitBox().getHeight()/2 );
                break;
            case SOUTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed + height/2, super.getHitBox().getWidth(), super.getHitBox().getHeight()/2);
                break;
            case EAST:
                moved.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY() + height/2, super.getHitBox().getWidth(), super.getHitBox().getHeight()/2);
                break;
            case WEST:
                moved.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY() + height/2, super.getHitBox().getWidth(), super.getHitBox().getHeight()/2);
                break;
        }
        for (Sprite sprite : environment) {
            if ((sprite instanceof HealthSources) && sprite != this) {
                if (sprite.getHitBox().intersects(moved)) {
                    return true;
                }
            }
            if ((sprite instanceof TeleportationDoor) && sprite != this) {
                if (sprite.getHitBox().intersects(moved)) {
                    return true;
                }
            }
            if ((sprite instanceof Traps) && sprite != this) {
                if (sprite.getHitBox().intersects(moved)) {
                    return true;
                }
            }
            if ((sprite instanceof SolidSprite) && sprite != this) {
                if (sprite.getHitBox().intersects(moved)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }

    private boolean isPlayerAttacked(ArrayList<Sprite> environment) {
        Rectangle2D.Double nextPosition = new Rectangle2D.Double();
        switch (direction) {
            case NORTH:
                nextPosition.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed, super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:
                nextPosition.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed, super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case EAST:
                nextPosition.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY() - speed, super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:
                nextPosition.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY() - speed, super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }
        for (Sprite sprite : environment) {
            if ((sprite instanceof Traps) && sprite != this){
                if (sprite.getHitBox().intersects(nextPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void playerAttacked(ArrayList<Sprite> environment, double damage){
        if (isPlayerAttacked(environment)){
            setPlayerDamage (damage);
        }
    }

    private boolean isPlayerShielding(ArrayList<Sprite> environment) {
        Rectangle2D.Double nextPosition = new Rectangle2D.Double();
        switch (direction) {
            case NORTH:
                nextPosition.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed, super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:
                nextPosition.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed, super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case EAST:
                nextPosition.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY() - speed, super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:
                nextPosition.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY() - speed, super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }
        for (Sprite sprite : environment) {
            if ((sprite instanceof HealthSources) && sprite != this){
                if (sprite.getHitBox().intersects(nextPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void playerShielding(ArrayList<Sprite> environment, double shield){
        if (isPlayerShielding(environment) && playerLifePoint<maxLifePoint){
            this.playerLifePoint += shield;
        }
    }

    private boolean isPlayerTeleporting(ArrayList<Sprite> environment) {
        Rectangle2D.Double nextpos = new Rectangle2D.Double();
        switch (direction) {
            case NORTH:
                nextpos.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed + height / 2, super.getHitBox().getWidth(), super.getHitBox().getHeight() / 2);
                break;
            case SOUTH:
                nextpos.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed + height / 2, super.getHitBox().getWidth(), super.getHitBox().getHeight() / 2);
                break;
            case EAST:
                nextpos.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY() + height / 2, super.getHitBox().getWidth(), super.getHitBox().getHeight() / 2);
                break;
            case WEST:
                nextpos.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY() + height / 2, super.getHitBox().getWidth(), super.getHitBox().getHeight() / 2);
                break;
        }
        for (Sprite sprite : environment) {
            if ((sprite instanceof TeleportationDoor) && sprite != this) {
                if (sprite.getHitBox().intersects(nextpos)) {
                    return true;
                }
            }
        }
        return false;
    }

    int numberOfTransportationDone = 0;
    public void playerTeleporting (ArrayList<Sprite> environment) throws Exception {
        if (isPlayerTeleporting(environment) && numberOfTransportationDone==0){
            numberOfTransportationDone++;
            Main.displayZoneFrame.dispose();
            new MainNextLevel();
        }
    }

    public void setPlayerDamage (double damagePoints){
        this.playerLifePoint -= damagePoints;
        Main.isGameOver(DynamicSprite.playerLifePoint);
    }

    public void setSpeed(double settedSpeed){
        this.speed = settedSpeed;
    }

    @Override
    public void draw(Graphics g){
        // --- Animation du héro ---
        int index = (int) (System.currentTimeMillis()/timeBetweenFrame)%spriteSheetNumberOfColumn;
        int attitude = direction.getFrameLineNumber();
        g.drawImage(
                image,(int) x,(int) y,(int) (width+x),(int) (height+y),
                (int) (index*width), (int) (attitude*height),
                (int) ((index+1)*width),(int) ((attitude+1)*height),
                null
                );

        // --- Affichage barre de point de vie ---
        int barWidth = 60;
        int barHeight = 10;
        int barX = (int) (x + (width-barWidth)/2);
        int barY = (int) (y-15);

        g.setColor(Color.RED);
        g.fillRect(barX,barY,barWidth,barHeight);
        g.setColor(Color.GREEN);
        int barWidthCurrent = (int) ((playerLifePoint/maxLifePoint)*barWidth);
        g.fillRect(barX,barY,barWidthCurrent,barHeight);
        g.setColor(Color.BLACK);
        g.drawRect(barX,barY,barWidth,barHeight);
    }
}
