package com.github.suninvr.virtualadditions.command;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VARegistries;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class GildCommand {
    private static final DynamicCommandExceptionType FAILED_ENTITY_EXCEPTION = new DynamicCommandExceptionType(
            entityName -> Component.translatableEscape("commands.enchant.failed.entity", entityName)
    );
    private static final DynamicCommandExceptionType FAILED_ITEMLESS_EXCEPTION = new DynamicCommandExceptionType(
            entityName -> Component.translatableEscape("commands.enchant.failed.itemless", entityName)
    );
    private static final DynamicCommandExceptionType FAILED_INCOMPATIBLE_EXCEPTION = new DynamicCommandExceptionType(
            itemName -> Component.translatableEscape("commands.virtual_additions.gild.failed.incompatible", itemName)
    );

    public static void register() {
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) ->
                commandDispatcher.register(Commands.literal("gild")
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS)).then(
                                Commands.argument("target", EntityArgument.entities()).then(
                                        Commands.argument("type", ResourceArgument.resource(commandRegistryAccess, VARegistries.GILD_TYPE_REGISTRY_KEY))
                                                .executes(context -> applyGild(context, false))
                                                .then(Commands.literal("force")
                                                        .executes(context -> applyGild(context, true))
                                                )
                                )))

        );
    }

    private static int applyGild(CommandContext<CommandSourceStack> context, boolean force) throws CommandSyntaxException {
        Entity entity = EntityArgument.getEntity(context, "target");
        if (!(entity instanceof LivingEntity)) {
            throw FAILED_ENTITY_EXCEPTION.create(entity.getName().getString());
        }
        ItemStack stack = entity.getWeaponItem();
        GildType type = ResourceArgument.getResource(context, "type", VARegistries.GILD_TYPE_REGISTRY_KEY).value();
        if (stack == null || stack.isEmpty()) {
            throw FAILED_ITEMLESS_EXCEPTION.create(entity.getName().getString());
        }
        if (stack.has(VADataComponentTypes.GILD_TYPE) || Objects.isNull(type) || (!stack.is(VAItemTags.ACCEPTS_TOOL_GILDS)) && !force) {
            throw FAILED_INCOMPATIBLE_EXCEPTION.create(stack.getHoverName());
        }
        stack.set(VADataComponentTypes.GILD_TYPE, force ? type : type.getOrAlternate(stack));
        ((LivingEntity) entity).setItemSlot(EquipmentSlot.MAINHAND, stack.transmuteCopy(stack.getItem()));
        context.getSource().sendSuccess(() -> Component.translatable("commands.virtual_additions.gild.success", type.getTranslationKey().getString(), entity.getName()), false);
        return 1;
    }
}
