package minecraftschurli.mcslib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;

/**
 * @author Minecraftschurli
 * @version 2019-10-24
 */
public abstract class BaseItemTagsProvider extends ItemTagsProvider {
    public BaseItemTagsProvider(DataGenerator p_i48255_1_) {
        super(p_i48255_1_);
    }

    @Override
    abstract protected void registerTags();

    protected void registerSuperTags() {
        super.registerTags();
    }

    @Override
    public Tag.Builder<Item> getBuilder(Tag<Item> tag) {
        return super.getBuilder(tag);
    }
}
