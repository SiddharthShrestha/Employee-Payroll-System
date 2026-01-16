package Controller;

import Model.EmployeeModel;
import Model.EmployeeModel.Task;
import View.VIEW;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class EmployeeController {
    private VIEW view;
    private ArrayList<Employee> employeeList; // ArrayList for employee storage
    private Queue<Employee> employeeQueue; // Queue for FIFO employee processing
    private LinkedList<Employee> employeeLinkedList; // LinkedList for efficient insertions/deletions
    private LinkedList<Task> taskLinkedList; // LinkedList for task management
    
    public EmployeeController(VIEW view) {
        this.view = view;
        this.employeeList = new ArrayList<>(); // Initialize ArrayList
        this.employeeQueue = new LinkedList<>(); // Initialize Queue using LinkedList
        this.employeeLinkedList = new LinkedList<>(); // Initialize LinkedList for employees
        this.taskLinkedList = new LinkedList<>(); // Initialize LinkedList for tasks
        initController();
        loadInitialData();
        loadInitialTasks();
    }
    
    private void initController() {
        view.getAddButton().addActionListener(e -> handleAddEmployee());
        view.getRemoveButton().addActionListener(e -> handleDequeueEmployee());
        view.getUpdateButton().addActionListener(e -> handleUpdateEmployee());
        view.getAddTaskButton().addActionListener(e -> handleAddTask());
        view.getCompleteTaskButton().addActionListener(e -> handleCompleteTask());
        view.getSortByIdButton().addActionListener(e -> handleSortById());
        view.getSortByNameButton().addActionListener(e -> handleSortByName());
        view.getSearchButton().addActionListener(e -> handleSearchByName());
        view.getSortTaskByIdButton().addActionListener(e -> handleSortTaskById());
        view.getSortTaskByNameButton().addActionListener(e -> handleSortTaskByName());
        view.getSearchTaskButton().addActionListener(e -> handleSearchTaskByName());
        view.getCalculateTotalButton().addActionListener(e -> handleCalculateTotalSalary());
        view.getMergeSortSalaryButton().addActionListener(e -> handleMergeSortBySalary());
    }
    
    // Selection Sort Algorithm - Sorts LinkedList by Employee ID in ascending order
    public static void SelectionSortById(LinkedList<Employee> list) {
        int size = list.size();
        
        for (int step = 0; step < size - 1; step++) {
            int min_idx = step;
            
            // Find minimum element in remaining unsorted array
            for (int i = step + 1; i < size; i++) {
                if (Integer.parseInt(list.get(i).getEmpId()) < Integer.parseInt(list.get(min_idx).getEmpId())) {
                    min_idx = i;
                }
            }
            
            // Swap the found minimum element with the first element
            Employee temp = list.get(step);
            list.set(step, list.get(min_idx));
            list.set(min_idx, temp);
        }
    }
    
    // Selection Sort Algorithm - Sorts LinkedList by Employee Name alphabetically
    public static void SelectionSortByName(LinkedList<Employee> list) {
        int size = list.size();

        for (int step = 0; step < size - 1; step++) {
            int min_idx = step;

            for (int i = step + 1; i < size; i++) {
                // Compare names case-insensitively
                if (0 >= list.get(i).getName().compareToIgnoreCase(list.get(min_idx).getName())) {
                    min_idx = i;
                }
            }

            // Swap elements in LinkedList
            Employee temp = list.get(step);
            list.set(step, list.get(min_idx));
            list.set(min_idx, temp);
        }
    }
    
    // Merge Sort Algorithm - Sorts LinkedList by Salary in descending order (highest to lowest)
    public static void MergeSortBySalary(LinkedList<Employee> list) {
        if (list.size() <= 1) {
            return; // Base case: list with 0 or 1 element is already sorted
        }
        
        int mid = list.size() / 2;
        LinkedList<Employee> left = new LinkedList<>(); // Left half LinkedList
        LinkedList<Employee> right = new LinkedList<>(); // Right half LinkedList
        
        // Divide the list into two halves
        for (int i = 0; i < mid; i++) {
            left.add(list.get(i));
        }
        
        for (int i = mid; i < list.size(); i++) {
            right.add(list.get(i));
        }
        
        // Recursively sort both halves
        MergeSortBySalary(left);
        MergeSortBySalary(right);
        
        // Merge the sorted halves
        merge(list, left, right);
    }
    
    // Merge function for Merge Sort - Combines two sorted LinkedLists
    private static void merge(LinkedList<Employee> result, LinkedList<Employee> left, LinkedList<Employee> right) {
        int i = 0, j = 0, k = 0;
        
        // Merge elements in descending order of salary
        while (i < left.size() && j < right.size()) {
            double leftSalary = Double.parseDouble(left.get(i).getSalary());
            double rightSalary = Double.parseDouble(right.get(j).getSalary());
            
            if (leftSalary >= rightSalary) {
                result.set(k, left.get(i));
                i++;
            } else {
                result.set(k, right.get(j));
                j++;
            }
            k++;
        }
        
        // Copy remaining elements from left LinkedList
        while (i < left.size()) {
            result.set(k, left.get(i));
            i++;
            k++;
        }
        
        // Copy remaining elements from right LinkedList
        while (j < right.size()) {
            result.set(k, right.get(j));
            j++;
            k++;
        }
    }
    
    // Binary Search Algorithm - Searches for employee by name in sorted LinkedList
    public static int binarySearchByName(LinkedList<Employee> list, String targetName) {
        int left = 0;
        int right = list.size() - 1;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            String midName = list.get(mid).getName();
            int comparison = targetName.compareToIgnoreCase(midName);
            
            if (comparison == 0) {
                return mid; // Found the target
            }
            if (comparison > 0) {
                left = mid + 1; // Search right half
            } else {
                right = mid - 1; // Search left half
            }
        }
        return -1; // Not found
    }
    
    // Insertion Sort Algorithm - Sorts LinkedList of Tasks by TaskID in ascending order
    public static void InsertionSortByTaskId(LinkedList<Task> list) {
        int size = list.size();

        for (int i = 1; i < size; i++) {
            Task key = list.get(i);
            int j = i - 1;

            // Move elements greater than key to one position ahead
            while (j >= 0 && Integer.parseInt(list.get(j).getTaskId()) > Integer.parseInt(key.getTaskId())) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }

            list.set(j + 1, key); // Insert key at correct position
        }
    }
    
    // Insertion Sort Algorithm - Sorts LinkedList of Tasks by TaskName alphabetically
    public static void InsertionSortByTaskName(LinkedList<Task> list) {
        int size = list.size();

        for (int i = 1; i < size; i++) {
            Task key = list.get(i);
            int j = i - 1;

            // Compare task names case-insensitively
            while (j >= 0 && list.get(j).getTaskName().compareToIgnoreCase(key.getTaskName()) > 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }

            list.set(j + 1, key);
        }
    }
    
    // Linear Search Algorithm - Searches for task by name in LinkedList
    public static int linearSearchByTaskName(LinkedList<Task> list, String targetName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTaskName().equalsIgnoreCase(targetName)) {
                return i; // Return index when found
            }
        }
        return -1; // Return -1 if not found
    }
    
    public void handleSortById() {
        SelectionSortById(employeeLinkedList);
        LoadEmployeeTable();
        JOptionPane.showMessageDialog(view, "Sorted by ID", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void handleSortByName() {
        SelectionSortByName(employeeLinkedList);
        LoadEmployeeTable();
        JOptionPane.showMessageDialog(view, "Sorted by Name", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void handleMergeSortBySalary() {
        if (employeeLinkedList.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "No employees in the system", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        MergeSortBySalary(employeeLinkedList);
        LoadEmployeeTable();
        
        JOptionPane.showMessageDialog(view, 
            "Sorted by Salary (Highest to Lowest)", 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void handleSearchByName() {
        String name = view.getSearchField().getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Must sort before binary search
        SelectionSortByName(employeeLinkedList);
        LoadEmployeeTable();
        
        // Perform binary search on sorted LinkedList
        int index = binarySearchByName(employeeLinkedList, name);
        
        if (index == -1) {
            JOptionPane.showMessageDialog(view, "Employee not found", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Employee emp = employeeLinkedList.get(index);
            JOptionPane.showMessageDialog(view,
                "Name: " + emp.getName() + "\n" +
                "ID: " + emp.getEmpId() + "\n" +
                "Role: " + emp.getRole() + "\n" +
                "Contact: " + emp.getContact() + "\n" +
                "Salary: " + emp.getSalary(),
                "Employee Found",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        view.getSearchField().setText("");
    }
    
    public void handleCalculateTotalSalary() {
        if (employeeLinkedList.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "No employees in the system", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        double totalSalary = 0.0;
        int employeeCount = employeeLinkedList.size();
        
        // Iterate through LinkedList to calculate total salary
        for (Employee emp : employeeLinkedList) {
            try {
                double salary = Double.parseDouble(emp.getSalary());
                totalSalary += salary;
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format for employee: " + emp.getName());
            }
        }
        
        String formattedTotal = String.format("%,.2f", totalSalary);
        double averageSalary = totalSalary / employeeCount;
        String formattedAverage = String.format("%,.2f", averageSalary);
        
        JOptionPane.showMessageDialog(view,
            "Total Employees: " + employeeCount + "\n" +
            "Total Salary: Rs. " + formattedTotal + "\n" +
            "Average Salary: Rs. " + formattedAverage,
            "Salary Calculation",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void handleSortTaskById() {
        InsertionSortByTaskId(taskLinkedList);
        LoadTaskTable();
        JOptionPane.showMessageDialog(view, "Sorted by ID", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void handleSortTaskByName() {
        InsertionSortByTaskName(taskLinkedList);
        LoadTaskTable();
        JOptionPane.showMessageDialog(view, "Sorted by Name", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void handleSearchTaskByName() {
        String name = view.getSearchTaskField().getText().trim();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter a task name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Linear search - no sorting required
        int index = linearSearchByTaskName(taskLinkedList, name);
        
        if (index == -1) {
            JOptionPane.showMessageDialog(view, "Task not found", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Task task = taskLinkedList.get(index);
            JOptionPane.showMessageDialog(view,
                "Task: " + task.getTaskName() + "\n" +
                "ID: " + task.getTaskId() + "\n" +
                "Priority: " + task.getPriority() + "\n" +
                "Status: " + task.getStatus(),
                "Task Found",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        view.getSearchTaskField().setText("");
    }
    
    // Enqueue operation - Adds employee to Queue (FIFO), ArrayList, and LinkedList
    public void enqueue(String empId, String name, String role, String contact, String salary) {
        Employee newEmployee = new Employee(empId, name, role, contact, salary);
        employeeQueue.offer(newEmployee); // Add to Queue at rear
        employeeList.add(newEmployee); // Add to ArrayList
        employeeLinkedList.addLast(newEmployee); // Add to end of LinkedList
        
        LoadEmployeeTable();
    }
    
    // Dequeue operation - Removes employee from front of Queue (FIFO)
    public void dequeue() {
        if (employeeQueue.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No employees to remove");
            return;
        }
        
        Employee removedEmployee = employeeQueue.poll(); // Remove from Queue front
        
        if (removedEmployee != null) {
            if (!employeeList.isEmpty()) {
                employeeList.remove(0); // Remove from ArrayList
            }
            if (!employeeLinkedList.isEmpty()) {
                employeeLinkedList.removeFirst(); // Remove from LinkedList front
            }
            
            JOptionPane.showMessageDialog(view, "Employee removed: " + removedEmployee.getName());
            LoadEmployeeTable();
        }
    }
    
    public void LoadEmployeeTable() {
        DefaultTableModel model = (DefaultTableModel) view.getEmployeeTable().getModel();
        model.setRowCount(0);
        
        // Iterate through LinkedList to populate table
        for (Employee emp : employeeLinkedList) {
            Object[] row = {
                emp.getEmpId(), 
                emp.getName(), 
                emp.getRole(), 
                emp.getContact(), 
                emp.getSalary()
            };
            model.addRow(row);
        }
    }
    
    public void LoadTaskTable() {
        DefaultTableModel model = (DefaultTableModel) view.getTaskTable().getModel();
        model.setRowCount(0);
        
        // Iterate through LinkedList to populate task table
        for (Task task : taskLinkedList) {
            Object[] row = {
                task.getTaskId(),
                task.getTaskName(),
                task.getPriority(),
                task.getStatus()
            };
            model.addRow(row);
        }
    }
    
    private boolean isDuplicateEmpId(String empId) {
        for (Employee emp : employeeLinkedList) {
            if (emp.getEmpId().equals(empId)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isDuplicateTaskId(String taskId) {
        for (Task task : taskLinkedList) {
            if (task.getTaskId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }
    
    public void loadInitialData(){
        enqueue("1", "John Smith", "Manager", "9841234567", "75000");
        enqueue("2", "Sarah Johnson", "Developer", "9851234567", "60000");
        enqueue("3", "Michael Brown", "Designer", "9861234567", "55000");
        enqueue("4", "Emily Davis", "HR Specialist", "9871234567", "50000");
        enqueue("5", "David Wilson", "Accountant", "9881234567", "52000");
    }
    
    public void loadInitialTasks() {
        Stack<Task> taskStack = EmployeeModel.getTaskHistory(); // Get Stack from Model
        
        // Push operations - Adding tasks to Stack (LIFO)
        Task task1 = new Task("1", "Update Website", "High", "Pending");
        taskStack.push(task1); // Push to Stack
        taskLinkedList.addLast(task1); // Add to LinkedList
        
        Task task2 = new Task("2", "Client Meeting", "Medium", "In Progress");
        taskStack.push(task2);
        taskLinkedList.addLast(task2);
        
        Task task3 = new Task("3", "Database Backup", "High", "Pending");
        taskStack.push(task3);
        taskLinkedList.addLast(task3);
        
        Task task4 = new Task("4", "Code Review", "Low", "Completed");
        taskStack.push(task4);
        taskLinkedList.addLast(task4);
        
        Task task5 = new Task("5", "Security Audit", "High", "Pending");
        taskStack.push(task5);
        taskLinkedList.addLast(task5);
        
        LoadTaskTable();
    }
    
    public void handleAddEmployee() {
        try {
            String empId = view.getEmpIdField().getText().trim();
            String name = view.getNameField().getText();
            String role = view.getRoleField().getText();
            String contact = view.getContactField().getText();
            String salary = view.getSalaryField().getText();
            
            if (empId.isEmpty() || name.isEmpty() || role.isEmpty() || contact.isEmpty() || salary.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!empId.matches("\\d+")) {
                JOptionPane.showMessageDialog(view, "Employee ID must be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (isDuplicateEmpId(empId)) {
                JOptionPane.showMessageDialog(view, "Employee ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Integer.parseInt(salary); // Validate salary is numeric
            
            enqueue(empId, name, role, contact, salary); // Add to data structures
            
            view.getEmpIdField().setText("");
            view.getNameField().setText("");
            view.getRoleField().setText("");
            view.getContactField().setText("");
            view.getSalaryField().setText("");
            
            JOptionPane.showMessageDialog(view, "Employee added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Salary must be a number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void handleDequeueEmployee() {
        dequeue(); // Remove employee from Queue
    }
    
    public void handleUpdateEmployee() {
        try {
            String empId = view.getEmpIdField().getText().trim();
            String name = view.getNameField().getText();
            String role = view.getRoleField().getText();
            String contact = view.getContactField().getText();
            String salary = view.getSalaryField().getText();
            
            if (empId.isEmpty() || name.isEmpty() || role.isEmpty() || contact.isEmpty() || salary.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!empId.matches("\\d+")) {
                JOptionPane.showMessageDialog(view, "Employee ID must be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Integer.parseInt(salary);
            
            boolean found = false;
            // Update in LinkedList
            for (Employee emp : employeeLinkedList) {
                if (emp.getEmpId().equals(empId)) {
                    emp.setName(name);
                    emp.setRole(role);
                    emp.setContact(contact);
                    emp.setSalary(salary);
                    found = true;
                    break;
                }
            }
            
            // Update in ArrayList
            for (Employee emp : employeeList) {
                if (emp.getEmpId().equals(empId)) {
                    emp.setName(name);
                    emp.setRole(role);
                    emp.setContact(contact);
                    emp.setSalary(salary);
                    break;
                }
            }
            
            if (found) {
                LoadEmployeeTable();
                view.getEmpIdField().setText("");
                view.getNameField().setText("");
                view.getRoleField().setText("");
                view.getContactField().setText("");
                view.getSalaryField().setText("");
                JOptionPane.showMessageDialog(view, "Employee updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Employee ID not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Salary must be a number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void handleAddTask() {
        try {
            String taskId = view.getTaskIdField().getText().trim();
            String taskName = view.getTaskNameField().getText().trim();
            String priority = view.getPriorityField().getText().trim();
            String status = view.getStatusField().getText().trim();
            
            if (taskId.isEmpty() || taskName.isEmpty() || priority.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!taskId.matches("\\d+")) {
                JOptionPane.showMessageDialog(view, "Task ID must be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (isDuplicateTaskId(taskId)) {
                JOptionPane.showMessageDialog(view, "Task ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Stack<Task> taskStack = EmployeeModel.getTaskHistory();
            
            Task newTask = new Task(taskId, taskName, priority, status);
            taskStack.push(newTask); // Push to Stack (LIFO)
            taskLinkedList.addLast(newTask); // Add to LinkedList
            
            view.getTaskIdField().setText("");
            view.getTaskNameField().setText("");
            view.getPriorityField().setText("");
            view.getStatusField().setText("");
            
            LoadTaskTable();
            JOptionPane.showMessageDialog(view, "Task added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error adding task", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void handleCompleteTask() {
        Stack<Task> taskStack = EmployeeModel.getTaskHistory();
        
        if (taskStack.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No tasks to complete");
            return;
        }
        
        Task completedTask = taskStack.pop(); // Pop from Stack (LIFO) - removes most recently added
        
        if (!taskLinkedList.isEmpty()) {
            taskLinkedList.removeLast(); // Remove from LinkedList
        }
        
        LoadTaskTable();
        
        JOptionPane.showMessageDialog(view, "Task completed: " + completedTask.getTaskName(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private class Employee {
        private String empId;
        private String name;
        private String role;
        private String contact;
        private String salary;
        
        public Employee(String empId, String name, String role, String contact, String salary) {
            this.empId = empId;
            this.name = name;
            this.role = role;
            this.contact = contact;
            this.salary = salary;
        }
        
        public String getEmpId() { return empId; }
        public String getName() { return name; }
        public String getRole() { return role; }
        public String getContact() { return contact; }
        public String getSalary() { return salary; }
        
        public void setName(String name) { this.name = name; }
        public void setRole(String role) { this.role = role; }
        public void setContact(String contact) { this.contact = contact; }
        public void setSalary(String salary) { this.salary = salary; }
    }
}