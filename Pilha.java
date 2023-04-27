import java.util.*;
import java.lang.reflect.Method;

public class Pilha<X> {
    private int tamanhoMaximo;
    private int ultimo=-1; //vazio
    private Object[] elementos;

    //construtor
    public Pilha(int tamanhoMaximo) throws Exception
    {
        if (tamanhoMaximo <=0)
            throw new Exception("Tamanho inválido");

        this.tamanhoMaximo = tamanhoMaximo;
        this.elementos = new Object [tamanhoMaximo];
    }

    //adiciona pilha
    public void addElemento(X elemento) throws Exception
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
    public void removeElemento() throws Exception{
        if (this.ultimo==-1)
            throw new Exception ("Nada a remover");

        this.elementos[this.ultimo] = null;
        this.ultimo--;
    }

    //pega elemento
    public X getElemento() throws Exception{
        if (this.ultimo==-1)
            throw new Exception ("Nada a recuperar");

        X ret=null;
        if (this.elementos[this.ultimo] instanceof Cloneable)
            ret = meuCloneDeX((X)this.elementos[this.ultimo]);
        else
            ret = (X)this.elementos[this.ultimo];

        return ret;
    }
    
    public void desempilhe() throws Exception
    {
       if(this.isEmpty())
           throw new Exception("Pilha vazia.");

        this.elementos[this.ultimo] = null;
        this.ultimo--;
    }

    //verifica se está vazio
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
			ret = (X)metodo.invoke(x, parms);
        }
        catch(Exception erro)
        {}

        return ret;
    }

    @Override
    public String toString() {
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

        Pilha<X> pil = (Pilha<X>) obj;

        if(this.ultimo!=pil.ultimo)
            return false;

        for(int i=0 ; i<this.ultimo;i++)
            if(!this.elementos[i].equals (pil.elementos[i]))
                return false;

        return true;
    }

     @Override
    public int hashCode ()
    {
        int ret=12/*qualquer positivo*/;

        //ret = ret*7/*primo*/ + new Integer(this.ultimo).hashCode();
        ret = ret*7/*primo*/ + Integer.valueOf(this.ultimo).hashCode();

        for (int i=0; i<this.ultimo; i++)
            ret = ret*7/*primo*/ + this.elementos[i].hashCode();

        if (ret<0)
            ret=-ret;

        return ret;
    }

}