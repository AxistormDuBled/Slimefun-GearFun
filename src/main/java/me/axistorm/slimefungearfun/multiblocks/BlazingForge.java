package me.axistorm.slimefungearfun.multiblocks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.events.MultiBlockCraftEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.services.sounds.SoundEffect;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.backpacks.SlimefunBackpack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;

public class BlazingForge extends MultiBlockMachine {
	
	public BlazingForge(ItemGroup category, SlimefunItemStack item) {
		super(category, item, new ItemStack[] {
				null, null, null,
				null, new ItemStack(Material.GOLD_BLOCK), null,
				null, new ItemStack(Material.DISPENSER), null
				}, BlockFace.DOWN);
	}
	
	@Override
	public void onInteract(Player p, Block b) {
		Block possibleDispenser = b.getRelative(BlockFace.DOWN);
		BlockState state = possibleDispenser.getState();
		
		if (state instanceof Dispenser) {
			Dispenser dispenser = (Dispenser) state;
			Inventory inv = dispenser.getInventory();
			List<ItemStack[]> inputs = RecipeType.getRecipeInputList(this);
		
			 for (ItemStack[] input : inputs) {
	                if (isCraftable(inv, input)) {
	                    ItemStack output = RecipeType.getRecipeOutputList(this, input).clone();
	                    MultiBlockCraftEvent event = new MultiBlockCraftEvent(p, this, input, output);

	                    Bukkit.getPluginManager().callEvent(event);
	                    if (!event.isCancelled() && SlimefunUtils.canPlayerUseItem(p, output, true)) {
	                        craft(inv, possibleDispenser, p, b, event.getOutput());
	                    }

	                    return;
	                }
	            }
			
			if (inv.isEmpty()) {
				Slimefun.getLocalization().sendMessage(p, "machines.inventory-empty", true);
			} else {
                Slimefun.getLocalization().sendMessage(p, "machines.pattern-not-found", true);
            }
		}
		
	}


protected Inventory createVirtualInventory(Inventory inv) {
    Inventory fakeInv = Bukkit.createInventory(null, 9, "Fake Inventory");

    for (int j = 0; j < inv.getContents().length; j++) {
        ItemStack stack = inv.getContents()[j];

        /*
         * Fixes #2103 - Properly simulating the consumption
         * (which may leave behind empty buckets or glass bottles)
         */
        if (stack != null) {
            stack = stack.clone();
            ItemUtils.consumeItem(stack, true);
        }

        fakeInv.setItem(j, stack);
    }

    return fakeInv;
}

private void craft(Inventory inv, Block dispenser, Player p, Block b, ItemStack output) {
    Inventory fakeInv = createVirtualInventory(inv);
    Inventory outputInv = findOutputInventory(output, dispenser, inv, fakeInv);

    if (outputInv != null) {
        SlimefunItem sfItem = SlimefunItem.getByItem(output);


        for (int j = 0; j < 9; j++) {
            ItemStack item = inv.getContents()[j];

            if (item != null && item.getType() != Material.AIR) {
                ItemUtils.consumeItem(item, true);
            }
        }

        outputInv.addItem(output);

    } else {
        Slimefun.getLocalization().sendMessage(p, "machines.full-inventory", true);
    }
}

private boolean isCraftable(Inventory inv, ItemStack[] recipe) {
    for (int j = 0; j < inv.getContents().length; j++) {
        if (!SlimefunUtils.isItemSimilar(inv.getContents()[j], recipe[j], true, true, false)) {
            if (SlimefunItem.getByItem(recipe[j]) instanceof SlimefunBackpack) {
                if (!SlimefunUtils.isItemSimilar(inv.getContents()[j], recipe[j], false, true, false)) {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
    return true;
}
}