package main;

public class EventHandler {
    GamePanel gamePanel;
    EventRect eventRect[][];
    int previousX, previousY;
    boolean canTouchEvent = false;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new EventRect[gamePanel.maxWorldMapRow][gamePanel.maxWorldMapCol];

        for (int row = 0; row < eventRect.length; row++) {
            for (int col = 0; col < eventRect[row].length; col++) {
                eventRect[row][col] = new EventRect(23, 23, 2, 2);
            }
        }
    }

    public void checkEvent(boolean needToMove) {
        // reset one touch events
        if (!canTouchEvent) {
            int xDistance = Math.abs(gamePanel.player.worldX - previousX);
            int yDistance = Math.abs(gamePanel.player.worldY - previousY);
            int distance = Math.max(xDistance, yDistance);
            if (distance > gamePanel.tileSize) {
                canTouchEvent = true;
            }
        }

        if (needToMove) {
            // player must move for those events
            if (hit(27, 8, "right") && canTouchEvent) {
                damageTrap(27, 8, gamePanel.dialogueState);
            }
        } else {
            // player must stand steel for those events
        }

        // player can move or stand steel for those events
        if (hit(24, 8, "up") && gamePanel.keyHandler.enterPressed) {
            healingPool(gamePanel.dialogueState);
        }
    }

    public boolean hit(int row, int col, String reqDirection) {
        boolean hit = false;

        gamePanel.player.solidArea.x += gamePanel.player.worldX;
        gamePanel.player.solidArea.y += gamePanel.player.worldY;
        eventRect[row][col].x = row * gamePanel.tileSize - eventRect[row][col].x;
        eventRect[row][col].y = col * gamePanel.tileSize - eventRect[row][col].y;

        if (gamePanel.player.solidArea.intersects(eventRect[row][col])) {
            if (gamePanel.player.direction.contentEquals(reqDirection)
                    || reqDirection.contentEquals("any")) {
                hit = true;

                previousX = gamePanel.player.worldX;
                previousY = gamePanel.player.worldY;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
        eventRect[row][col].x = eventRect[row][col].eventRectDefaultX;
        eventRect[row][col].y = eventRect[row][col].eventRectDefaultY;

        return hit;
    }

    public void damageTrap(int row, int col, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fall into a trap";
        gamePanel.player.hp--;
        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You drink the water.\nYour hp has been recovered.";
        gamePanel.player.hp = gamePanel.player.maxHp;
    }
}
