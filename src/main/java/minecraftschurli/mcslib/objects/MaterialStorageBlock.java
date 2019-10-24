package minecraftschurli.mcslib.objects;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

/**
 * @author Minecraftschurli
 * @version 2019-10-20
 */
public class MaterialStorageBlock extends Block {
    public MaterialStorageBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isBeaconBase(BlockState state, IWorldReader world, BlockPos pos, BlockPos beacon) {
        return true;
    }
}
