package xyz.ipiepiepie.whitelist.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.command.commands.CommandWhitelist;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.ipiepiepie.whitelist.config.Data;

import java.util.UUID;

@Mixin(value = CommandWhitelist.class, remap = false)
public class WhitelistEditMixin {

	// ADD NICKNAME TO WHITELIST //

	@Inject(method = "lambda$register$8(Lcom/mojang/brigadier/context/CommandContext;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;addToWhiteList(Ljava/util/UUID;)V"))
	private static void btawhitelist_addPlayerToWhitelist(CommandContext<Object> c, CallbackInfoReturnable<Integer> cir, @Local(ordinal = 0) String nameToAdd) {
		Data.whitelist.add(nameToAdd);
	}

	@Inject(method = "lambda$register$6(Lnet/minecraft/server/MinecraftServer;Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;Ljava/util/UUID;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;addToWhiteList(Ljava/util/UUID;)V"))
	private static void btawhitelist_addOfflinePlayerToWhitelist(MinecraftServer server, CommandContext<Object> c, String nameToAdd, UUID uuid, CallbackInfo ci) {
		Data.whitelist.add(nameToAdd);
	}

	@Inject(method = "lambda$register$7(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)V", at = @At(value = "HEAD"), cancellable = true)
	private static void btawhitelist_addNotFoundOfflinePlayerToWhitelist(CommandContext<Object> c, String username, CallbackInfo ci) {
		Data.whitelist.add(username);
		// cancel sending error message
		ci.cancel();
	}

	// REMOVE NICKNAME FROM WHITELIST //

	@Inject(method = "lambda$register$11(Lcom/mojang/brigadier/context/CommandContext;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;removeFromWhiteList(Ljava/util/UUID;)V"))
	private static void btawhitelist_removePlayerFromWhitelist(CommandContext<Object> c, CallbackInfoReturnable<Integer> cir, @Local(ordinal = 0) String nameToAdd) {
		Data.whitelist.remove(nameToAdd);
	}

	@Inject(method = "lambda$register$9(Lnet/minecraft/server/MinecraftServer;Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;Ljava/util/UUID;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;removeFromWhiteList(Ljava/util/UUID;)V"))
	private static void btawhitelist_removeOfflinePlayerFromWhitelist(MinecraftServer server, CommandContext<Object> c, String nameToAdd, UUID uuid, CallbackInfo ci) {
		Data.whitelist.remove(nameToAdd);
	}

	@Inject(method = "lambda$register$10(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)V", at = @At(value = "HEAD"), cancellable = true)
	private static void btawhitelist_removeNotFoundOfflinePlayerFromWhitelist(CommandContext<Object> c, String username, CallbackInfo ci) {
		Data.whitelist.remove(username);
		// cancel sending error message
		ci.cancel();
	}



}
