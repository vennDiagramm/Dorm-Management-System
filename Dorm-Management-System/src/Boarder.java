public class Boarder {
  private String name;
  private String id;
  private String contactInfo;
  private Room assignedRoom;

  public Boarder(String name, String id, String contactInfo, Room assignedRoom) {
      this.name = name;
      this.id = id;
      this.contactInfo = contactInfo;
      this.assignedRoom = assignedRoom;
  }

  public String getName() {
      return name;
  }

  public String getId() {
      return id;
  }

  public String getContactInfo() {
      return contactInfo;
  }

  public Room getAssignedRoom() {
      return assignedRoom;
  }

  public void setName(String name) {
      this.name = name;
  }

  public void setContactInfo(String contactInfo) {
      this.contactInfo = contactInfo;
  }

  public void setAssignedRoom(Room assignedRoom) {
      this.assignedRoom = assignedRoom;
  }

  @Override
  public String toString() {
      return "Boarder [Name: " + name + ", ID: " + id + ", Contact: " + contactInfo +
             ", Assigned Room: " + (assignedRoom != null ? assignedRoom.getRoomNumber() : "None") + "]";
  }
}
