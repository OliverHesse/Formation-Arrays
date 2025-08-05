package net.Lucent.FormationArrays.formations.data_channels;

import net.Lucent.FormationArrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.Lucent.FormationArrays.formations.AbstractFormationNode;
import net.Lucent.FormationArrays.formations.FormationRuntimeData;

import java.util.Optional;
import java.util.UUID;

public class DataChannel<T> {

    public final T defaultValue;
    public String socketId;
    public UUID formationId;
    public AbstractFormationCoreBlockEntity core;
    public DataChannel(T defaultValue){
        this.defaultValue = defaultValue;
    }
    public DataChannel(T defaultValue,String socketId,UUID formationId,AbstractFormationCoreBlockEntity core){
        this.defaultValue = defaultValue;
        this.core = core;
        this.socketId = socketId;
        this.formationId = formationId;

    }
    public T querySocket(){

        try{
            return (T) core.getOutputSocket(socketId,formationId).getData();
        }catch (Exception e){
            return this.defaultValue;
        }
    }
}
