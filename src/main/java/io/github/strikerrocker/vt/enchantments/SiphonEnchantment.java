package io.github.strikerrocker.vt.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.stream.Collectors;

public class SiphonEnchantment extends Enchantment {
    SiphonEnchantment(String name) {
        super(Rarity.UNCOMMON, EnchantmentType.DIGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void harvestDropEvent(BlockEvent.HarvestDropsEvent event) {
        if (EnchantmentFeature.enableSiphon.get() && event.getHarvester() != null && EnchantmentHelper.getEnchantmentLevel(this, event.getHarvester().getHeldItemMainhand()) > 0) {
            event.getDrops().removeAll(event.getDrops().stream().filter(event.getHarvester().inventory::addItemStackToInventory).collect(Collectors.toList()));
        }
        //TODO Update when https://github.com/MinecraftForge/MinecraftForge/pull/5871 is merged
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 61;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableSiphon.get() ? 1 : 0;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ToolItem && EnchantmentFeature.enableSiphon.get();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return EnchantmentFeature.enableSiphon.get();
    }
}
