package com.carcass.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;

import java.util.List;

public class KnifeItem extends SwordItem {
    private final int cutCooldown;
    private final float bonusChance;

    public KnifeItem(Tier tier, int cutCooldown, float bonusChance, Properties props) {
        super(tier, 0, -1.8F, props);
        this.cutCooldown = cutCooldown;
        this.bonusChance = bonusChance;
    }

    public int getCutCooldown() { return cutCooldown; }
    public float getBonusChance() { return bonusChance; }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.carcass.bonus", Math.round(bonusChance * 100)).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.carcass.speed", String.format("%.1f", cutCooldown / 20.0)).withStyle(ChatFormatting.GRAY));
    }
}
