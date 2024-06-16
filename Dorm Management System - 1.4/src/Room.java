// BASE CLASS FOR ROOM MANAGEMENT
public class Room {
  private int roomNumber;
  private String type;
  private double rent;
  private boolean isAvailable; // Sa boarder ra ata ni e manipulate

  public Room(int roomNumber, String type, double rent, boolean isAvailable) {
      this.roomNumber = roomNumber;
      this.type = type;
      this.rent = rent;
      this.isAvailable = isAvailable;
  }

  // SETTERS AND GETTERS
  public int getRoomNumber() { return roomNumber; }
  public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public double getRent() { return rent; }
  public void setRent(double rent) { this.rent = rent; }
  public boolean isAvailable() { return isAvailable; }
  public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + "\n Type: " + type + "\n Rent: ₱" + rent +
                "\n Status: " + (isAvailable ? "Available" : "Occupied");
    }
}