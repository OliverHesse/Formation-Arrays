package net.Lucent.FormationArrays.formations.data_channels;

import net.Lucent.FormationArrays.formations.AbstractFormationNode;
import net.Lucent.FormationArrays.formations.interfaces.ISocket;

public class DataSocket<T> implements ISocket<T> {
    private T data;
    private final AbstractFormationNode parent;

    public DataSocket(T data,AbstractFormationNode parent){
        this.data = data;

        this.parent = parent;
    }

    public AbstractFormationNode getParent(){
        return parent;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
