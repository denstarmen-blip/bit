package com.carcass.client;

import com.carcass.entity.CorpseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CorpseRenderer extends EntityRenderer<CorpseEntity> {
    public CorpseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(CorpseEntity corpse, float yaw, float partialTick, PoseStack pose,
                       MultiBufferSource buffer, int light) {
        LivingEntity mob = corpse.getRenderEntity();
        if (mob == null) return;

        float rot = corpse.getYRot();
        mob.setPos(corpse.getX(), corpse.getY(), corpse.getZ());
        mob.setYRot(rot); mob.yRotO = rot;
        mob.yBodyRot = rot; mob.yBodyRotO = rot;
        mob.yHeadRot = rot; mob.yHeadRotO = rot;
        mob.setXRot(0); mob.xRotO = 0;
        mob.deathTime = 21;

        Minecraft.getInstance().getEntityRenderDispatcher()
                .render(mob, 0, 0, 0, rot, partialTick, pose, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(CorpseEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
