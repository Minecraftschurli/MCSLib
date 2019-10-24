package minecraftschurli.mcslib.data;

import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;

/**
 * @author Minecraftschurli
 * @version 2019-10-24
 */
public class RecipeHelper {
    public static ShapedRecipeBuilder addLargeCompressRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapedRecipeBuilder.shapedRecipe(item1)
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapedRecipeBuilder addLargeCompressRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapedRecipeBuilder.shapedRecipe(item1)
                .patternLine("XXX")
                .patternLine("XXX")
                .patternLine("XXX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static ShapedRecipeBuilder addSmallCompressRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapedRecipeBuilder.shapedRecipe(item1)
                .patternLine("XX")
                .patternLine("XX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapedRecipeBuilder addSmallCompressRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapedRecipeBuilder.shapedRecipe(item1)
                .patternLine("XX")
                .patternLine("XX")
                .key('X', item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static ShapelessRecipeBuilder addLargeDecompressRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapelessRecipeBuilder
                .shapelessRecipe(item1, 9)
                .addIngredient(item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapelessRecipeBuilder addLargeDecompressRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapelessRecipeBuilder
                .shapelessRecipe(item1, 9)
                .addIngredient(item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static ShapelessRecipeBuilder addSmallDecompressRecipe(IItemProvider item1, IItemProvider item2) {
        return ShapelessRecipeBuilder
                .shapelessRecipe(item1, 4)
                .addIngredient(item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(item2).build()));
    }

    public static ShapelessRecipeBuilder addSmallDecompressRecipe(IItemProvider item1, Tag<Item> item2) {
        return ShapelessRecipeBuilder
                .shapelessRecipe(item1, 4)
                .addIngredient(item2)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(item2).build()));
    }

    public static CookingRecipeBuilder addSmeltingRecipe(IItemProvider output, IItemProvider input, float exp) {
        return addSmeltingRecipe(output, input, exp, 200);
    }

    public static CookingRecipeBuilder addSmeltingRecipe(IItemProvider output, Tag<Item> input, float exp) {
        return addSmeltingRecipe(output, input, exp, 200);
    }

    public static CookingRecipeBuilder addSmeltingRecipe(IItemProvider output, IItemProvider input, float exp, int time) {
        return CookingRecipeBuilder
                .smeltingRecipe(Ingredient.fromItems(input), output, exp, time)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(input));
    }

    public static CookingRecipeBuilder addSmeltingRecipe(IItemProvider output, Tag<Item> input, float exp, int time) {
        return CookingRecipeBuilder
                .smeltingRecipe(Ingredient.fromTag(input), output, exp, time)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(input).build()));
    }

    public static CookingRecipeBuilder addBlastingRecipe(IItemProvider output, IItemProvider input, float exp) {
        return addBlastingRecipe(output,input,exp, 100);
    }

    public static CookingRecipeBuilder addBlastingRecipe(IItemProvider output, Tag<Item> input, float exp) {
        return addBlastingRecipe(output, input, exp, 100);
    }

    public static CookingRecipeBuilder addBlastingRecipe(IItemProvider output, IItemProvider input, float exp, int time) {
        return CookingRecipeBuilder
                .blastingRecipe(Ingredient.fromItems(input), output, exp, time)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(input));
    }

    public static CookingRecipeBuilder addBlastingRecipe(IItemProvider output, Tag<Item> input, float exp, int time) {
        return CookingRecipeBuilder
                .blastingRecipe(Ingredient.fromTag(input), output, exp, time)
                .addCriterion("item", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(input).build()));
    }
}
