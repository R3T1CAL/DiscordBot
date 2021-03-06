package discordbot.command;

import discordbot.permission.SimpleRank;
import discordbot.util.Emojibet;

public enum CommandCategory {
	CREATOR("creator", Emojibet.MAN_IN_SUIT, "Development", SimpleRank.CREATOR),
	BOT_ADMINISTRATION("bot_administration", Emojibet.MONKEY, "Bot administration", SimpleRank.BOT_ADMIN),
	ADMINISTRATIVE("administrative", Emojibet.POLICE, "Administration", SimpleRank.GUILD_ADMIN),
	INFORMATIVE("informative", Emojibet.INFORMATION, "Information"),
	MUSIC("music", Emojibet.MUSIC_NOTE, "Music"),
	ECONOMY("economy", Emojibet.MONEY_BAG, "Money"),
	FUN("fun", Emojibet.GAME_DICE, "Fun"),
	POE("poe", Emojibet.CURRENCY_EXCHANGE, "Path of exile"),
	HEARTHSTONE("hearthstone", Emojibet.SLOT_MACHINE, "Hearthstone"),
	ADVENTURE("adventure", Emojibet.FOOTPRINTS, "Adventure"),
	UNKNOWN("nopackage", Emojibet.QUESTION_MARK, "Misc");
	private final String packageName;
	private final String emoticon;
	private final String displayName;
	private final SimpleRank rankRequired;

	CommandCategory(String packageName, String emoticon, String displayName) {

		this.packageName = packageName;
		this.emoticon = emoticon;
		this.displayName = displayName;
		this.rankRequired = SimpleRank.USER;
	}

	public static CommandCategory getFirstWithPermission(SimpleRank rank) {
		if (rank == null) {
			return INFORMATIVE;
		}
		for (CommandCategory category : values()) {
			if (rank.isAtLeast(category.getRankRequired())) {
				return category;
			}
		}
		return INFORMATIVE;
	}

	CommandCategory(String packageName, String emoticon, String displayName, SimpleRank rankRequired) {

		this.packageName = packageName;
		this.emoticon = emoticon;
		this.displayName = displayName;
		this.rankRequired = rankRequired;
	}

	public static CommandCategory fromPackage(String packageName) {
		if (packageName != null) {
			for (CommandCategory cc : values()) {
				if (packageName.equalsIgnoreCase(cc.packageName)) {
					return cc;
				}
			}
		}
		return UNKNOWN;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getEmoticon() {
		return emoticon;
	}

	public SimpleRank getRankRequired() {
		return rankRequired;
	}
}