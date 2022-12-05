package models;

public enum RoomType {
    SINGLE("1"),
    DOUBLE("2");

    public String value;

    private RoomType(String value) {
        this.value = value;
    }

    public static RoomType isRoomType(String value) {
        for (RoomType roomType : values()) {
            if (roomType.value.equals(value)) {
                return roomType;
            }
        }
        throw new IllegalArgumentException();
    }
}
