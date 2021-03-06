package discordbot.db.version;

import discordbot.db.IDbVersion;

/**
 * expanding on playlists
 */
public class db_25_to_26 implements IDbVersion {
	@Override
	public int getFromVersion() {
		return 25;
	}

	@Override
	public int getToVersion() {
		return 26;
	}

	@Override
	public String[] getExecutes() {
		return new String[]{
				"ALTER TABLE playlist ADD code VARCHAR(32) DEFAULT 'default' NOT NULL",
				"CREATE UNIQUE INDEX playlist_owner_id_guild_id_code_uindex ON playlist (owner_id, guild_id, code)",
				"CREATE INDEX playlist_owner_id_code_index ON playlist (owner_id, code)",
				"CREATE INDEX playlist_guild_id_code_index ON playlist (guild_id, code)"
		};
	}
}