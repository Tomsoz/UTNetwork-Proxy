package tk.utnetwork.utnetworkproxy.Commands.General;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.Date;

public class Report extends Command {
    UTNetworkProxy plugin;
    public Report(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Utils.chat("%tThis is a player command."));
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (!(args.length >= 2)) {
            sender.sendMessage(Utils.chat("%tUsage: /report <player> <reason>"));
            return;
        }
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.chat("%tPlayer " + args[0] + " is not online."));
            return;
        }

        String reason = String.join(" ", Utils.removeFirstElement(args));

        Utils.sendStaffMessage("&b" + p.getDisplayName() + " &3has reported &b" + target.getDisplayName() + " &3for &b" + reason + "&3.");
        sender.sendMessage(Utils.chat("%pYour report has been submitted for review by our staff team."));

        int index = plugin.getConfigManager().getReports().getInt("index") + 1;

        plugin.getConfigManager().getReports().set(index + ".executor", p.getUniqueId().toString());
        plugin.getConfigManager().getReports().set(index + ".target", target.getUniqueId().toString());
        plugin.getConfigManager().getReports().set(index + ".reason", reason);
        plugin.getConfigManager().getReports().set(index + ".time", new Date().getTime());
        plugin.getConfigManager().getReports().set("index", index);
        plugin.getConfigManager().saveReports();
    }
}
