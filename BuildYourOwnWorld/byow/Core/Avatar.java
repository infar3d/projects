package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {

    public int startingXPos;
    public int startingYPos;
    public int xPos;
    public int yPos;
    public TETile image;

    public TETile[][] tiles;
    public Avatar(int xPos, int yPos, int startingxPos, int startingyPos, TETile image, TETile[][] tiles) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.image = image;
        this.tiles = tiles;
        this.startingXPos = startingxPos;
        this.startingYPos = startingyPos;
    }


}
