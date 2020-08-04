package view;

import controller.Controller;
import model.Customer;
import model.Smartphone;

import java.io.IOException;
import java.util.*;

public class view {

    public static void main(String[] args) throws IOException {
        final String pass = "111111";
        final String delayFile = "Delay.dat";
        final String transactionHistory = "TransactionHistory.dat";
        final String cusFIle = "Customer.dat";
        final String smFile = "SmartPhone.Dat";
        int choice = -1;
        int choiceOfAdmin = -1;
        LinkedList<Smartphone> smartphones;
        LinkedList<Customer> customers;
        Controller controller = Controller.getInstance();

        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("******************Menu******************");
            System.out.println("1. Show smartphone on shop!");
            System.out.println("2. Show headphone on shop!");
            System.out.println("3. Show speaker on shop!");
            System.out.println("4. Admin !");
            System.out.println("0. Exit!");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e) {
                System.out.println("Data invalid");
                sc.nextLine();
            }
            switch (choice) {
                case 1 :
//                    SmartPhone show
                    int choiceSmartPhone = -1;
                    smartphones = controller.readSmartPhoneFromFile(smFile);
                    for (Smartphone sm : smartphones) {
                        sm.showInfo();
                    }
                    do {
                        System.out.println("******************Menu******************");
                        System.out.println("1. Sort smartphone from low to high!");
                        System.out.println("2. Find smartphone follow name!");
                        System.out.println("3. Find smartphone follow price!");
                        System.out.println("4. Buy smartphone by one click !!");
                        System.out.println("0. Back to ground!");
                        try {
                            choiceSmartPhone = sc.nextInt();
                            sc.nextLine();
                        }catch (InputMismatchException e) {
                            System.out.println("Data invalid");
                            sc.nextLine();
                        }
                        switch (choiceSmartPhone) {
                            case 1 :
                                Collections.sort(smartphones);
                                smartphones.forEach(smartphone -> smartphone.showInfo());
                                break;
                            case 2 :
                                System.out.println("Enter smartphone you want find :");
                                String nameFind  = sc.nextLine();
                                LinkedList<Smartphone> smFindByName = controller.findSmartPhoneByName(nameFind,smFile);
                                smFindByName.forEach(smartphone -> smartphone.showInfo());
                                break;
                            case 3 :
                                System.out.println("Enter price : ");
                                int priceFind = Integer.parseInt(sc.nextLine());
                                LinkedList<Smartphone> smFindByPrice = controller.findSmartPhoneByPrice(priceFind,smFile);
                                smFindByPrice.forEach(smartphone -> smartphone.showInfo());
                                break;
                            case 4 :
                                System.out.println("Enter the product code of smartphone : ");
                                String smartphoneCodeBuy = sc.nextLine();
                                controller.buySmartPhone(smartphoneCodeBuy,smFile);
                                break;
                        }
                    }while (choiceSmartPhone != 0);
                    break;
//                    end of SmartPhone show
//                Admin
                case 4 :
                    System.out.println("Enter the password : ");
                    String password = sc.nextLine();
                    if(password.equals(pass)){
                        do {
                            System.out.println("******************Menu******************");
                            System.out.println("1. Add new SmartPhone!");
                            System.out.println("2. Check shop!");
                            System.out.println("3. Check order! ");
                            System.out.println("4. Check history of receipt!");
                            System.out.println("5. Post Goods Receipt!");
                            System.out.println("0. Back to ground!");
                            try {
                                choiceOfAdmin = sc.nextInt();
                                sc.nextLine();
                            }catch (InputMismatchException e) {
                                System.out.println("Data invalid");
                                sc.nextLine();
                            }
                            switch (choiceOfAdmin) {
                                case 1 :
                                    String productCode,name,design,color;
                                    int price,quantity;
                                    try {
                                        System.out.println("Input the product code");
                                        productCode = sc.nextLine();
                                        System.out.println("Input the product name");
                                        name = sc.nextLine();
                                        System.out.println("Input the Design");
                                        design = sc.nextLine();
                                        System.out.println("Input the product price");
                                        price = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("Input the color of product");
                                        color = sc.nextLine();
                                        System.out.println("Input the quantity");
                                        quantity = sc.nextInt();
                                        sc.nextLine();
                                        Smartphone smartphone = new Smartphone(productCode,name,design,price,color,quantity);
                                        controller.writeSmartPhoneToFile(smFile,smartphone);
                                        System.out.println("Add new smartphone done!");
                                    }catch (InputMismatchException e){
                                        System.out.println("Data invalid! Please check again!");
                                        sc.nextLine();
                                    }
                                    break;
                                case 2 :
                                    smartphones = controller.readSmartPhoneFromFile(smFile);
                                    for (Smartphone sm : smartphones) {
                                        sm.showInfoFull();
                                    }
                                    break;
                                case 3 :
                                    customers = controller.readCustomerFromFile(cusFIle);
                                    smartphones = controller.readSmartPhoneFromFile(smFile);
                                    if(customers.size() > 0) {
                                        for (Customer c : customers) {
                                            c.showInfo();
                                        }
                                        System.out.println("Choice Y to confirm or any key to delay order!");
                                        while (!customers.isEmpty()){
                                            Customer customer = customers.pop();
                                            customer.showInfo();
                                            String c = sc.nextLine();
                                            if(c.equalsIgnoreCase("Y")) {
                                                controller.writeCustomerToFile(transactionHistory,customer);
                                                for (Smartphone s:smartphones) {
                                                    if(s.getProductCode().equalsIgnoreCase(customer.getProductCode())){
                                                        s.setQuantity(s.getQuantity() - 1);
                                                        break;
                                                    }
                                                }
                                            } else controller.writeCustomerToFile(delayFile,customer);
                                        }
                                        controller.deleteFile(cusFIle);
                                        controller.deleteFile(smFile);
                                        controller.writeSmartPhoneToFile(smFile,smartphones);
                                    } else System.out.println("You dont have new order!");
                                    break;
                                case 4 :
                                    customers = controller.readCustomerFromFile(transactionHistory);
                                        for (Customer c : customers) {
                                            c.showInfo();
                                        }
                                    break;
                                case 5 :
                                    smartphones = controller.readSmartPhoneFromFile(smFile);
                                    System.out.println("Enter the code product : ");
                                    String codeProduct = sc.nextLine();
                                    for (Smartphone s:smartphones) {
                                        if(s.getProductCode().equalsIgnoreCase(codeProduct)) {
                                            s.showInfoFull();
                                            System.out.println("Enter the number of product post :");
                                            int nub = sc.nextInt();
                                            sc.nextLine();
                                            s.setQuantity(s.getQuantity() + nub);
                                            break;
                                        }
                                    }
                                    controller.deleteFile(smFile);
                                    controller.writeSmartPhoneToFile(smFile,smartphones);
                                    break;
                            }
                        }while (choiceOfAdmin != 0);
                    }
                    break;
            }
        }while (choice!=0);
    }
}
