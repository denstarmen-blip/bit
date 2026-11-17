package com.carcass.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModTiers implements Tier {
    FLINT(0, 60, 2.0F, 0.0F, 5, () -> Ingredient.of(Items.FLINT));

    private final int level, uses, enchant;
    private final float speed, damage;
    private final Supplier<Ingredient> repair;

    ModTiers(int level, int uses, float speed, float damage, int enchant, Supplier<Ingredient> repair) {
        this.level = level; this.uses = uses; this.speed = speed;
        this.damage = damage; this.enchant = enchant; this.repair = repair;
    }

    @Override public int getUses() { return uses; }
    @Override public float getSpeed() { return speed; }
    @Override public float getAttackDamageBonus() { return damage; }
    @Override public int getLevel() { return level; }
    @Override public int getEnchantmentValue() { return enchant; }
    @Override public Ingredient getRepairIngredient() { return repair.get(); }
}
