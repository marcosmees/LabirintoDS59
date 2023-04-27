import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Leitor {
    private BufferedReader bufferedReader;           
    private int nLinhas = 0;
    private int nColunas = 0;
    private File caminhoArquivo;

    char[][] montagemLabirinto = new char[nLinhas][nColunas];
    
    public Leitor(File caminhoArquivo)throws Exception {
        try{
        this.bufferedReader = new BufferedReader(new FileReader(caminhoArquivo));
        this.caminhoArquivo = caminhoArquivo;
        }
        catch(Exception erro){
            throw new Exception("Deu erro ao ler o arquivo");
        }
    }
    
    public char[][] instanciarMatriz(File caminhoArquivo) throws Exception{
        try{
            try
            {
                try{
                     // Ve se o arquivo texto tem o numero de linhas e colunas
                    this.nLinhas = Integer.parseInt(bufferedReader.readLine());
                    // lanca excecao caso não possam ser convertidos para int
                
                try
                {
                    this.nColunas = Integer.parseInt(bufferedReader.readLine());
                    // lanca excecao caso não possam ser convertidos para int
                }catch(Exception erro)
                {
                    throw new Exception("Nao há o numero de colunas no arquivo texto!");
                    //explica o erro: ou coloca os dois valores (linha e coluna) ou nao coloca nenhum
                }
            }catch(Exception erro){
                throw new Exception("Não há numero de linhas");}
                    for (int i = 0; i < nLinhas; i++) {
                        
                        String linha = bufferedReader.readLine();
                
                
                        for (int j = 0; j < nColunas; j++) {
                            this.montagemLabirinto[i][j] = linha.charAt(j);//string q guarda caracter por caracter quebrando a linha, Marcos M A R C O S
                            if((this.montagemLabirinto[i][j] != ' ')||(this.montagemLabirinto[i][j] != '#') || (this.montagemLabirinto[i][j] != 'E')|| (this.montagemLabirinto[i][j] != 'S')){
                                throw new Exception("O Labirinto possui Caracteres inválidos");
                            }
                        }
                    }
                }
                catch(Exception erro){
                    throw new Exception("");
                } 
            finally{
                this.nLinhas  = 1;
                // ele ja leu uma linha quando tentou transformar a primeira linha em int
                nColunas = 0;
                boolean primeiraVez = true;

                //While para fazer verificação se a quantidade de linhas e colunas estão condizentes com o tamanho contado
                while(this.bufferedReader.ready())
                {
                    String linha = this.bufferedReader.readLine();

                    if(primeiraVez)
                    {
                        nColunas = linha.length();
                        primeiraVez = false;
                    }else
                        if(linha.length()!=nColunas)
                        {
                            int linhaErro = this.nLinhas+1;
                            throw new Exception("A linha "+linhaErro+" nao esta com o numero de caracteres adequado.");
                        }

                    this.nLinhas++;
                    // uma linha a mais
                }
                
                this.bufferedReader.close();
                this.bufferedReader = new BufferedReader(
                                    new FileReader(
                                    this.caminhoArquivo));
                //fecha o arquivo e abre de novo (para voltar ao comeco do arquivo)
            }

            // INSTANCIA A MATRIZ
            // instancia a matriz com o labirinto com as informacoes de linha e coluna dadas ou contadas
           
            this.montagemLabirinto = new char[nLinhas][nColunas];

            return montagemLabirinto;
        
        }catch(Exception e) {
            throw new Exception("ERRO NO ARQUIVO ");
        }
    }
}
