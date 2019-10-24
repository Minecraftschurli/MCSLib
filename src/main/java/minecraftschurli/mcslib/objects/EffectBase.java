package minecraftschurli.mcslib.objects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-10-15
 */
public class EffectBase extends Effect {
    public EffectBase(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }
}
