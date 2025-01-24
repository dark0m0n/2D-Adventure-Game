package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    // public int keys = 0;

    int standCounter = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        solidArea = new Rectangle(8, 16, 31, 31);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 35;
        worldY = gamePanel.tileSize * 21;

        // stats
        speed = 4;
        maxHp = 6;
        hp = maxHp;
    }

    public void getPlayerImage() {
        up1 = setup("player/player_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("player/player_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("player/player_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("player/player_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("player/player_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("player/player_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("player/player_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("player/player_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void update() {
        if (invincible) {
            invincibleCD++;
            if (invincibleCD >= 30) {
                invincible = false;
                invincibleCD = 0;
            }
        }

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed
                || keyHandler.rightPressed || keyHandler.enterPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }

            // check tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // check object collision
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // check events
            gamePanel.eventHandler.checkEvent(true);

            // check npc collision
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            interactNPC(npcIndex);

            // check monster collision
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            contactMonster(monsterIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;

                    default:
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;

            if (standCounter == 17) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;

            default:
                break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }

    public void pickUpObject(int index) {
        if (index != 999) {

        }
    }

    public void interactNPC(int npcIndex) {
        if (npcIndex != 999) {
            if (gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[npcIndex].speak();
            }
        }
    }

    public void contactMonster(int monsterIndex) {
        if (monsterIndex != 999) {
            if (!invincible) {
                hp -= gamePanel.monster[monsterIndex].damage;
                invincible = true;
            }
        }
    }

    public void getPlayerAttackImage() {
        attackDown1 = setup("player/attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("player/attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp1 = setup("player/attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("player/attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackRight1 = setup("player/attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("player/attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft1 = setup("player/attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("player/attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }
}
