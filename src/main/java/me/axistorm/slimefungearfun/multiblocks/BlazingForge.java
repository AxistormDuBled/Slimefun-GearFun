<<<<<<< HEAD
package me.axistorm.slimefungearfun.multiblocks;

import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import java.util.Objects;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlazingForge extends MultiBlockMachine {
	
	public BlazingForge(ItemGroup category, SlimefunItemStack item) {
		super(category, item, new ItemStack[] {
				null, null, null,
				null, null, null,
				null, null, null
				}, BlockFace.DOWN);
	}
	
	@Override
	public void onInteract(Player p, Block b) {
		
	}
	
}
=======

>>>>>>> origin/master
