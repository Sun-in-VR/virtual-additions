package com.github.suninvr.virtualadditions.mixin;

import com.github.suninvr.virtualadditions.interfaces.ExperienceDroppingBlockInterface;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DropExperienceBlock.class)
public class ExperienceDroppingBlockMixin extends Block implements ExperienceDroppingBlockInterface {

    @Shadow @Final private IntProvider xpRange;

    public ExperienceDroppingBlockMixin(Properties settings) {
        super(settings);
    }

    public IntProvider virtualAdditions$getExperienceDropped() {
        return this.xpRange;
    }
}
