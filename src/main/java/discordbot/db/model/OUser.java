package discordbot.db.model;

import discordbot.db.AbstractModel;

import java.util.EnumSet;

public class OUser extends AbstractModel {
	public int id;
	public String discord_id;
	public String name;
	public int commandsUsed;
	public int banned;

	private int permissionTotal;
	private EnumSet<PermissionNode> nodes;

	public OUser() {
		discord_id = "";
		id = 0;
		name = "";
		commandsUsed = 0;
		banned = 0;
		nodes = EnumSet.noneOf(PermissionNode.class);
		permissionTotal = 0;
	}

	public boolean hasPermission(PermissionNode node) {
		return nodes.contains(node);
	}

	public int getEncodedPermissions() {
		return permissionTotal;
	}

	public EnumSet<PermissionNode> getPermission() {
		return nodes;
	}

	public void setPermission(int total) {
		nodes = decode(total);
		permissionTotal = total;
	}

	public boolean addPermission(PermissionNode node) {
		if (nodes.contains(node)) {
			return false;
		}
		nodes.add(node);
		permissionTotal = encode();
		return true;
	}

	public boolean removePermission(PermissionNode node) {
		if (!nodes.contains(node)) {
			return false;
		}
		nodes.remove(node);
		permissionTotal = encode();
		return true;
	}

	private EnumSet<PermissionNode> decode(int code) {
		PermissionNode[] values = PermissionNode.values();
		EnumSet<PermissionNode> result = EnumSet.noneOf(PermissionNode.class);
		while (code != 0) {
			int ordinal = Integer.numberOfTrailingZeros(code);
			code ^= Integer.lowestOneBit(code);
			result.add(values[ordinal]);
		}
		return result;
	}

	private int encode() {
		int ret = 0;
		for (PermissionNode val : nodes) {
			ret |= 1 << val.ordinal();
		}
		return ret;
	}

	public enum PermissionNode {
		IMPORT_PLAYLIST("use youtube playlists"),
		BAN_TRACKS("ban tracks from the global playlist");
		private final String description;

		PermissionNode(String description) {

			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
}
