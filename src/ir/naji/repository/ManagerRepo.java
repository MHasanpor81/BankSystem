package ir.naji.repository;

import ir.naji.entity.user.employee.Manager;
import java.util.ArrayList;
import java.util.List;

public class ManagerRepo {
    private static final ManagerRepo INSTANCE = new ManagerRepo();
    private final List<Manager> managers = new ArrayList<>();

    private ManagerRepo() {
        managers.add(new Manager("M001", "admin", "1234"));
    }

    public static ManagerRepo getInstance() { return INSTANCE; }

    public void addManager(Manager m) { managers.add(m); }
    public void updateManager(Manager m) {
        managers.removeIf(x -> x.getId().equals(m.getId()));
        managers.add(m);
    }
    public Manager findByUsername(String username) {
        return managers.stream().filter(m -> m.getUsername().equals(username)).findFirst().orElse(null);
    }
    public List<Manager> getAll() { return managers; }
}
