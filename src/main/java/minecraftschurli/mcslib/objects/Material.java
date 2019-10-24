package minecraftschurli.mcslib.objects;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import minecraftschurli.mcslib.MCSLib;
import minecraftschurli.mcslib.data.BaseItemTagsProvider;
import minecraftschurli.mcslib.data.RecipeHelper;
import minecraftschurli.mcslib.data.TagHelper;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-10-17
 */
public class Material {
    private final String name;
    private Map<String, Item.Properties> itemProperties = new HashMap<>();
    private Pair<String, Pair<Block.Properties, Item.Properties>> block;
    private Pair<String, Triple<Block.Properties, Item.Properties, OreBase.OreSpawnProperties>> ore;
    private RegistryObject<Block> blockRegistryObject;
    private RegistryObject<Block> oreRegistryObject;
    private Map<String, RegistryObject<Item>> items = new HashMap<>();

    public Material(String name) {
        this.name = name;
    }

    public Material withStorageBlock(Block.Properties blockProperties, Item.Properties itemProperties) {
        String name = this.name+"_block";
        this.block = Pair.of(name, Pair.of(blockProperties, itemProperties));
        return this;
    }

    public Material withOre(Block.Properties blockProperties, Item.Properties itemProperties, OreBase.OreSpawnProperties oreSpawnProperties) {
        String name = this.name+"_ore";
        this.ore = Pair.of(name, Triple.of(blockProperties, itemProperties, oreSpawnProperties));
        return this;
    }

    public Material withIngot(Item.Properties properties) {
        return withItem(properties, "ingot");
    }

    public Material withNugget(Item.Properties properties) {
        return withItem(properties, "nugget");
    }

    public Material withDust(Item.Properties properties) {
        return withItem(properties, "dust");
    }

    public Material withPlate(Item.Properties properties) {
        return withItem(properties, "plate");
    }

    public Material withRod(Item.Properties properties) {
        return withItem(properties, "rod");
    }

    public Material withItem(Item.Properties properties, String suffix) {
        this.itemProperties.put(suffix, properties);
        return this;
    }
    
    public Material register(DeferredRegister<Block> blockDeferredRegister, DeferredRegister<Item> itemDeferredRegister) {
        if (this.block != null) {
            String blockName = this.block.getFirst();
            Pair<Block.Properties, Item.Properties> properties = this.block.getSecond();
            this.blockRegistryObject = blockDeferredRegister.register(blockName, () -> new MaterialStorageBlock(properties.getFirst()));
            this.items.put("block", itemDeferredRegister.register(blockName, () -> new BlockItem(blockRegistryObject.get(), properties.getSecond())));
        }
        if (this.ore != null) {
            String oreName = this.ore.getFirst();
            Triple<Block.Properties, Item.Properties, OreBase.OreSpawnProperties> properties = this.ore.getSecond();
            this.oreRegistryObject = blockDeferredRegister.register(oreName, () -> new OreBase(properties.getLeft(), properties.getRight()));
            this.items.put("ore", itemDeferredRegister.register(oreName, () -> new BlockItem(oreRegistryObject.get(), properties.getMiddle())));
        }
        this.items.putAll(this.itemProperties
                .entrySet()
                .stream()
                .map(entry -> Pair.of(entry.getKey(), itemDeferredRegister.register(getName(entry.getKey()), () -> new Item(entry.getValue()))))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
        return this;
    }

    public Item get(String suffix) {
        return this.items.get(suffix).get();
    }

    public boolean has(String suffix) {
        return this.items.containsKey(suffix);
    }

    private String getName(String suffix) {
        return suffix.length() > 0 ? this.name + "_" + suffix : this.name;
    }

    public void registerRecipes(Consumer<IFinishedRecipe> consumer, String modid) {
        if (this.has("block")) {
            if (this.has("ingot")) {
                RecipeHelper.addLargeCompressRecipe(this.get("block"), TagHelper.getTag(TagHelper.TagType.INGOT, this.name))
                        .build(consumer, new ResourceLocation(modid, this.name+"_block_from_"+this.name+"_ingot"));
                RecipeHelper.addLargeDecompressRecipe(this.get("ingot"), TagHelper.getTag(TagHelper.TagType.BLOCK, this.name))
                        .build(consumer, new ResourceLocation(modid, this.name+"_ingot_from_"+this.name+"_block"));
            }
            if (this.has("gem")) {
                RecipeHelper.addLargeCompressRecipe(this.get("block"), TagHelper.getTag(TagHelper.TagType.GEM, this.name))
                        .build(consumer, new ResourceLocation(modid, this.name+"_block_from_"+this.name+"_gem"));
                RecipeHelper.addLargeDecompressRecipe(this.get("gem"), TagHelper.getTag(TagHelper.TagType.BLOCK, this.name))
                        .build(consumer, new ResourceLocation(modid, this.name+"_gem_from_"+this.name+"_block"));
            }
        }
        if (this.has("ingot")) {
            if (this.has("nugget")) {
                RecipeHelper.addLargeCompressRecipe(this.get("ingot"), TagHelper.getTag(TagHelper.TagType.NUGGET, this.name))
                        .build(consumer, new ResourceLocation(modid, this.name + "_ingot_from_" + this.name + "_nugget"));
                RecipeHelper.addLargeDecompressRecipe(this.get("nugget"), TagHelper.getTag(TagHelper.TagType.INGOT, this.name))
                        .build(consumer, new ResourceLocation(modid, this.name + "_nugget_from_" + this.name + "_ingot"));
            }
            if (this.has("dust")) {
                RecipeHelper.addSmeltingRecipe(this.get("ingot"), TagHelper.getTag(TagHelper.TagType.DUST, this.name), 0)
                        .build(consumer, new ResourceLocation(modid, this.name+"_ingot_from_"+this.name+"_dust_smelt"));
                RecipeHelper.addBlastingRecipe(this.get("ingot"), TagHelper.getTag(TagHelper.TagType.DUST, this.name), 0)
                        .build(consumer, new ResourceLocation(modid, this.name+"_ingot_from_"+this.name+"_dust_blast"));
            }
        }
    }

    public void registerItemTags(BaseItemTagsProvider itemTagsProvider) {
        this.items.keySet().forEach(s -> itemTagsProvider.getBuilder(TagHelper.getTag(TagHelper.TagType.get(s), Material.this.name)).add(Material.this.get(s)));
    }

    public String name() {
        return this.name;
    }
}
