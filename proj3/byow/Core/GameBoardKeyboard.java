package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class GameBoardKeyboard {
    public int width;
    public int height;
    public boolean gameOver;
    public TETile image;
    public boolean choosingAvatar;
    public TERenderer ter;
    public TETile[][] tiles;
    public WorldGenerator world;
    public String inputs;
    public String inputString;

    public GameBoardKeyboard(int w, int h, String inputString) {
        this.width = w;
        this.height = h;
        this.gameOver = false;
        choosingAvatar = false;
        this.inputString = inputString;
        ter = new TERenderer();

        // setting the initial canvas in StdDraw
        StdDraw.setCanvasSize(width * 15, height * 15);
        Font font = new Font("Monaco", Font.BOLD, 35);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        menuDisplay();

    }

    public void menuDisplay() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        Font headerFont = new Font("Monaco", Font.BOLD, 35);
        StdDraw.setFont(headerFont);
        StdDraw.text(width / 2, height - 10, "CS61B: The Game");

        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontSmall);

        StdDraw.text(width / 2, height / 2 - 1, "New Game (N)");
        StdDraw.text(width / 2, (height / 2) - 4, "Load Game (L)");
        StdDraw.text(width / 2, (height / 2) - 7, "Quit (Q)");
        StdDraw.text(width / 2, (height / 2) - 10, "Replay (R)");

        StdDraw.show();
        menuSelection();
    }

    public void menuSelection() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char answer = StdDraw.nextKeyTyped();
                if (answer == 'n' || answer == 'N') {
                    insertSeed(); //changed this
                }
            else if (answer == 'l' || answer == 'L') {
                playFromLastGame();
            }
            else if (answer == 'q' || answer == 'Q') {
                System.exit(0);
            }
            else if (answer == 'r' || answer == 'R') {
                replay();
                }
            }
        }
    }

    public void pickAvatar() {
        boolean onAvatar = true;
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        Font headerFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(headerFont);
        StdDraw.text(width / 2, height - 10, "Choose your character!");

        Font avatarFont = new Font("Monaco", Font.PLAIN ,20);
        StdDraw.setFont(avatarFont);
        // avatar 1
        StdDraw.setPenColor(new Color(158, 134, 46));
        StdDraw.picture(width / 2 - 10, height - 20, "byow/Core/icons8-boba-16.png", 2, 2);
        StdDraw.text(width / 2 - 10, height - 25, "Boba (B)");

        // avatar 2
        StdDraw.setPenColor(new Color(0, 102, 0));
        StdDraw.picture(width / 2, height - 20, "byow/Core/icons8-tea-16.png", 2, 2);
        StdDraw.text(width / 2, height - 25, "Matcha (M)");

        // avatar 3
        StdDraw.setPenColor(new Color(255, 128, 0));
        StdDraw.picture(width / 2 + 10, height - 20, "byow/Core/icons8-juice-16.png", 2, 2);
        StdDraw.text(width / 2 + 10, height - 25, "Juice (J)");

        StdDraw.show();

        while(onAvatar) {
            StdDraw.pause(1000);
            if (StdDraw.hasNextKeyTyped()) {
                char answer = StdDraw.nextKeyTyped();
                if (answer == 'b' || answer == 'B') {
                    image = Tileset.BOBA;
                    onAvatar = false;
                } else if (answer == 'm' || answer == 'M') {
                    image = Tileset.MATCHA;
                    onAvatar = false;
                } else if (answer == 'j' || answer == 'J') {
                    image = Tileset.JUICE;
                    onAvatar = false;
                }
            }
//            else {
//               image = Tileset.BOBA;
//               onAvatar = false;
//            }
        }

        startNewGame(); //changed this
    }

    public void insertSeed() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        Font headerFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(headerFont);
        StdDraw.text(width / 2, height - 10, "Please enter a seed and press s to start!");
        StdDraw.show();

        inputString = "";
        boolean onSeed = true;
        while (onSeed) {
            if (StdDraw.hasNextKeyTyped()) {
                char nextChar = StdDraw.nextKeyTyped();
                if (Character.toLowerCase(nextChar) != 's') {
                    inputString += nextChar;
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(width / 2, height - 10, "Please enter a seed and press s to start!");
                    StdDraw.text(width / 2, height / 2, "The seed is: " + inputString);
                    StdDraw.show();
                }
                else {
                    onSeed = false;
                }
            }
        }
        inputString = inputString.substring(1);
        pickAvatar(); //changed this
    }

    public void startNewGame() {
        tiles = new TETile[width][height];
        this.world = new WorldGenerator(tiles, inputString);
        this.world.addAvatar(image);
        if (world.movement != null) {
            loadMovement(world.movement);
        }
        else {
            renderFrame();
            gamePlay();
        }
    }
    public void displayDescription() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();

        TETile currTile = tiles[x][y];
        String desc = currTile.description();
        Font headerFont = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(headerFont);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(3, height - 1, desc);
        StdDraw.show();
    }

    public void gamePlay() {
        inputs = "";
        while (!gameOver) {
            renderFrame();
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                inputs += curr;
                if (curr == 'w' || curr == 'W') {
                    tiles = world.moveUp();
                }
                else if (curr == 'a' || curr == 'A') {
                    tiles = world.moveLeft();
                }
                else if (curr == 's' || curr == 'S') {
                    tiles = world.moveDown();
                }
                else if (curr == 'd' || curr == 'D') {
                    tiles = world.moveRight();
                }
                renderFrame();

                if (inputs.length() >= 2) {
                if (inputs.charAt(inputs.length() - 2) == ':') {
                    if (inputs.charAt(inputs.length() - 1) == 'q' || inputs.charAt(inputs.length() - 1) == 'Q') {
                        saveGame();
                        gameOver = true;
                        System.exit(0);
                    }
                }
                    }
                }
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
        out.print(inputs.substring(0, inputs.length() - 2));
    }
    public void playFromLastGame() {
        In in = new In("byow/Core/savedVariables.txt");
        String[] currLine;
        currLine = in.readLine().split(" ");
        String seed = currLine[0];

        if (currLine[5].equals("BOBA")) {
            this.image = Tileset.BOBA;
        }
        else if (currLine[5].equals("MATCHA")) {
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
        gamePlay();
    }

    public void replay() {
        In in = new In("byow/Core/savedVariables.txt");
        String[] currLine;
        currLine = in.readLine().split(" ");
        inputString = currLine[0];

        if (currLine[5].equals("BOBA")) {
            this.image = Tileset.BOBA;
        }
        else if (currLine[5].equals("MATCHA")) {
            this.image = Tileset.MATCHA;
        } else {
            this.image = Tileset.JUICE;
        }

        tiles = new TETile[width][height];
        world = new WorldGenerator(tiles, inputString);
        world.addAvatar(this.image);

        int avX = Integer.parseInt(currLine[3]);
        int avY = Integer.parseInt(currLine[4]);
        tiles[avX][avY] = this.image;
        tiles[world.avatar.xPos][world.avatar.yPos] = Tileset.FLOOR;
        world.avatar.xPos = avX;
        world.avatar.yPos = avY;
        ter.renderFrame(tiles);
        StdDraw.pause(300);

        if (currLine.length > 6) {
            char[] inputs = currLine[6].toCharArray();
            for (char c : inputs) {
                renderFrame();
                if (c == 'w' || c == 'W') {
                    tiles = world.moveUp();
                } else if (c == 'a' || c == 'A') {
                    tiles = world.moveLeft();
                } else if (c == 's' || c == 'S') {
                    tiles = world.moveDown();
                } else if (c == 'd' || c == 'D') {
                    tiles = world.moveRight();
                }
                renderFrame();
                StdDraw.pause(200);
            }
        }
        StdDraw.pause(300);

    }

    public void loadMovement(String input) {
        char[] inputs = input.toCharArray();
        for (char c: inputs) {
            if (c == 'w' || c == 'W') {
                tiles = world.moveUp();
            } else if (c == 'a' || c == 'A') {
                tiles = world.moveLeft();
            } else if (c == 's' || c == 'S') {
                tiles = world.moveDown();
            } else if (c == 'd' || c == 'D') {
                tiles = world.moveRight();
            }
        }
        renderFrame();
    }

    public void renderFrame() {
     displayDescription();
     ter.renderFrame(tiles);
    }

}
