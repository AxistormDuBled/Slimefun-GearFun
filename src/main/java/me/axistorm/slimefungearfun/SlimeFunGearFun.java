package me.axistorm.slimefungearfun;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.axistorm.slimefungearfun.items.blazed_ingot;
import me.axistorm.slimefungearfun.multiblocks.BlazingForge;

public class SlimeFunGearFun extends JavaPlugin implements SlimefunAddon {

    @Override
    public void onEnable() {
        // Read something from your config.yml
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }

        /*
         * 1. Creating a new Category
         * This Category will use the following ItemStack
         */
        ItemStack itemGroupItem = new CustomItemStack(Material.BLAZE_ROD, "&Gearfun", "", "&a> Click to open");

        // Give your Category a unique id.
        NamespacedKey itemGroupId = new NamespacedKey(this, "addon_category");
        ItemGroup itemGroup = new ItemGroup(itemGroupId, itemGroupItem);

        /*
         * 2. Create a new SlimefunItemStack
         * This class has many constructors, it is very important
         * that you give each item a unique id.
         */
        SlimefunItemStack blazed_ingot = new SlimefunItemStack("BLAZED INGOT", Material.GOLD_INGOT, "&6Blazed ingot", "An ingot forged in the pits of hell");
        SlimefunItemStack blazing_forge = new SlimefunItemStack("BLAZING FORGE", Material.GOLD_BLOCK, "&6Blazing forge", "An advanced forge");
        SlimefunItemStack blaze_sword = new SlimefunItemStack("BLAZE SWORD", Material.BLAZE_ROD, "&4Blaze sword", "A sword forged in the pits of hell");

        /*
         * 3. Creating a Recipe
         * The Recipe is an ItemStack Array with a length of 9.
         * It represents a Shaped Recipe in a 3x3 crafting grid.
         * The machine in which this recipe is crafted in is specified
         * further down as the RecipeType.
         */
        ItemStack[] blazed_ingot_recipe = {null, null, null, null, new ItemStack(Material.BLAZE_ROD), null, null, null, null};
        
        ItemStack[] blaze_sword_recipe = { 
        		null, 								SlimefunItems.REINFORCED_ALLOY_INGOT,  null,
        		null, 								SlimefunItems.REINFORCED_ALLOY_INGOT,  null,
        		null, 								null, 							  null };

        /*
         * 4. Registering the Item
         * Now you just have to register the item.
         * RecipeType.ENHANCED_CRAFTING_TABLE refers to the machine in
         * which this item is crafted in.
         * Recipe Types from Slimefun itself will automatically add the recipe to that machine.
         */
        
        BlazingForge blazing_forge_machine = new BlazingForge(itemGroup, blazing_forge);
        blazing_forge_machine.register(this);
        
        blazed_ingot blazed_ingot_item = new blazed_ingot(itemGroup, blazed_ingot, RecipeType.SMELTERY, blazed_ingot_recipe);
        blazed_ingot_item.register(this);

    	
        
        SlimefunItem blaze_sword_item = new SlimefunItem(itemGroup, blaze_sword, RecipeType.ENHANCED_CRAFTING_TABLE, blaze_sword_recipe);
        blaze_sword_item.register(this);
    }

    
    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * You will need to return a reference to your Plugin here.
         * If you are using your main class for this, simply return "this".
         */
        return this;
    }

}



