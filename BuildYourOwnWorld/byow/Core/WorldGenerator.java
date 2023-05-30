package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;


public class WorldGenerator {

    private TETile[][] tiles;
    char[] seedArray;
    private static final int width = 80;
    private static final int height = 30;
    private Map<Integer, Room> rooms;
    Random r;
    Long seedInt;
    public Avatar avatar;
    public String movement;

    public WorldGenerator(TETile[][] tiles, String seedString) {
        this.tiles = tiles;
        String seed = "";
        int index = 0;
        //might need to change back to i = 1 here
        for (int i = 0; i < seedString.length(); i++) {
            if (Character.isDigit(seedString.charAt(i))) {
                seed += seedString.charAt(i); //seed stores no N, just numbers
                index = i;
            }
        }
        if (index + 2 < seedString.length()) {
            movement = seedString.substring(index + 2);
        }

        if (seed.length() != 0) {
            this.seedInt = Long.parseLong(seed);
            //seedInt only takes in number, not the N
        }
        rooms = new TreeMap<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }

        this.r = new Random(this.seedInt);
        rooms();
        hallways();
    }


    public void rooms() {
        int numRooms = r.nextInt(80,100);
        for (int i = 0; i<numRooms; i++) {
            int xPos = r.nextInt(1, width - 1);
            int yPos = r.nextInt(1, height - 1);
            new Room(r, tiles, xPos, yPos, width, height, rooms);
        }
    }
    public void hallways() {
        HallwayConnector hallways = new HallwayConnector(r, tiles, width, height, rooms);
        hallways.isConnected();
    }

    public TETile[][] getTiles(){
        return tiles;
    }

    public void addAvatar(TETile image) {

        int avXpos = r.nextInt(width);
        int avYpos = r.nextInt(height);

        while(tiles[avXpos][avYpos] != Tileset.FLOOR) {
            avXpos = r.nextInt(width);
            avYpos = r.nextInt(height);
        }
        int startX = avXpos;
        int startY = avYpos;
        this.avatar = new Avatar(avXpos, avYpos,startX, startY, image, tiles);
        tiles[avXpos][avYpos] = image;
    }

    public TETile[][] moveUp() {
        if (tiles[avatar.xPos][avatar.yPos + 1] != Tileset.white) {
            tiles[avatar.xPos][avatar.yPos + 1] = avatar.image;
            tiles[avatar.xPos][avatar.yPos] = Tileset.FLOOR;
            avatar.yPos++;
        }
        return tiles;
    }
    public TETile[][] moveLeft() {
        if (tiles[avatar.xPos - 1][avatar.yPos] != Tileset.white) {
            tiles[avatar.xPos  - 1][avatar.yPos] = avatar.image;
            tiles[avatar.xPos][avatar.yPos] = Tileset.FLOOR;
            avatar.xPos--;
        }
        return tiles;
    }
    public TETile[][] moveRight() {
        if (tiles[avatar.xPos + 1][avatar.yPos] != Tileset.white) {
            tiles[avatar.xPos  + 1][avatar.yPos] = avatar.image;
            tiles[avatar.xPos][avatar.yPos] = Tileset.FLOOR;
            avatar.xPos++;
        }
        return tiles;
    }
    public TETile[][] moveDown() {
        if (tiles[avatar.xPos][avatar.yPos - 1] != Tileset.white) {
            tiles[avatar.xPos][avatar.yPos - 1] = avatar.image;
            tiles[avatar.xPos][avatar.yPos] = Tileset.FLOOR;
            avatar.yPos--;
        }
        return tiles;
    }

}
