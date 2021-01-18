package sample.Items;

import sample.*;

public class InvisPotion extends Item {
    public InvisPotion(Point point, Maze maze) {
        super(point, maze);
        setImage(ResourcePack.getInvisPotion());
    }

    @Override
    public void collect(PlayerModel player) {
        //ADD FUNCTIONALITY
        setGone(true);
        getMaze().getChildren().remove(this);
    }
}
