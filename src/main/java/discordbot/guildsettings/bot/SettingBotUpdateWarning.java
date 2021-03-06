package discordbot.guildsettings.bot;

import discordbot.guildsettings.AbstractGuildSetting;
import discordbot.guildsettings.types.EnumSettingType;


public class SettingBotUpdateWarning extends AbstractGuildSetting<EnumSettingType> {

	@Override
	protected EnumSettingType getSettingsType() {
		return new EnumSettingType("always", "playing", "off");
	}


	@Override
	public String getKey() {
		return "bot_update_warning";
	}

	@Override
	public String[] getTags() {
		return new String[]{"bot", "update", "warn"};
	}

	@Override
	public String getDefault() {
		return "playing";
	}

	@Override
	public String[] getDescription() {
		return new String[]{
				"Show a warning that there is an update and that the bot will be updating soon.",
				"always  -> always show the message in the bot's configured default channel",
				"playing -> only announce when the bot is playing music and in the bot's configured music channel",
				"off     -> don't announce when the bot is going down for an update"
		};
	}
}
