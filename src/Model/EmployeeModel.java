package Model;
import java.util.Stack;

public class EmployeeModel {
    private String empId;
    private String name;
    private String role;
    private String contact;
    private int salary;
    
    // Stack for Task History - LIFO structure
    private static Stack<Task> taskHistory = new Stack<>();
    
    public EmployeeModel(String empId, String name, String role, String contact, int salary){
        this.empId = empId;
        this.name = name;
        this.role = role;
        this.contact = contact;
        this.salary = salary;
    }
    
    public String getempId(){
        return empId;
    }
    
    public void empId(String empId){
        this.empId = empId;
    }
    
    public String name(){
        return name;
    }
    
    public void name(String name){
        this.name = name;
    }
    
    public String role(){
        return role;
    }
    
    public void role(String role){
        this.role = role;
    }
    
    public String getcontact(){
        return contact;
    }
    
    public void setcontact(String contact){
        this.contact = contact;
    }
    
    public int getsalary(){
        return salary;
    }
    
    public void setsalary(int salary){
        this.salary = salary;
    }
    
    boolean getSalary() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    Object getname() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    // Returns the Stack containing task history
    public static Stack<Task> getTaskHistory() {
        return taskHistory;
    }
    
    // Task class for managing work orders
    public static class Task {
        private String taskId;
        private String taskName;
        private String priority;
        private String status;
        
        public Task(String taskId, String taskName, String priority, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.priority = priority;
            this.status = status;
        }
        
        public String getTaskId() { return taskId; }
        public String getTaskName() { return taskName; }
        public String getPriority() { return priority; }
        public String getStatus() { return status; }
    }
}