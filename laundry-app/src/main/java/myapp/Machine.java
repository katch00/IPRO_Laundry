package myapp;

public final class Machine {
    private final long id;
    private final String name;
    private final long timestamp;
    private final String location;
    private final String status;
    private final String type;
    private final String testing;

    public Machine(long id, String name, long timestamp, String location, String status, String type, String testing) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.location = location;
        this.status = status;
        this.type = type;
        this.testing = testing;
    }
}