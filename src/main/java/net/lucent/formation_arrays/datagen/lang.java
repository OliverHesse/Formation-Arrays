package net.lucent.formation_arrays.datagen;

import net.lucent.formation_arrays.FormationArrays;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class lang extends LanguageProvider {
    public lang(PackOutput output, String locale) {
        super(output, FormationArrays.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add("creativetab.formation_arrays.formation_cores","Formation Cores");
        //items
        add("item.formation_arrays.player_access_control_token", "Player Access Token");
        add("block.formation_arrays.tier_1_formation_core","[TIER 1] Formation Core");
        add("block.formation_arrays.tier_2_formation_core","[TIER 2] Formation Core");
    }
}
