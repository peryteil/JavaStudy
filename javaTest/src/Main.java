import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("고용형태 - 정규직<P>, 비정규직<T>를 입력하세요.");
        String employmentType = scanner.nextLine();

        if (employmentType.equalsIgnoreCase("P")) {
            System.out.println("이름을 입력하세요.");
            String name = scanner.nextLine();

            System.out.println("기본급을 입력하세요.");
            double basePay = scanner.nextDouble();

            System.out.println("보너스를 입력하세요.");
            double bonus = scanner.nextDouble();


            Employee employee = new Permanent(name, (int) basePay, (int) bonus);
            employee.printDetails();
        } else if (employmentType.equalsIgnoreCase("T")) {
            System.out.println("이름을 입력하세요.");
            String name = scanner.nextLine();

            System.out.println("작업시간을 입력하세요.");
            int hoursWorked = scanner.nextInt();

            System.out.println("시간당급여를 입력하세요.");
            double hourlyRate = scanner.nextDouble();

            int pay = (int) (hoursWorked * hourlyRate);
            Employee employee = new Temporary(name, hoursWorked, pay);
            employee.printDetails();
        } else {
            System.out.println("잘못된 입력입니다.");
        }

        scanner.close();
    }
}

