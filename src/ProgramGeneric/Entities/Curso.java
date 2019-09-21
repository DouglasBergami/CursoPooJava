package ProgramGeneric.Entities;


public class Curso {
    
    private Integer id;
    private String nome;
    private Integer[] idAluno;
    private Integer idProfessor;
    
    public Curso(){
        
    }
    
    public Curso(Integer id, String nome, Integer[] idAluno, Integer idProfessor){
        this.id = id;
        this.nome = nome;
        this.idAluno = idAluno;
        this.idProfessor = idProfessor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer[] getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer[] idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }
}
