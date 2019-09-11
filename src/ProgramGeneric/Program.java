package ProgramGeneric;

import java.util.Scanner;
import ProgramGeneric.services.PrintService;

public class Program {

    public static void executeGeneric() {

        Scanner sc = new Scanner(System.in);

        //informar qual o tipo genérico sera adicionado a classe
        PrintService<String> ps = new PrintService();

        System.out.println("How many values? ");
        Integer n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            String value = sc.next();
            ps.addList(value);
        }

        ps.print();

        System.out.println("First: " + ps.first());

    }

}
