/*     */ package com.norwood.mcheli.block;
/*     */ import com.norwood.mcheli.MCH_Lib;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.helper.block.EnumDirection8;
/*     */ import com.norwood.mcheli.helper.block.properties.PropertyDirection8;
/*     */ import com.norwood.mcheli.wrapper.W_BlockContainer;
/*     */ import com.norwood.mcheli.wrapper.W_Item;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.ITileEntityProvider;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_DraftingTableBlock extends W_BlockContainer implements ITileEntityProvider {
/*  32 */   public static final PropertyDirection8 DIRECTION8 = PropertyDirection8.create("direction8");
/*     */   private final boolean isLighting;
/*     */   
/*     */   public MCH_DraftingTableBlock(int blockId, boolean isOn) {
/*  36 */     super(blockId, Material.IRON);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)DIRECTION8, (Comparable)EnumDirection8.NORTH));
/*  38 */     setSoundType(SoundType.METAL);
/*  39 */     setHardness(0.2F);
/*  40 */     this.isLighting = isOn;
/*  41 */     if (isOn) {
/*  42 */       setLightLevel(1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer player, @NotNull EnumHand hand, @NotNull EnumFacing facing, float par7, float par8, float par9) {
/*  49 */     if (!world.isRemote) {
/*  50 */       if (!player.isSneaking()) {
/*  51 */         MCH_Lib.DbgLog(player.world, "MCH_DraftingTableGui.MCH_DraftingTableGui OPEN GUI (%d, %d, %d)", new Object[] {
/*     */ 
/*     */               
/*  54 */               Integer.valueOf(pos.getX()), 
/*  55 */               Integer.valueOf(pos.getY()), 
/*  56 */               Integer.valueOf(pos.getZ())
/*     */             });
/*  58 */         player.openGui(MCH_MOD.instance, 4, world, pos.getX(), pos.getY(), pos.getZ());
/*     */       } else {
/*  60 */         EnumDirection8 dir = (EnumDirection8)state.getValue((IProperty)DIRECTION8);
/*  61 */         MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockActivated:yaw=%d Light %s", new Object[] { Integer.valueOf((int)dir.getAngle()), this.isLighting ? "OFF->ON" : "ON->OFF" });
/*  62 */         if (this.isLighting) {
/*  63 */           world.setBlockState(pos, MCH_MOD.blockDraftingTable.getDefaultState().withProperty((IProperty)DIRECTION8, (Comparable)dir), 2);
/*     */         } else {
/*  65 */           world.setBlockState(pos, MCH_MOD.blockDraftingTableLit.getDefaultState().withProperty((IProperty)DIRECTION8, (Comparable)dir), 2);
/*     */         } 
/*     */         
/*  68 */         world.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 0.3F, 0.5F);
/*     */       } 
/*     */     }
/*     */     
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(@NotNull World world, int a) {
/*  76 */     return new MCH_DraftingTableTileEntity();
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(World world) {
/*  80 */     return new MCH_DraftingTableTileEntity();
/*     */   }
/*     */   
/*     */   public boolean shouldCheckWeakPower(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing side) {
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(@NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EntityPlayer player) {
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canRenderInPass(int pass) {
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 104 */     return EnumPushReaction.DESTROY;
/*     */   }
/*     */   
/*     */   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 108 */     return getDefaultState().withProperty((IProperty)DIRECTION8, (Comparable)EnumDirection8.fromAngle(MCH_Lib.getRotate360(placer.rotationYaw)));
/*     */   }
/*     */   
/*     */   public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack itemStack) {
/* 112 */     float pyaw = (float)MCH_Lib.getRotate360(entity.rotationYaw);
/* 113 */     pyaw += 22.5F;
/* 114 */     int yaw = (int)(pyaw / 45.0F);
/* 115 */     if (yaw < 0) {
/* 116 */       yaw = yaw % 8 + 8;
/*     */     }
/*     */     
/* 119 */     world.setBlockState(pos, state.withProperty((IProperty)DIRECTION8, (Comparable)EnumDirection8.fromAngle(MCH_Lib.getRotate360(entity.rotationYaw))), 2);
/* 120 */     MCH_Lib.DbgLog(world, "MCH_DraftingTableBlock.onBlockPlacedBy:yaw=%d", new Object[] { Integer.valueOf(yaw) });
/*     */   }
/*     */   
/*     */   public boolean getUseNeighborBrightness(IBlockState state) {
/* 124 */     return true;
/*     */   }
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 128 */     return W_Item.getItemFromBlock((Block)MCH_MOD.blockDraftingTable);
/*     */   }
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 132 */     return new ItemStack((Block)MCH_MOD.blockDraftingTable);
/*     */   }
/*     */   
/*     */   protected ItemStack createStackedBlock(int meta) {
/* 136 */     return new ItemStack((Block)MCH_MOD.blockDraftingTable);
/*     */   }
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 140 */     return ((EnumDirection8)state.getValue((IProperty)DIRECTION8)).getIndex();
/*     */   }
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 144 */     return getDefaultState().withProperty((IProperty)DIRECTION8, (Comparable)EnumDirection8.getFront(meta));
/*     */   }
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 148 */     return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)DIRECTION8 });
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\block\MCH_DraftingTableBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */