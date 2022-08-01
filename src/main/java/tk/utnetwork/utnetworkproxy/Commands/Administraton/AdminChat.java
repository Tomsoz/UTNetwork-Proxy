package tk.utnetwork.utnetworkproxy.Commands.Administraton;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class AdminChat extends Command {
    UTNetworkProxy plugin;
    public AdminChat(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(Utils.chat("%tUsage: /adminchat <message>"));
                return;
            }
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (Utils.toggleAdminChat(p)) {
                p.sendMessage(Utils.chat("%pYou've successfully %senabled%p adminchat."));
            } else {
                p.sendMessage(Utils.chat("%pYou've successfully %sdisabled%p adminchat."));
            }
        } else {
            Utils.sendAdminChat((sender instanceof ProxiedPlayer ? ((ProxiedPlayer)sender).getDisplayName() : plugin.getConfigManager().getConfig().getString("console_displayname") == null ? "&c&lConsole" : plugin.getConfigManager().getConfig().getString("console_displayname")), String.join(" ", args));
        }
    }
}
