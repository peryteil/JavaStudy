public class Temporary extends Employee{
    private int time;
    private int pay;

    public Temporary(String name, int time, int pay) {
        super(name);
        this.time = time;
        this.pay = pay;
    }

    @Override
    int getPay() {
        return time+pay;
    }

    @Override
    public void printDetails() {
        System.out.println("================================");
        System.out.println("고용형태 : 비정규직");
        System.out.println("이      름 : " + name);
        System.out.println("급      여 : " + String.format("%,d원", getPay()));
    }
}
