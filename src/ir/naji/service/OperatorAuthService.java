package ir.naji.service;

import ir.naji.dto.OperatorDto;
import ir.naji.entity.user.employee.Operator;
import ir.naji.repository.OperatorRepo;

public class OperatorAuthService {
    private final OperatorRepo operatorRepo = OperatorRepo.getInstance();

    public OperatorDto login(String username, String password) {
        Operator op = operatorRepo.findByUsername(username);
        if (op != null && op.getPassword().equals(password)) {
            return new OperatorDto(op.getId(), op.getUsername());
        }
        return null;
    }
}
