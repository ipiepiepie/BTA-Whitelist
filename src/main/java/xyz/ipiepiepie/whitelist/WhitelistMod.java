package xyz.ipiepiepie.whitelist;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ipiepiepie.whitelist.config.Data;

public class WhitelistMod implements ModInitializer {
	public static final String MOD_ID = "bta-whitelist";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// load whitelist
		Data.load();

		LOGGER.info("BTA Whitelist initialized.");
	}


}
