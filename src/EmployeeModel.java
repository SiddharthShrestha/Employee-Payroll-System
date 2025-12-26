public class EmployeeModel {
    //Global Variables
    private String empId;
    private String name;
    private String role;
    private String contact;
    private int salary;
    
    //Constructor 
    public EmployeeModel(String empId, String name, String role, String contact, int salary){
        this.empId = empId;
        this.name=name;
        this.role=role;
        this.contact=contact;
        this.salary=salary;   
    }
    //Getter and Setter Methods
    public String getempId(){
    return empId;
}
public void empId(String empId){
    this.empId = empId;
}
public String name(){
    return name;
}
public void name(String mame){
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object getname() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
   
    
