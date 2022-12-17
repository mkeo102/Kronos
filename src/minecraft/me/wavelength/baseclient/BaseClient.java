package me.wavelength.baseclient;

import java.io.File;

import org.lwjgl.opengl.Display;

import com.google.common.collect.Lists;

import me.wavelength.baseclient.account.AccountManager;
import me.wavelength.baseclient.command.CommandManager;
import me.wavelength.baseclient.event.EventManager;
import me.wavelength.baseclient.font.Font;
import me.wavelength.baseclient.friends.FriendsManager;
import me.wavelength.baseclient.gui.altmanager.GuiAltManager;
import me.wavelength.baseclient.gui.clickgui.ClickGui;
import me.wavelength.baseclient.irc.IRCClient;
import me.wavelength.baseclient.module.ModuleManager;
import me.wavelength.baseclient.overlay.HotbarOverlay;
import me.wavelength.baseclient.overlay.TabGui1;
import me.wavelength.baseclient.overlay.ToggledModules1;
import me.wavelength.baseclient.thealtening.AltService;
import me.wavelength.baseclient.utils.Config;
import me.wavelength.baseclient.utils.Files;
import me.wavelength.baseclient.utils.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Locale;

public class BaseClient {

	/**
	 * @formatter:off
	 * Credits
	 * 
	 * Fonts: Slick's font manager edited by Russian412 and color system by me
	 * Alt Manager: Russian412's Alt Manager with some small bug-fixes by me
	 * The Altening Implementation: Russian412
	 * 
	 * Everything else is made by me
	 * @formatter:on
	 **/

	private final String clientName = "Kronos";
	private final String clientVersion = "0.1";
	private final String author = "Mkeo102";

	public static BaseClient instance;

	private final String defaultUsername = "Mkeo102";

	private EventManager eventManager;

	private FriendsManager friendsManager;

	private CommandManager commandManager;
	private ModuleManager moduleManager;

	private IRCClient ircClient;

	private AccountManager accountManager;

	private AltService altService;

	private Font font;

	private String packageBase = "me.wavelength.baseclient";

	private boolean defaultHotbar = false;

	private Config genericConfig;

	private ClickGui clickGui;

	private Locale englishLocale;

	public BaseClient() {
		instance = this;
	}

