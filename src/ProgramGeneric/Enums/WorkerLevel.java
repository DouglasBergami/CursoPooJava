package ProgramGeneric.Enums;

public enum WorkerLevel {
    
    JUNIOR(1, "Junior"),
    MID_LEVEL(2, "Mid_level"),
    SENIOR(3, "Senior");
    
    private final int cod;
    private final String descricao;
    
    private WorkerLevel(int cod, String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }
    
}
