package net.Lucent.FormationArrays.formations;

import net.Lucent.FormationArrays.formations.data_channels.DataChannel;
import net.Lucent.FormationArrays.formations.data_channels.DataSocket;
import net.Lucent.FormationArrays.formations.interfaces.ISocket;

import java.math.BigDecimal;
import java.util.HashMap;

public abstract class AbstractFormationNode {

    private final HashMap<String, ISocket<?>> outputSockets = new HashMap<>();
    private final HashMap<String, DataChannel<?>> inputChannels = new HashMap<>();

    private final HashMap<String, BigDecimal> qiTypeDrain = new HashMap<>();


    public void addQiDrainType(String qiType, BigDecimal amount){
        qiTypeDrain.put(qiType,amount);
    }

    public void addOutputSocket(String id,ISocket<?> socket){
        outputSockets.put(id,socket);
    }
    public void addInputChannel(String id,DataChannel<?> socket){
        inputChannels.put(id,socket);
    }

    public ISocket<?> getOutputSocket(String id){
        return outputSockets.get(id);
    }
    public DataChannel<?> getInputChannel(String id){
        return inputChannels.get(id);
    }
    public BigDecimal getQiTypeDrain(String id){
        return qiTypeDrain.get(id);
    }

    public abstract void run(FormationRuntimeData runtimeData);
}
