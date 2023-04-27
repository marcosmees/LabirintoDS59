import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Labirinto {
    
    private BufferedReader bufferedReader;           
    private int nLinhas = 0;
    private int nColunas = 0;
    private File caminhoArquivo;

    private Coordenadas                 entrada;
    private Coordenadas                 saida;
    private Fila<Coordenadas>           fila;  
    private Pilha<Fila<Coordenadas>>    possibilidades;// possibilidades de caminho (adjacentes)
    private Pilha<Coordenadas>          caminho;
    private char[][]                    labirinto;
    private Coordenadas                 atual; 
    private int tamanhoMaximoLab = nLinhas * nColunas;


    
    //construtor do labirinto para vericar se arquivo é legivel
    public Labirinto(File caminhoArquivo) throws Exception{
        try{
            this.bufferedReader = new BufferedReader(new FileReader(caminhoArquivo));
            this.caminhoArquivo = caminhoArquivo;
            }
            catch(Exception erro){
                throw new Exception("Deu erro ao ler o arquivo");
            }
    }
    //faz verificações
    public void verificacaoArquivo(File caminhoArquivo) throws Exception{
        try
		{
            this.nLinhas = Integer.parseInt(bufferedReader.readLine());

			try
			{
				this.nColunas = Integer.parseInt(bufferedReader.readLine());
			}catch(Exception erro)
			{
				throw new Exception("Nao ha o numero de colunas no arquivo texto!");
			}
		}catch(Exception erro)
		{
			this.nLinhas  = 1;
			this.nColunas = 0;
			boolean primeiraVez = true;

			while(this.bufferedReader.ready())
			{
				String linha = this.bufferedReader.readLine();

				if(primeiraVez)
				{
					this.nColunas = linha.length();
					primeiraVez = false;
				}else
					if(linha.length()!=this.nColunas)
					{
						int linhaErro = this.nLinhas+1;
						throw new Exception("A linha "+linhaErro+" nao esta com o numero de caracteres adequado.");
					}

				nLinhas++;
			}

			this.bufferedReader.close();
			this.bufferedReader = new BufferedReader(
					  	   new FileReader(
					  	   this.caminhoArquivo));
		}

		this.labirinto = new char[this.nLinhas][this.nColunas];
	}

    public void montarLabirinto() throws Exception
	{
        
        for(int i=0; i<=this.labirinto.length-1; i++)
		{
            if(!this.bufferedReader.ready())
            throw new Exception("Arquivo nao esta completo!");
            
            String linha = this.bufferedReader.readLine();
                    
			if(linha.length()!=this.labirinto[0].length)
			{
                int linhaComErro = i+1;
				throw new Exception("A linha " + linhaComErro + " não possui o numero de caracteres passados ou contados");
			}
            for(int j=0; j<=this.labirinto[0].length-1; j++){
                this.labirinto[i][j] = linha.charAt(j);
                if((this.labirinto[i][j] != ' ')&&(this.labirinto[i][j] != '#') && (this.labirinto[i][j] != 'E')&& (this.labirinto[i][j] != 'S')){
                    throw new Exception("O Labirinto possui Caracteres inválidos");
                }
            }
            
        }
        validacoesDaEntrada();
        validacoesDaSaida();
        this.bufferedReader.close();
    }
    

    public Coordenadas acharEntrada() throws Exception{
        for (int i = 0; i < this.labirinto.length; i++) {
            for (int j = 0; j < this.labirinto[i].length; j++) {
                if(this.labirinto[i][j] == 'E'){
                    this.entrada = new Coordenadas(i, j);
                    caminho.addElemento(entrada);
                }
            }
        }
        return this.entrada;
    }

    public void validacoesDaEntrada()throws Exception { 
         
        int count = 0; 

        try{ 
            for (int j = 0; j < this.nColunas; j++) { 
                //borda superior
                if (this.labirinto[0][j] == 'E') { 
                    count++; 
                    return; 
                } 
                //borda inferior
                if (this.labirinto[this.nLinhas-1][j] == 'E') { 
                    count++; 
                    return; 
                } 
            }   
            for (int i = 1; i < this.nLinhas-1; i++) { 
                //borda da esquerda
                if (this.labirinto[i][0] == 'E') { 
                    count++; 
                    return; 
                } 
                //borda da direita
                if (this.labirinto[i][this.nColunas-1] == 'E') { 
                    count++; 
                    return; 
                } 
            }  
            for (int i = 1; i < this.labirinto.length - 1; i++) { 
                for (int j = 0; j < this.labirinto[i].length -1; j++) { 
                    if(this.labirinto[i][j] == 'E'){ 
                        count++;
                        throw new Exception("Entrada achada dentro do labirinto!!, não é possível resolver seu labirinto");
                    } 
                }     
            }
            if((count == 0)||(count !=1)){
                throw new Exception("O numero de entradas excede o numero maximo, no caso: 1, ou nao ha entradas"); 
            } 
        } 
        catch(Exception e){ 
        System.out.println(("O caractere 'E' nao foi encontrado nas bordas da matriz.\n"+e.getMessage())); 
        } 

    }
    
     public Coordenadas acharSaida() throws Exception{ 
        for (int i = 0; i < this.labirinto.length; i++) { 
            for (int j = 0; j < this.labirinto[i].length; j++) { 
                if(this.labirinto[i][j] == 'S'){ 
                    this.saida = new Coordenadas(i, j); 
                } 
            } 
        } 
        return this.saida;
    } 

    public void validacoesDaSaida() throws Exception 
    { 
        
         int count = 0; 

        try{ 
            for (int j = 0; j < this.nColunas; j++) { 
                //borda superior
                if (this.labirinto[0][j] == 'S') { 
                    count++; 
                    return; 
                } 
                //borda inferior
                if (this.labirinto[this.nLinhas-1][j] == 'S') { 
                    count++; 
                    return; 
                } 
            }   
            for (int i = 1; i < this.nLinhas-1; i++) { 
                //borda da esquerda
                if (this.labirinto[i][0] == 'S') { 
                    count++; 
                    return; 
                } 
                //borda da direita
                if (this.labirinto[i][this.nColunas-1] == 'S') { 
                    count++; 
                    return; 
                } 
            }  
            for (int i = 1; i < this.labirinto.length - 1; i++) { 
                for (int j = 0; j < this.labirinto[i].length -1; j++) { 
                    if(this.labirinto[i][j] == 'S'){ 
                        count++;
                        throw new Exception("Saida achada dentro do labirinto!!, não é possível resolver seu labirinto");
                    } 
                }     
            }
            if((count == 0)||(count !=1)){
                throw new Exception("O numero de saidas excede o numero maximo, no caso: 1, ou nao ha saidas"); 
            } 
        } 
        catch(Exception e){ 
        System.out.println(("O caractere 'S' nao foi encontrado nas bordas da matriz.\n"+e.getMessage())); 
        } 
    }
    
    	public StringBuilder inverso() throws Exception { 
		StringBuilder caminhoInverso = new StringBuilder(); 
		while(!this.caminho.isEmpty()) { 
		 caminhoInverso.append(this.caminho.getElemento() + ", "); 
		 this.caminho.removeElemento(); 
		} 
		return caminhoInverso;  
	   } 
    
    public Fila<Coordenadas> possibilidadesAVolta(Coordenadas atual) throws Exception
    {
        this.atual = atual;
     
		Fila<Coordenadas> fila=null;
		try
		{
			fila = new Fila<Coordenadas>(3);

			// em cima
			if(atual.getX()+1<=this.labirinto.length-1 && (labirinto[atual.getX()+1][atual.getY()]==' ' || labirinto[atual.getX()+1][atual.getY()]=='S'))
			{
				Coordenadas livre = new Coordenadas(atual.getX()+1, atual.getY());
				fila.addElementoFila(livre);  
			}

			// direita
			if(atual.getY()+1<=this.labirinto[0].length-1 && (labirinto[atual.getX()][atual.getY()+1]==' ' ||labirinto[atual.getX()][atual.getY()+1]=='S'))
			{
				Coordenadas livre = new Coordenadas(atual.getX(), atual.getY()+1);
				fila.addElementoFila(livre);
			}

			// baixo
			if(atual.getX()-1>=0 && (labirinto[atual.getX()-1][atual.getY()]==' '|| labirinto[atual.getX()-1][atual.getY()]=='S'))
			{
				Coordenadas livre = new Coordenadas(atual.getX()-1, atual.getY());
				fila.addElementoFila(livre);
			}

			// esquerda
			if(atual.getY()-1>=0 && (labirinto[atual.getX()][atual.getY()-1]==' ' || labirinto[atual.getX()][atual.getY()-1]=='S'))
			{
				Coordenadas livre = new Coordenadas(atual.getX(), atual.getY()-1);
				fila.addElementoFila(livre);
			}
		}catch(Exception erro)
		{}

		return fila;
	}

    public void ResolveLabirinto() throws Exception{

        try
		{
			this.caminho = new Pilha<Coordenadas>(this.labirinto.length*this.labirinto[0].length);
			this.possibilidades = new Pilha<Fila<Coordenadas>>(this.labirinto.length*this.labirinto[0].length);
           
            this.entrada = acharEntrada();
            saida = acharSaida();

			this.atual = new Coordenadas(this.entrada);

			boolean progressivo = true;


			while(atual!=saida)
			{
				if(progressivo){
 					// procura as possibilidades de caminho a serem seguidos
					this.fila = this.possibilidadesAVolta(atual);
                }
				if(!fila.isEmpty())
				{
					progressivo = true;

                    atual = new Coordenadas(fila.getElementoFila());

					fila.desenfileire();
					possibilidades.addElemento(this.fila);

					if(this.labirinto[atual.getX()][atual.getY()]==' ');
				
						this.labirinto[atual.getX()][atual.getY()]='*';
						// anda (se nao for a saída)
						this.caminho.addElemento(atual); //nao empilha a saida
					}
                    
               else
                 
				// não existem mais possibilidades ao redor dessa coordenada
				// modo regressivo: volta até o cruzamento mais proximo e pega outro caminho
				{
					this.labirinto[atual.getX()][atual.getY()]=' ';
					// volta um quadrado

					// o caminho pode chegar vazio (se nao tiver nenhum espaco livre logo na entrada)
					if(this.caminho.isEmpty())
						throw new Exception("O labirinto nao tem um caminho para a saida!");

					this.caminho.desempilhe();

					if(this.caminho.isEmpty())
						throw new Exception("O labirinto nao tem um caminho para a saida!");

					 atual = new Coordenadas(this.caminho.getElemento());

					if(!possibilidades.getElemento().isEmpty())
						fila = new Fila<Coordenadas>(possibilidades.getElemento());

					possibilidades.desempilhe();
					progressivo = false;
				}
            }
                

			Pilha<Coordenadas> inverso = new Pilha<Coordenadas>(this.labirinto.length*this.labirinto[0].length);

			while(!caminho.isEmpty())
			{
				inverso.addElemento(atual);
				caminho.desempilhe();
			}

			this.caminho = inverso;
		}catch(Exception erro)
		{
            throw new Exception("Não foi possivel resolver porque o labirinto não possui caminho da entrada até a saida");
        }
    }

    public File getCaminho(){
        return this.caminhoArquivo;
    }

    public String getLabirinto()
	{
		String ret="";

		for(int l=0; l<=this.labirinto.length-1; l++)
			for(int c=0; c<=this.labirinto[0].length-1; c++)
				ret += c==this.labirinto[0].length-1?this.labirinto[l][c]+"\r\n":
					this.labirinto[l][c];
		return ret;
	}
    public Boolean saiu(Coordenadas atual, Coordenadas saida){
        this.saida = saida;
        boolean saiu = false;
        return saiu;
    }

    @Override 
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        for (int i = 0; i < this.labirinto.length; i++) { 
            for (int j = 0; j < this.labirinto[i].length; j++) { 
                sb.append(this.labirinto[i][j] + " "); 
            } 
            sb.append("\n"); 
        } 
        return sb.toString(); 
    }
}
