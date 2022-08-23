package com.commitorquit.clanrecruitment;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DiscordClanRecruitmentPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DiscordClanRecruitmentPlugin.class);
		RuneLite.main(args);
	}
}