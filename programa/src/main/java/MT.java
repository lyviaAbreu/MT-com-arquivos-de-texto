//Manipulacao de arquivo
import java.io.*;
//Arraylist
import java.util.ArrayList;

public class MT {
    private String[] estados;
    private String[] alfabeto;
    private String[] alfFita;
    private String estadoInicial;
    private char[] palavra;
    private ArrayList<FuncTransicao> transicoes;
    private String nomeArqSaida;

    // Construtor
    public MT(String[] estados, String[] alfabeto, String[] alfFita, String estadoInicial, char[] palavra, ArrayList<FuncTransicao> transicoes, String nomeArqSaida){
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.alfFita = alfFita;
        this.estadoInicial = estadoInicial;
        this.palavra = palavra;
        this.transicoes = transicoes;
        this.nomeArqSaida=nomeArqSaida;
    }

    //Metodo principal que comandará toda execucao
    public void Executa()throws Exception{
        //Controlador de posicoes no vetor da palavra

        int posAtual = 0;
        String estadoAtual = estadoInicial;

        String direcao = "";
        String novoDigito = "";
        String digAtual = Character.toString(palavra[posAtual]);
        String proxEstado = "";

        //Coloca a extenção do arquivo de saida
        String nomeArqSai=nomeArqSaida+".txt";

        //Le o arquivo
        try(FileWriter arq = new FileWriter(nomeArqSai)) {

            while(TenhoMovimento(estadoAtual, digAtual).equals("Ok")){// Enquanto eu posso transicionar
                Imprimir(estadoAtual, posAtual,arq);
                //Salvo o digito que estou lendo
                digAtual =  Character.toString(palavra[posAtual]);
                //Salvo o digiyo que irei ecrever
                novoDigito = OQEscrever(estadoAtual,digAtual);
                //Salvo a direcao que irei mover
                direcao = ProxDirecao(estadoAtual, digAtual);
                //Salvo o proximo estado
                proxEstado = ProxEstado(estadoAtual,digAtual);
                //Atualizo o estado atual
                estadoAtual = proxEstado;
                //Insiro o novo digito na fita
                palavra[posAtual] = novoDigito.charAt(0);

                //Faco a movimentacao da cabeca de leitura
                if(direcao.equals("L") ){
                    posAtual= posAtual - 1;
                }
                else if(direcao.equals("R")){
                    posAtual++;
                }
                //Se atingi o limite a direita, aumento o vetor adicionando um espaco em branco
                if(posAtual >= palavra.length) {
                    AumentaPalavra();
                }
                //Atualizo o diito atual
                digAtual =  Character.toString(palavra[posAtual]);
            }
            // Se estou na primeira posicao e nao tenho mais movimentos, ou eu terminei ou comecei e nao tenho movimentos
            // portanto dou uma ultima impressao na fita
            if(posAtual == 0 && !TenhoMovimento(estadoAtual, digAtual).equals("Ok")){
                Imprimir(estadoAtual, posAtual,arq);
                System.out.println("Maquina computada com sucesso!!");
            }
            //Se nao terminei entao deu erro, emito a msg de erro
            else{
                System.out.println("Ooops, parece que a palavra que desejava trabalhar nao pôde ser executada por essa MT pois " + TenhoMovimento(estadoAtual, digAtual));
            }
            //Caso não consiga criar o arquivo de saida, imprime a mensagem de erro
        }catch (IOException e){
            System.out.println("Falha ao salvar do arquivo "+nomeArqSaida);
        }
    }

