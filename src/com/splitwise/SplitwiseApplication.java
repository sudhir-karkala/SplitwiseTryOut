package com.splitwise;

import com.splitwise.controllers.ExpenseManager;
import com.splitwise.models.User;
import com.splitwise.repositories.UserRepository;
import com.splitwise.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author sudhir on 11-Dec-2020
 */
public class SplitwiseApplication {
    private static ExpenseManager expenseManager;
    private static UserService userService;
    private static UserRepository userRepository = new UserRepository();

    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        userList.add(new User("u1", "u1abc", "u1@gmail.com", 99999));
        userList.add(new User("u2", "u2abc", "u2@gmail.com", 88888));
        userList.add(new User("u3", "u3abc", "u3@gmail.com", 77777));
        userList.add(new User("u4", "u4abc", "u4@gmail.com", 66666));
        userRepository.setAllUsers(userList);
        userService = new UserService(userRepository);
        expenseManager = new ExpenseManager(userService);

        boolean isExit = false;

        try (Scanner scanner = new Scanner(System.in)) {
            while (!isExit) {
                System.out.println("1:Add Expense in the format below:\n" +
                        "<user-id-of-person-who-paid> <amount> <no-of-users> <space-separated-list-of-users>" +
                        " <EQUAL/EXACT/PERCENT> <space-separated-values-in-case-of-non-equal>" +
                        "\n2:SHOW\n3:SHOW <user-id>");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        String expenseLine = scanner.nextLine();
                        expenseManager.splitExpense(expenseLine);
                        break;
                    case 2:
                        expenseManager.showAllExpenses();
                        break;
                    case 3:
                        System.out.println("Specify user-id");
                        String userId = scanner.next();
                        expenseManager.showExpensesForAUser(userId);
                        break;
                    default:
                        System.out.println("Invalid option selected. Exiting...");
                        isExit = true;
                }
            }
        }
    }
}
