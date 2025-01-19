package monster;

import java.awt.Rectangle;
import java.util.Random;
import entity.Entity;

import main.GamePanel;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);

        name = "Green Slime";
        speed = 1;
        maxHp = 2;
        hp = maxHp;
        damage = 1;

        type = gamePanel.monsterType;

        solidArea = new Rectangle(3, 18, 42, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("monsters/green_slime/down_1");
        up2 = setup("monsters/green_slime/down_2");
        down1 = up1;
        down2 = up2;
        left1 = up1;
        left2 = up2;
        right1 = up1;
        right2 = up2;
    }

    public void setAction() {
        actionCD++;

        if (actionCD == 100) {
            Random random = new Random();
            int number = random.nextInt(100) + 1;

            if (number <= 25) {
                direction = "up";
            } else if (number <= 50) {
                direction = "down";
            } else if (number <= 75) {
                direction = "left";
            } else if (number <= 100) {
                direction = "right";
            }

            actionCD = 0;
        }
    }
}
