import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeController {
    private VIEW view;
    private int size = 10;
    private String[][] queue = new String[size][5];
    private int front = -1;
    private int rear = -1;
    
    public EmployeeController(VIEW view) {
        this.view = view;
        initController();
        loadInitialData();
    }
    
    private void initController() {
        view.getAddButton().addActionListener(e -> handleAddEmployee());
        view.getRemoveButton().addActionListener(e -> handleDequeueEmployee());
    }
    
    // this method adds employee to the back of queue
    public void enqueue(String empId, String name, String role, String contact, String salary) {
        if (rear == size - 1) {
            JOptionPane.showMessageDialog(view, "Queue is full!");
            return;
        }
        if (front == -1)
            front = 0;
        rear++;
        queue[rear][0] = empId;
        queue[rear][1] = name;
        queue[rear][2] = role;
        queue[rear][3] = contact;
        queue[rear][4] = salary;
        LoadEmployeeTable();
    }
    
    // this method removes employee from the front of queue
    public void dequeue() {
        if (front == -1) {
            JOptionPane.showMessageDialog(view, "The Queue is empty!");
            return;
        }
        String empId = queue[front][0];
        String name = queue[front][1];
        String role = queue[front][2];
        String contact = queue[front][3];
        String salary = queue[front][4];
        
        JOptionPane.showMessageDialog(view, "Removed Employee: " + name);
        
        queue[front][0] = queue[front][1] = queue[front][2] = queue[front][3] = queue[front][4] = null;
        front++;
        if (front > rear)
            front = rear = -1;
        
        LoadEmployeeTable();
    }
    
    // this loads all employee data into the table
    public void LoadEmployeeTable() {
        DefaultTableModel model = (DefaultTableModel) view.getEmployeeTable().getModel();
        model.setRowCount(0);
        
        if (front == -1 || front > rear) return;
        
        for (int i = front; i <= rear; i++) {
            if (queue[i][0] != null) {
                model.addRow(new Object[]{queue[i][0], queue[i][1], queue[i][2], queue[i][3], queue[i][4]});
            }
        }
    }
    
    // puts some starting employee data when program starts
    public void loadInitialData(){
        enqueue("EMP001", "John Smith", "Manager", "9841234567", "75000");
        enqueue("EMP002", "Sarah Johnson", "Developer", "9851234567", "60000");
        enqueue("EMP003", "Michael Brown", "Designer", "9861234567", "55000");
        enqueue("EMP004", "Emily Davis", "HR Specialist", "9871234567", "50000");
        enqueue("EMP005", "David Wilson", "Accountant", "9881234567", "52000");
    }
    
    // this runs when add button is clicked
    public void handleAddEmployee() {
        try {
            String empId = view.getEmpIdField().getText();
            String name = view.getNameField().getText();
            String role = view.getRoleField().getText();
            String contact = view.getContactField().getText();
            String salary = view.getSalaryField().getText();
            
            if (empId.isEmpty() || name.isEmpty() || role.isEmpty() || contact.isEmpty() || salary.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Integer.parseInt(salary);
            
            enqueue(empId, name, role, contact, salary);
            view.getEmpIdField().setText("");
            view.getNameField().setText("");
            view.getRoleField().setText("");
            view.getContactField().setText("");
            view.getSalaryField().setText("");
            
            JOptionPane.showMessageDialog(view, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Salary must be a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // this runs when remove button is clicked
    public void handleDequeueEmployee() {
        try {
            dequeue();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error removing employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}