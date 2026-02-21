package ir.naji.repository;

import ir.naji.entity.user.employee.Operator;
import java.util.ArrayList;
import java.util.List;

public class OperatorRepo {
    private static final OperatorRepo INSTANCE = new OperatorRepo();
    private final List<Operator> operators = new ArrayList<>();
    private OperatorRepo() {
        operators.add(new Operator("O001", "operator", "op1234"));
    }

    public static OperatorRepo getInstance() { return INSTANCE; }

    public void addOperator(Operator op) { operators.add(op); }
    public void updateOperator(Operator op) {
        operators.removeIf(o -> o.getId().equals(op.getId()));
        operators.add(op);
    }
    public Operator findByUsername(String username) {
        return operators.stream().filter(o -> o.getUsername().equals(username)).findFirst().orElse(null);
    }
    public List<Operator> getAll() { return operators; }
}
