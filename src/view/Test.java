package view;

import controller.Controller;
import model.Specifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
//        Specifications specifications = new Specifications("Iphone X 64g","Super Retina 5.8in"
//                ,"3G","A11","IOS 12");
//        controller.writeSpecificationsToFile("Specifications.dat",specifications);
//        ArrayList<Specifications> spec = controller.readSpecificationsFromFile("Specifications.dat");
//        System.out.println(spec.size());
//        for (Specifications s:spec
//             ) {
//            s.showInfo();
//        }
        Date date = java.util.Calendar.getInstance().getTime();
        String s = date.toString();
        System.out.println(s);
    }
}
