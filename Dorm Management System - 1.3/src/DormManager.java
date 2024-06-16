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

    // Boarder management methods
    public void addBoarder(String name, String id, String contact, int roomNumber) {
        Room assignedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                assignedRoom = room;
                room.setAvailable(false); // Mark the room as unavailable
                break;
            }
        }
        Boarder boarder = new Boarder(name, id, contact, assignedRoom);
        boarders.add(boarder);
    }

    public void updateBoarder(String id, String name, String contact, Integer roomNumber) {
        for (Boarder boarder : boarders) {
            if (boarder.getId().equals(id)) {
                // Make current assigned room available
                Room currentRoom = boarder.getAssignedRoom();
                if (currentRoom != null) {
                    currentRoom.setAvailable(true);
                }

                // Assign new room if roomNumber is provided
                if (roomNumber != null) {
                    Room newRoom = findRoomByNumber(roomNumber);
                    if (newRoom != null && newRoom.isAvailable()) {
                        newRoom.setAvailable(false); // Mark the new room as unavailable
                        boarder.setAssignedRoom(newRoom);
                    } else {
                        boarder.setAssignedRoom(null); // No available room found, clear assignment
                    }
                } else {
                    boarder.setAssignedRoom(null); // Remove room assignment
                }
                boarder.setName(name);
                boarder.setContactInfo(contact);
                break;
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

    public ArrayList<Boarder> viewBoarders() {
        return boarders;
    }

    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null; // Room not found
    }

    // Added. This is for the rooms. REPORT
    public String generateProfitReport() {
        StringBuilder report = new StringBuilder();
        double totalRentCollected = 0;

        report.append("Profit Report\n");
        report.append("-----------------\n");

        for (Room room : rooms) {
            if (!room.isAvailable()) {
                report.append("Room Number: ").append(room.getRoomNumber()).append("\n");
                report.append("Type: ").append(room.getType()).append("\n");
                report.append("Rent: ₱").append(room.getRent()).append("\n");
                report.append("-----------------\n");
                totalRentCollected += room.getRent();
            }
        }

        report.append("Total Rent Collected: ₱").append(totalRentCollected).append("\n");

        return report.toString();
    }


    // Added. This is for the Boarders. REPORT
    public String generateOccupancyReport() {
        StringBuilder report = new StringBuilder();

        report.append("Occupancy Report\n");
        report.append("-----------------\n");

        for (Room room : rooms) {
            report.append("Room Number: ").append(room.getRoomNumber()).append("\n");
            report.append("Type: ").append(room.getType()).append("\n");
            report.append("Rent: ₱").append(room.getRent()).append("\n");

            if (room.isAvailable()) {
                report.append("Status: Available\n");
            } else {
                report.append("Status: Occupied\n");
                for (Boarder boarder : boarders) {
                    if (boarder.getAssignedRoom() != null && boarder.getAssignedRoom().getRoomNumber() == room.getRoomNumber()) {
                        report.append("Boarder Name: ").append(boarder.getName()).append("\n");
                        report.append("Boarder ID: ").append(boarder.getId()).append("\n");
                        report.append("Boarder Contact: ").append(boarder.getContactInfo()).append("\n");
                        break;
                    }
                }
            }
            report.append("-----------------\n");
        }

        return report.toString();
    }

}