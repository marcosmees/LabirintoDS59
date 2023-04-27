import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        String caminhoArquivo = "";
        
        do
        try {   
            
            BufferedReader teclado = new BufferedReader(
                                    new InputStreamReader
                                    (System.in));
                    

            System.out.print("Digite o caminho para o Labirinto: ");
            caminhoArquivo = teclado.readLine();
        
            //le o caminho que esta no arquivo e armazena o arquivo lido na var aquivo
            File arquivo = new File(caminhoArquivo);
            
            Labirinto labirinto = new Labirinto(arquivo);                      
            labirinto.verificacaoArquivo(arquivo);
            labirinto.montarLabirinto();
            labirinto.ResolveLabirinto();
            System.out.println();

            System.out.println("\nResolução: \n");
            System.out.println(labirinto.getLabirinto());


            System.out.println(labirinto.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        while(caminhoArquivo != "0");
    }
}
