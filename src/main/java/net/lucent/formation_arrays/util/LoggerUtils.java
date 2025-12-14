package net.lucent.formation_arrays.util;

import net.lucent.formation_arrays.FormationArrays;
import net.minecraft.core.BlockPos;

public class LoggerUtils {

    public static void portTypeMismatch(String port, String connectionName, BlockPos coreLocation){
        FormationArrays.LOGGER.error("error when trying to get data from port: {} and connection: {}. mismatched data types. formation core: {}", port, connectionName, coreLocation.toString());

    }
}
