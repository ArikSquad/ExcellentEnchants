package su.nightexpress.excellentenchants;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.event.RegistryFreezeEvent;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.excellentenchants.api.EnchantmentID;
import su.nightexpress.excellentenchants.api.enchantment.Definition;
import su.nightexpress.excellentenchants.config.Config;
import su.nightexpress.excellentenchants.enchantment.impl.GameEnchantment;
import su.nightexpress.excellentenchants.enchantment.impl.armor.*;
import su.nightexpress.excellentenchants.enchantment.impl.bow.*;
import su.nightexpress.excellentenchants.enchantment.impl.fishing.*;
import su.nightexpress.excellentenchants.enchantment.impl.tool.*;
import su.nightexpress.excellentenchants.enchantment.impl.universal.CurseOfFragilityEnchant;
import su.nightexpress.excellentenchants.enchantment.impl.universal.RestoreEnchant;
import su.nightexpress.excellentenchants.enchantment.impl.universal.SoulboundEnchant;
import su.nightexpress.excellentenchants.enchantment.impl.weapon.*;
import su.nightexpress.nightcore.util.FileUtil;

import java.io.File;
import java.util.function.Function;
import java.util.stream.Collectors;

import static su.nightexpress.excellentenchants.registry.EnchantRegistry.BY_ID;
import static su.nightexpress.excellentenchants.registry.EnchantRegistry.BY_KEY;

/*
Currently there's pretty good implementation of the registering, however the plugin instance is most
likely null at the point when this is running, because the plugin is not initialized, but we don't
need anything here, so at the point where its needed, i hope its regisstered.

2nd problem: Currently features like setupDistribution from Internal_1_21_4 is not implemented
 */

