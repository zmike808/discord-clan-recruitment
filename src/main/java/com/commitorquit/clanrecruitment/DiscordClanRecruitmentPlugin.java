package com.commitorquit.clanrecruitment;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "discord clan recruitment"
)
public class DiscordClanRecruitmentPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private DiscordClanRecruitmentConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("discord clan recruitment started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("discord clan recruitment stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "clan recruitment plugin", "discord clan recruitment says " + config.greeting(), null);
		}
	}

	@Provides
	DiscordClanRecruitmentConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DiscordClanRecruitmentConfig.class);
	}
	@Subscribe
	public void onChatMessage(ChatMessage event) {
//		if (event.getType() != ChatMessageType.CLAN_MESSAGE || event.getType() != ChatMessageType.CLAN_CHAT) {
//			return;
//		}
	if (event.getType() != ChatMessageType.CLAN_MESSAGE) {
            return;
        }
		String msg = event.getMessage();
//		msg = Text.standardize(event.getMessage());//remove color
		msg = Text.removeTags(msg).replace('\u00A0', ' ').trim();
		String target_str = " has been invited into the clan by ";
		if (msg.contains(target_str)) {
			String tosplit = msg.replace(target_str, ":");
			log.info("tosplit: " + tosplit);
			String[] splitted = tosplit.split(":");
			String invited = Text.toJagexName(splitted[0]);
			String recruiter = Text.toJagexName(splitted[1]);

			log.info("recruitment message: " + msg);
			log.info("splitted: " + invited + " " + recruiter);

			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "clan recruitment plugin", msg + " event type: " + event.getType(), null);
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "clan recruitment plugin", "invited: " + invited + " recruiter " + recruiter, null);
		}
	}
}
