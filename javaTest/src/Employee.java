public abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract int getPay();

    public void printDetails() {
    }
}
