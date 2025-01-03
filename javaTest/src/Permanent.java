public class Permanent extends Employee {
    private int salary;
    private int bonus;

    public Permanent(String name, int salary, int bonus) {
        super(name);
        this.salary = salary;
        this.bonus = bonus;
    }

    @Override
    public int getPay() {
        return salary+bonus;
    }

    public void printDetails() {
        System.out.println("================================");
        System.out.println("고용형태 : 정규직");
        System.out.println("이      름 : " + name);
        System.out.println("급      여 : " + String.format("%,d원",getPay()));
    }
}

