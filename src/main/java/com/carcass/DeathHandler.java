package com.carcass;

import com.carcass.entity.CorpseEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class DeathHandler {

    /** Коли моб помирає — замість викидання предметів лишаємо труп з ними всередині. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDrops(LivingDropsEvent event) {
        LivingEntity mob = event.getEntity();
        Level level = mob.level();
        if (level.isClientSide) return;
        if (mob instanceof Player || mob instanceof ArmorStand
                || mob instanceof EnderDragon || mob instanceof WitherBoss) return;
        if (event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) return;
        if (event.getDrops().isEmpty()) return;

        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(mob.getType());
        if (id == null) return;

        List<ItemStack> loot = new ArrayList<>();
        for (ItemEntity drop : event.getDrops()) {
            loot.add(drop.getItem().copy());
        }

        // NBT моба потрібен лише для вигляду трупа (колір вівці, варіант кота, броня...)
        CompoundTag nbt = new CompoundTag();
        try {
            mob.saveWithoutId(nbt);
            for (String k : new String[]{"Brain", "Passengers", "UUID", "Leash", "Leashed", "Attributes", "ActiveEffects"}) {
                nbt.remove(k);
            }
        } catch (Exception ex) {
            nbt = new CompoundTag();
        }

        event.setCanceled(true);

        CorpseEntity corpse = new CorpseEntity(Carcass.CORPSE.get(), level);
        corpse.setup(id, nbt, loot);
        corpse.moveTo(mob.getX(), mob.getY(), mob.getZ(), mob.getYRot(), 0.0F);
        level.addFreshEntity(corpse);
    }
}
