package ProgramGeneric;

import ProgramGeneric.Entities.Circle;
import ProgramGeneric.Entities.Curso;
import ProgramGeneric.Entities.Funcionarios;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static void exerciseSet() {

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

    public static void exerciseMap() {

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
    
    public static void exerciseSet2(){
        
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
    
    public static void executeStream(){
        
        //Demonstração de uso com stream
        
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Stream<Integer> st1 = list.stream();
        System.out.println(Arrays.toString(st1.toArray()));
        
        Stream<String> st2 = Stream.of("Maria", "Alex", "Bob");
        System.out.println(Arrays.toString(st2.toArray()));
        
        Stream<Integer> st3 = Stream.iterate(0, x -> x +2);
        System.out.println(Arrays.toString(st3.limit(10).toArray()));
        
        Stream<Long> st4 = Stream.iterate(new Long[] {0L, 1L}, p -> new Long[] {p[1], p[0]+p[1]}).map(p -> p[0]);
        System.out.println(Arrays.toString(st4.limit(20).toArray()));
        
    }
    
    public static void Executepipeline(){
        
         List<Integer> list = Arrays.asList(3,4,5,10,7);
         
         Stream<Integer> st1 = list.stream().map(x -> x * 10);
         System.out.println(Arrays.toString(st1.toArray()));
         
         int soma = list.stream().reduce(0, (x ,y) -> x + y);
         System.out.println("soma: " + soma);
         
         List<Integer> newList = list.stream()
                 .filter(x -> x % 2 ==0)
                 .map(x -> x * 10)
                 .collect(Collectors.toList());
         System.out.println(Arrays.toString(newList.toArray()));
    }
    
    public static void exerciseStream(){
        
        String path = "src\\arquivos\\in_2.txt";
        
        try(BufferedReader bf = new BufferedReader(new FileReader(path))){
            
        List<Product> list = new ArrayList<>();
        
        String line = bf.readLine();
        
        while(line != null){
            String[] fields = line.split(",");
            list.add(new Product(fields[0], Double.parseDouble(fields[1])));
            line = bf.readLine();
        }
        
        double avg = list.stream()
                .map(p -> p.getPrice())
                .reduce(0.0, (x,y) -> x + y) / list.size();
        
            System.out.println("Avarege price: " + String.format("%.2f", avg));
            
            Comparator<String> comp = (s1,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
            
            List<String> names = list.stream()
                    .filter(p -> p.getPrice() < avg)
                    .map(p -> p.getName())
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());
            
            names.forEach(System.out::println);
            
        }catch(IOException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    public static void exerciseStream2() {
        String path = "src\\arquivos\\in_1_1_1.txt";
        
        System.out.println("Digite o valor do salario!");
        Scanner sc = new Scanner(System.in);
        
        double valor = sc.nextDouble();

        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {

            List<Funcionarios> list = new ArrayList<>();

            String line = bf.readLine();

            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Funcionarios(fields[0], fields[1], Double.parseDouble(fields[2])));
                line = bf.readLine();
            }

            List<String> names = list.stream()
                    .filter(p -> p.getSalario() > valor)
                    .map(p -> p.getEmail())
                    .sorted((s1,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase()))
                    .collect(Collectors.toList());

            names.forEach(System.out::println);
            
            double sum = list.stream()
                    .filter(p -> p.getNome().toUpperCase().charAt(0) == 'M')
                    .map(p -> p.getSalario())
                    .reduce(0.0, (x,y) -> x + y);
            
            System.out.println("\nTotal dos salarios dos funcionarios com a letra M: " + sum);
            
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    public static void executeList(){
        
         List<String> listaTeste = new ArrayList<>();
        
        listaTeste.add("douglas");
        listaTeste.add("drogaria");
        listaTeste.add("maria");
        listaTeste.add("fernando");
        listaTeste.add("jose");
        
        
        /*Utilizando predicate booleano para remover os nomes com a o caracter m*/
        listaTeste.removeIf(x -> x.charAt(0) == 'm');
        
        for(String obj : listaTeste){
            System.out.println(obj);
        }
        
        System.out.println("----------------------------------------");
        
        List<String> resultado = listaTeste;
        
        for(String obj : resultado){
            System.out.println(obj);
        }
        
        System.out.println("----------------------------------------");
        
        /*Utilizando predicate de filtro para localizar todos os nomes com 
        o caracter d*/
        List<String> resultado2 = resultado.stream().filter(x -> x.charAt(0) == 'd').collect(Collectors.toList());
        
        for(String obj : resultado2){
            System.out.println(obj);
        }
        
        System.out.println("----------------------------------------");
        
        
          /*Utilizando predicate de filtro para localizar o primeiro nome com 
        o caracter d*/
        String primeiro = resultado2.stream().filter(x -> x.charAt(0) == 'd').findFirst().orElse(null);
       
        System.out.println(primeiro);
        
    }
    
    public static void executeExerciseMatriz1(){
        /**
        Fazer um programa para ler dois números inteiros M e N, e depois ler
        uma matriz de M linhas por N colunas contendo números inteiros,
        podendo haver repetições. Em seguida, ler um número inteiro X que
        pertence à matriz. Para cada ocorrência de X, mostrar os valores à
        esquerda, acima, à direita e abaixo de X, quando houver, conforme
        exemplo.
        * 
        * 3 4
        10 8 15 12
        21 11 23 8
        14 5 13 19
        8
        Position 0,1:
        Left: 10
        Right: 15
        Down: 11
        Position 1,3:
        Left: 23
        Up: 12
        Down: 19
         */
        int[][] matriz;
        List<Integer> negativeNumbers = new ArrayList<>();
        List<Integer> mainDiagonal = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        int qtdLinhas = scanner.nextInt();
        int qtdColunas = scanner.nextInt();

        matriz = new int[qtdLinhas][qtdColunas];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                int valorDigitado = scanner.nextInt();
                matriz[i][j] = valorDigitado;
            }
        }

        int valorDigitado = scanner.nextInt();

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {

                if (matriz[i][j] == valorDigitado) {

                    System.out.println("Positin: " + i + " , " + j);

                    if (j > 0) {
                        System.out.println("Left: " + matriz[i][j - 1]);
                    }

                    if (j < matriz[i].length - 1) {
                        System.out.println("Right: " + matriz[i][j + 1]);
                    }

                    if (i > 0) {
                        System.out.println("Up: " + matriz[i - 1][j]);
                    }

                    if (i < matriz.length - 1) {
                        System.out.println("down: " + matriz[i + 1][j]);
                    }

                }

            }
        }
    }
    
    public static void executeExerciseMatriz2(){
        /**
        * Fazer um programa para ler um número inteiro N e uma matriz de
        ordem N contendo números inteiros. Em seguida, mostrar a diagonal
        principal e a quantidade de valores negativos da matriz.
        * 
        * input:
        * 3
        5 -3 10
        15 8 2
        7 9 -4
        * output:
        * Main diagonal:
        5 8 -4
        Negative numbers = 2
         */
        int[][] matriz;
        List<Integer> negativeNumbers = new ArrayList<>();
        List<Integer> mainDiagonal = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a quantidade da matriz");

        int qtdMatriz = scanner.nextInt();

        matriz = new int[qtdMatriz][qtdMatriz];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                int valorDigitado = scanner.nextInt();
                if (valorDigitado < 0) {
                    negativeNumbers.add(valorDigitado);
                }
                if (i == j) {
                    mainDiagonal.add(valorDigitado);
                }
                matriz[i][j] = valorDigitado;
            }
        }

        System.out.println("Main Diagonal: " + mainDiagonal.toString());
        System.out.println("negative numbers: " + negativeNumbers.size());
    }

}
