package tk.utnetwork.utnetworkproxy.Commands.Staff;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class StaffChat extends Command {
    UTNetworkProxy plugin;
    public StaffChat(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (Utils.areStaffMessagesOff(p)) {
                p.sendMessage(Utils.chat("%tYou can't use staffchat as your staff messages are disabled."));
                return;
            }
        }
        if (args.length == 0) {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(Utils.chat("%tUsage: /staffchat <message>"));
                return;
            }
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (Utils.toggleStaffChat(p)) {
                p.sendMessage(Utils.chat("%pYou've successfully %senabled%p staffchat."));
            } else {
                p.sendMessage(Utils.chat("%pYou've successfully %sdisabled%p staffchat."));
            }
        } else {
            Utils.sendStaffChat((sender instanceof ProxiedPlayer ? ((ProxiedPlayer)sender).getDisplayName() : plugin.getConfigManager().getConfig().getString("console_displayname") == null ? "&c&lConsole" : plugin.getConfigManager().getConfig().getString("console_displayname")), String.join(" ", args));
        }
    }
}
