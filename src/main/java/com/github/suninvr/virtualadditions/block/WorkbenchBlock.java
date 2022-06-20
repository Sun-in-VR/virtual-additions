package com.github.suninvr.virtualadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import com.github.suninvr.virtualadditions.recipe.WorkbenchSingleRecipe;
import com.github.suninvr.virtualadditions.screenhandler.WorkbenchScreenHandler;

import java.util.Optional;

public class WorkbenchBlock extends Block {
    private static final Text TITLE = Text.translatable("container.workbench");

    public WorkbenchBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {


        if(world.isClient){
            return ActionResult.SUCCESS;
        } else {
            //Legacy Mode
            SimpleInventory inventory = new SimpleInventory(player.getMainHandStack(), player.getOffHandStack(), ItemStack.EMPTY);

            Optional<WorkbenchSingleRecipe> match = world.getRecipeManager()
                    .getFirstMatch(WorkbenchSingleRecipe.Type.INSTANCE, inventory, world);

            if (match.isPresent()) {
                player.getInventory().offerOrDrop(match.get().getOutput().copy());
                player.getMainHandStack().damage(match.get().getDamage(), player, (p) -> {
                    p.sendToolBreakStatus(hand);
                });
                player.getOffHandStack().decrement(1);

                Identifier sound = match.get().getSound();
                if(Registry.SOUND_EVENT.containsId(sound)) {
                    world.playSound(null, pos, Registry.SOUND_EVENT.get(sound), SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                player.unlockRecipes(new Identifier[] {match.get().getId()});
                return ActionResult.SUCCESS;
            } else
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            return ActionResult.CONSUME;
        }

    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
                new WorkbenchScreenHandler( syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE);
    }
}
