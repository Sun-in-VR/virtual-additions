package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.ExperienceDroppingBlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.util.math.intprovider.IntProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ExperienceDroppingBlock.class)
public class ExperienceDroppingBlockMixin extends Block implements ExperienceDroppingBlockInterface {

    @Shadow @Final private IntProvider experienceDropped;

    public ExperienceDroppingBlockMixin(Settings settings) {
        super(settings);
    }

    public IntProvider getExperienceDropped() {
        return this.experienceDropped;
    }
}
