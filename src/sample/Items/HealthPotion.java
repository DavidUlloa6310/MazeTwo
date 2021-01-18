package sample.Items;

import sample.*;

public class HealthPotion extends Item {
    public HealthPotion(Point point, Maze maze) {
        super(point, maze);
        setImage(ResourcePack.getHealthPotion());
    }

    @Override
    public void collect(PlayerModel player) {
        if (player.getHealth() < 4) {
            player.setHealth(3);
        }
        setGone(true);
        getMaze().getChildren().remove(this);
    }
}
