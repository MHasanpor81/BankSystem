package ir.naji.service;

import ir.naji.dto.ManagerDto;
import ir.naji.entity.user.employee.Manager;
import ir.naji.repository.ManagerRepo;

public class ManagerAuthService {
    private final ManagerRepo managerRepo = ManagerRepo.getInstance();

    public ManagerDto login(String username, String password) {
        Manager m = managerRepo.findByUsername(username);
        if (m != null && m.getPassword().equals(password)) {
            return new ManagerDto(m.getId(), m.getUsername());
        }
        return null;
    }
}
