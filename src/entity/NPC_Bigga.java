package entity;

import java.util.Random;

import java.awt.Rectangle;

import main.GamePanel;

public class NPC_Bigga extends Entity {
    public NPC_Bigga(GamePanel gamePanel) {
        super(gamePanel);

        speed = 1;

        solidArea = new Rectangle(8, 16, 31, 31);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("npc/bigga/up_1");
        up2 = setup("npc/bigga/up_2");
        down1 = setup("npc/bigga/down_1");
        down2 = setup("npc/bigga/down_2");
        left1 = setup("npc/bigga/left_1");
        left2 = setup("npc/bigga/left_2");
        right1 = setup("npc/bigga/right_1");
        right2 = setup("npc/bigga/right_2");
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

    public void setDialogue() {
        dialogues[0] = "Get lost";
        dialogues[1] = "You suck";
    }
}
