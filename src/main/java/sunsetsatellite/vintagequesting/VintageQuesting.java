package sunsetsatellite.vintagequesting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.entity.monster.EntityGiant;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.monster.EntitySlime;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.vintagequesting.gui.GuiChapterButton;
import sunsetsatellite.vintagequesting.gui.GuiQuestButton;
import sunsetsatellite.vintagequesting.gui.GuiQuestInfo;
import sunsetsatellite.vintagequesting.gui.GuiQuestbook;
import sunsetsatellite.vintagequesting.interfaces.IKeybinds;
import sunsetsatellite.vintagequesting.quest.Chapter;
import sunsetsatellite.vintagequesting.quest.Quest;
import sunsetsatellite.vintagequesting.quest.reward.ItemReward;
import sunsetsatellite.vintagequesting.quest.task.ClickTask;
import sunsetsatellite.vintagequesting.quest.task.RetrievalTask;
import sunsetsatellite.vintagequesting.quest.template.ChapterTemplate;
import sunsetsatellite.vintagequesting.quest.template.QuestTemplate;
import sunsetsatellite.vintagequesting.quest.template.reward.ItemRewardTemplate;
import sunsetsatellite.vintagequesting.quest.template.task.ClickTaskTemplate;
import sunsetsatellite.vintagequesting.quest.template.task.CraftingTaskTemplate;
import sunsetsatellite.vintagequesting.quest.template.task.KillTaskTemplate;
import sunsetsatellite.vintagequesting.quest.template.task.RetrievalTaskTemplate;
import sunsetsatellite.vintagequesting.registry.ChapterRegistry;
import sunsetsatellite.vintagequesting.registry.QuestRegistry;
import sunsetsatellite.vintagequesting.registry.RewardRegistry;
import sunsetsatellite.vintagequesting.registry.TaskRegistry;
import sunsetsatellite.vintagequesting.util.Logic;
import turniplabs.halplibe.helper.gui.factory.base.GuiFactory;
import turniplabs.halplibe.helper.gui.registered.RegisteredGui;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.*;


