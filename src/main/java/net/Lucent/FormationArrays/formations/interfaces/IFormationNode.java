package net.Lucent.FormationArrays.formations.interfaces;

import net.Lucent.FormationArrays.formations.data_channels.DataChannel;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public interface IFormationNode {

    //socket -> input node socket
    //output node -> node outputting data
    //outputSocket -> output node socket
    //inputNode is not needed since this is always input node (Design choice)
    void addConnection(String socket,IFormationNode outputNode,String outputSocket);

    //TODO add more options
    Optional<?> runOutputSocket(String socket, Level level, BlockPos pos);

    List<String> getInputSockets();
    List<String> getOutputSockets();

    //only for input sockets
    DataChannel getSocketDataChannel(String socket);
    String getDisplayName();
}
