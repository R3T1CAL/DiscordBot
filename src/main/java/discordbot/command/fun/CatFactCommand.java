package discordbot.command.fun;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import discordbot.core.AbstractCommand;
import discordbot.handler.Template;
import discordbot.main.DiscordBot;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * !catfact
 * gives you a random cat fact
 */
public class CatFactCommand extends AbstractCommand {
	public CatFactCommand() {
		super();
	}

	public static String getCatFact() {
		try {
			URL loginurl = new URL("http://catfacts-api.appspot.com/api/facts");
			URLConnection yc = loginurl.openConnection();
			yc.setConnectTimeout(10 * 1000);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							yc.getInputStream()));
			String inputLine = in.readLine();
			JsonParser parser = new JsonParser();
			JsonObject array = parser.parse(inputLine).getAsJsonObject();
			return ":cat:  " + array.get("facts").getAsString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public String getDescription() {
		return "Cat facts!";
	}

	@Override
	public String getCommand() {
		return "catfact";
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
		String catFact = getCatFact();
		if (catFact != null) {
			return StringEscapeUtils.unescapeHtml4(catFact);
		}
		return Template.get("command_catfact_not_today");
	}
}
