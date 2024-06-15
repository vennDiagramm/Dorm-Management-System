import java.util.ArrayList;

public class DormManager {
    private ArrayList<Room> rooms;
    private ArrayList<Boarder> boarders;

    public DormManager() {
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

    public void addBoarder(String name, String id, String contact, int roomNumber) {
        Room assignedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                assignedRoom = room;
                room.setAvailable(false);
                break;
            }
        }
        Boarder boarder = new Boarder(name, id, contact, assignedRoom);
        boarders.add(boarder);
    }

    public ArrayList<Boarder> viewBoarders() {
        return boarders;
    }

    public void updateBoarder(String id, String name, String contact, int roomNumber) {
        for (Boarder boarder : boarders) {
            if (boarder.getId().equals(id)) {
                boarder.setName(name);
                boarder.setContactInfo(contact);
                if (boarder.getAssignedRoom() != null) {
                    boarder.getAssignedRoom().setAvailable(true); // Make the previous room available
                }
                Room newRoom = null;
                for (Room room : rooms) {
                    if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                        newRoom = room;
                        room.setAvailable(false);
                        break;
                    }
                }
                boarder.setAssignedRoom(newRoom);
            }
        }
    }

    public void deleteBoarder(String id) {
        Boarder toRemove = null;
        for (Boarder boarder : boarders) {
            if (boarder.getId().equals(id)) {
                if (boarder.getAssignedRoom() != null) {
                    boarder.getAssignedRoom().setAvailable(true); // Make the room available
                }
                toRemove = boarder;
                break;
            }
        }
        if (toRemove != null) {
            boarders.remove(toRemove);
        }
    }

    public void assignRoom(Boarder boarder, Room room) {
        if (room.isAvailable()) {
            boarder.setAssignedRoom(room);
            room.setAvailable(false);
        }
    }

    public void unassignRoom(Boarder boarder) {
        if (boarder.getAssignedRoom() != null) {
            boarder.getAssignedRoom().setAvailable(true);
            boarder.setAssignedRoom(null);
        }
    }
}