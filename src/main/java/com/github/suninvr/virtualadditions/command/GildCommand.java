package com.github.suninvr.virtualadditions.command;

import com.github.suninvr.virtualadditions.item.gild.GildType;
import com.github.suninvr.virtualadditions.registry.VADataComponentTypes;
import com.github.suninvr.virtualadditions.registry.VAItemTags;
import com.github.suninvr.virtualadditions.registry.VARegistries;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Objects;

public class GildCommand {
    private static final DynamicCommandExceptionType FAILED_ENTITY_EXCEPTION = new DynamicCommandExceptionType(
            entityName -> Text.stringifiedTranslatable("commands.enchant.failed.entity", entityName)
    );
    private static final DynamicCommandExceptionType FAILED_ITEMLESS_EXCEPTION = new DynamicCommandExceptionType(
            entityName -> Text.stringifiedTranslatable("commands.enchant.failed.itemless", entityName)
    );
    private static final DynamicCommandExceptionType FAILED_INCOMPATIBLE_EXCEPTION = new DynamicCommandExceptionType(
            itemName -> Text.stringifiedTranslatable("commands.virtual_additions.gild.failed.incompatible", itemName)
    );

    public static void register() {
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) ->
                commandDispatcher.register(CommandManager.literal("gild")
                        .requires(CommandManager.requirePermissionLevel(CommandManager.GAMEMASTERS_CHECK)).then(
                                CommandManager.argument("target", EntityArgumentType.entities()).then(
                                        CommandManager.argument("type", RegistryEntryReferenceArgumentType.registryEntry(commandRegistryAccess, VARegistries.GILD_TYPE_REGISTRY_KEY))
                                                .executes(context -> applyGild(context, false))
                                                .then(CommandManager.literal("force")
                                                        .executes(context -> applyGild(context, true))
                                                )
                                )))

        );
    }

    private static int applyGild(CommandContext<ServerCommandSource> context, boolean force) throws CommandSyntaxException {
        Entity entity = EntityArgumentType.getEntity(context, "target");
        if (!(entity instanceof LivingEntity)) {
            throw FAILED_ENTITY_EXCEPTION.create(entity.getName().getString());
        }
        ItemStack stack = entity.getWeaponStack();
        GildType type = RegistryEntryReferenceArgumentType.getRegistryEntry(context, "type", VARegistries.GILD_TYPE_REGISTRY_KEY).value();
        if (stack == null || stack.isEmpty()) {
            throw FAILED_ITEMLESS_EXCEPTION.create(entity.getName().getString());
        }
        if (stack.contains(VADataComponentTypes.GILD_TYPE) || Objects.isNull(type) || (!stack.isIn(VAItemTags.ACCEPTS_TOOL_GILDS)) && !force) {
            throw FAILED_INCOMPATIBLE_EXCEPTION.create(stack.getName());
        }
        stack.set(VADataComponentTypes.GILD_TYPE, type);
        type.modifyStackOnCrafted(stack);
        context.getSource().sendFeedback(() -> Text.translatable("commands.virtual_additions.gild.success", type.getTranslationKey().getString(), entity.getName()), false);
        return 1;
    }
}
