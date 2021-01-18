package sample.Items;

import sample.*;

public class Armor extends Item {

    public Armor(Point point, Maze maze) {
        super(point, maze);
        setImage(ResourcePack.getArmor());
    }

    @Override
    public void collect(PlayerModel player) {
        player.setHealth(4);
        setGone(true);
        getMaze().getChildren().remove(this);
    }
}
