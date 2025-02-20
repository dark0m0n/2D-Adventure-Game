package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
    GamePanel gamePanel;

    public int worldX, worldY;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2,
            attackRight1, attackRight2;

    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public int actionCD = 0;

    public boolean invincible = false;
    public int invincibleCD = 0;
    boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean hpBarOn = false;
    public int hpBarCD = 0;

    public int type;

    // stats
    public int speed;
    public int maxHp;
    public int hp;
    public int damage = 0;

    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    // objects
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() {}

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        try {
            gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            dialogueIndex = 0;
        }

        dialogueIndex++;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/" + imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY
                        + gamePanel.player.screenY) {
            BufferedImage image = null;

            switch (direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : right2;
                    break;

                default:
                    break;
            }

            // monster HP bar
            if (type == gamePanel.monsterType && hpBarOn) {
                double hpBarScale = (double) gamePanel.tileSize / maxHp;
                double hpBarValue = hpBarScale * hp;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY + gamePanel.tileSize + 9, gamePanel.tileSize + 2,
                        12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY + gamePanel.tileSize + 10, (int) hpBarValue, 10);

                hpBarCD++;

                if (hpBarCD >= 3600) {
                    hpBarOn = false;
                    hpBarCD = 0;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCD = 0;
            }

            g2.drawImage(image, screenX, screenY, null);
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
        gamePanel.collisionChecker.checkPlayer(this);

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

        if (invincible) {
            invincibleCD++;
            if (invincibleCD >= 30) {
                invincible = false;
                invincibleCD = 0;
            }
        }
    }

    public void damageReaction() {
        actionCD = 75;
        direction = gamePanel.player.direction;
    }
}
