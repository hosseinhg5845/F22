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

    public void onResourceManagerReload(@NotNull IResourceManager resourceManager) {}

    public boolean accepts(@NotNull ResourceLocation modelLocation) {
        ModelResourceLocation location;
        if (modelLocation instanceof ModelResourceLocation) {
            location = (ModelResourceLocation) modelLocation;
        } else {
            return false;
        }
        return (location.getResourceDomain().equals("mcheli") && location.getVariant().equals(VARIANT));
    }

    @NotNull
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        String path = modelLocation.getResourceDomain() + ":items/" + modelLocation.getResourcePath();
        ModelBlock modelblock = ModelBlock.deserialize(TEMPLATE.replaceAll("__item__", path));
        modelblock.parent = ModelLoaderRegistry.getModel(modelblock.getParentLocation()).asVanillaModel().get();
        return new MCH_WrapperItemLayerModel(modelblock);
    }
}