    //Verifico se tenho movimentos
    private String TenhoMovimento(String estadoAtual, String infoLida){
        boolean digitoLido = false;// se o digito lido faz parte do alfabeto
        boolean transicaoExiste = false;// se existe transicao com o estado atual e o digito lido
        boolean estadoDestino = false;// se o estado destino existe
        boolean digitoInserido = false;// se o digito a ser inserido faz parte do alfabeto
        String proxEstado = "";//salvo o proximo estado
        String proxDigito = "";//salvo o proximo digito a ser inserido na fita

        //Conferindo se o digito lido existe
        for(String s: alfabeto){
            if(s.equals(infoLida) || infoLida.equals("B")){
                digitoLido = true;
            }
        }

        if(!digitoLido){
            return "o digito lido nao existe no alfabeto da maquina.";
        }

        for(FuncTransicao e: transicoes){
            if(e.getAtualEstado().equals(estadoAtual)){
                if(e.getLeitura().equals(infoLida)){
                    proxDigito = e.getEscrita();
                    proxEstado = e.getProxEstado();
                    transicaoExiste = true;
                }
            }
        }

        if(!transicaoExiste){
            return "nao existe transicao com o estado em que se encontra e a informacao lida";
        }

        for(String s: estados){
            if(s.equals(proxEstado)){
                estadoDestino = true;
            }
        }
        if(!estadoDestino){
            return "o proximo estado nao existe";
        }

        for(String s: alfabeto){
            if(s.equals(proxDigito) || proxDigito.equals("B")){
                digitoInserido = true;
            }
        }

        if(!digitoInserido){
            return "o digito a ser inserido nao existe no alfabeto";
        }

        return "Ok";
    }

    //Retorno o que devo escrever
    public String OQEscrever(String estadoAtual, String infoLida){//Passo o estado atual e o que li e me retorna o que tenho que escrever
        for(FuncTransicao e: transicoes){
            if(e.getAtualEstado().equals(estadoAtual)){
                if(e.getLeitura().equals(infoLida)){
                    return e.getEscrita();
                }
            }
        }
        //Retorno nunca executado
        return "0";
    }

    //Retorno direcao que devo mover a cabeca de leitura
    public String ProxDirecao(String estadoAtual, String infoLida){//Passo o estado atual e o que li e me retorna em qual direcao me movo
        for(FuncTransicao e: transicoes){
            if(e.getAtualEstado().equals(estadoAtual)){
                if(e.getLeitura().equals(infoLida)){
                    return e.getDirecao();
                }
            }
        }
        //Retorno nunca executado
        return "0";
    }

    //Retorno o proximo estado
    public  String ProxEstado(String estadoAtual, String infoLida){//Passo o estado atual e o que li e me retorna em qual o proximo estado
        for(FuncTransicao e: transicoes){
            if(e.getAtualEstado().equals(estadoAtual)){
                if(e.getLeitura().equals(infoLida)){
                    return e.getProxEstado();
                }
            }
        }
        //Retorno nunca executado
        return "0";
    }

    //Funcao de impressao
    //Salva o resultado no arquivo texto
    public void Imprimir(String estadoAtual, int posAtual, FileWriter arq) throws Exception{

        for(int i= 0; i < palavra.length; i++){
            if(i == posAtual){
                arq.write("{" + estadoAtual + "}" + palavra[i]);
            }
            else{
                arq.write(palavra[i]);
            }
        }
        arq.write("\n");
    }


    //Funcao que aumenta o tamanho da minha palavra, adicionando um B (Branco)
    private void AumentaPalavra(){
        String aux = new String(palavra);
        aux = aux + "B";
        palavra = aux.toCharArray();
    }
}

