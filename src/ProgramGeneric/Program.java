package ProgramGeneric;

import ProgramGeneric.Entities.Circle;
import ProgramGeneric.Entities.Curso;
import ProgramGeneric.Entities.LogEntry;
import ProgramGeneric.Entities.Product;
import ProgramGeneric.Entities.Rectangle;
import ProgramGeneric.Entities.Shape;
import ProgramGeneric.services.CalculationService;
import java.util.Scanner;
import ProgramGeneric.services.PrintService;
import ProgramGeneric.services.ProductService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static void executeDelimitedGenerics() {

        //caso desejar que apareca com ponto ao invés de virgula.
        Locale.setDefault(Locale.US);

        List<Product> list = new ArrayList<>();

        String path = "src\\arquivos\\in.txt";

        //realiza leitura do arquivo e armazena no buffer
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            //realiza leitura por linha
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            Product x = CalculationService.max(list);
            System.out.println("Most Expensive: ");
            System.out.println(x);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    public static void executeWildcardTypes() {

        //Com tipos coringo pode-se fazer metodos que recebem generico de qualquer tipo
        List<Integer> myInts = Arrays.asList(5, 2, 10);

        List<String> myStrs = Arrays.asList("Douglas", "joao", "Maria");

        printList(myStrs);
    }

    private static void printList(List<?> myInts) {

        /*Porem nao pode-se adicionar dados a uma colecao do tipo corunga, pois o compilador
        nao sabe qual é o tipo da lista, só pode consultar
        myInts.add("Roberto");*/
        myInts.forEach((obj) -> {
            System.out.println(obj);
        });

    }

    public static void executeDelimitedWildcard() {

        List<Shape> myShapes = new ArrayList<>();
        myShapes.add(new Circle(2.0));
        myShapes.add(new Rectangle(3.0, 2.0));

        List<Circle> myCircle = new ArrayList<>();
        myCircle.add(new Circle(3.0));
        myCircle.add(new Circle(6.0));

        /*caso for alterado para a lista de MyCircle o compilador apresentará erro
        pois a classe Shape não é o super tipo de Circle, nesse caso precisa utilizar o coringa
        System.out.println("Total area: " + totalArea(myShapes));*/
 /*Como objeto a mesma situação funciona, onde shape recebe circle devido a implementação
       mas para list não funciona mesmo circle sendo um sub tipo.
       Circle circle = new Circle(5.0);
       Shape shape = circle;*/
        System.out.println("Total area: " + totalArea(myShapes));

    }

    private static double totalArea(List<? extends Shape> myShapes) {
        /*Para funcionar como coringa precisa adicionar ponto de interrogacao e extender
    pois pode ser uma lista de shape ou de qualquer sub tipo de shape*/

        double sum = 0;

        for (Shape s : myShapes) {
            sum += s.area();
        }

        return sum;
    }

    public static void executeDelimitedWildcardGetPut() {

        /* Metodo que copia os elementos de uma lista para outra lista que pode ser mais generica
        que a primeira*/
        List<Integer> myInts = Arrays.asList(1, 2, 3, 4);
        List<Double> myDoubles = Arrays.asList(14.5, 18.0);
        List<Object> myObj = new ArrayList<>();

        copy(myInts, myObj);
        printList(myObj);
        copy(myDoubles, myObj);
        printList(myObj);
    }

    private static void copy(List<? extends Number> source, List<? super Number> destinty) {
        /* Lista generica que aceita tipo Number e os sub tipos*/
 /* Lista generica aceitando até o super tipo de Number, no caso object*/

        source.forEach((number) -> {
            destinty.add(number);
        });

    }

    public static void exerciceSet() {

        String path = "src\\arquivos\\in_1.txt";

        Set<LogEntry> log = new HashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            String line = bufferedReader.readLine();

            while (line != null) {

                String fields[] = line.split(",");
                log.add(new LogEntry(fields[0], Date.from(Instant.parse(fields[1]))));
                line = bufferedReader.readLine();

            }

            System.out.println("Total users: " + log.size());

            log.forEach((item) -> {
                System.out.println("User: " + item.getName() + "Date: " + item.getDateTime());
            });

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void exerciceMap() {

        String path = "src\\arquivos\\in_1_1.txt";

        Map<String, Integer> votacao = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            String line = bufferedReader.readLine();

            while (line != null) {

                String fields[] = line.split(",");

                if (votacao.containsKey(fields[0])) {
                    int votos = votacao.get(fields[0]) + Integer.parseInt(fields[1]);
                    votacao.put(fields[0], votos);
                } else {
                    votacao.put(fields[0], Integer.parseInt(fields[1]));

                }
                line = bufferedReader.readLine();
            }

            votacao.keySet().forEach((key) -> {
                System.out.println("Candidados: " + key + " Votos: " + votacao.get(key));
            });

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

    public static void ExecuteComparator() {

        List<Product> list = new ArrayList<>();

        list.add(new Product("Mesa", 900.00));
        list.add(new Product("Cama", 100.00));
        list.add(new Product("Banho", 400.00));

        //para se utilizar um Collection.sort a lista precisa estar implementando a classe Comparable e sobrescrevendo o metodo compareTo
        //Collections.sort(list);
        
        
        /*pode se utilizar a classe Comparador junto á uma função anonima
        para realizar a comparaçaõ entre os atributos, ao invés de implementar no VO 
        a classe Comparable e sobrescrever o compareTo.
        Comparator<Product> comp = new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getName().toUpperCase().compareTo(p2.getName().toUpperCase());
            }
        };
        
        list.sort(comp);*/
        
        //Comprator definido com a sintaxe de expressão lambida
        list.sort((p1, p2) -> p1.getName().toUpperCase().compareTo(p2.getName()));
        
        
        for(Product product : list){
            System.out.println(product);
        }

    }
    
    public static void exerciceSet2(){
        
       Set<Integer> totalAlunos = new HashSet<>();
       
       Integer[] listaAlunoCurso1 = {1,2,3,4,5,6};
       Integer[] listaAlunoCurso2 = {1,2,3};
       Integer[] listaAlunoCurso3 = {4,5,6};
       
       Curso c1 = new Curso(1, "Meio ambiente", listaAlunoCurso1, 1);
       Curso c2 = new Curso(1, "Informática", listaAlunoCurso2, 1);
       Curso c3 = new Curso(1, "Informática", listaAlunoCurso3, 1);
       
       List<Curso> listaCursos = Arrays.asList(c1,c2,c3);
       
       for (Curso percorreCurso : listaCursos){
          
            System.out.println("\nCurso: " + percorreCurso.getNome());
            System.out.println("Total de alunos: " + percorreCurso.getIdAluno().length);
            
           for (Integer percorreAluno : percorreCurso.getIdAluno()){
               
               totalAlunos.add(percorreAluno);
           }
       }
        
        System.out.println("\nTotal de alunos do professor Douglas: " + totalAlunos.size());
    }
    
    public static void executePredicate(){
        
        List<Product> listProduct = new ArrayList<>();
        
        listProduct.add(new Product("TV", 900.00));
        listProduct.add(new Product("Balanca", 50.00));
        listProduct.add(new Product("Geladeira", 350.50));
        listProduct.add(new Product("Fogao", 80.90));
        
        
        /*Há 5 formas para se utilizar o predicate :
        1 - implementação da interface
        2 - reference method com metodo estático
        3 - reference method com metodo nao estático
        4 - expressão lambda decladara
        5 - expressão lambda inline ( mais simples e utiliza no exemplo abaixo 
        ara remover valores maiores que 100)
        */
        
        //utiliza expressao lambda para o tipo predicate
        listProduct.removeIf(p -> p.getPrice() >= 100);
        
        //utiliza expressao lambda para o tipo consumer
        listProduct.forEach(p -> {p.setPrice(p.getPrice()*1.1);});
        
        //utiliza expressao lambda para o tipo Function
        List<String> listString = listProduct.stream().map(p -> p.getName().toUpperCase()).collect(Collectors.toList());
        
        /*Conversoes
        * List para stream: .stream()
        * Stream para list: collect(Collectors.toList()
        */
        System.out.println(listProduct);
        listString.forEach(System.out::println);
    }
    
    public static void executeFunction(){
        
        List<Product> listProduct = new ArrayList<>();
        
        listProduct.add(new Product("TV", 900.00));
        listProduct.add(new Product("Balanca", 50.00));
        listProduct.add(new Product("Geladeira", 350.50));
        listProduct.add(new Product("Fogao", 80.90));
        
        ProductService ps = new ProductService();
        
        //Passando apenas a function direto com expressao lambda do tipo inline
        double sum = ps.filteredSum(listProduct, p -> p.getName().charAt(0) == 'T');
        
        System.out.println(sum);
        
    }
}
