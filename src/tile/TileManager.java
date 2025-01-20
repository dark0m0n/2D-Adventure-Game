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

        tile = new Tile[32];
        mapTileNum = new int[gamePanel.maxWorldMapRow][gamePanel.maxWorldMapCol];

        getTileImage();
        loadMap("/res/maps/world01.txt");
    }

    public void getTileImage() {
        // grass
        setup(0, "grass_1", false);
        setup(1, "grass_2", false);
       
        // sand
        setup(3, "sand_1", false);
        setup(4, "sand_2", false);
        setup(5, "sand_3", false);
        setup(5, "sand_4", false);
        setup(6, "sand_5", false);
        setup(7, "sand_6", false);
        setup(8, "sand_7", false);
        setup(9, "sand_8", false);
        setup(10, "sand_9", false);
        setup(11, "sand_10", false);
        setup(12, "sand_11", false);
        setup(13, "sand_12", false);
        setup(14, "sand_13", false);

        // tree
        setup(15, "tree", true);

        // wall
        setup(16, "wall", true);

        // water
        setup(17, "water_1", true);
        setup(18, "water_2", true);
        setup(19, "water_3", true);
        setup(20, "water_4", true);
        setup(21, "water_5", true);
        setup(22, "water_6", true);
        setup(23, "water_7", true);
        setup(24, "water_8", true);
        setup(25, "water_9", true);
        setup(26, "water_10", true);
        setup(27, "water_11", true);
        setup(28, "water_12", true);
        setup(29, "water_13", true);
        setup(30, "water_14", true);

        // earth
        setup(31, "earth", false);
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
