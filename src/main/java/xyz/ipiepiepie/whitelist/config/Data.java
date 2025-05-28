package xyz.ipiepiepie.whitelist.config;

import com.b100.utils.FileUtils;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.fabricmc.loader.api.FabricLoader;
import xyz.ipiepiepie.whitelist.WhitelistMod;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Data {
	public static OfflineWhitelist whitelist;
	// path //
	private static final String whitelistDir = FabricLoader.getInstance().getConfigDir() + "/" + WhitelistMod.MOD_ID;
	// gson //
	public static Gson gson = new GsonBuilder()
		.registerTypeAdapter(OfflineWhitelist.class, new OfflineWhitelistAdapter())
		.create();

	/*=========================================* I/O METHODS *========================================*/

	public static void load() {
		File child = new File(whitelistDir, "whitelist.json");

		// set whitelist variable
		try {
			whitelist = gson.fromJson(new JsonReader(new FileReader(child)), OfflineWhitelist.class);
		} catch (FileNotFoundException e) {
			whitelist = new OfflineWhitelist();
		}
	}

	public static void save() {
		File file = FileUtils.createNewFile(new File(whitelistDir, "whitelist.json"));

		// save whitelist
		try (FileWriter writer = new FileWriter(file)) {
			gson.toJson(whitelist, OfflineWhitelist.class, writer);
		} catch (IOException e) {
			WhitelistMod.LOGGER.error("Whitelist failed to save!");
		}
	}

	/*======================================* OFFLINE WHITELIST *=====================================*/

	public static class OfflineWhitelist {
		private final List<String> names = new ArrayList<>();

		/**
		 * Constructor to create OfflineWhitelist
		 */
		public OfflineWhitelist() {}

		/**
		 * Constructor to load OfflineWhitelist
		 */
		public OfflineWhitelist(List<String> names) {
			this.names.addAll(names);
		}

		// SETTERS //

		/**
		 * Add nickname to whitelist.
		 *
		 * @param name nickname to add
		 */
		public void add(String name) {
			this.names.add(name);
			Data.save();
		}

		/**
		 * Remove nickname from whitelist.
		 *
		 * @param name nickname to add
		 */
		public void remove(String name) {
			this.names.remove(name);
			Data.save();
		}

		// GETTERS //

		/**
		 * Check if nickname persists in whitelist.
		 *
		 * @param name name to check
		 * @return {@code true} if name whitelisted, otherwise {@code false}
		 */
		public boolean contains(String name) {
			return names.contains(name);
		}

		/**
		 * Get names from whitelist.
		 *
		 * @return {@link List} of whitelisted names
		 */
		public List<String> getNames() {
			return names;
		}
	}

	/*==================================* OFFLINE WHITELIST ADAPTER *=================================*/

	private static class OfflineWhitelistAdapter implements JsonSerializer<OfflineWhitelist>, JsonDeserializer<OfflineWhitelist> {

		@Override
		public OfflineWhitelist deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject obj = json.getAsJsonObject();

			return new OfflineWhitelist(obj.get("whitelist")
				.getAsJsonArray()
				.asList()
				.stream()
				.map(JsonElement::getAsString)
				.collect(Collectors.toList())
			);
		}

		@Override
		public JsonElement serialize(OfflineWhitelist src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject obj = new JsonObject();

			// write whitelisted players to array
			JsonArray players = new JsonArray();
			for (String nick : src.getNames())
				players.add(nick);

			obj.add("whitelist", players);

			return obj;
		}

	}

}
