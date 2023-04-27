import java.lang.reflect.Method;

public class Fila<X> {
    private int             ultimo;
    private int             tamanhoMaximo;
    private Object[]        elementos;

    public Fila(int tamanhoMaximo) {
        this.elementos = new Object[tamanhoMaximo];
        this.tamanhoMaximo = tamanhoMaximo;
    }

    //Adiciona Elemento
     public void addElementoFila (X elemento) throws Exception
    {
        if (elemento==null)
            throw new Exception ("Falta o que guardar");

        this.ultimo++;

        if (elemento instanceof Cloneable)
            this.elementos[this.ultimo] = meuCloneDeX(elemento);
        else
            this.elementos[this.ultimo] = elemento;
    }

    //remove elemento
    public void removeElementoFila() throws Exception
    {
        if (this.ultimo==-1)
            throw new Exception ("Nada a remover");

        for (int i=0; i<this.ultimo; i++)
            this.elementos[i] = this.elementos[i+1];

        this.elementos[this.ultimo] = null;
        this.ultimo--;
    }

    
    //retorna primeiro elemento
    public X getElementoFila() throws Exception{
        if (this.ultimo==-1)
            throw new Exception ("Nada a recuperar");

        X ret=null;
        if (this.elementos[this.ultimo] instanceof Cloneable)
            ret = meuCloneDeX((X)this.elementos[1]);
        else
            ret = (X)this.elementos[1];

        return ret;
    }

    public void desenfileire() throws Exception
 	{
		if(this.isEmpty())
		throw new Exception("Fila vazia.");

 		this.elementos[this.ultimo] = null;

 		if(this.ultimo==this.tamanhoMaximo-1)
 			this.ultimo = 0;
 		else
 			this.ultimo++;

 		this.tamanhoMaximo--;
 	}

    //verifica se esta vazio
    public boolean isEmpty() 
    {
        if(this.ultimo==-1)
            return true;

        return false;
    }

     private X meuCloneDeX(X x)
    {

        X ret  = null;

        try
        {
			Class<?> classe = x.getClass();
			Class<?>[] tipoDosParms = null;
			Method metodo = classe.getMethod("clone", tipoDosParms);
			Object[] parms = null;
			ret = (X)metodo.invoke(x,parms);
        }
        catch(Exception erro)
        {}

        return ret;
    }

    @Override
    public String toString()
    {
        String ret = this.ultimo + " elementos";

        if (this.ultimo!=-1)
            ret += ", sendo o ultimo "+this.elementos[this.ultimo];

        return ret;
    }

    @Override
    public boolean equals (Object obj)
    {
        if(this==obj)
            return true;

        if(obj==null)
            return false;

        if(this.getClass()!=obj.getClass())
            return false;

        Fila<X> fil = (Fila<X>) obj;

        if(this.ultimo!=fil.ultimo)
            return false;

        for(int i=0 ; i<this.ultimo;i++)
            if(!this.elementos[i].equals (fil.elementos[i]))
                return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret=777/*qualquer positivo*/;

        //ret = ret*7/*primo*/ + new Integer(this.ultimo).hashCode();
        ret = ret*7/*primo*/ + Integer.valueOf(this.ultimo).hashCode();

        for (int i=0; i<this.ultimo; i++)
            ret = ret*7/*primo*/ + this.elementos[i].hashCode();
        if (ret<0)
            ret=-ret;

        return ret;
    }

    public Fila(Fila<X> modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("modelo ausente");

        this.ultimo = modelo.ultimo;

        this.elementos = new Object[modelo.elementos.length];

        for(int i=0 ; i<=modelo.ultimo; i++)
            this.elementos[i] = modelo.elementos[i];
    }
  
    @Override
    public Object clone()
    {
        Fila<X> ret = null;

        try
        {
            ret  = new Fila<X>(this);
        }
        catch(Exception erro)
        {}

        return ret;
    }
}