public class VintageQuesting implements ModInitializer, ClientStartEntrypoint, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "vintagequesting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ChapterRegistry CHAPTERS = new ChapterRegistry();
	public static final QuestRegistry QUESTS = new QuestRegistry();
	public static final RewardRegistry REWARDS = new RewardRegistry();
	public static final TaskRegistry TASKS = new TaskRegistry();

	public static final RegisteredGui QUESTBOOK = new RegisteredGui(MOD_ID, "questbook", new GuiFactory() {
		@Override
		public @NotNull GuiScreen createGui(@NotNull RegisteredGui gui, @NotNull EntityPlayerSP player) {
			return new GuiQuestbook(player,null);
		}

		@Override
		public @Nullable Container createContainer(@NotNull RegisteredGui gui, @NotNull EntityPlayerMP player) {
			return null;
		}
	}, true);

    @Override
    public void onInitialize() {
        LOGGER.info("Vintage Questing initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		Registries.getInstance().register("vintagequesting:chapters",CHAPTERS);
		Registries.getInstance().register("vintagequesting:quests",QUESTS);
		Registries.getInstance().register("vintagequesting:rewards", REWARDS);
        Registries.getInstance().register("vintagequesting:tasks", TASKS);

		ItemRewardTemplate reward = new ItemRewardTemplate("vintagequesting:reward1",new ItemStack(Item.basket));
		ClickTaskTemplate clickTask = new ClickTaskTemplate("vintagequesting:click1");
		RetrievalTaskTemplate retrievalTask = new RetrievalTaskTemplate("vintagequesting:retrieval1",new ItemStack(Block.netherrackIgneous,32));
		RetrievalTaskTemplate retrievalTask2 = new RetrievalTaskTemplate("vintagequesting:retrieval2",new ItemStack(Block.blockDiamond,16)).setConsume();
		CraftingTaskTemplate craftingTask = new CraftingTaskTemplate("vintagequesting:crafting1",new ItemStack(Block.workbench,8));
		KillTaskTemplate killTask = new KillTaskTemplate("vintagequesting:kill1", EntityPig.class,4);
		QuestTemplate quest = new QuestTemplate("vintagequesting:test","quest.vq.test","quest.vq.test",Block.netherrackIgneous,Logic.AND,Logic.AND).setTasks(listOf(clickTask,retrievalTask,retrievalTask2,craftingTask,killTask)).setRewards(listOf(reward));
		ChapterTemplate chapter = new ChapterTemplate("vintagequesting:test",Block.dirt,"chapter.vq.test","chapter.vq.test",listOf(quest));

		ItemRewardTemplate reward2 = new ItemRewardTemplate("vintagequesting:reward2",new ItemStack(Block.torchRedstoneIdle));
		ClickTaskTemplate clickTask2 = new ClickTaskTemplate("vintagequesting:click2");
		RetrievalTaskTemplate retrievalTask3 = new RetrievalTaskTemplate("vintagequesting:retrieval3",new ItemStack(Block.slabBrickStonePolished,32));
		RetrievalTaskTemplate retrievalTask4 = new RetrievalTaskTemplate("vintagequesting:retrieval4",new ItemStack(Block.trommelIdle,16)).setConsume();
		CraftingTaskTemplate craftingTask2 = new CraftingTaskTemplate("vintagequesting:crafting2",new ItemStack(Block.furnaceBlastIdle,8));
		KillTaskTemplate killTask2 = new KillTaskTemplate("vintagequesting:kill2", EntitySlime.class,4);
		QuestTemplate quest2 = new QuestTemplate("vintagequesting:test2","quest.vq.test2","quest.vq.test2",Block.bedrock,Logic.AND,Logic.AND)
			.setTasks(listOf(clickTask2,retrievalTask3,retrievalTask4,craftingTask2,killTask2))
			.setRewards(listOf(reward2))
			.setPreRequisites(listOf(quest))
			.setX(64);
		ChapterTemplate chapter2 = new ChapterTemplate("vintagequesting:test2",Block.blockDiamond,"chapter.vq.test2","chapter.vq.test2",listOf(quest,quest2));

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		OptionsCategory category = new OptionsCategory("gui.options.page.controls.category.vintagequesting");
		IKeybinds keybinds = (IKeybinds) Minecraft.getMinecraft(Minecraft.class).gameSettings;
		category
			.withComponent(new KeyBindingComponent(keybinds.vintage_questing$getKeyOpenQuestbook()));
		OptionsPages.CONTROLS
			.withComponent(category);
	}

	public static <K,V> Map<K,V> mapOf(K[] keys, V[] values){
		if(keys.length != values.length){
			throw new IllegalArgumentException("Arrays differ in size!");
		}
		HashMap<K,V> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i],values[i]);
		}
		return map;
	}

	public static <T,V> T[] arrayFill(T[] array,V value){
		Arrays.fill(array,value);
		return array;
	}

	@SafeVarargs
	public static <T> List<T> listOf(T... values){
		return new ArrayList<>(Arrays.asList(values));
	}

	public static <T,U> List<Pair<T,U>> zip(List<T> first, List<U> second){
		List<Pair<T,U>> list = new ArrayList<>();
		List<?> shortest = first.size() < second.size() ? first : second;
		for (int i = 0; i < shortest.size(); i++) {
			list.add(Pair.of(first.get(i),second.get(i)));
		}
		return list;
	}

	public static ArrayList<ItemStack> condenseItemList(List<ItemStack> list) {
		ArrayList<ItemStack> stacks = new ArrayList<>();
		for (ItemStack stack : list) {
			if (stack != null) {
				Optional<ItemStack> existing = stacks.stream().filter((S) -> S.itemID == stack.itemID).findAny();
				if (existing.isPresent()) {
					existing.get().stackSize += stack.stackSize;
				} else {
					stacks.add(stack.copy());
				}
			}
		}
		return stacks;
	}
}
