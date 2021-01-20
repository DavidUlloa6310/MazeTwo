package sample;

import javafx.scene.image.Image;

public enum ResourcePack {
    GRASS("images/blocks/grassBlock.png", "images/blocks/woodPlank.png", "images/characters/creeper.png"),
    CAVE("images/blocks/stoneBrickBlock.png", "images/blocks/lavaBlock.png", "images/characters/spider.png"),
    NETHER("images/blocks/netherrack.png", "images/blocks/netherBrick.png", "images/characters/blaze.png"),
    END("images/blocks/endstone.png", "images/blocks/obsidian.png", "images/characters/enderman.png"),
    MUSHROOM("images/blocks/myceliumBlock.png", "images/blocks/mushroomBlock.png", "images/characters/zombie.png"),
    SNOW("images/blocks/snowBlock.png", "images/blocks/spruceWoodBlock.png", "images/characters/wolf.png");

    private final Image walkablePath;
    private final Image blockedPath;
    private final Image mobBlock;

    private static final Image startBlock = new Image("images/blocks/emeraldBlock.png");
    private static final Image sword = new Image("images/items/sword.png");
    private static final Image heart = new Image("images/items/heart.png");
    private static final Image armor = new Image("images/items/armor.png");
    private static final Image boots = new Image("images/items/boots.png");
    private static final Image healthPotion = new Image("images/items/healPotion.png");
    private static final Image invisPotion = new Image("images/items/invisPotion.gif");
    private static final Image endBlock = new Image("images/blocks/goldBlock.png");

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

    public static Image getEndBlock() {
        return endBlock;
    }

    public static Image getTeleporter() {
        return teleporter;
    }

    public static Image getStartBlock() {
        return startBlock;
    }

    public static Image getArmor() {
        return armor;
    }

    public static Image getBoots() {
        return boots;
    }

    public static Image getHealthPotion() {
        return healthPotion;
    }

    public static Image getInvisPotion() {
        return invisPotion;
    }
}
