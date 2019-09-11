package ProgramGeneric.services;

import java.util.ArrayList;
import java.util.List;

//Precisar ser declaro o nome do generic da classe
public class PrintService<T> {
    
    //atualizar o valor da lista com o mesmo generic informado
    List<T> list = new ArrayList<>();
    
    public void addList (T value){
        this.list.add(value);
    }
    
    public T first(){
        if (list.isEmpty()){
            throw new IllegalStateException("List is empty");
        }
        return list.get(0);
    }
    
    public void print(){
        System.out.println(list.toString());
    }
    
}
