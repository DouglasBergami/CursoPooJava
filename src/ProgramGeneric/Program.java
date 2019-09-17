package ProgramGeneric;

import ProgramGeneric.Entities.Product;
import ProgramGeneric.services.CalculationService;
import java.util.Scanner;
import ProgramGeneric.services.PrintService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    
    public static void executeDelimitedGenerics(){
        
        //caso desejar que apareca com ponto ao invés de virgula.
        Locale.setDefault(Locale.US);
        
        List<Product> list = new ArrayList<>();
        
        String path = "src\\arquivos\\in.txt";
        
        //realiza leitura do arquivo e armazena no buffer
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            
            //realiza leitura por linha
            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }
            
            Product x = CalculationService.max(list);
            System.out.println("Most Expensive: ");
            System.out.println(x);
        }catch(IOException e){
            System.out.println("Erro: "+e.getMessage());
            
        }
    }
    
    public static void executeWildcardTypes(){
        
        //Com tipos coringo pode-se fazer metodos que recebem generico de qualquer tipo
        
        List<Integer> myInts = Arrays.asList(5, 2, 10);
        
        List<String> myStrs = Arrays.asList("Douglas", "joao", "Maria");
        
        printList(myStrs);
    }

    private static void printList(List<?> myInts) {
        
        //Porem nao pode-se adicionar dados a uma colecao do tipo corunga, pois o compilador
        //nao sabe qual é o tipo da lista, só pode consultar
        //myInts.add("Roberto");
        
        myInts.forEach((obj) -> {
            System.out.println(obj);
        });
        
    }
}
