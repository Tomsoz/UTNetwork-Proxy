package tk.utnetwork.utnetworkproxy.Commands.Administraton;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class AlertRaw extends Command {
    UTNetworkProxy plugin;
    public AlertRaw(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.chat("%tUsage: /alertraw <message>"));
            return;
        }

        String message = String.join(" ", args);
        plugin.getUtils().broadcast(message, false);
    }
}
