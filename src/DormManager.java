import java.util.ArrayList;

public class DormManager {
    private ArrayList<Room> rooms;
    private ArrayList<Boarder> boarders;
    private User landlord;

    public DormManager() {
        this.landlord = landlord;
        this.rooms = new ArrayList<>();
        this.boarders = new ArrayList<>();
    }

    // Room management methods
    public void addRoom(int roomNumber, String type, double rent, boolean isAvailable) {
        rooms.add(new Room(roomNumber, type, rent, isAvailable));
    }

    public ArrayList<Room> viewRooms() {
        return rooms;
    }

    public void updateRoom(int roomNumber, String type, double rent, boolean isAvailable) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setType(type);
                room.setRent(rent);
                room.setAvailable(isAvailable);
            }
        }
    }

    public void deleteRoom(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }

    /**<b>Boarder management methods</b>*/
    public void addBoarder(String name, String id, String contactInfo) {
        boarders.add(new Boarder(name, id, contactInfo));
    }

    public ArrayList<Boarder> viewBoarders() {
        return boarders;
    }

    public void updateBoarder(String id, String name, String contactInfo, Room assignedRoom) {
        for (Boarder boarder : boarders) {
            if (boarder.getId().equals(id)) {
                boarder.setName(name);
                boarder.setContactInfo(contactInfo);
                boarder.setAssignedRoom(assignedRoom);
            }
        }
    }

    public void deleteBoarder(String id) {
        boarders.removeIf(boarder -> boarder.getId().equals(id));
    }

    public void assignRoom(Boarder boarder, Room room) {
        if (room.isAvailable()) {
            boarder.setAssignedRoom(room);
            room.setAvailable(false);
        }
    }

    /**<B>View details methods</B>*/
    public Room viewRoomDetails(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public Boarder viewBoarderDetails(String id) {
        for (Boarder boarder : boarders) {
            if (boarder.getId().equals(id)) {
                return boarder;
            }
        }
        return null;
    }

    /**<b>Report methods</b>*/
    public double generateProfitReport() {
        double totalRent = 0;
        for (Room room : rooms) {
            if (!room.isAvailable()) {
                totalRent += room.getRent();
            }
        }
        return totalRent;
    }

    public String generateOccupancyReport() {
        StringBuilder report = new StringBuilder();
        for (Room room : rooms) {
            report.append("Room ").append(room.getRoomNumber()).append(": ")
                    .append(room.isAvailable() ? "Available" : "Occupied")
                    .append("\n");
        }
        return report.toString();
    }
}
