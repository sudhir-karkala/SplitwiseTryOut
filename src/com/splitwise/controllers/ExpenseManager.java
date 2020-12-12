package com.splitwise.controllers;

import com.splitwise.models.ExpenseSplitType;
import com.splitwise.models.User;
import com.splitwise.services.UserService;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * @author sudhir on 11-Dec-2020
 */
public class ExpenseManager {
    private UserService userService;

    public ExpenseManager(UserService userService) {
        this.userService = userService;
    }

    public void splitExpense(String expenseLine) {
        String[] parts = expenseLine.split("\\s");
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        int i = 0;
        while (i < parts.length) {
            String currentUser = parts[i++];
            double expense = Double.parseDouble(parts[i++]);
            int noOfUsers = Integer.parseInt(parts[i++]);
            String[] users = new String[noOfUsers];
            for (int u = 0; u < noOfUsers; u++) {
                users[u] = parts[i++];
            }
            ExpenseSplitType expenseSplitType = ExpenseSplitType.valueOf(parts[i++]);
            if (ExpenseSplitType.EQUAL.equals(expenseSplitType)) {
                User user = userService.getUserById(currentUser);
                double individualExpense = Double.valueOf(decimalFormat.format(expense / noOfUsers));
                for (int u = 0; u < users.length; u++) {
                    User user1 = userService.getUserById(users[u]);
                    if (!user1.equals(user)) {
                        user1.getOwesToMap().put(user,
                                user.getOwesToMap().getOrDefault(user, 0.0) + individualExpense);
                        user1.setOwesTo(user1.getOwesTo() + individualExpense);
                        user.getOwesFromMap().put(user1,
                                user.getOwesFromMap().getOrDefault(user1, 0.0) + individualExpense);
                        user.setOwesFrom(user.getOwesFrom() + individualExpense);
                    }
                    if (u == 0) {
                        if (!user1.equals(user)) {
                            user.getOwesFromMap().put(user1,
                                    user.getOwesFromMap().getOrDefault(user1, 0.0) + (expense - individualExpense * users.length));
                            user.setOwesFrom(user.getOwesFrom() + (expense - individualExpense * users.length));
                            user1.getOwesToMap().put(user,
                                    user.getOwesToMap().getOrDefault(user, 0.0) + (expense - individualExpense * users.length));
                            user1.setOwesTo(user1.getOwesTo() + (expense - individualExpense * users.length));
                        }
                    }
                }

            } else {
                Double[] expenses = new Double[noOfUsers];
                for (int u = 0; u < noOfUsers; u++) {
                    expenses[u] = Double.parseDouble(parts[i++]);
                }
                User user = userService.getUserById(currentUser);
                switch (expenseSplitType) {
                    case EXACT:
                        for (int u = 0; u < users.length; u++) {
                            User user1 = userService.getUserById(users[u]);
                            if (!user1.equals(user)) {
                                user.getOwesFromMap().put(user1,
                                        user.getOwesFromMap().getOrDefault(
                                                user1, 0.0d) + expenses[u]);
                                user.setOwesFrom(user.getOwesFrom() + expenses[u]);
                                user1.getOwesToMap().put(user,
                                        user1.getOwesToMap().getOrDefault(
                                                user, 0.0d) + expenses[u]);
                                user1.setOwesTo(user1.getOwesTo() + expenses[u]);
                            }
                        }
                        break;
                    case PERCENT:
                        for (int u = 0; u < users.length; u++) {
                            User user1 = userService.getUserById(users[u]);
                            if (!user1.equals(user)) {
                                user.getOwesFromMap().put(user1,
                                        user.getOwesFromMap().getOrDefault(
                                                user1, 0.0) + (expenses[u] * expense / 100.0));
                                user.setOwesFrom(user.getOwesFrom() + (expenses[u] * expense / 100.0));
                                user1.getOwesToMap().put(user,
                                        user1.getOwesToMap().getOrDefault(
                                                user, 0.0) + expenses[u] * expense / 100.0);
                                user1.setOwesTo(user1.getOwesTo() + (expenses[u] * expense / 100.0));
                            }
                        }
                        break;
                }
            }
        }
    }

    public void showAllExpenses() {
        List<User> users = userService.getUserRepository().getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println("------------");
            System.out.println("| User: " + user.getUserId() + " |");
            System.out.println("------------");
            System.out.println("Balance : " + String.format("%.2f", user.getOwesTo() - user.getOwesFrom()));
            if (user.getOwesFromMap().isEmpty()
                    && user.getOwesToMap().isEmpty()) {
                System.out.println("No Balances");
            }
            for (Map.Entry<User, Double> entry : user.getOwesToMap().entrySet()) {
                System.out.println(user.getUserId() + " owes " + entry.getKey().getUserId() + ":" + entry.getValue());
            }
            for (Map.Entry<User, Double> entry : user.getOwesFromMap().entrySet()) {
                System.out.println(entry.getKey().getUserId() + " owes " + user.getUserId() + ":" + entry.getValue());
            }
            System.out.println("-----------------------------------------------");
        }
    }

    public void showExpensesForAUser(String userId) {
        User user = userService.getUserById(userId);
        System.out.println("------------");
        System.out.println("| User: " + userId + " |");
        System.out.println("------------");
        System.out.println("Balance : " + String.format("%.2f", user.getOwesTo() - user.getOwesFrom()));
        if (userService.getUserById(userId).getOwesFromMap().isEmpty()
                && userService.getUserById(userId).getOwesToMap().isEmpty()) {
            System.out.println("No Balances");
        }
        for (Map.Entry<User, Double> entry : user.getOwesToMap().entrySet()) {
            System.out.println(userId + " owes " + entry.getKey().getUserId() + ":" + entry.getValue());
        }
        for (Map.Entry<User, Double> entry : user.getOwesFromMap().entrySet()) {
            System.out.println(entry.getKey().getUserId() + " owes " + userId + ":" + entry.getValue());
        }
        System.out.println("-----------------------------------------------");
    }
}
