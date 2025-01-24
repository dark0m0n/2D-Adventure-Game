package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
    public OBJ_Door(GamePanel gamePanel) {
        super(gamePanel);

        name = "Door";
        collision = true;
        image = setup("objects/door", gamePanel.tileSize, gamePanel.tileSize);
        
        solidArea.y = 16;
        solidArea.height = 31;
    }
}
