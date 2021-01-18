package sample.Items;

import sample.*;

import java.util.Timer;
import java.util.TimerTask;

public class InvisPotion extends Item {
    public InvisPotion(Point point, Maze maze) {
        super(point, maze);
        setImage(ResourcePack.getInvisPotion());
    }

    @Override
    public void collect(PlayerModel player) {
        setGone(true);
        getMaze().getChildren().remove(this);
        getMaze().stopMobs();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getMaze().startMobs();
            }
        };

        timer.schedule(task, 5000l);
    }
}
