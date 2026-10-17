package com.norwood.mcheli.helper.client.model;

import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.jetbrains.annotations.NotNull;

public enum LegacyModelLoader implements ICustomModelLoader {
    INSTANCE;

    public static final String VARIANT = "mcheli_legacy";
    static final String TEMPLATE;

    static {
        TEMPLATE = "{'parent':'item/generated','textures':{'layer0':'__item__'}}".replaceAll("'", "\"");
    }

    public void func_110549_a(@NotNull IResourceManager resourceManager) {}

    public boolean accepts(@NotNull ResourceLocation modelLocation) {
        ModelResourceLocation location;
        if (modelLocation instanceof ModelResourceLocation) {
            location = (ModelResourceLocation) modelLocation;
        } else {
            return false;
        }
        return (location.func_110624_b().equals("mcheli") && location.func_177518_c().equals(VARIANT));
    }

    @NotNull
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        String path = modelLocation.func_110624_b() + ":items/" + modelLocation.func_110623_a();
        ModelBlock modelblock = ModelBlock.func_178294_a(TEMPLATE.replaceAll("__item__", path));
        modelblock.field_178315_d = ModelLoaderRegistry.getModel(modelblock.func_178305_e()).asVanillaModel().get();
        return new MCH_WrapperItemLayerModel(modelblock);
    }
}
