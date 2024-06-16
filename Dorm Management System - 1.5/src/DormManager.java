import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DormManager {
    private ArrayList<Room> rooms;
    private ArrayList<Boarder> boarders;

    public DormManager() {
        this.rooms = new ArrayList<>();
        this.boarders = new ArrayList<>();
    }

    // Room management methods
    public void addRoom(int roomNumber, String type, double rent, boolean isAvailable) throws Exception {
        if (isRoomNumberDuplicate(roomNumber)) {
            JOptionPane.showMessageDialog(null, "Room ID already exists!", "Duplicate", JOptionPane.ERROR_MESSAGE);
            throw new Exception("Room ID already exists!");
        } else
        rooms.add(new Room(roomNumber, type, rent, isAvailable));
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
    public void addBoarder(String name, String id, String contact, int roomNumber) throws Exception {
        if (isBoarderIdDuplicate(id)) {
            JOptionPane.showMessageDialog(null, "Boarder ID already exists!", "Duplicate", JOptionPane.ERROR_MESSAGE);
            throw new Exception("Boarder ID already exists!");
        }
        if (isBoarderNameDuplicate(name)) {
            JOptionPane.showMessageDialog(null, "Boarder name already exists!", "Duplicate", JOptionPane.ERROR_MESSAGE);
            throw new Exception("Boarder name already exists!");
        }
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

        // Check for duplicate room numbers
        public boolean isRoomNumberDuplicate(int roomNumber) {
            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNumber) {
                    return true;
                }
            }
            return false;
        }
    
        // Check for duplicate boarder IDs
        public boolean isBoarderIdDuplicate(String id) {
            for (Boarder boarder : boarders) {
                if (boarder.getId().equals(id)) {
                    return true;
                }
            }
            return false;
        }
    
        // Check for duplicate boarder names
        public boolean isBoarderNameDuplicate(String name) {
            for (Boarder boarder : boarders) {
                if (boarder.getName().equals(name)) {
                    return true;
                }
            }
            return false;
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

    //FOR VIEWING
    public ArrayList<Room> viewRooms() {
        return rooms;
    }

    // Updated method to search rooms by type or room number
    public ArrayList<Room> searchRooms(String keyword) {
        ArrayList<Room> results = new ArrayList<>();
        for (Room room : rooms) {
            if (String.valueOf(room.getRoomNumber()).contains(keyword) ||
                    room.getType().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(room);
            }
        }
        return results;
    }

    public ArrayList<Boarder> viewBoarders() {
        return boarders;
    }

    // Updated methods to search boarders by ID, name, or contact
    public ArrayList<Boarder> searchBoardersById(String id) {
        ArrayList<Boarder> results = new ArrayList<>();
        for (Boarder boarder : boarders) {
            if (boarder.getId().equalsIgnoreCase(id)) {
                results.add(boarder);
            }
        }
        return results;
    }

    public ArrayList<Boarder> searchBoardersByName(String name) {
        ArrayList<Boarder> results = new ArrayList<>();
        for (Boarder boarder : boarders) {
            if (boarder.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(boarder);
            }
        }
        return results;
    }

    public ArrayList<Boarder> searchBoardersByContact(String contact) {
        ArrayList<Boarder> results = new ArrayList<>();
        for (Boarder boarder : boarders) {
            if (boarder.getContactInfo().contains(contact)) {
                results.add(boarder);
            }
        }
        return results;
    }

    // Helper method to find a room by its number
    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null; // Room not found
    }
        // Save data to file using BufferedWriter
    public void saveData(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Save rooms
            for (Room room : rooms) {
                writer.write("ROOM");
                writer.newLine();
                writer.write(room.getRoomNumber() + "," + room.getType() + "," + room.getRent() + "," + room.isAvailable());
                writer.newLine();
            }

            // Save boarders
            for (Boarder boarder : boarders) {
                writer.write("BOARDER");
                writer.newLine();
                Room assignedRoom = boarder.getAssignedRoom();
                writer.write(boarder.getName() + "," + boarder.getId() + "," + boarder.getContactInfo() + "," +
                        (assignedRoom != null ? assignedRoom.getRoomNumber() : "null"));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load data from file using BufferedReader
    public void loadData(String filename) {
        rooms.clear();
        boarders.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("ROOM")) {
                    String[] roomData = reader.readLine().split(",");
                    int roomNumber = Integer.parseInt(roomData[0]);
                    String type = roomData[1];
                    double rent = Double.parseDouble(roomData[2]);
                    boolean isAvailable = Boolean.parseBoolean(roomData[3]);
                    rooms.add(new Room(roomNumber, type, rent, isAvailable));
                } else if (line.equals("BOARDER")) {
                    String[] boarderData = reader.readLine().split(",");
                    String name = boarderData[0];
                    String id = boarderData[1];
                    String contactInfo = boarderData[2];
                    String roomNumberStr = boarderData[3];
                    Room assignedRoom = roomNumberStr.equals("null") ? null : findRoomByNumber(Integer.parseInt(roomNumberStr));
                    boarders.add(new Boarder(name, id, contactInfo, assignedRoom));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

