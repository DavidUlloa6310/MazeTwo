package sample.Items;

import sample.*;

public class Sword extends Item {
    public Sword(Point point, Maze maze) {
        super(point, maze);
        setImage(ResourcePack.getSword());
    }

    @Override
    public void collect(PlayerModel player) {
        player.addSword();
        setGone(true);
        getMaze().getChildren().remove(this);
    }
}
