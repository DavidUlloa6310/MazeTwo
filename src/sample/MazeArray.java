package sample;

public enum MazeArray {;
//Enums of the arrays which are used to generate mazes.

    private static boolean[][] firstMaze = {
        {false, false, false, false, true , true , false, false, false, false, false, false, false, false, true , false, false, false, false, false, false, false, false, false},
        {false, true , false, false, false, false, false, true , false, true , true , true , true , false, true , false, true , true , true , true , false, true , true , false},
        {false, true , true , true , false, true , true , true , false, true , false, false, false, false, true , false, false, false, false, false, false, false, false, false},
        {false, false, false, true , false, true , false, false, false, true , false, true , false, false, false, false, true , true , true , false, true , false, false, false},
        {false, true , false, false, false, false, false, true , false, true , false, true , true , true , true , false, false, false, true , false, true , false, true , true },
        {false, true , false, true , true , true , false, true , false, false, false, false, false, false, true , false, true , false, true , false, false, false, false, true },
        {false, true , false, false, false, false, false, true , true , false, true , true , true , false, true , false, true , false, true , false, true , true , false, false},
        {true , true , true , true , false, true , false, false, false, false, false, false, false, false, false, false, true , false, false, false, false, false, false, false},
        {false, true , false, false, false, true , false, true , true , true , false, true , true , false, true , true , true , false, true , true , true , true , true , false},
        {false, true , true , true , false, true , false, false, false, false, false, true , false, false, false, false, false, false, false, false, false, false, true , false},
        {false, false, false, false, false, true , true , true , false, true , false, true , false, true , false, true , true , true , true , true , true , false, true , false},
        {true , true , true , true , false, false, false, true , false, true , false, true , false, true , false, false, false, false, false, false, false, false, true , false},
        {false, false, false, false, false, true , false, true , false, false, false, true , false, true , true , false, true , true , true , false, true , false, true , false},
        {false, true , true , true , true , true , false, false, false, true , false, true , false, false, false, false, false, false, false, false, true , false, false, false},
        {false, false ,false, false, false, false, false, true , false, true , false, true , false, true , false, true , false, true , true , false, true , true , true , true },
        {false, true , true , true , true , true , true , true , false, false, false, false, false, true , false, true , false, false, false, false, false, false, false, false},
    };

    private static boolean[][] secondMaze = {
            {false, true , false, false, false, false, false, true , false, false, true , false, true , true , true , false, false, false, false, false, false, false, false, false},
            {false, true , false, false, false, false, false, true , false, false, true , false, false, false, true , false, false, true , false, false, false, false, true , false},
            {false, true , false, true , true , true , false, true , false, false, true , false, true , false, true , false, false, true , false, true , true , true , true , false},
            {false, false, false, true , false, false, false, true , false, false, false, false, true , false, true , false, false, true , false, true , false, false, false, false},
            {false, true , true , true , true , false, false, true , false, true , true , true , true , false, false, false, false, false, false, true , false, true , false, true },
            {false, false, false, false, true , true , false, true , true , false, false, false, true , true , true , false, false, true , false, true , false, true , false, true },
            {false, true , true , false, false, false, false, false, false, false, false, false, false, false, false, false, false, true , false, true , false, true , false, true },
            {true , true , true , true , true , false, false, true , true , true , true , true , false, false, true , false, false, true , false, false, false, false, false, false},
            {true , true , false, false, false, false, false, true , true , false, false, true , true , false, true , false, true , true , true , true , true , true , true , false},
            {false, true , false, true , true , true , false, true , true , false, false, true , false, false, true , false, true , false, false, false, false, false, true , false},
            {false, true , false, false, false, false, false, false, true , false, false, true , false, false, true , false, true , false, true , false, false, false, true , false},
            {false, true , false, true , true , false, false, false, false, false, false, false, false, false, true , false, false, false, true , false, false, false, true , false},
            {false, true , false, true , true , true , true , true , true , true , true , false, false, false, false, false, true , false, true , false, false, false, true , false},
            {false, false, false, false, false, false, false, false, false, false, true , false, false, true , false, true , true , false, true , false, false, false, true , false},
            {true , true , true , true , true , false, false, false, true , false, true , false, false, true , false, false, true , false, true , false, false, false, false, false},
            {true , false, false, false, false, false, false, true , true , false, false, false, false, true , false, false, false, false, true , false, false, true , true , false},
    };