/*//Manipulacao de arquivo
import java.io.*;
//Arraylist
import java.util.ArrayList;

public class MT {
    private String[] estados;
    private String[] alfabeto;
    private String[] alfFita;
    private String estadoInicial;
    private char[] palavra;
    private ArrayList<FuncTransicao> transicoes;
    private String nomeArqSaida;

    // Construtor
    public MT(String[] estados, String[] alfabeto, String[] alfFita, String estadoInicial, char[] palavra, ArrayList<FuncTransicao> transicoes, String nomeArqSaida){
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.alfFita = alfFita;
        this.estadoInicial = estadoInicial;
        this.palavra = palavra;
        this.transicoes = transicoes;
        this.nomeArqSaida=nomeArqSaida;
    }

    //Metodo principal que comandará toda execucao
    public void Executa()throws Exception{
        //Controlador de posicoes no vetor da palavra

        int posAtual = 0;
        String estadoAtual = estadoInicial;

        String direcao = "";
        String novoDigito = "";
        String digAtual = Character.toString(palavra[posAtual]);
        String proxEstado = "";

        int cont = 0;

        //Coloca a extenção do arquivo de saida
        String nomeArqSai=nomeArqSaida+".txt";

        //Le o arquivo
        try(FileWriter arq = new FileWriter(nomeArqSai)) {

            while (TenhoMovimento(estadoAtual, digAtual)) {// enquanto eu posso transicionar
                Imprimir(estadoAtual, posAtual, arq);

                digAtual = Character.toString(palavra[posAtual]);

                novoDigito = OQEscrever(estadoAtual, digAtual);

                direcao = ProxDirecao(estadoAtual, digAtual);

                proxEstado = ProxEstado(estadoAtual, digAtual);

                estadoAtual = proxEstado;

                palavra[posAtual] = novoDigito.charAt(0);


                if (direcao.equals("L")) {
                    posAtual = posAtual - 1;
                } else if (direcao.equals("R")) {
                    posAtual++;
                }

                if (posAtual >= palavra.length) {
                    AumentaPalavra();
                }
                digAtual = Character.toString(palavra[posAtual]);
            }
            if (posAtual == 0 && !TenhoMovimento(estadoAtual, digAtual)) {
                Imprimir(estadoAtual, posAtual, arq);
                System.out.println("Maquina computada com sucesso!!");
            } else {
                System.out.println("Ooops, parece que a palavra que desejava trabalhar nao pôde ser executada por essa MT...");
            }

            //Caso não consiga criar o arquivo de saida, imprime a mensagem de erro
        }catch (IOException e){
            System.out.println("Falha ao salvar do arquivo "+nomeArqSaida);
        }
    }

    private boolean TenhoMovimento(String estadoAtual, String infoLida){
        for(FuncTransicao e: transicoes){
            if(e.getAtualEstado().equals(estadoAtual)){
                if(e.getLeitura().equals(infoLida)){
                    return true;
                }
            }
        }
        return false;
    }

    public String OQEscrever(String estadoAtual, String infoLida){//passo o estado atual e o que li e me retorna o que tenho que escrever
        for(FuncTransicao e: transicoes){
            if(e.getAtualEstado().equals(estadoAtual)){
                if(e.getLeitura().equals(infoLida)){
                    return e.getEscrita();
                }
            }
        }
        //adicionar excessao
        return "0";
    }
    public String ProxDirecao(String estadoAtual, String infoLida){//passo o estado atual e o que li e me retorna em qual direcao me movo
        for(FuncTransicao e: transicoes){
            if(e.getAtualEstado().equals(estadoAtual)){
                if(e.getLeitura().equals(infoLida)){
                    return e.getDirecao();
                }
            }
        }
        return "0";
    }

    public  String ProxEstado(String estadoAtual, String infoLida){//passo o estado atual e o que li e me retorna em qual o proximo estado
        for(FuncTransicao e: transicoes){
            if(e.getAtualEstado().equals(estadoAtual)){
                if(e.getLeitura().equals(infoLida)){
                    return e.getProxEstado();
                }
            }
        }
        //adicionar excessao
        return "0";
    }

    private void AumentaPalavra(){
        String aux = new String(palavra);
        aux = aux + "B";
        palavra = aux.toCharArray();
    }

    //Salva o resultado no arquivo texto
    public void Imprimir(String estadoAtual, int posAtual, FileWriter arq) throws Exception{

        for(int i= 0; i < palavra.length; i++){
            if(i == posAtual){
                arq.write("{" + estadoAtual + "}" + palavra[i]);
            }
            else{
                arq.write(palavra[i]);
            }
        }
        arq.write("\n");
    }
}
*/