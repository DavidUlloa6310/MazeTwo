package sample.Items;

import sample.Item;
import sample.Maze;
import sample.PlayerModel;
import sample.Point;

public class Bow extends Item {
    public Bow(Point point, Maze maze) {
        super(point, maze);
    }

    @Override
    public void collect(PlayerModel player) {
        player.addBow();
        setGone(true);
        getMaze().getChildren().remove(this);
    }
}
