package io.papermc.generator.types.craftblockdata.converter;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class RotationConverter extends Converter<Integer, BlockFace> {

    private static final String DIRECTION_VAR = "dir";
    private static final String ANGLE_VAR = "angle";

    @Override
    public Property<Integer> getProperty() {
        return BlockStateProperties.ROTATION_16;
    }

    @Override
    public Class<BlockFace> getApiType() {
        return BlockFace.class;
    }

    @Override
    public void convertSetter(final MethodSpec.Builder method, final FieldSpec field, final ParameterSpec parameter) {
        method.addStatement("$T $L = $N.getDirection()", Vector.class, DIRECTION_VAR, parameter);
        method.addStatement("$1T $2L = ($1T) -$3T.toDegrees($3T.atan2($4L.getX(), $4L.getZ()))", Float.TYPE, ANGLE_VAR, Math.class, DIRECTION_VAR);
        method.addStatement("this.set($N, $T.convertToSegment($L))", field, RotationSegment.class, ANGLE_VAR);
    }

    @Override
    public void convertGetter(final MethodSpec.Builder method, final FieldSpec field) {
        method.addCode("return CraftBlockData.ROTATION_CYCLE[this.get($N)];", field);
    }
}
