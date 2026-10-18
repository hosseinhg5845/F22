/*    */ package com.norwood.mcheli.helper.block;
/*    */ 
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public enum EnumDirection8 implements IStringSerializable {
/*  9 */   SOUTH(0, 4, "south", new Vec3i(0, 0, 1)),
/* 10 */   SOUTHWEST(1, 5, "southwest", new Vec3i(-1, 0, 1)),
/* 11 */   WEST(2, 6, "west", new Vec3i(-1, 0, 0)),
/* 12 */   NORTHWEST(3, 7, "northwest", new Vec3i(-1, 0, -1)),
/* 13 */   NORTH(4, 0, "north", new Vec3i(0, 0, -1)),
/* 14 */   NORTHEAST(5, 1, "northeast", new Vec3i(1, 0, -1)),
/* 15 */   EAST(6, 2, "east", new Vec3i(1, 0, 0)),
/* 16 */   SOUTHEAST(7, 3, "southeast", new Vec3i(1, 0, 1));

  public static final EnumDirection8[] VALUES;
  private final int index;
  private final int opposite;
  private final String name;
  private final Vec3i directionVec;

  static {
    VALUES = new EnumDirection8[8];

    for (EnumDirection8 value : values())
      VALUES[value.index] = value;
  }
/*    */   
/*    */   EnumDirection8(int index, int opposite, String name, Vec3i dirVec) {
/* 32 */     this.index = index;
/* 33 */     this.opposite = opposite;
/* 34 */     this.name = name;
/* 35 */     this.directionVec = dirVec;
/*    */   }
/*    */   
/*    */   public static EnumDirection8 getFront(int index) {
/* 39 */     return VALUES[MathHelper.abs(index % VALUES.length)];
/*    */   }
/*    */   
/*    */   public static EnumDirection8 fromAngle(double angle) {
/* 43 */     return getFront(MathHelper.floor(angle / 45.0D + 0.5D) & 0x7);
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 47 */     return this.index;
/*    */   }
/*    */   @NotNull
/*    */   public String getName() {
/* 51 */     return this.name;
/*    */   }
/*    */   
/*    */   public EnumDirection8 opposite() {
/* 55 */     return getFront(this.opposite);
/*    */   }
/*    */   
/*    */   public Vec3i getDirectionVec() {
/* 59 */     return this.directionVec;
/*    */   }
/*    */   
/*    */   public float getAngle() {
/* 63 */     return ((this.index & 0x7) * 45);
/*    */   }
/*    */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\helper\block\EnumDirection8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */