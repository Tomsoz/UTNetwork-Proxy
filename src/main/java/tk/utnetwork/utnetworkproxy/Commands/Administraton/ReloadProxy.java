package tk.utnetwork.utnetworkproxy.Commands.Administraton;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class ReloadProxy extends Command {
    UTNetworkProxy plugin;
    public ReloadProxy(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(Utils.chat("%pReloading all config files."));
        plugin.getConfigManager().reloadConfig();
        sender.sendMessage(Utils.chat("%pReloaded %sconfig.yml%p."));
        plugin.getConfigManager().reloadData();
        sender.sendMessage(Utils.chat("%pReloaded %sdata.yml%p."));
        plugin.getConfigManager().reloadReports();
        sender.sendMessage(Utils.chat("%pReloaded %sreports.yml%p."));
        sender.sendMessage(Utils.chat("%pAll config files have been reloaded."));
    }
}
