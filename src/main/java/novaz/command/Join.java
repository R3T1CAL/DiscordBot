package novaz.command;

import novaz.core.AbstractCommand;
import novaz.handler.TextHandler;
import novaz.main.NovaBot;
import novaz.util.Misc;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;

/**
 * !joinme
 * make the bot join the channel of the user
 */
public class Join extends AbstractCommand {
	public Join(NovaBot b) {
		super(b);
	}

	@Override
	public String getDescription() {
		return "joins a voicechannel";
	}

	@Override
	public String getCommand() {
		return "join";
	}

	@Override
	public String[] getUsage() {
		return new String[]{
				"join //attempts to join you",
				"join <channelname> //attempts to join channelname"
		};
	}

	@Override
	public String execute(String[] args, IChannel channel, IUser author) {
		if (args.length == 0) {
			IVoiceChannel voiceChannel = author.getConnectedVoiceChannels().get(0);
			if (voiceChannel == null) {
				return TextHandler.get("command_join_cantfindyou");
			}
			try {
				voiceChannel.join();
			} catch (MissingPermissionsException e) {
				return TextHandler.get("command_join_nopermssiontojoin");
			}
			return TextHandler.get("command_join_joinedyou");
		} else {
			String channelname = Misc.concat(args);
			IVoiceChannel unicornChannel = null;
			for (IVoiceChannel vc : channel.getGuild().getVoiceChannels()) {
				if (vc.getName().equalsIgnoreCase(channelname)) {
					unicornChannel = vc;
					break;
				}
			}
			if (unicornChannel != null) {
				try {
					unicornChannel.join();
				} catch (MissingPermissionsException e) {
					return TextHandler.get("command_join_nopermssiontojoin");
				}
				return TextHandler.get("command_join_joinedyou");
			}
			return TextHandler.get("command_join_cantfindchannel");
		}
	}
}