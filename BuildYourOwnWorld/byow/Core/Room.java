package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


import java.util.*;


public class Room {
    int xPos;
    int yPos;
    int width;
    int height;
    int roomWidth;
    int roomHeight;
    TETile[][] twodtiles;
    Random r;
    Map<Integer, Room> rooms;
    public Room(Random r, TETile[][] tiles, int xPos, int yPos, int width, int height, Map rooms){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width; //
        this.height = height;
        this.twodtiles =  tiles;
        this.rooms = rooms;
        this.r = r;
        makeRoomInRoom();
    }

    public void makeRoomInRoom() {
        roomWidth = r.nextInt(2,7);
        roomHeight = r.nextInt(2,7);

        if (checkRoom()) {
            for (int i = 0; i < roomWidth; i++) {
                for (int j = 0; j < roomHeight; j++) {
                        twodtiles[xPos + i][yPos + j] = Tileset.FLOOR;
                        // putting the room onto the big grid
                }
            }
            rooms.put(rooms.size(), this); // maybe instead of storing rooms randomly store by distance from a certain point ???
            makeBorder();

                }
            }

    private boolean checkRoom() {
        for (int i = -1 ; i < roomWidth + 1; i++) {
            for (int j = -1; j < roomHeight + 1; j++) {
                if (!(xPos + i < width - 1) || !(yPos + j < height - 1) || !(xPos >= 1) || !(yPos >= 1) || twodtiles[xPos + i][yPos + j] != Tileset.NOTHING) {
                   return false;
                }
            }
        }
        return true;
    }

    private void makeBorder() {
        for (int i = -1; i <= roomWidth; i++) {
            twodtiles[xPos + i][yPos - 1] = Tileset.white;
        }
        for (int i = -1; i<= roomWidth; i++) {
            twodtiles[xPos + i][yPos + roomHeight] = Tileset.white;
        }
        for (int i = 0; i< roomHeight; i++) {
            twodtiles[xPos - 1][yPos + i] = Tileset.white;
        }
        for (int i =0; i<roomHeight; i++) {
            twodtiles[xPos + roomWidth][yPos + i] = Tileset.white;
        }
    }

}
