package com.carcass.client;

import com.carcass.entity.CorpseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Spider;

public class CorpseRenderer extends EntityRenderer<CorpseEntity> {
    public CorpseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(CorpseEntity corpse, float yaw, float partialTick, PoseStack pose,
                       MultiBufferSource buffer, int light) {
        LivingEntity mob = corpse.getRenderEntity();
        if (mob == null) return;

        mob.setPos(corpse.getX(), corpse.getY(), corpse.getZ());
        mob.setYRot(180); mob.yRotO = 180;
        mob.yBodyRot = 180; mob.yBodyRotO = 180;
        mob.yHeadRot = 180; mob.yHeadRotO = 180;
        mob.setXRot(0); mob.xRotO = 0;
        // 0, щоб не було червоного ефекту смерті/удару
        mob.deathTime = 0;
        mob.hurtTime = 0;

        pose.pushPose();
        // Повертаємо моба самі: напрямок, потім кладемо на бік
        pose.mulPose(Axis.YP.rotationDegrees(180.0F - corpse.getYRot()));
        pose.mulPose(Axis.ZP.rotationDegrees(mob instanceof Spider ? 180.0F : 90.0F));
        Minecraft.getInstance().getEntityRenderDispatcher()
                .render(mob, 0, 0, 0, 0, partialTick, pose, buffer, light);
        pose.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(CorpseEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
