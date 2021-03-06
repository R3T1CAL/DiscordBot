package discordbot.command.fun;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import discordbot.core.AbstractCommand;
import discordbot.handler.CommandHandler;
import discordbot.handler.Template;
import discordbot.main.DiscordBot;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

/**
 * !joke
 * gives you a random chuck norris joke with chuck norris replaced by <@user>
 */
public class JokeCommand extends AbstractCommand {
	public JokeCommand() {
		super();
	}

	@Override
	public String getDescription() {
		return "An attempt to be funny";
	}

	@Override
	public String getCommand() {
		return "joke";
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
		bot.out.sendAsyncMessage(channel, Template.get("command_joke_wait"), message -> {
			String joketxt = "";
			if (new Random().nextInt(100) < 80) {
				joketxt = CommandHandler.getCommand("reddit").execute(bot, new String[]{"jokes"}, channel, author);
			} else {
				try {
					joketxt = getJokeFromWeb(URLEncoder.encode(author.getName(), "UTF-8"));
				} catch (UnsupportedEncodingException ignored) {
				}
			}
			if (joketxt != null && !joketxt.isEmpty()) {
				bot.out.editAsync(message, StringEscapeUtils.unescapeHtml4(joketxt.replace(author.getName(), "<@" + author.getId() + ">")));
			} else {
				bot.out.editAsync(message, Template.get("command_joke_not_today"));
			}
		});
		return "";
	}

	private String getJokeFromWeb(String username) {
		try {
			URL loginurl = new URL("http://api.icndb.com/jokes/random?firstName=&lastName=" + username);
			URLConnection yc = loginurl.openConnection();
			yc.setConnectTimeout(10 * 1000);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							yc.getInputStream()));
			String inputLine = in.readLine();
			JsonParser parser = new JsonParser();
			JsonObject array = parser.parse(inputLine).getAsJsonObject();
			return array.get("value").getAsJsonObject().get("joke").getAsString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}