package ir.naji.entity.user.employee;

import ir.naji.entity.user.User;

public class Manager extends User {
    private String id;
    private String linkedCustomerId;

    public Manager(String id, String username, String password) {
        super(username, password);
        this.id = id;
    }

    public String getId() { return id; }
    public String getLinkedCustomerId() { return linkedCustomerId; }
    public void setLinkedCustomerId(String linkedCustomerId) { this.linkedCustomerId = linkedCustomerId; }
    public boolean isAlsoCustomer() { return linkedCustomerId != null; }

    @Override
    public String toString() {
        return "Manager{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                (linkedCustomerId != null ? ", linkedCustomerId='" + linkedCustomerId + '\'' : "") +
                '}';
    }
}
