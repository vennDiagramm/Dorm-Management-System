// Base Class for Boarders
public class Boarder{
  private String name, id, contactInfo;
  private Room assignedRoom; 

  public Boarder(String name, String id, String contactInfo) {
      this.name = name;
      this.id = id;
      this.contactInfo = contactInfo;
      this.assignedRoom = null; // how to true n false
  }
  // SETTERS AND GETTERS
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getContactInfo() { return contactInfo; }
  public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
  public Room getAssignedRoom() { return assignedRoom; }
  public void setAssignedRoom(Room assignedRoom) { this.assignedRoom = assignedRoom; }
  
}