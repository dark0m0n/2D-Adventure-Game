package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldMapRow][gamePanel.maxWorldMapCol];

        getTileImage();
        loadMap("/res/maps/world01.txt");
    }

    public void getTileImage() {
        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
    }

    public void loadMap(String mapPath) {
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gamePanel.maxWorldMapRow; row++) {
                String line = br.readLine();

                for (int col = 0; col < gamePanel.maxWorldMapCol; col++) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int row = 0; row < gamePanel.maxWorldMapRow; row++) {
            for (int col = 0; col < gamePanel.maxWorldMapCol; col++) {
                int tileNum = mapTileNum[row][col];

                int worldX = row * gamePanel.tileSize;
                int worldY = col * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                        && worldX - gamePanel.tileSize < gamePanel.player.worldX
                                + gamePanel.player.screenX
                        && worldY + gamePanel.tileSize > gamePanel.player.worldY
                                - gamePanel.player.screenY
                        && worldY - gamePanel.tileSize < gamePanel.player.worldY
                                + gamePanel.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }
        }
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO
                    .read(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"));
            tile[index].image =
                    uTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
