package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;
import entity.Entity;
import object.OBJ_Heart;

public class UI {
    GamePanel gamePanel;
    Font font;
    Graphics2D g2;
    BufferedImage heart_full, heart_half, heart_blank;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public String currentDialogue = "";

    public boolean gameFinished = false;
    public int commandNum = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/NotoSans-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        Entity heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(font);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        // states
        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        } else if (gamePanel.gameState == gamePanel.playState) {
            drawPlayerHp();
        } else if (gamePanel.gameState == gamePanel.pauseState) {
            drawPlayerHp();
            drawPauseScreen();
        } else if (gamePanel.gameState == gamePanel.dialogueState) {
            drawPlayerHp();
            drawDialogueScreen();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }

    public void drawDialogueScreen() {
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - gamePanel.tileSize * 4;
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize / 2;
        y += gamePanel.tileSize;

        g2.setFont(g2.getFont().deriveFont(26F));

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 36;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(new Color(0, 0, 0, 190));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x + 8, y + 8, width - 17, height - 17, 25, 25);
    }

    public void drawTitleScreen() {
        // title screen background
        g2.setColor(new Color(206, 108, 178));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // title text
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 96F));
        String text = "Title???";
        int x = getXForCenteredText(text);
        int y = gamePanel.tileSize * 3;

        // shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 2, y + 2);

        // draw title text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // draw player
        x = gamePanel.screenWidth / 2;
        y += gamePanel.tileSize * 2;
        g2.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2,
                null);

        // draw bigga
        x += gamePanel.tileSize * 2;
        y += gamePanel.tileSize;
        g2.drawImage(gamePanel.npc[0].down1, x, y, gamePanel.tileSize, gamePanel.tileSize, null);

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "LOAD";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gamePanel.tileSize, y);
        }
    }

    public void drawPlayerHp() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;

        // draw max hp
        for (int i = 0; i < gamePanel.player.maxHp / 2; i++) {
            g2.drawImage(heart_blank, x, y, null);
            x += gamePanel.tileSize;
        }

        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;

        for (int i = 0; i < gamePanel.player.hp; i++) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gamePanel.player.hp) {
                g2.drawImage(heart_full, x, y, null);
            }
            x += gamePanel.tileSize;
        }
    }
}
