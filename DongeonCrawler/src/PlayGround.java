import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PlayGround {
    private ArrayList<Sprite> environment = new ArrayList<>();

    public PlayGround (String pathName){
        try{
            final Image imageTree = ImageIO.read(new File("./img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));
            final Image imageTrap = ImageIO.read(new File("./img/trap.png"));
            final Image imageShield = ImageIO.read(new File("./img/shield.png"));
            final Image imageTeleportationDoor = ImageIO.read(new File("./img/teleportationDoor.png"));


            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);

            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);

            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);

            final int imageTrapWidth = imageTrap.getWidth(null);
            final int imageTrapHeight = imageTrap.getHeight(null);

            final int imageShieldWidth = imageShield.getWidth(null);
            final int imageShieldHeight = imageShield.getHeight(null);

            final int imageTeleportationDoorWidth = imageTeleportationDoor.getWidth(null);
            final int imageTeleportationDoorHeight = imageTeleportationDoor.getHeight(null);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line=bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;
            while (line!= null){
                for (byte element : line.getBytes(StandardCharsets.UTF_8)){
                    switch (element){
                        case 'T' : environment.add(new SolidSprite(imageTree,
                                lineNumber*imageTreeHeight,columnNumber*imageTreeWidth, imageTreeWidth, imageTreeHeight));
                            break;
                        case ' ' : environment.add(new Sprite(imageGrass,
                                lineNumber*imageGrassHeight, columnNumber*imageGrassWidth, imageGrassWidth, imageGrassHeight));
                            break;
                        case 'R' : environment.add(new SolidSprite(imageRock,
                                lineNumber*imageRockHeight, columnNumber*imageRockWidth, imageRockWidth, imageRockHeight));
                            break;
                        case 'O' : environment.add(new Traps(imageTrap,
                                lineNumber*imageTrapHeight, columnNumber*imageTrapWidth, imageTrapWidth, imageTrapHeight));
                            break;
                        case 'S' : environment.add(new HealthSources(imageShield,
                                lineNumber*imageShieldHeight, columnNumber*imageShieldWidth, imageShieldWidth, imageShieldHeight));
                            break;
                        case 'P' : environment.add(new TeleportationDoor(imageTeleportationDoor,
                                lineNumber*imageTeleportationDoorHeight, columnNumber*imageTeleportationDoorWidth, imageTeleportationDoorWidth, imageTeleportationDoorHeight));
                    }
                    columnNumber++;
                }
                columnNumber =0;
                lineNumber++;
                line=bufferedReader.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Sprite> getSolidSpriteList(){
        ArrayList <Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment){
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList(){
        ArrayList <Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment){
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }
}
