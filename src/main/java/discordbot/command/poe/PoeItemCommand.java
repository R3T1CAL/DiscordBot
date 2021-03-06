package discordbot.command.poe;

import com.google.common.base.Joiner;
import discordbot.core.AbstractCommand;
import discordbot.main.DiscordBot;
import discordbot.modules.pathofexile.ItemAnalyzer;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * !poeitem
 * Analyzes an item from path of exile
 */
public class PoeItemCommand extends AbstractCommand {
	public PoeItemCommand() {
		super();
	}

	@Override
	public String getDescription() {
		return "Analyzes an item from path of exile.";
	}

	@Override
	public String getCommand() {
		return "poeitem";
	}

	@Override
	public String[] getUsage() {
		return new String[]{};
	}

	@Override
	public String[] getAliases() {
		return new String[]{};
	}

	@Override
	public String execute(DiscordBot bot, String[] args, MessageChannel channel, User author) {
		String input = Joiner.on(" ").join(args);
		ItemAnalyzer itemAnalyzer = new ItemAnalyzer();
		return itemAnalyzer.attemptToANALyze(input).toString();

//		return TextHandler.get("command_not_implemented");
	}
}