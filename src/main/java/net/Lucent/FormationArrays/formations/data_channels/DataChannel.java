package net.Lucent.FormationArrays.formations.data_channels;

import net.Lucent.FormationArrays.formations.AbstractFormationNode;

import java.util.Optional;

public class DataChannel<T> {

    public final T defaultValue;
    public DataChannel(T defaultValue){
        this.defaultValue = defaultValue;

    }
    public T querySocket(String socketId, AbstractFormationNode node) {

        try{
            return (T) node.getOutputSocket(socketId).getData();
        }catch (Exception e){
            return this.defaultValue;
        }
    }
}
