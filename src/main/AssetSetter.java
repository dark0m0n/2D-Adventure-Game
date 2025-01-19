package main;

import entity.NPC_Bigga;

import monster.MON_BlueSlime;
import monster.MON_GreenSlime;

// import object.OBJ_Boots;
// import object.OBJ_Chest;
// import object.OBJ_Door;
// import object.OBJ_Key;

public class AssetSetter {
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {

    }

    public void setNPC() {
        gamePanel.npc[0] = new NPC_Bigga(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 35;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 39;
    }

    public void setMonster() {
        gamePanel.monster[0] = new MON_GreenSlime(gamePanel);
        gamePanel.monster[0].worldX = gamePanel.tileSize * 39;
        gamePanel.monster[0].worldY = gamePanel.tileSize * 11;

        gamePanel.monster[1] = new MON_BlueSlime(gamePanel);
        gamePanel.monster[1].worldX = gamePanel.tileSize * 12;
        gamePanel.monster[1].worldY = gamePanel.tileSize * 33;
    }
}
