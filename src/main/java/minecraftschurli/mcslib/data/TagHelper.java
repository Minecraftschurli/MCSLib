package minecraftschurli.mcslib.data;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-10-24
 */
public class TagHelper {
    public static final Map<TagType, Map<String, Tag<Item>>> TAG_MAP = new HashMap<>();
    public static Tag<Item> getTag(TagType type, String name) {
        Map<String, Tag<Item>> tagMap = TAG_MAP.getOrDefault(type, null);
        if (tagMap == null) {
            TAG_MAP.put(type, new HashMap<>());
            tagMap = TAG_MAP.get(type);
        }
        Tag<Item> tag = tagMap.getOrDefault(name, null);
        if (tag == null) {
            tagMap.put(name, forgeTag(type.getName()+"/"+name));
            tag = tagMap.get(name);
        }
        return tag;
    }

    public static Tag<Item> tag(String modid, String name) {
        return new ItemTags.Wrapper(new ResourceLocation(modid, name));
    }

    public static Tag<Item> forgeTag(String name) {
        return tag("forge", name);
    }

    public static void registerTagsToParent(Function<Tag<Item>, Tag.Builder<Item>> getBuilder) {
        TAG_MAP.entrySet().stream().parallel().forEach(entry -> getBuilder.apply(entry.getKey().parentTag).add(entry.getValue().values().toArray((Tag<Item>[]) new Tag[0])));
    }

    public static class TagType implements IStringSerializable {
        private static final List<TagType> VALUES = new ArrayList<>();
        public static final TagType INGOT = new TagType("ingots", Tags.Items.INGOTS);
        public static final TagType GEM = new TagType("gems", Tags.Items.GEMS);
        public static final TagType NUGGET = new TagType("nuggets", Tags.Items.NUGGETS);
        public static final TagType BLOCK = new TagType("storage_blocks", Tags.Items.STORAGE_BLOCKS);
        public static final TagType DUST = new TagType("dusts", Tags.Items.DUSTS);

        private final String name;
        private final Tag<Item> parentTag;

        private TagType(String name, Tag<Item> parentTag) {
            this.name = name;
            this.parentTag = parentTag;
            VALUES.add(this);
        }

        public static TagType get(String s) {
            switch (s) {
                case "ingot":
                    return INGOT;
                case "gem":
                    return GEM;
                case "nugget":
                    return NUGGET;
                case  "block":
                    return BLOCK;
                case  "dust":
                    return DUST;
                default:
                    return new TagType(s + "s", Tags.Items.DUSTS);
            }
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
