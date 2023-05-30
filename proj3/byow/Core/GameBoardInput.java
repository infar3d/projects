package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class GameBoardInput {
    public int width;
    public int height;
    public boolean gameOver;
    public TETile image;
    public boolean choosingAvatar;
    public TETile[][] tiles;
    public WorldGenerator world;
    public String inputs;
    public String inputString;

    public GameBoardInput(int w, int h, String inputString) {
        this.width = w;
        this.height = h;
        this.gameOver = false;
        choosingAvatar = false;
        this.inputString = inputString;

        if (Character.toLowerCase(inputString.charAt(0)) == 'l') {
            playFromLastGame();
            //if the inputString has directions
        } else if (Character.toLowerCase(inputString.charAt(0)) == 'n') {
            pickAvatar();
        }
    }


    public void pickAvatar() {
        image = Tileset.BOBA;
        startNewGame(); //changed this
    }
    public void startNewGame() {
        tiles = new TETile[width][height];
        this.world = new WorldGenerator(tiles, inputString);
        this.world.addAvatar(image);
        if (world.movement != null) {
            loadMovement(world.movement);
        }
    }

    public void saveGame() {
        Out out = new Out("byow/Core/savedVariables.txt");
        out.print(world.seedInt.toString());
        out.print(" ");
        out.print(world.avatar.xPos);
        out.print(" ");
        out.print(world.avatar.yPos);
        out.print(" ");
        out.print(world.avatar.startingXPos);
        out.print(" ");
        out.print(world.avatar.startingYPos);
        out.print(" ");
        out.print(world.avatar.image.description());
        out.print(" ");
        out.print(world.movement);
    }
    public void playFromLastGame() {
        In in = new In("byow/Core/savedVariables.txt");
        String[] currLine;
        currLine = in.readLine().split(" ");
        String seed = currLine[0];

        if (currLine[5].equals("BOBA")) {
            this.image = Tileset.BOBA;
        } else if (currLine[5].equals("MATCHA")) {
            this.image = Tileset.MATCHA;
        } else {
            this.image = Tileset.JUICE;
        }

        tiles = new TETile[width][height];
        world = new WorldGenerator(tiles, seed);
        world.addAvatar(this.image);

        int avX = Integer.parseInt(currLine[1]);
        int avY = Integer.parseInt(currLine[2]);
        tiles[avX][avY] = this.image;
        tiles[world.avatar.xPos][world.avatar.yPos] = Tileset.FLOOR;
        world.avatar.xPos = avX;
        world.avatar.yPos = avY;
        world.avatar.startingXPos = avX;
        world.avatar.startingYPos = avY;
        loadMovement(inputString.substring(1));
    }

    public void loadMovement(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'w' || input.charAt(i) == 'W') {
                tiles = world.moveUp();
            } else if (input.charAt(i) == 'a' || input.charAt(i) == 'A') {
                tiles = world.moveLeft();
            } else if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                tiles = world.moveDown();
            } else if (input.charAt(i) == 'd' || input.charAt(i) == 'D') {
                tiles = world.moveRight();
            } else if (Character.toLowerCase(input.charAt(i)) == 'q' && input.charAt(i-1) == ':') {
                saveGame();
                break;
            }
        }
    }

}