@SuppressWarnings("UnstableApiUsage")
public class ExcellentBootstrapper implements PluginBootstrap {
    // bet this doesnt work
    private EnchantsPlugin plugin = EnchantsAPI.getRarityManager().plugin();
    /* use this sample
    context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {
            event.registry().register(
                // The key of the registry
                // Plugins should use their own namespace instead of minecraft or papermc
                EnchantmentKeys.create(Key.key("papermc:pointy")),
                b -> b.description(Component.text("Pointy"))
                    .supportedItems(event.getOrCreateTag(ItemTypeTagKeys.SWORDS))
                    .anvilCost(1)
                    .maxLevel(25)
                    .weight(10)
                    .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(1, 1))
                    .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(3, 1))
                    .activeSlots(EquipmentSlotGroup.ANY)
            );
        }));
        */

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {
            // Fishing Enchants
            this.register(AutoReelEnchant.ID, file -> new AutoReelEnchant(plugin, file), context, event);
            this.register(DoubleCatchEnchant.ID, file -> new DoubleCatchEnchant(plugin, file), context, event);
            this.register(SeasonedAnglerEnchant.ID, file -> new SeasonedAnglerEnchant(plugin, file), context, event);
            this.register(SurvivalistEnchant.ID, file -> new SurvivalistEnchant(plugin, file), context, event);
            this.register(CurseOfDrownedEnchant.ID, file -> new CurseOfDrownedEnchant(plugin, file), context, event);
            this.register(RiverMasterEnchant.ID, file -> new RiverMasterEnchant(plugin, file), context, event);

            // Tool enchants
            this.register(BlastMiningEnchant.ID, file -> new BlastMiningEnchant(plugin, file), context, event);
            this.register(CurseOfBreakingEnchant.ID, file -> new CurseOfBreakingEnchant(plugin, file), context, event);
            this.register(CurseOfMisfortuneEnchant.ID, file -> new CurseOfMisfortuneEnchant(plugin, file), context, event);
            this.register(SilkSpawnerEnchant.ID, file -> new SilkSpawnerEnchant(plugin, file), context, event);
            this.register(HasteEnchant.ID, file -> new HasteEnchant(plugin, file), context, event);
            this.register(LuckyMinerEnchant.ID, file -> new LuckyMinerEnchant(plugin, file), context, event);
            this.register(ReplanterEnchant.ID, file -> new ReplanterEnchant(plugin, file), context, event);
            this.register(SilkChestEnchant.ID, file -> new SilkChestEnchant(plugin, file), context, event);
            this.register(SmelterEnchant.ID, file -> new SmelterEnchant(plugin, file), context, event);
            this.register(TelekinesisEnchant.ID, file -> new TelekinesisEnchant(plugin, file), context, event);
            this.register(TreasureHunterEnchant.ID, file -> new TreasureHunterEnchant(plugin, file), context, event);
            this.register(TunnelEnchant.ID, file -> new TunnelEnchant(plugin, file), context, event);
            this.register(VeinminerEnchant.ID, file -> new VeinminerEnchant(plugin, file), context, event);

            // Weapon enchants
            this.register(BaneOfNetherspawnEnchant.ID, file -> new BaneOfNetherspawnEnchant(plugin, file), context, event);
            this.register(BlindnessEnchant.ID, file -> new BlindnessEnchant(plugin, file), context, event);
            this.register(ConfusionEnchant.ID, file -> new ConfusionEnchant(plugin, file), context, event);
            this.register(CutterEnchant.ID, file -> new CutterEnchant(plugin, file), context, event);
            this.register(CurseOfDeathEnchant.ID, file -> new CurseOfDeathEnchant(plugin, file), context, event);
            this.register(DecapitatorEnchant.ID, file -> new DecapitatorEnchant(plugin, file), context, event);
            this.register(DoubleStrikeEnchant.ID, file -> new DoubleStrikeEnchant(plugin, file), context, event);
            this.register(ExhaustEnchant.ID, file -> new ExhaustEnchant(plugin, file), context, event);
            this.register(WisdomEnchant.ID, file -> new WisdomEnchant(plugin, file), context, event);
            this.register(IceAspectEnchant.ID, file -> new IceAspectEnchant(plugin, file), context, event);
            this.register(InfernusEnchant.ID, file -> new InfernusEnchant(plugin, file), context, event);
            this.register(EnchantmentID.NIMBLE, file -> new NimbleEnchant(plugin, file), context, event);
            this.register(ParalyzeEnchant.ID, file -> new ParalyzeEnchant(plugin, file), context, event);
            this.register(CureEnchant.ID, file -> new CureEnchant(plugin, file), context, event);
            this.register(RageEnchant.ID, file -> new RageEnchant(plugin, file), context, event);
            this.register(RocketEnchant.ID, file -> new RocketEnchant(plugin, file), context, event);
            this.register(ScavengerEnchant.ID, file -> new ScavengerEnchant(plugin, file), context, event);
            this.register(SurpriseEnchant.ID, file -> new SurpriseEnchant(plugin, file), context, event);
            this.register(SwiperEnchant.ID, file -> new SwiperEnchant(plugin, file), context, event);
            this.register(TemperEnchant.ID, file -> new TemperEnchant(plugin, file), context, event);
            this.register(ThriftyEnchant.ID, file -> new ThriftyEnchant(plugin, file), context, event);
            this.register(ThunderEnchant.ID, file -> new ThunderEnchant(plugin, file), context, event);
            this.register(VampireEnchant.ID, file -> new VampireEnchant(plugin, file), context, event);
            this.register(VenomEnchant.ID, file -> new VenomEnchant(plugin, file), context, event);
            this.register(VillageDefenderEnchant.ID, file -> new VillageDefenderEnchant(plugin, file), context, event);
            this.register(WitherEnchant.ID, file -> new WitherEnchant(plugin, file), context, event);

            // Armor enchants
            this.register(WaterBreathingEnchant.ID, file -> new WaterBreathingEnchant(plugin, file), context, event);
            this.register(JumpingEnchant.ID, file -> new JumpingEnchant(plugin, file), context, event);
            this.register(ColdSteelEnchant.ID, file -> new ColdSteelEnchant(plugin, file), context, event);
            this.register(IceShieldEnchant.ID, file -> new IceShieldEnchant(plugin, file), context, event);
            this.register(ElementalProtectionEnchant.ID, file -> new ElementalProtectionEnchant(plugin, file), context, event);
            this.register(FireShieldEnchant.ID, file -> new FireShieldEnchant(plugin, file), context, event);
            this.register(EnchantmentID.FLAME_WALKER, file -> new FlameWalkerEnchant(plugin, file), context, event);
            this.register(HardenedEnchant.ID, file -> new HardenedEnchant(plugin, file), context, event);
            this.register(NightVisionEnchant.ID, file -> new NightVisionEnchant(plugin, file), context, event);
            this.register(RegrowthEnchant.ID, file -> new RegrowthEnchant(plugin, file), context, event);
            this.register(SaturationEnchant.ID, file -> new SaturationEnchant(plugin, file), context, event);
            this.register(KamikadzeEnchant.ID, file -> new KamikadzeEnchant(plugin, file), context, event);
            this.register(EnchantmentID.REBOUND, file -> new ReboundEnchant(plugin, file), context, event);
            this.register(StoppingForceEnchant.ID, file -> new StoppingForceEnchant(plugin, file), context, event);
            this.register(SpeedyEnchant.ID, file -> new SpeedyEnchant(plugin, file), context, event);

            // Bow enchants
            this.register(BomberEnchant.ID, file -> new BomberEnchant(plugin, file), context, event);
            this.register(ConfusingArrowsEnchant.ID, file -> new ConfusingArrowsEnchant(plugin, file), context, event);
            this.register(DragonfireArrowsEnchant.ID, file -> new DragonfireArrowsEnchant(plugin, file), context, event);
            this.register(ElectrifiedArrowsEnchant.ID, file -> new ElectrifiedArrowsEnchant(plugin, file), context, event);
            this.register(EnderBowEnchant.ID, file -> new EnderBowEnchant(plugin, file), context, event);
            this.register(ExplosiveArrowsEnchant.ID, file -> new ExplosiveArrowsEnchant(plugin, file), context, event);
            this.register(EnchantmentID.LINGERING, file -> new LingeringEnchant(plugin, file), context, event);
            this.register(FlareEnchant.ID, file -> new FlareEnchant(plugin, file), context, event);
            this.register(GhastEnchant.ID, file -> new GhastEnchant(plugin, file), context, event);
            this.register(HoverEnchant.ID, file -> new HoverEnchant(plugin, file), context, event);
            this.register(SniperEnchant.ID, file -> new SniperEnchant(plugin, file), context, event);
            this.register(PoisonedArrowsEnchant.ID, file -> new PoisonedArrowsEnchant(plugin, file), context, event);
            this.register(VampiricArrowsEnchant.ID, file -> new VampiricArrowsEnchant(plugin, file), context, event);
            this.register(WitheredArrowsEnchant.ID, file -> new WitheredArrowsEnchant(plugin, file), context, event);
            this.register(DarknessArrowsEnchant.ID, file -> new DarknessArrowsEnchant(plugin, file), context, event);
            this.register(DarknessCloakEnchant.ID, file -> new DarknessCloakEnchant(plugin, file), context, event);

            // Universal
            this.register(CurseOfFragilityEnchant.ID, file -> new CurseOfFragilityEnchant(plugin, file), context, event);
            this.register(CurseOfMediocrityEnchant.ID, file -> new CurseOfMediocrityEnchant(plugin, file), context, event);
            this.register(SoulboundEnchant.ID, file -> new SoulboundEnchant(plugin, file), context, event);
            this.register(RestoreEnchant.ID, file -> new RestoreEnchant(plugin, file), context, event);
        }));
    }

    private void register(@NotNull String id, @NotNull Function<File, GameEnchantment> constructor, BootstrapContext context, RegistryFreezeEvent<Enchantment, EnchantmentRegistryEntry.Builder> event) {
        File file = new File(plugin.getDataFolder() + Config.DIR_ENCHANTS, id + ".yml");
        FileUtil.create(file);
        GameEnchantment enchantment = constructor.apply(file);
        Definition definition = enchantment.getDefinition();

        event.registry().register(
                EnchantmentKeys.create(Key.key("minecraft", id)),
                b -> b.description(Component.text(definition.getDisplayName()))
                        .supportedItems(event.getOrCreateTag(ItemTypeTagKeys.SWORDS))
                        .anvilCost(definition.getAnvilCost())
                        .maxLevel(definition.getMaxLevel())
                        .weight(definition.getRarity().getWeight())
                        .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(1, 1))
                        .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(3, 1))
                        .activeSlots(EquipmentSlotGroup.ANY)
                        .exclusiveWith(RegistrySet.keySet(
                                RegistryKey.ENCHANTMENT,
                                definition.getConflicts().stream()
                                        .map(conflict -> TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("minecraft", conflict)))
                                        .collect(Collectors.toSet())
                        ))
        );

        // statically imported
        BY_KEY.put(NamespacedKey.minecraft(enchantment.getId()), enchantment);
        BY_ID.put(enchantment.getId(), enchantment);

        // can't wait for the null pointer exception
        this.plugin.info("Registered enchantment: " + enchantment.getId());
    }
}
