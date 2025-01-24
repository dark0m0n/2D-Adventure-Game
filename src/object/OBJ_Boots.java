package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity {
    public OBJ_Boots(GamePanel gamePanel) {
        super(gamePanel);

        name = "Boots";
        image = setup("objects/boots", gamePanel.tileSize, gamePanel.tileSize);
    }
}
