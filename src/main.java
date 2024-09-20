import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String carBrand;
    private String carModel;
    private double carBasePricePerDay;
    private boolean isAvailabble;

    public Car(String carId,String carBrand, String carModel, double carBasePricePerDay){
        this.carId=carId;
        this.carBrand=carBrand;
        this.carModel=carModel;
        this.carBasePricePerDay=carBasePricePerDay;
        this.isAvailabble=true;
    }

    public String getCarId(){
        return carId;
    }
    public String getCarBrand(){
        return carBrand;
    }
    public String getCarModel(){
        return carModel;
    }
    public Boolean isAvailable(){
        return isAvailabble;
    }
    public void rent(){
        isAvailabble=false;
    }
    public void returnCar(){
        isAvailabble=true;
    }

    public double calculatePrice(int rentalDays){
        return carBasePricePerDay * rentalDays;
    }
}

class Customer{
    private String customerId;
    private String customerName;

    public Customer(String customerId, String customerName){
        this.customerId=customerId;
        this.customerName=customerName;
    }

    public String getCustomerId(){
        return customerId;
    }
    public  String getCustomerName() {
        return customerName;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car,Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car,Customer customer,int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }else {
            System.out.println("Car is not available for rent !! ");
        }
    }

    public void returnCar(Car car){
        Rental rentalToRemove=null;
        for (Rental rental:rentals){
            if (rental.getCar()==car){
                rentalToRemove=rental;
                break;
            }
        }
        if (rentalToRemove!=null){
            rentals.remove(rentalToRemove);
            System.out.println("Car is returned Successfully");
        }else {
            System.out.println("This Car was not rented");
        }
    }

    public void menu(){
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("===== Welcome To Car Rental System =====");
            System.out.println("1. Rent a Car ");
            System.out.println("2. Return a Car ");
            System.out.println("3. Exit ");
            System.out.println(" Enter Your Choice: ");

            int choice= sc.nextInt();
            sc.nextLine();

            if(choice == 1){
                System.out.println("== Rent a Car ==");
                System.out.println("Enter your name: ");
                String customerName= sc.nextLine();

                System.out.println("Available Cars: ");
                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId()+" "+car.getCarBrand()+" "+car.getCarModel());
                    }
                }
                System.out.println("Enter Car Id you want to get: ");
                String carId=sc.nextLine();

                System.out.println("Enter the number of days reantal: ");
                int rentalDays=sc.nextInt();
                sc.nextLine();

                Customer newCustomer= new Customer("CUS"+(customers.size()+1),customerName);
                addCustomer(newCustomer);

                Car selectedCar=null;
                for (Car car:cars){
                    if (car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }
                if (selectedCar!=null){
                    double totalPrice=selectedCar.calculatePrice(rentalDays);
                    System.out.println("===== Rental Iformation =====");
                    System.out.println("Customer Id: "+newCustomer.getCustomerId());
                    System.out.println("Customer Name: "+newCustomer.getCustomerName());
                    System.out.println("Car: "+selectedCar.getCarBrand()+" "+selectedCar.getCarModel());
                    System.out.println("Rental Days: "+rentalDays);
                    System.out.println("Total Price: "+totalPrice);

                    System.out.println("Confirm Rental(Y/N): ");
                    String cofirm=sc.nextLine();

                    if(cofirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("Car Rented Successfully ");
                    }
                    else {
                        System.out.println("Rental Canceled. ");
                    }
                }else {
                    System.out.println("Invalid Car Selection or Car not available for rent ");
                }
            }else if (choice==2){
                System.out.println("===== Return a Car =====");
                System.out.println("Enter Car Id you want to return: ");
                String carId=sc.nextLine();

                Car carToReturn=null;
                for (Car car:cars){
                    if (car.getCarId().equals(carId) && !car.isAvailable()){
                        carToReturn=car;
                        break;
                    }
                }
                if (carToReturn!=null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }


                    if (customer !=null){
                        returnCar(carToReturn);
                        System.out.println("Car Returned Successfully by "+customer.getCustomerName());
                    }else {
                        System.out.println("Car was not rented or rental information is missing ");
                    }
                }else {
                    System.out.println("Invalid Car Id or Car was not rented ");
                }
            } else if (choice==3) {
                break;
            }else {
                System.out.println("Invalid Choice. Please Enter Valid Options. ");
            }
        }
        System.out.println("Thank You for using Car Rental System ");
    }

}

public class main {
    public static void main(String[] args) {
        CarRentalSystem carRentalSystem= new CarRentalSystem();

        Car car1 =new Car("C001","Toyota","Camry",60);
        Car car2 =new Car("C002","Honda","Accord",70);
        Car car3 =new Car("C003","Mahindra","XUV700",150);
        Car car4 =new Car("C004","Mahindra","Thar",100);

        carRentalSystem.addCar(car1);
        carRentalSystem.addCar(car2);
        carRentalSystem.addCar(car3);
        carRentalSystem.addCar(car4);

        carRentalSystem.menu();
    }
}

