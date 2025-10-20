package org.wt.com.expense_sharing_app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wt.com.expense_sharing_app.persistence.entity.BalanceSheet;
import org.wt.com.expense_sharing_app.persistence.entity.Expense;
import org.wt.com.expense_sharing_app.persistence.entity.ExpenseShares;
import org.wt.com.expense_sharing_app.persistence.entity.Group;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.persistence.repository.ExpenseRepository;
import org.wt.com.expense_sharing_app.persistence.repository.GroupRepository;
import org.wt.com.expense_sharing_app.persistence.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseRepository expenseRepository;

    public DataLoader(UserRepository userRepository,
                      GroupRepository groupRepository,
                      ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) {
        loadSampleData();
    }

    private void loadSampleData() {

        // Create Users
        User u1 = new User(null, "Alice", "alice@gmail.com", "0501234567");
        User u2 = new User(null, "Bob", "bob@gmail.com", "0502345678");
        User u3 = new User(null, "Charlie", "charlie@gmail.com", "0503456789");
        User u4 = new User(null, "David", "david@gmail.com", "0504567890");
        User u5 = new User(null, "Emma", "emma@gmail.com", "0505678901");

        List<User> users = userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5));

        // Create Groups
        Group g1 = new Group(null, "Dubai Trip", "Trip to Dubai with friends", Arrays.asList(u1, u2, u3));
        Group g2 = new Group(null, "Office Lunch", "Monthly team lunch", Arrays.asList(u4, u5, u1));

        groupRepository.saveAll(Arrays.asList(g1, g2));

        // Create Expenses

        // Expense 1: Alice paid 300 for "Dinner" (equal split)
        Expense exp1 = new Expense();
        exp1.setDescription("Dinner");
        exp1.setPaidBy(u1);
        exp1.setGroup(g1);
        exp1.setTotalAmount(300.0);
    
        ExpenseShares s1 = new ExpenseShares(null, exp1, u1, 100.0);
        ExpenseShares s2 = new ExpenseShares(null, exp1, u2, 100.0);
        ExpenseShares s3 = new ExpenseShares(null, exp1, u3, 100.0);

        exp1.setExpenseShares(Arrays.asList(s1, s2, s3));
        //expenseRepository.save(exp1);

        // Expense 2: Bob paid 200 for "Taxi" (unequal split)
        // Expense exp2 = new Expense();
        // exp2.setDescription("Taxi");
        // exp2.setPaidBy(u2);
        // exp2.setGroup(g1);
        // exp2.setTotalAmount(200.0);

        // ExpenseShares s4 = new ExpenseShares(null, exp2, u1, 50.0);
        // ExpenseShares s5 = new ExpenseShares(null, exp2, u2, 100.0);
        // ExpenseShares s6 = new ExpenseShares(null, exp2, u3, 50.0);

        // exp2.setExpenseShares(Arrays.asList(s4, s5, s6));
        // expenseRepository.save(exp2);

        // // Expense 3: Office Lunch (Emma paid)
        // Expense exp3 = new Expense();
        // exp3.setDescription("Office Lunch");
        // exp3.setPaidBy(u5);
        // exp3.setGroup(g2);
        // exp3.setTotalAmount(150.0);

        // ExpenseShares s7 = new ExpenseShares(null, exp3, u4, 50.0);
        // ExpenseShares s8 = new ExpenseShares(null, exp3, u5, 50.0);
        // ExpenseShares s9 = new ExpenseShares(null, exp3, u1, 50.0);

        // exp3.setExpenseShares(Arrays.asList(s7, s8, s9));
        // expenseRepository.save(exp3);

        System.out.println("✅ Sample data loaded successfully!");
    }
}
