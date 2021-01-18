package sample.Items;

import sample.*;

public class Boot extends Item {
    public Boot(Point point, Maze maze) {
        super(point, maze);
        setImage(ResourcePack.getBoots());
    }

    @Override
    public void collect(PlayerModel player) {
        player.addBoots();
        setGone(true);
        getMaze().changeMobSpeed(1);
        getMaze().getChildren().remove(this);
    }
}
