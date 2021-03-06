package discordbot.command.creator;

import com.google.common.base.Joiner;
import discordbot.core.AbstractCommand;
import discordbot.handler.Template;
import discordbot.main.DiscordBot;
import discordbot.permission.SimpleRank;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;
import java.io.IOException;

/**
 */
public class SendFileCommand extends AbstractCommand {
	public SendFileCommand() {
		super();
	}

	@Override
	public String getDescription() {
		return "executes commandline stuff";
	}

	@Override
	public String getCommand() {
		return "sendfile";
	}

	@Override
	public boolean isListed() {
		return true;
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
		SimpleRank rank = bot.security.getSimpleRank(author);
		if (!rank.isAtLeast(SimpleRank.CREATOR)) {
			return Template.get(channel, "command_no_permission");
		}
		if (args.length == 0) {
			return ":face_palm: I expected you to know how to use it";
		}
		File f = new File(Joiner.on("").join(args));
		if (f.exists()) {
			try {
				channel.sendFile(f, null).queue();
			} catch (IOException e) {
				e.printStackTrace();
				return "can't for some reason; " + e.getMessage();
			}
			return "";
		}
		return "File doesn't exist";
	}
}