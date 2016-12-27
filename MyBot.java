import java.util.ArrayList;

public class MyBot {
    public static void main(String[] args) throws java.io.IOException {
        InitPackage iPackage = Networking.getInit();
        int myID = iPackage.myID;
        GameMap gameMap = iPackage.map;

        Networking.sendInit("bSkwared");
        Direction dir = Direction.randomDirection();
        int ctr = 0;

        while(true) {
            ++ctr;
            ArrayList<Move> moves = new ArrayList<Move>();

            gameMap = Networking.getFrame();


            for(int y = 0; y < gameMap.height; y++) {
                for(int x = 0; x < gameMap.width; x++) {
                    Location loc = new Location(x, y);
                    Site site = gameMap.getSite(loc);
                    if(site.owner == myID) {
                        
                        Site left  = gameMap.getSite(loc, Direction.WEST);
                        Site right = gameMap.getSite(loc, Direction.EAST);
                        Site down  = gameMap.getSite(loc, Direction.SOUTH);
                        Site up    = gameMap.getSite(loc, Direction.NORTH);

                        int state = 0;
                        if (left.owner != myID && left.strength < site.strength/1.2) {
                            state = 1;
                        }
                        if (right.owner != myID && right.strength < site.strength/1.2) {
                            state = 2;
                        }
                        if (down.owner != myID && down.strength < site.strength/1.2) {
                            state = 3;
                        }
                        if (up.owner != myID && up.strength < site.strength/1.2) {
                            state = 4;
                        }
                        
                        if (left.owner == myID && right.owner == myID && up.owner == myID && down.owner == myID && site.strength > 100) {
                            moves.add(new Move(new Location(x, y), Direction.EAST));
                        } else if (state == 0 && left.owner == myID && (site.strength+left.strength > 80) && (site.strength + left.strength < 130)) {
                            moves.add(new Move(new Location(x, y), Direction.WEST));
                        } else if (state == 0 && down.owner == myID && (site.strength+down.strength > 80) && (site.strength + down.strength < 130)) {
                            moves.add(new Move(new Location(x, y), Direction.SOUTH));
                        } else {

                        switch (state) {
                            case 1:
                                moves.add(new Move(new Location(x, y), Direction.WEST));
                                break;
                            case 2:
                                moves.add(new Move(new Location(x, y), Direction.EAST));
                                break;
                            case 3:
                                moves.add(new Move(new Location(x, y), Direction.SOUTH));
                                break;
                            case 4:
                                moves.add(new Move(new Location(x, y), Direction.NORTH));
                                break;
                            default:
                                break;
                        }
                        }

                    }
                }
            }
            Networking.sendFrame(moves);
        }
    }
}