    private static boolean[][] thirdMaze = {
            {false, false, true , true , true , true , true , true , true , true , true , true , true , true , true , true , true , true , true , true , false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false, false, true , false, false, false, false, false, false, false, false, false, false, false, true },
            {false, true , false, false, true , false, true , false, false, true , false, true , false, true , true , true , true , true , false, false, false, false, false, false},
            {false, true , false, true , true , false, true , false, false, true , false, false, false, false, false, false, false, true , false, true , true , false, true , false},
            {false, false, false, false, true , false, true , false, false, true , false, false, false, true , true , false, false, true , false, true , false, false, true , false},
            {false, true , true , false, true , false, true , false, false, true , false, false, false, false, true , false, false, true , false, true , false, false, true , false},
            {false, false, true , false, true , false, true , false, false, true , false, false, false, false, true , false, false, true , false, true , false, false, true , false},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {true , true , true , false, true , true , false, false, false, false, false, false, false, false, false, false, false, false, false, true , true , true , true , false},
            {false, false, false, false, false, false, false, false, false, true , false, false, false, false, true , false, false, true , false, false, false, false, false, false},
            {false, false, true , false, true , false, true , false, false, true , false, false, false, false, true , false, false, true , false, true , true , true , true , false},
            {false, false, true , false, true , false, true , false, false, true , true , false, false, true , true , false, false, true , false, false, false, false, false, false},
            {false, false, true , false, true , false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true , false},
            {false, false, false, false, true , false, true , false, true , true , false, false, false, true , true , true , true , true , true , true , true , true , true , false},
            {false, false, true , false, false, false, false, false, false, false, false, true , false, false, false, false, false, false, false, false, false, false, false, false},
            {false, false, true , true , true , true , true , true , true , true , true , true , false, true , true , true , true , true , true , true , true , true , true , true },
    };

    private static boolean[][] fourthMaze = {
            {false, true , false, true , false, false, false, true , false, false, false, false, false, false, true , false, true , true , true , false, false, false, false, false},
            {false, true , false, true , false, true , false, true , false, true , true , true , true , true , true , false, false, false, false, false, true , true , true , true },
            {false, true , false, true , false, true , false, true , false, true , false, false, false, false, true , true , true , true , true , false, true , false, false, false},
            {false, true , false, true , true , true , false, true , false, true , false, true , false, false, false, false, false, false, false, false, true , false, false, false},
            {false, false, false, true , false, true , false, true , false, true , false, true , true , true , true , true , true , true , true , true , true , true , true , false},
            {false, true , false, false, false, true , false, false, false, true , false, false, false, false, false, false, false, false, true , false, true , false, true , false},
            {false, true , true , true , false, true , false, true , true , true , false, true , true , false, true , false, true , true , true , false, true , false, true , false},
            {false, true , false, false, false, true , false, false, false, false, false, false, true , false, true , false, true , false, false, false, true , false, false, false},
            {false, false, false, true , true , true , false, true , true , true , true , false, true , false, true , false, true , false, true , true , true , false, true , false},
            {true , true , false, true , false, false, false, false, false, false, true , false, true , false, true , false, true , false, true , false, false, false, true , false},
            {false, false, false, true , false, true , true , true , true , false, true , false, true , true , true , false, true , false, true , false, true , false, true , false},
            {false, true , true , true , false, true , false, false, false, false, false, false, true , false, true , false, true , false, true , true , true , false, true , true },
            {false, true , false, true , false, true , false, true , false, true , true , true , true , false, true , false, true , false, true , false, true , true , true , false},
            {false, true , false, false, false, true , false, true , false, false, false, false, false, false, false, false, true , false, false, false, false, false, false, false},
            {false, true , true , true , true , true , false, true , true , true , true , false, true , false, true , true , true , true , true , false, true , true , true , true },
            {false, false, false, false, false, false, false, true , false, false, false, false, true , false, false, false, true , false, false, false, true , false, false, false},
    };

    public static boolean[][] getFirstMaze() {
        return firstMaze;
    }

    public static boolean[][] getSecondMaze() {
        return secondMaze;
    }

    public static boolean[][] getThirdMaze() {
        return thirdMaze;
    }

    public static boolean[][] getFourthMaze() {
        return fourthMaze;
    }

}
