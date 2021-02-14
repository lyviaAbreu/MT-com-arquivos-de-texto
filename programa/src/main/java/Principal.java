//Manipulacao de arquivo
import java.io.*;

public class Principal {
    //Apenas chama as classes e funções para execução do trabalho
    public static void main(String[] args)throws Exception{

        //Cria um objeto do tipo ManipulaEntrada
        ManipulaEntrada me=new ManipulaEntrada();

        //Cria um objeto do tipo MT e declara como null para caso não consiga ler o arqumento
        MT mt=null;

        //A MT passa a valer os dados do Arquivo 1
        //args[0] é o arquivo de entrada e args[1] é a saida
        mt=me.leDadosArq(args[0],args[1]);

        //Executa a máquina
        mt.Executa();

    }
}
