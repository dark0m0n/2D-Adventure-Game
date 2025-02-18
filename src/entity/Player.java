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

        attackArea.width = 36;
        attackArea.height = 36;

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
        damage = 1;
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

        if (attacking) {
            attack();
        } else if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed
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
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (attacking) {
                    image = (spriteNum == 1) ? attackUp1 : attackUp2;
                    tempScreenY -= gamePanel.tileSize;
                } else {
                    image = (spriteNum == 1) ? up1 : up2;
                }
                break;
            case "down":
                if (attacking) {
                    image = (spriteNum == 1) ? attackDown1 : attackDown2;
                } else {
                    image = (spriteNum == 1) ? down1 : down2;
                }
                break;
            case "left":
                if (attacking) {
                    image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                    tempScreenX -= gamePanel.tileSize;
                } else {
                    image = (spriteNum == 1) ? left1 : left2;
                }
                break;
            case "right":
                if (attacking) {
                    image = (spriteNum == 1) ? attackRight1 : attackRight2;
                } else {
                    image = (spriteNum == 1) ? right1 : right2;
                }
                break;

            default:
                break;
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
    }

    public void pickUpObject(int index) {
        if (index != 999) {

        }
    }

    public void interactNPC(int npcIndex) {
        if (gamePanel.keyHandler.enterPressed) {
            if (npcIndex != 999) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[npcIndex].speak();
            } else {
                attacking = true;
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

    public void attack() {
        spriteCounter++;

        if (spriteCounter <= 10) {
            spriteNum = 1;
        } else if (spriteCounter <= 25) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            
                default:
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        } else {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damageMonster(int index) {
        if (index != 999) {
            if (!gamePanel.monster[index].invincible) {
                gamePanel.monster[index].hp -= damage;
                gamePanel.monster[index].invincible = true;

                if (gamePanel.monster[index].hp <= 0) {
                    gamePanel.monster[index] = null;
                }
            }
        }
    }
}
