package ProgramGeneric.Entities;

import ProgramGeneric.Enums.WorkerLevel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Worker {

    private String name;
    private WorkerLevel workerLvel;
    private Double baseSalary;
    
    private Departament departament;
    private List<HourContract> hourContract = new ArrayList<>();

    public Worker() {

    }

    public Worker(String name, WorkerLevel workerLvel, Double baseSalary, Departament departament) {
        this.name = name;
        this.workerLvel = workerLvel;
        this.baseSalary = baseSalary;
        this.departament = departament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkerLevel getWorkerLvel() {
        return workerLvel;
    }

    public void setWorkerLvel(WorkerLevel workerLvel) {
        this.workerLvel = workerLvel;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public List<HourContract> getHourContract() {
        return hourContract;
    }

    public void addContract(HourContract hourContract){
        this.hourContract.add(hourContract);
    }
    
    public void removeContract(HourContract hourContract){
        this.hourContract.remove(hourContract);
    }
    
    public Double income(Integer month, Integer year){
        
        Double valueTotal = 0.0;
        
        for (HourContract obj : this.hourContract){
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(obj.getDate());
            int yearObj = cal.get(Calendar.YEAR);
            int monthObj = cal.get(Calendar.MONTH)+1;
            
            if (yearObj == year && monthObj == month){
                valueTotal += obj.totalValue();
            }
            
        }
        
        return valueTotal + this.baseSalary;
    }
    
    @Override
    public String toString() {
        return "Name: " + name
                +"\nDepartment: " + departament.getName();
    }
}
