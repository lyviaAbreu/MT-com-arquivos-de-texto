//Manipulacao de arquivo
import java.io.*;

//Arraylist
import java.util.ArrayList;

public class ManipulaEntrada {

    //Le os dados do arquivo e salva em variáveis
    public MT leDadosArq(String nomeArqEntrada, String nomeArqSaida){
        //Coloca a extenção do arquivo
        String nomeArqEnt=nomeArqEntrada+".txt";

        //Le o arquivo
        try(BufferedReader arqEntrada = new BufferedReader(new FileReader(nomeArqEnt))) {
            String linha = arqEntrada.readLine();

            //Quantidade de linhas no arquivo
            int cont=1;

            //Manipular dados do arquivo
            String[] estados = {""};
            String[] alfabeto = {""};
            String[] alfFita = {""};
            String estadoInicial = "";
            String palavra = "";
            char[] palavraChars = palavra.toCharArray();

            ArrayList<FuncTransicao> transicoes = new ArrayList<FuncTransicao>();

            int contAux = 0;

            boolean existeEstados = true;

            //Verificações para salvar os dados em variáveis
            while (linha != null) {

                //Se tiver na segunda linha salva os estados
                if (cont == 2) {
                    estados = salvaDados(linha, cont);
                }

                //Se tiver na terceira linha, salva o alfabeto
                else if (cont == 3) {
                    alfabeto = salvaDados(linha, cont);
                }

                //Se tiver na quarta linha, salva o alfabeto da fita
                else if (cont == 4) {
                    alfFita = salvaDados(linha, cont);
                }

                //Se tiver na sexta linha em diante, salva as transicoes
                else if (existeEstados && cont >= 6) {

                    if (!linha.equals("}")) {
                        arrayTransicoes(linha, transicoes);
                    } else {
                        existeEstados = false;
                        contAux = cont;
                    }

                }

                //Se acabou as transicoes salva o estado inicial e depois a palavra;
                else {
                    if (cont == (contAux + 1)) {
                        String aux = linha.replace("{", "");
                        aux = aux.replace("}", "");
                        estadoInicial = aux;
                    } else if (cont == (contAux + 3)) {
                        palavra = linha;
                        palavraChars = palavra.toCharArray();
                    }
                }


                //Le outra linha do arquivo
                linha = arqEntrada.readLine();
                cont++;
            }

            //Chama a máquina de turing
            MT maquina = new MT(estados, alfabeto, alfFita, estadoInicial, palavraChars, transicoes, nomeArqSaida);
            return maquina;
        }
        //Caso não consiga ler o arquivo de entrada, imprimi o erro e retorna null
        catch (IOException e){
            System.out.println("Falha ao salvar do arquivo "+nomeArqEntrada);
        }
        return null;

    }

    //Auxilia para salvar os dados em um vetor de string
    public static String[] salvaDados(String linha, int cont){

        //Substitui valores que nao serão usados por vazio
        String aux = linha.replace("{", "");
        aux = aux.replace("},", "");

        //Retorna o vetor com os dados separados de acordo com o espaço necessário
        if(cont == 4){
            return aux.split(",");
        }
        return aux.split(", ");
    }

    //Auxilia para salvar os dados em um Array de transições
    public static void arrayTransicoes(String linha, ArrayList <FuncTransicao> transicoes){

        //Substitui valores que nao serão usados por vazio
        String aux = linha.replace("(", "");
        aux = aux.replace("),", "");
        aux = aux.replace(")", "");

        //Troca a seta por espaço e vírgula para dividir posterioemente em um vetor
        aux = aux.replace(" -> ", ", ");

        //Retorna o vetor com os dados separados de acordo com o espaço necessário
        String[] auxTransicoes= aux.split(", ");

        //Cria uma variável do tipo FuncTransicao com os dados necessários
        FuncTransicao transicao = new FuncTransicao (
                auxTransicoes[0], //atualEstado
                auxTransicoes[1], //leiura
                auxTransicoes[2], //proxEstado
                auxTransicoes[3], //escrita
                auxTransicoes[4] //direção
        );

        //Os dados são add em um array de FuncTransicao
        transicoes.add(transicao);
    }
}