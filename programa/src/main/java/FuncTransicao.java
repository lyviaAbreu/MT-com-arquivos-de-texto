public class FuncTransicao {
    private String atualEstado;
    private String leitura;
    private String proxEstado;
    private String escrita;
    private String direcao;

    //Construtor
    public FuncTransicao(String atualEstado, String leitura, String proxEstado, String escrita, String direcao){
        this.atualEstado=atualEstado;
        this.leitura=leitura;
        this.proxEstado=proxEstado;
        this.escrita=escrita;
        this.direcao=direcao;
    }

    //Retorna os dados
    public String getAtualEstado(){return this.atualEstado;}
    public String getLeitura(){return this.leitura;}
    public String getProxEstado(){return this.proxEstado;}
    public String getEscrita(){return this.escrita;}
    public String getDirecao(){return this.direcao;}
}