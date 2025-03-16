import java.util.ArrayList;
import java.util.LinkedList;

public class PhysicEngine implements Engine{

    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList<Sprite> environment;

    public PhysicEngine() {
        movingSpriteList = new ArrayList<DynamicSprite>();
    }

    public void addToMovingSpriteList(DynamicSprite sprite){
        this.movingSpriteList.add(sprite);
    }

    public void setEnvironment(ArrayList<Sprite> environment){
        this.environment = environment;
    }

    @Override
    public void update(){
        for (DynamicSprite elt : movingSpriteList){
            elt.moveIfPossible(environment);
            elt.playerAttacked(environment,DynamicSprite.trapDamagePoint);
            elt.playerShielding(environment,DynamicSprite.shieldPoint);
            try {
                elt.playerTeleporting(environment);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };
}
