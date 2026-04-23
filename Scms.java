public class Main {

    // ================= USER =================
    static abstract class User {
        protected String userId;
        protected String name;

        public User(String userId, String name) {
            this.userId = userId;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public abstract String getRole();
    }

    static class Admin extends User {
        public Admin(String id, String name) {
            super(id, name);
        }

        public String getRole() {
            return "Admin";
        }
    }

    static class Staff extends User {
        public Staff(String id, String name) {
            super(id, name);
        }

        public String getRole() {
            return "Staff";
        }
    }

    static class Student extends User {
        public Student(String id, String name) {
            super(id, name);
        }

        public String getRole() {
            return "Student";
        }
    }

    // ================= FACTORY =================
    static class UserFactory {
        public static User createUser(String type, String id, String name) {
            switch (type.toLowerCase()) {
                case "admin":
                    return new Admin(id, name);
                case "staff":
                    return new Staff(id, name);
                case "student":
                    return new Student(id, name);
                default:
                    throw new IllegalArgumentException("Invalid user type");
            }
        }
    }

    // ================= BOOKING =================
    static class Booking {
        private String roomId;
        private String timeSlot;

        public Booking(String roomId, String timeSlot) {
            this.roomId = roomId;
            this.timeSlot = timeSlot;
        }

        public String getRoomId() {
            return roomId;
        }

        public String getTimeSlot() {
            return timeSlot;
        }
    }

    // ================= EXCEPTION =================
    static class BookingException extends Exception {
        public BookingException(String msg) {
            super(msg);
        }
    }

    // ================= OBSERVER =================
    interface Observer {
        void update(String message);
    }

    static class UserNotification implements Observer {
        private String name;

        public UserNotification(String name) {
            this.name = name;
        }

        public void update(String message) {
            System.out.println("Notification for " + name + ": " + message);
        }
    }

    static class NotificationService {
        java.util.List<Observer> observers = new java.util.ArrayList<>();

        public void addObserver(Observer o) {
            observers.add(o);
        }

        public void notifyUsers(String msg) {
            for (Observer o : observers) {
                o.update(msg);
            }
        }
    }

    // ================= BOOKING MANAGER =================
    static class BookingManager {
        java.util.List<Booking> bookings = new java.util.ArrayList<>();
        NotificationService notificationService;

        public BookingManager(NotificationService ns) {
            this.notificationService = ns;
        }

        public void bookRoom(String roomId, String timeSlot) throws BookingException {

            for (Booking b : bookings) {
                if (b.getRoomId().equals(roomId) && b.getTimeSlot().equals(timeSlot)) {
                    throw new BookingException("Room already booked!");
                }
            }

            bookings.add(new Booking(roomId, timeSlot));
            notificationService.notifyUsers("Room booked successfully!");
        }
    }

    // ================= FACADE =================
    static class SCMSFacade {
        BookingManager manager;

        public SCMSFacade(BookingManager m) {
            this.manager = m;
        }

        public void bookRoom(String roomId, String timeSlot) {
            try {
                manager.bookRoom(roomId, timeSlot);
                System.out.println("Booking successful!");
            } catch (BookingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        NotificationService ns = new NotificationService();

        User u1 = UserFactory.createUser("staff", "1", "Aadhil");
        User u2 = UserFactory.createUser("student", "2", "John");

        ns.addObserver(new UserNotification(u1.getName()));
        ns.addObserver(new UserNotification(u2.getName()));

        BookingManager bm = new BookingManager(ns);
        SCMSFacade facade = new SCMSFacade(bm);

        facade.bookRoom("R101", "10AM-12PM");

        // Duplicate booking (FAIL CASE)
        facade.bookRoom("R101", "10AM-12PM");
    }
}
import java.util.ArrayList;
import java.util.List;

/**
 * SCMS - Student Center Management System
 * Implementation of Factory, Observer, and Facade Patterns.
 */
public class Main {

    // ================= USER CLASSES =================
    static abstract class User {
        protected String userId;
        protected String name;

        public User(String userId, String name) {
            this.userId = userId;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public abstract String getRole();
    }

    static class Admin extends User {
        public Admin(String id, String name) {
            super(id, name);
        }
        @Override
        public String getRole() { return "Admin"; }
    }

    static class Staff extends User {
        public Staff(String id, String name) {
            super(id, name);
        }
        @Override
        public String getRole() { return "Staff"; }
    }

    static class Student extends User {
        public Student(String id, String name) {
            super(id, name);
        }
        @Override
        public String getRole() { return "Student"; }
    }

    // ================= FACTORY PATTERN =================
    static class UserFactory {
        public static User createUser(String type, String id, String name) {
            if (type == null) throw new IllegalArgumentException("Type cannot be null");
            
            switch (type.toLowerCase()) {
                case "admin": return new Admin(id, name);
                case "staff": return new Staff(id, name);
                case "student": return new Student(id, name);
                default: throw new IllegalArgumentException("Invalid user type: " + type);
            }
        }
    }

    // ================= OBSERVER PATTERN =================
    interface Observer {
        void update(String message);
    }

    static class UserNotification implements Observer {
        private String name;

        public UserNotification(String name) {
            this.name = name;
        }

        @Override
        public void update(String message) {
            System.out.println("Notification for " + name + ": " + message);
        }
    }

    static class NotificationService {
        private List<Observer> observers = new ArrayList<>();

        public void addObserver(Observer o) {
            observers.add(o);
        }

        public void notifyUsers(String msg) {
            for (Observer o : observers) {
                o.update(msg);
            }
        }
    }

    // ================= CORE LOGIC =================
    static class Booking {
        private String roomId;
        private String timeSlot;

        public Booking(String roomId, String timeSlot) {
            this.roomId = roomId;
            this.timeSlot = timeSlot;
        }

        public String getRoomId() { return roomId; }
        public String getTimeSlot() { return timeSlot; }
    }

    static class BookingException extends Exception {
        public BookingException(String msg) {
            super(msg);
        }
    }

    static class BookingManager {
        private List<Booking> bookings = new ArrayList<>();
        private NotificationService notificationService;

        public BookingManager(NotificationService ns) {
            this.notificationService = ns;
        }

        public void bookRoom(String roomId, String timeSlot) throws BookingException {
            for (Booking b : bookings) {
                if (b.getRoomId().equals(roomId) && b.getTimeSlot().equals(timeSlot)) {
                    throw new BookingException("Room " + roomId + " is already booked for " + timeSlot);
                }
            }
            bookings.add(new Booking(roomId, timeSlot));
            notificationService.notifyUsers("Room " + roomId + " booked successfully!");
        }
    }

    // ================= FACADE PATTERN =================
    static class SCMSFacade {
        private BookingManager manager;

        public SCMSFacade(BookingManager m) {
            this.manager = m;
        }

        public void bookRoom(String roomId, String timeSlot) {
            try {
                manager.bookRoom(roomId, timeSlot);
                System.out.println("--- System: Booking Process Completed ---\n");
            } catch (BookingException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    // ================= MAIN EXECUTION =================
    public static void main(String[] args) {
        // 1. Setup Services
        NotificationService ns = new NotificationService();
        
        // 2. Create Users via Factory
        User u1 = UserFactory.createUser("staff", "S001", "Aadhil");
        User u2 = UserFactory.createUser("student", "ST99", "John");

        // 3. Register Observers
        ns.addObserver(new UserNotification(u1.getName()));
        ns.addObserver(new UserNotification(u2.getName()));

        // 4. Initialize Manager and Facade
        BookingManager bm = new BookingManager(ns);
        SCMSFacade facade = new SCMSFacade(bm);

        // 5. Perform Operations
        System.out.println("Attempting first booking...");
        facade.bookRoom("R101", "10AM-12PM");

        System.out.println("Attempting duplicate booking...");
        facade.bookRoom("R101", "10AM-12PM");
    }
}