	public void initialize() {
		Display.setTitle(String.format("%1$s - %2$s | Loading...", clientName, clientVersion));

		this.englishLocale = new Locale();

		this.ircClient = new IRCClient("chat.freenode.net", 6667, Minecraft.getMinecraft().getSession().getUsername(), "#WaveLengthBaseClient");

		new GuiAltManager(); // We create the instance.

		String clientFolder = new File(".").getAbsolutePath();

		clientFolder = (clientFolder.contains("jars") ? new File(".").getAbsolutePath().substring(0, clientFolder.length() - 2) : new File(".").getAbsolutePath()) + Strings.getSplitter() + clientName;

		String accountManagerFolder = clientFolder + Strings.getSplitter() + "alts";

		Files.createRecursiveFolder(accountManagerFolder);

		this.accountManager = new AccountManager(new File(accountManagerFolder));

		this.clickGui = new ClickGui();

		this.eventManager = new EventManager();

		this.friendsManager = new FriendsManager();

		this.moduleManager = new ModuleManager();
		this.commandManager = new CommandManager(".");

		commandManager.registerCommands(); // Moved here to make sure the CommandManager instance is created, else the
											// "commandManager" variable in the Command class would be null (since we are
											// getting the CommandManager instance from this class)

		this.altService = new AltService();

		switchToMojang();

		this.genericConfig = new Config(new File(clientFolder + Strings.getSplitter() + "config.cfg"));
		genericConfig.addDefault("tabguicolor", "5556190");

		/** Setting a custom icon */

		/** Both 16x16 and 32x32 version encoded in Base64 */
		String icon16x16 = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAABhGlDQ1BJQ0MgcHJvZmlsZQAAKJF9kTtIw1AUhv+mSkUiDnYQcchQnSyIFXGUKhbBQmkrtOpgctMXNGlIUlwcBdeCg4/FqoOLs64OroIg+ABxdHJSdJESz00KLWI8cLkf/z3/4ZxzAaFZZZrVMwloum2mE3Epl1+VQq8QEUAYQExmlpHMLGbhG1/3lEtxF+W1/PP+jAG1YDEgIBHPMcO0iTeIZzZtg/M+cZiVZZX4nHjCpAaJH7muePzGueSywGuGzWx6npj3L5W6WOliVjY14mniiKrpVF/Ieaxy3uKsVeus3SefUCzoKxmu0xlFAktIIgUJCuqooAobUbp1Uiyk6T3u4x9x/SlyKeSqgJFjATVokF0/+B/83q1VjE15lcQ40PviOB9jQGgXaDUc5/vYcVonQPAZuNI7/loTmP0kvdHRIkfA4DZwcd3RlD3gcgcYfjJkU3alIB2hWATez+ib8sDQLdC/5u2t/Y7TByBLu1q+AQ4OgfES1V73mbuve2//5rT39wNg+HKgfpS+4QAAAAlwSFlzAAAuIwAALiMBeKU/dgAAAAd0SU1FB+YMAg4rK7NiXpYAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAAQ0lEQVQoz2NkePmYgRTAxEAiQNUgLssgLouuBFWQZBtY8ElCDEb1JLVswGY2VUKJFhpw+AHielqGEm57SLaBkeapFQC7CBAn04PZmQAAAABJRU5ErkJggg==";
		String icon32x32 = "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAIAAAD8GO2jAAABhGlDQ1BJQ0MgcHJvZmlsZQAAKJF9kTtIw1AUhv+mSkUiDnYQcchQnSyIFXGUKhbBQmkrtOpgctMXNGlIUlwcBdeCg4/FqoOLs64OroIg+ABxdHJSdJESz00KLWI8cLkf/z3/4ZxzAaFZZZrVMwloum2mE3Epl1+VQq8QEUAYQExmlpHMLGbhG1/3lEtxF+W1/PP+jAG1YDEgIBHPMcO0iTeIZzZtg/M+cZiVZZX4nHjCpAaJH7muePzGueSywGuGzWx6npj3L5W6WOliVjY14mniiKrpVF/Ieaxy3uKsVeus3SefUCzoKxmu0xlFAktIIgUJCuqooAobUbp1Uiyk6T3u4x9x/SlyKeSqgJFjATVokF0/+B/83q1VjE15lcQ40PviOB9jQGgXaDUc5/vYcVonQPAZuNI7/loTmP0kvdHRIkfA4DZwcd3RlD3gcgcYfjJkU3alIB2hWATez+ib8sDQLdC/5u2t/Y7TByBLu1q+AQ4OgfES1V73mbuve2//5rT39wNg+HKgfpS+4QAAAAlwSFlzAAAuIwAALiMBeKU/dgAAAAd0SU1FB+YMAg42LaVtl78AAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAAuUlEQVRIx+VW0Q6AIAgUxz/01P9/WE99hT3YrK3QA6Wtcj00ZCB3HkJhXYLnYnGH6PhPqRaj6iknqAeFPWNwXnxTbz7RNB/GzFPZ0nhaKyDaPzvJCO4ATx0cYLeA1chIuAu54zDcBTuPwb0kVghNBbqcW5MAxn2ckgEdxK7ogNa4VwctNbg3u79DBLQjTQXnu998Ley9SMkHW2R1PemYVlFnVbCzkVWYj+jaiD7x6JP3bPr8ZPe64XcDIjZji/VsSXIAAAAASUVORK5CYII=";

		/** Calling the #setWindowIcon() method with the two encoded icons */
		Minecraft.getMinecraft().setWindowIcon(icon16x16, icon32x32);
	}

	public void afterMinecraft() {
		Display.setTitle(String.format("%1$s - %2$s", clientName, clientVersion));

		this.font = new Font(packageBase + ".font.fonts", "BwModelicaSS01-RegularCondensed", 50, 25, 30, 33);

		registerHuds();
	}

	private void registerHuds() {
		new HotbarOverlay();
		new ToggledModules1();
		new TabGui1();
	}

	public String getClientName() {
		return clientName;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public String getAuthor() {
		return author;
	}

	public String getDefaultUsername() {
		return defaultUsername;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public FriendsManager getFriendsManager() {
		return friendsManager;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public IRCClient getIRCClient() {
		return ircClient;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public AltService getAltService() {
		return altService;
	}

	@Deprecated
	public Font getFontRenderer() {
		return font;
	}

	public Font getFont() {
		return font;
	}

	public String getPackageBase() {
		return packageBase;
	}

	public boolean isDefaultHotbar() {
		return defaultHotbar;
	}

	public Config getGenericConfig() {
		return genericConfig;
	}

	public ClickGui getClickGui() {
		return clickGui;
	}

	public Locale getEnglishLocale() {
		return englishLocale;
	}

	public void switchToMojang() {
		try {
			this.altService.switchService(AltService.EnumAltService.MOJANG);
		} catch (NoSuchFieldException e) {
			System.out.println("Couldn't switch to modank altservice");
		} catch (IllegalAccessException e) {
			System.out.println("Couldn't switch to modank altservice -2");
		}
	}

	public void switchToTheAltening() {
		try {
			this.altService.switchService(AltService.EnumAltService.THEALTENING);
		} catch (NoSuchFieldException e) {
			System.out.println("Couldn't switch to altening altservice");
		} catch (IllegalAccessException e) {
			System.out.println("Couldn't switch to altening altservice -2");
		}
	}

}