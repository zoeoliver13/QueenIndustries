import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {

    private int tier;
    private int bonus;
    private ArrayList<Employee> reports;

    public Manager(int salary, int bonus, String name, String department, String title, ArrayList<Employee> reports, int tier)
            throws Exception
    {
        super(salary, name, department, title);
        for(int i = 0; i < reports.size(); i++)
        {
            Employee employee = reports.get(i);
            if(employee.getTier() >= 2) throw new Exception("ERROR: cannot supervise an Employee of an equal or greater tier.");
        }
        this.tier = tier;
        this.bonus = bonus;
        this.reports = reports;
    }

    public int getTier()
    {
        return tier;
    }

    public int getBonus()
    {
        return bonus;
    }

    public void hire(Manager manager) throws Exception
    {
        if(this.getTier() <= manager.getTier())
        {
            throw new Exception("ERROR: cannot hire an Employee of an equal or greater tier.");
        }
        else{
            reports.add(manager);
            System.out.println("LOG: new Employee hired (" + manager.getName() + "," + manager.getDepartment()+ ", " + manager.getTitle() + ")");
        }
    }

    public void hire(Employee employee) throws Exception
    {
        if(this.getTier() <= employee.getTier())
        {
            throw new Exception("ERROR: cannot hire an Employee of an equal or greater tier.");
        }
        else {
            reports.add(employee);
            System.out.println("LOG: new Employee hired (" + employee.getName() + ", " + employee.getDepartment() + ", " + employee.getTitle() + ")");
        }
    }

    public void fire(Manager manager) throws Exception
    {
        List<Employee> x = manager.reports;
        if(this.getDepartment().equals(manager.getDepartment()))
        {
            this.reports.remove(manager);
            System.out.println("LOG: existing Employee fired (" + manager.getName() + ", " + manager.getDepartment() + ", " + manager.getTitle() + ")");

            boolean reAssign = false;

            String print = "Log: reports re-assigned [";
            for (int i = 0; i < this.reports.size(); i++) {
                if (this.reports.get(i).getDepartment().equals(manager.getDepartment())) {
                    for (Employee employee : x) {
                        reAssign = true;
                        this.reports.add(employee);
                        print += employee.getName() + ", " + employee.getDepartment() + ", " + employee.getTitle() + ", ";
                    }
                }
            }
            if (reAssign) System.out.println(print.substring(0, print.length() - 2) + "]");
        }
        else if (this.getTier() <= manager.getTier()){
            throw new Exception("ERROR: cannot fire an Employee of an equal or greater tier.");
        } else {
            throw new Exception("ERROR: cannot fire an Employee who is not a direct or indirect report.");
        }
    }

    public void fire(Employee employee) throws Exception
    {
        if (this.getDepartment().equals(employee.getDepartment()))
        {
            reports.remove(employee);
            System.out.println("LOG: new Employee fired (" + employee.getName() + ", " + employee.getDepartment() + ", " + employee.getTitle() + ")");
        } else if (this.getTier() <= employee.getTier()){
            throw new Exception("ERROR: cannot fire an Employee who is not a direct or indirect report.");
        } else {
            throw new Exception("ERROR: cannot fire an Employee of an equal or greater tier.");
        }
    }

    public void adjustSalary(int newSalary, Employee employee) throws Exception
    {
        boolean under = false;
        for (Employee manager: this.reports) {
            if (manager.getName().equals(employee.getName())) {
                under = true;
                break;
            }

            if (manager.getTier() >= 2) {
                for (Employee employee1 : ((Manager) manager).getEmployees()) {
                    if (employee1.getName().equals(employee.getName())) {
                        under = true;
                        break;
                    }
                }
            }
        }
        if (this.getTier() <= employee.getTier()) {
            throw new Exception("ERROR: cannot alter salary of an Employee of an equal or greater tier.");
        } else if (!under) {
            throw new Exception("ERROR: cannot alter salary of an Employee who is not a report.");
        } else {
            employee.setSalary(employee.getSalary() + newSalary);
        }
    }

    public ArrayList<Employee> getManagers() {
        return reports;
    }

    public ArrayList<Employee> getEmployees() {
        return reports;
    }
}