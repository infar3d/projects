package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;

public class HallwayConnector {
    int width;
    int height;


    TETile[][] tiles;

    WeightedQuickUnionUF roomsDS;

    Map<Integer, Room> rooms;

    Random r;


    public HallwayConnector(Random r, TETile[][] tiles, int width, int height, Map rooms) {
        this.width = width; //
        this.height = height;
        this.tiles = tiles;
        this.roomsDS = new WeightedQuickUnionUF(rooms.size());
        this.rooms = rooms;
        this.r = r;
        connectRooms();
    }

    public void connectRooms() {

        for (int roomNum: rooms.keySet()) {
            int currRoomX = rooms.get(roomNum).xPos;
            int currRoomY = rooms.get(roomNum).yPos;
            double distance = 100000000;
            Room closestRoom = rooms.get(roomNum);
            int closestRoomNum = roomNum;

            for (int otherRoom: rooms.keySet()) {
                int otherRoomX = rooms.get(otherRoom).xPos;
                int otherRoomY = rooms.get(otherRoom).yPos;

                if (otherRoomX != currRoomX && currRoomY != otherRoomY && !roomsDS.connected(roomNum,otherRoom)) {
                    double xDist = Math.pow(currRoomX - otherRoomX, 2);
                    double yDist = Math.pow(currRoomY - otherRoomY, 2);

                    if (xDist + yDist < distance) {
                        distance = xDist + yDist;
                        closestRoomNum = otherRoom;
                        closestRoom = rooms.get(otherRoom);
                    }
                }
            }

                makeHallway(rooms.get(roomNum), closestRoom);
                roomsDS.union(roomNum, closestRoomNum);
        }
    }

    public void makeHallway(Room room1, Room room2) {
        List<Integer> coord1 = randomRoomPoint(room1);
        List<Integer> coord2 = randomRoomPoint(room2);

        goHorizontal(coord1, coord2);
    }

    private void goHorizontal(List<Integer> coord1, List<Integer> coord2) {
        int currX = coord1.get(0);
        int currY = coord1.get(1);

        int x2 = coord2.get(0);
        int endX = currX;
        if (currX < x2) { // go right
            for (int i = currX; i < x2 + 1; i++) {
                if (tiles[i][currY - 1] == Tileset.NOTHING) {
                    tiles[i][currY - 1] = Tileset.white;
                }
                if (tiles[i][currY + 1] == Tileset.NOTHING) {
                    tiles[i][currY + 1] = Tileset.white;
                }
                tiles[i][currY] = Tileset.FLOOR;
                endX = i;
            }
            if (tiles[currX+1][currY] == Tileset.NOTHING) {
                tiles[currX + 1][currY] = Tileset.white;
                if (tiles[currX + 1][currY + 1] == Tileset.NOTHING) {
                    tiles[currX+ 1][currY + 1] = Tileset.white;
                }
                if (tiles[currX +  1][currY - 1] == Tileset.NOTHING) {
                    tiles[currX  + 1][currY - 1] = Tileset.white;
                }
            }
        }
        else { //  go left
            for (int i = currX; i > x2 - 1; i--) {
                if (tiles[i][currY - 1] == Tileset.NOTHING) {
                    tiles[i][currY - 1] = Tileset.white;
                }
                if (tiles[i][currY + 1] == Tileset.NOTHING) {
                    tiles[i][currY + 1] = Tileset.white;
                }
                tiles[i][currY] = Tileset.FLOOR;
                endX = i;
            }
            if (tiles[currX - 1][currY] == Tileset.NOTHING) {
                tiles[currX - 1][currY] = Tileset.white;
                if (tiles[currX - 1][currY + 1] == Tileset.NOTHING) {
                    tiles[currX - 1][currY + 1] = Tileset.white;
                }
                if (tiles[currX - 1][currY - 1] == Tileset.NOTHING) {
                    tiles[currX - 1][currY - 1] = Tileset.white;
                }
            }
        }
        List<Integer> newCoords = new ArrayList();
        newCoords.add(endX);
        newCoords.add(currY);
        goVertical(newCoords, coord2);
    }

    public void goVertical(List<Integer> coord1, List<Integer> coord2) {
        int currX = coord1.get(0);
        int currY = coord1.get(1);
        int y2 = coord2.get(1);

        if (currY < y2) { // go right
            for (int i = currY; i < y2 + 1; i++) {
                if (tiles[currX - 1][i] == Tileset.NOTHING) {
                    tiles[currX - 1][i] = Tileset.white;
                }
                if (tiles[currX + 1][i] == Tileset.NOTHING) {
                    tiles[currX + 1][i] = Tileset.white;
                }
                tiles[currX][i] = Tileset.FLOOR;
            }
            if (tiles[currX][currY + 1] == Tileset.NOTHING) {
                tiles[currX][currY + 1] = Tileset.white;
                if (tiles[currX + 1][currY + 1] == Tileset.NOTHING) {
                    tiles[currX + 1][currY + 1] = Tileset.white;
                }
                if (tiles[currX - 1][currY + 1] == Tileset.NOTHING) {
                    tiles[currX - 1][currY + 1] = Tileset.white;
                }
            }
        }
        else { //  go down
            for (int i = currY; i > y2 - 1; i--) {
                if (tiles[currX - 1][i] == Tileset.NOTHING) {
                    tiles[currX - 1][i] = Tileset.white;
                }
                if (tiles[currX + 1][i] == Tileset.NOTHING) {
                    tiles[currX + 1][i] = Tileset.white;
                }
                tiles[currX][i] = Tileset.FLOOR;
            }
            if (tiles[currX][currY - 1] == Tileset.NOTHING) {
                tiles[currX][currY - 1] = Tileset.white;
                if (tiles[currX + 1][currY - 1] == Tileset.NOTHING) {
                    tiles[currX + 1][currY - 1] = Tileset.white;
                }
                if (tiles[currX - 1][currY - 1] == Tileset.NOTHING) {
                    tiles[currX - 1][currY - 1] = Tileset.white;
                }
            }
        }


    }

    public void isConnected() {
        for (int room: rooms.keySet()) {
            if (!roomsDS.connected(0, room)) {
                makeHallway(rooms.get(room), rooms.get(0));
            }
        }
    }


    public List<Integer> randomRoomPoint(Room room) {
        List<Integer> coordList = new ArrayList<>();
        int xPoint = r.nextInt(room.roomWidth);
        int xCoord = room.xPos + xPoint;
        coordList.add(xCoord);

        int yPoint = r.nextInt(room.roomHeight);
        int yCoord = room.yPos + yPoint;
        coordList.add(yCoord);
        return coordList;
    }
}
