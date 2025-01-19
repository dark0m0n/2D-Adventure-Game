package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {    
    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);

        name = "Key";
        image = setup("objects/key");
    }
}
