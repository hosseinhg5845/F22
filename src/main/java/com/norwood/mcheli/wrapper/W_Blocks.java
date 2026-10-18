package com.norwood.mcheli.wrapper;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * Wrapper class that re-exports the few Blocks fields the mod actually
 * uses, so the mod source can refer to them as `W_Blocks.AIR` etc.
 *
 * The original code had `public class W_Blocks extends Blocks {}` but
 * `net.minecraft.init.Blocks` has a private (or no accessible) constructor
 * in 1.12.2, so subclasses cannot call super(). This rewrite keeps the
 * same static-field API surface but does not extend Blocks.
 */
public class W_Blocks {
    private W_Blocks() {}

    public static final Block AIR          = Blocks.AIR;
    public static final Block CAKE         = Blocks.CAKE;
    public static final Block COBBLESTONE  = Blocks.COBBLESTONE;
    public static final Block FIRE         = Blocks.FIRE;
    public static final Block OBSIDIAN     = Blocks.OBSIDIAN;
    public static final Block SNOW_LAYER   = Blocks.SNOW_LAYER;
    public static final Block WATER        = Blocks.WATER;
    public static final Block WATERLILY    = Blocks.WATERLILY;
}
