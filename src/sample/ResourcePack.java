package sample;

import javafx.scene.image.Image;

public enum ResourcePack {
    GRASS("images/blocks/grassBlock.png", "images/blocks/woodPlank.png", "images/characters/creeper.png"),
    CAVE("images/blocks/stoneBrickBlock.png", "images/blocks/lavaBlock.png", "images/characters/spider.png"),
    NETHER("images/blocks/netherrack.png", "images/blocks/netherBrick.png", "images/characters/blaze.png"),
    END("images/blocks/endstone.png", "images/blocks/obsidian.png", "images/characters/enderman.png");

    private final Image walkablePath;
    private final Image blockedPath;
    private final Image mobBlock;

    private static final Image sword = new Image("images/items/sword.png");
    private static final Image heart = new Image("images/items/heart.png");
    private static final Image finalBlock = new Image("images/blocks/goldBlock.png");

    private static final Image teleporter = new Image("images/blocks/portalBlock.png");

    ResourcePack(String walkablePath, String blockedPath, String mobBlock) {
        this.walkablePath = new Image(walkablePath);
        this.blockedPath = new Image(blockedPath);
        this.mobBlock = new Image(mobBlock);
    }

    public Image getWalkablePath() {
        return walkablePath;
    }

    public Image getBlockedPath() {
        return blockedPath;
    }

    public Image getMobBlock() {
        return mobBlock;
    }

    public static Image getSword() {
        return sword;
    }

    public static Image getHeart() {
        return heart;
    }

    public static Image getFinalBlock() {
        return finalBlock;
    }

    public static Image getTeleporter() {
        return teleporter;
    }
}
