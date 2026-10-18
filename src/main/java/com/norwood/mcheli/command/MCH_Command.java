/*     */ package com.norwood.mcheli.command;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.norwood.mcheli.MCH_Config;
/*     */ import com.norwood.mcheli.MCH_MOD;
/*     */ import com.norwood.mcheli.MCH_PacketNotifyServerSettings;
/*     */ import com.norwood.mcheli.helper.MCH_Utils;
/*     */ import com.norwood.mcheli.multiplay.MCH_MultiplayPacketHandler;
/*     */ import com.norwood.mcheli.multiplay.MCH_PacketIndClient;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandGameMode;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.SyntaxErrorException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.CommandEvent;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class MCH_Command extends CommandBase {
/*     */   public static final String CMD_GET_SS = "sendss";
/*     */   public static final String CMD_MOD_LIST = "modlist";
/*     */   public static final String CMD_RECONFIG = "reconfig";
/*     */   public static final String CMD_TITLE = "title";
/*     */   public static final String CMD_FILL = "fill";
/*     */   public static final String CMD_STATUS = "status";
/*  52 */   public static final String[] ALL_COMMAND = new String[] { "sendss", "modlist", "reconfig", "title", "fill", "status", "killentity", "removeentity", "attackentity", "showboundingbox", "list", "delayhitbox" }; public static final String CMD_KILL_ENTITY = "killentity";
/*     */   public static final String CMD_REMOVE_ENTITY = "removeentity";
/*     */   public static final String CMD_ATTACK_ENTITY = "attackentity";
/*  55 */   public static final MCH_Command instance = new MCH_Command(); public static final String CMD_SHOW_BB = "showboundingbox"; public static final String CMD_DELAY_BB = "delayhitbox"; public static final String CMD_LIST = "list";
/*     */   
/*     */   public static boolean canUseCommand(Entity player) {
/*  58 */     return (player instanceof EntityPlayer && instance.canCommandSenderUseCommand((ICommandSender)player));
/*     */   }
/*     */   
/*     */   public static boolean checkCommandPermission(MinecraftServer server, ICommandSender sender, String cmd) {
/*  62 */     if ((new CommandGameMode()).checkPermission(server, sender)) {
/*  63 */       return true;
/*     */     }
/*  65 */     if (sender instanceof EntityPlayer && !cmd.isEmpty()) {
/*  66 */       String playerName = ((EntityPlayer)sender).getGameProfile().getName();
/*     */       
/*  68 */       for (MCH_Config.CommandPermission c : MCH_Config.CommandPermissionList) {
/*  69 */         if (c.name.equals(cmd)) {
/*  70 */           for (String s : c.players) {
/*  71 */             if (s.equalsIgnoreCase(playerName)) {
/*  72 */               return true;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onCommandEvent(CommandEvent event) {
/*  84 */     if (event.getCommand() instanceof MCH_Command)
/*  85 */       if ((event.getParameters()).length > 0 && !event.getParameters()[0].isEmpty()) {
/*  86 */         if (!checkCommandPermission(MCH_Utils.getServer(), event.getSender(), event.getParameters()[0])) {
/*  87 */           event.setCanceled(true);
/*  88 */           TextComponentTranslation c = new TextComponentTranslation("commands.generic.permission", new Object[0]);
/*  89 */           c.getStyle().setColor(TextFormatting.RED);
/*  90 */           event.getSender().sendMessage((ITextComponent)c);
/*     */         } 
/*     */       } else {
/*  93 */         event.setCanceled(true);
/*     */       }  
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getName() {
/*  99 */     return "mcheli";
/*     */   }
/*     */   
/*     */   public boolean canCommandSenderUseCommand(ICommandSender player) {
/* 103 */     return true;
/*     */   }
/*     */   @NotNull
/*     */   public String getUsage(@NotNull ICommandSender sender) {
/* 107 */     return "commands.com.norwood.mcheli.usage";
/*     */   }
/*     */   
/*     */   public void execute(@NotNull MinecraftServer server, @NotNull ICommandSender sender, String[] prm) throws CommandException {
/* 111 */     if (MCH_Config.EnableCommand.prmBool) {
/* 112 */       if (!checkCommandPermission(server, sender, prm[0])) {
/* 113 */         TextComponentTranslation c = new TextComponentTranslation("commands.generic.permission", new Object[0]);
/* 114 */         c.getStyle().setColor(TextFormatting.RED);
/* 115 */         sender.sendMessage((ITextComponent)c);
/*     */       }
/* 117 */       else if (prm[0].equalsIgnoreCase("sendss")) {
/* 118 */         if (prm.length != 2) {
/* 119 */           throw new CommandException("Parameter error! : /mcheli sendss playerName", new Object[0]);
/*     */         }
/*     */         
/* 122 */         EntityPlayerMP player = getPlayer(server, sender, prm[1]);
/* 123 */         MCH_PacketIndClient.send((EntityPlayer)player, 1, prm[1]);
/* 124 */       } else if (prm[0].equalsIgnoreCase("modlist")) {
/* 125 */         if (prm.length < 2) {
/* 126 */           throw new CommandException("Parameter error! : /mcheli modlist playerName", new Object[0]);
/*     */         }
/*     */         
/* 129 */         EntityPlayerMP reqPlayer = (sender instanceof EntityPlayerMP) ? (EntityPlayerMP)sender : null;
/*     */         
/* 131 */         for (int i = 1; i < prm.length; i++) {
/* 132 */           EntityPlayerMP player = getPlayer(server, sender, prm[i]);
/* 133 */           MCH_PacketIndClient.send((EntityPlayer)player, 2, "" + MCH_MultiplayPacketHandler.getPlayerInfoId((EntityPlayer)reqPlayer));
/*     */         } 
/* 135 */       } else if (prm[0].equalsIgnoreCase("reconfig")) {
/* 136 */         if (prm.length != 1) {
/* 137 */           throw new CommandException("Parameter error! : /mcheli reconfig", new Object[0]);
/*     */         }
/*     */         
/* 140 */         MCH_MOD.proxy.reconfig();
/* 141 */         sender.getEntityWorld();
/* 142 */         if (!(sender.getEntityWorld()).isRemote) {
/* 143 */           MCH_PacketNotifyServerSettings.sendAll();
/*     */         }
/*     */         
/* 146 */         if (MCH_MOD.proxy.isSinglePlayer()) {
/* 147 */           sender.sendMessage((ITextComponent)new TextComponentString("Reload com.norwood.mcheli.cfg"));
/*     */         } else {
/* 149 */           sender.sendMessage((ITextComponent)new TextComponentString("Reload server side com.norwood.mcheli.cfg"));
/*     */         } 
/* 151 */       } else if (prm[0].equalsIgnoreCase("title")) {
/* 152 */         if (prm.length < 4) {
/* 153 */           throw new WrongUsageException("Parameter error! : /mcheli title time[1~180] position[0~4] messege[JSON format]", new Object[0]);
/*     */         }
/*     */         
/* 156 */         String s = buildString(prm, 3);
/* 157 */         int showTime = Integer.parseInt(prm[1]);
/* 158 */         if (showTime < 1) {
/* 159 */           showTime = 1;
/*     */         }
/*     */         
/* 162 */         if (showTime > 180) {
/* 163 */           showTime = 180;
/*     */         }
/*     */         
/* 166 */         int pos = Integer.parseInt(prm[2]);
/* 167 */         if (pos < 0) {
/* 168 */           pos = 0;
/*     */         }
/*     */         
/* 171 */         if (pos > 5) {
/* 172 */           pos = 5;
/*     */         }
/*     */         
/*     */         try {
/* 176 */           ITextComponent ichatcomponent = ITextComponent.Serializer.jsonToComponent(s);
/* 177 */           MCH_PacketTitle.send(ichatcomponent, 20 * showTime, pos);
/* 178 */         } catch (JsonParseException var9) {
/* 179 */           Throwable throwable = ExceptionUtils.getRootCause((Throwable)var9);
/* 180 */           throw new SyntaxErrorException("com.norwood.mcheli.title.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
/*     */         } 
/* 182 */       } else if (prm[0].equalsIgnoreCase("fill")) {
/* 183 */         executeFill(sender, prm);
/* 184 */       } else if (prm[0].equalsIgnoreCase("status")) {
/* 185 */         executeStatus(sender, prm);
/* 186 */       } else if (prm[0].equalsIgnoreCase("killentity")) {
/* 187 */         executeKillEntity(sender, prm);
/* 188 */       } else if (prm[0].equalsIgnoreCase("removeentity")) {
/* 189 */         executeRemoveEntity(sender, prm);
/* 190 */       } else if (prm[0].equalsIgnoreCase("attackentity")) {
/* 191 */         executeAttackEntity(sender, prm);
/* 192 */       } else if (prm[0].equalsIgnoreCase("showboundingbox")) {
/* 193 */         if (prm.length != 2) {
/* 194 */           throw new CommandException("Parameter error! : /mcheli showboundingbox true or false", new Object[0]);
/*     */         }
/*     */         
/* 197 */         if (!parseBoolean(prm[1])) {
/* 198 */           MCH_Config.EnableDebugBoundingBox.prmBool = false;
/* 199 */           MCH_PacketNotifyServerSettings.sendAll();
/* 200 */           sender.sendMessage((ITextComponent)new TextComponentString("Disabled bounding box"));
/*     */         } else {
/* 202 */           MCH_Config.EnableDebugBoundingBox.prmBool = true;
/* 203 */           MCH_PacketNotifyServerSettings.sendAll();
/* 204 */           sender.sendMessage((ITextComponent)new TextComponentString("Enabled bounding box [F3 + b]"));
/*     */         } 
/*     */         
/* 207 */         MCH_MOD.proxy.save();
/* 208 */       } else if (prm[0].equalsIgnoreCase("list")) {
/* 209 */         StringBuilder msg = new StringBuilder();
/*     */         
/* 211 */         for (String sx : ALL_COMMAND) {
/* 212 */           msg.append(sx).append(", ");
/*     */         }
/*     */         
/* 215 */         sender.sendMessage((ITextComponent)new TextComponentString("/mcheli command list : " + msg));
/*     */       } else {
/* 217 */         if (!prm[0].equalsIgnoreCase("delayhitbox")) {
/* 218 */           throw new CommandException("Unknown mcheli command. please type /mcheli list", new Object[0]);
/*     */         }
/*     */         
/* 221 */         if (prm.length == 1) {
/* 222 */           sender.sendMessage((ITextComponent)new TextComponentString("Current delay of hitbox = " + MCH_Config.HitBoxDelayTick.prmInt + " [0 ~ 50]"));
/*     */         } else {
/* 224 */           if (prm.length != 2) {
/* 225 */             throw new CommandException("Parameter error! : /mcheli delayhitbox 0 ~ 50", new Object[0]);
/*     */           }
/*     */           
/* 228 */           MCH_Config.HitBoxDelayTick.prmInt = parseInt(prm[1]);
/* 229 */           if (MCH_Config.HitBoxDelayTick.prmInt > 50) {
/* 230 */             MCH_Config.HitBoxDelayTick.prmInt = 50;
/*     */           }
/*     */           
/* 233 */           MCH_MOD.proxy.save();
/* 234 */           sender.sendMessage((ITextComponent)new TextComponentString("Current delay of hitbox = " + MCH_Config.HitBoxDelayTick.prmInt + " [0 ~ 50]"));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void executeAttackEntity(ICommandSender sender, String[] args) throws WrongUsageException {
/* 242 */     if (args.length < 3) {
/* 243 */       throw new WrongUsageException("/mcheli attackentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive> <damage> [damage source]", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 247 */     String className = args[1].toLowerCase();
/* 248 */     float damage = Float.parseFloat(args[2]);
/* 249 */     String damageName = (args.length >= 4) ? args[3].toLowerCase() : "";
/* 250 */     DamageSource ds = DamageSource.GENERIC;
/* 251 */     if (!damageName.isEmpty()) {
/* 252 */       switch (damageName) {
/*     */         case "player":
/* 254 */           if (sender instanceof EntityPlayer) {
/* 255 */             ds = DamageSource.causePlayerDamage((EntityPlayer)sender);
/*     */           }
/*     */           break;
/*     */         case "anvil":
/* 259 */           ds = DamageSource.ANVIL;
/*     */           break;
/*     */         case "cactus":
/* 262 */           ds = DamageSource.CACTUS;
/*     */           break;
/*     */         case "drown":
/* 265 */           ds = DamageSource.DROWN;
/*     */           break;
/*     */         case "fall":
/* 268 */           ds = DamageSource.FALL;
/*     */           break;
/*     */         case "fallingblock":
/* 271 */           ds = DamageSource.FALLING_BLOCK;
/*     */           break;
/*     */         case "generic":
/* 274 */           ds = DamageSource.GENERIC;
/*     */           break;
/*     */         case "infire":
/* 277 */           ds = DamageSource.IN_FIRE;
/*     */           break;
/*     */         case "inwall":
/* 280 */           ds = DamageSource.IN_WALL;
/*     */           break;
/*     */         case "lava":
/* 283 */           ds = DamageSource.LAVA;
/*     */           break;
/*     */         case "magic":
/* 286 */           ds = DamageSource.MAGIC;
/*     */           break;
/*     */         case "onfire":
/* 289 */           ds = DamageSource.ON_FIRE;
/*     */           break;
/*     */         case "starve":
/* 292 */           ds = DamageSource.STARVE;
/*     */           break;
/*     */         case "wither":
/* 295 */           ds = DamageSource.WITHER;
/*     */           break;
/*     */       } 
/*     */     
/*     */     }
/* 300 */     int attacked = 0;
/* 301 */     List<Entity> list = (sender.getEntityWorld()).loadedEntityList;
/*     */     
/* 303 */     for (Entity entity : list) {
/* 304 */       if (entity != null && !(entity instanceof EntityPlayer) && entity.getClass().getName().toLowerCase().contains(className)) {
/* 305 */         entity.attackEntityFrom(ds, damage);
/* 306 */         attacked++;
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     sender.sendMessage((ITextComponent)new TextComponentString(attacked + " entity attacked(" + args[1] + ", damage=" + damage + ")."));
/*     */   }
/*     */ 
/*     */   
/*     */   private void executeKillEntity(ICommandSender sender, String[] args) throws WrongUsageException {
/* 315 */     if (args.length < 2) {
/* 316 */       throw new WrongUsageException("/mcheli killentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]);
/*     */     }
/* 318 */     String className = args[1].toLowerCase();
/* 319 */     int killed = 0;
/* 320 */     List<Entity> list = (sender.getEntityWorld()).loadedEntityList;
/*     */     
/* 322 */     for (Entity entity : list) {
/* 323 */       if (entity != null && !(entity instanceof EntityPlayer) && entity.getClass().getName().toLowerCase().contains(className)) {
/* 324 */         entity.setDead();
/* 325 */         killed++;
/*     */       } 
/*     */     } 
/*     */     
/* 329 */     sender.sendMessage((ITextComponent)new TextComponentString(killed + " entity killed(" + args[1] + ")."));
/*     */   }
/*     */ 
/*     */   
/*     */   private void executeRemoveEntity(ICommandSender sender, String[] args) throws WrongUsageException {
/* 334 */     if (args.length < 2) {
/* 335 */       throw new WrongUsageException("/mcheli removeentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]);
/*     */     }
/* 337 */     String className = args[1].toLowerCase();
/* 338 */     List<Entity> list = (sender.getEntityWorld()).loadedEntityList;
/* 339 */     int removed = 0;
/*     */     
/* 341 */     for (Entity entity : list) {
/* 342 */       if (entity != null && !(entity instanceof EntityPlayer) && entity.getClass().getName().toLowerCase().contains(className)) {
/* 343 */         entity.isDead = true;
/* 344 */         removed++;
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     sender.sendMessage((ITextComponent)new TextComponentString(removed + " entity removed(" + args[1] + ")."));
/*     */   }
/*     */ 
/*     */   
/*     */   private void executeStatus(ICommandSender sender, String[] args) throws WrongUsageException {
/* 353 */     if (args.length < 2) {
/* 354 */       throw new WrongUsageException("/mcheli status <entity or tile> [min num]", new Object[0]);
/*     */     }
/* 356 */     if (args[1].equalsIgnoreCase("entity")) {
/* 357 */       executeStatusSub(sender, args, "Server loaded Entity List", (sender.getEntityWorld()).loadedEntityList);
/* 358 */     } else if (args[1].equalsIgnoreCase("tile")) {
/* 359 */       executeStatusSub(sender, args, "Server loaded Tile Entity List", (sender.getEntityWorld()).loadedTileEntityList);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void executeStatusSub(ICommandSender sender, String[] args, String title, List<?> list) {
/* 365 */     int minNum = (args.length >= 3) ? Integer.parseInt(args[2]) : 0;
/* 366 */     HashMap<String, Integer> map = new HashMap<>();
/*     */     
/* 368 */     for (Object o : list) {
/* 369 */       String key = o.getClass().getName();
/* 370 */       if (map.containsKey(key)) {
/* 371 */         map.put(key, Integer.valueOf(((Integer)map.get(key)).intValue() + 1)); continue;
/*     */       } 
/* 373 */       map.put(key, Integer.valueOf(1));
/*     */     } 
/*     */ 
/*     */     
/* 377 */     List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
/* 378 */     entries.sort(Comparator.comparing(Map.Entry::getKey));
/* 379 */     boolean send = false;
/* 380 */     sender.sendMessage((ITextComponent)new TextComponentString("--- " + title + " ---"));
/*     */     
/* 382 */     for (Map.Entry<String, Integer> s : entries) {
/* 383 */       if (((Integer)s.getValue()).intValue() >= minNum) {
/* 384 */         String msg = " " + (String)s.getKey() + " : " + s.getValue();
/* 385 */         System.out.println(msg);
/* 386 */         sender.sendMessage((ITextComponent)new TextComponentString(msg));
/* 387 */         send = true;
/*     */       } 
/*     */     } 
/*     */     
/* 391 */     if (!send) {
/* 392 */       System.out.println("none");
/* 393 */       sender.sendMessage((ITextComponent)new TextComponentString("none"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void executeFill(ICommandSender sender, String[] args) throws CommandException {
/* 398 */     if (args.length < 8) {
/* 399 */       throw new WrongUsageException("/mcheli fill <x1> <y1> <z1> <x2> <y2> <z2> <block name> [meta data] [oldBlockHandling] [data tag]", new Object[0]);
/*     */     }
/* 401 */     int x1 = sender.getPosition().getX();
/* 402 */     int y1 = sender.getPosition().getY();
/* 403 */     int z1 = sender.getPosition().getZ();
/* 404 */     int x2 = sender.getPosition().getX();
/* 405 */     int y2 = sender.getPosition().getY();
/* 406 */     int z2 = sender.getPosition().getZ();
/* 407 */     x1 = MathHelper.floor(parseCoordinate(x1, args[1], true).getResult());
/* 408 */     y1 = MathHelper.floor(parseCoordinate(y1, args[2], true).getResult());
/* 409 */     z1 = MathHelper.floor(parseCoordinate(z1, args[3], true).getResult());
/* 410 */     x2 = MathHelper.floor(parseCoordinate(x2, args[4], true).getResult());
/* 411 */     y2 = MathHelper.floor(parseCoordinate(y2, args[5], true).getResult());
/* 412 */     z2 = MathHelper.floor(parseCoordinate(z2, args[6], true).getResult());
/* 413 */     Block block = CommandBase.getBlockByText(sender, args[7]);
/* 414 */     IBlockState iblockstate = block.getDefaultState();
/* 415 */     if (args.length >= 9) {
/* 416 */       iblockstate = convertArgToBlockState(block, args[8]);
/*     */     }
/*     */     
/* 419 */     World world = sender.getEntityWorld();
/* 420 */     if (x1 > x2) {
/* 421 */       int t = x1;
/* 422 */       x1 = x2;
/* 423 */       x2 = t;
/*     */     } 
/*     */     
/* 426 */     if (y1 > y2) {
/* 427 */       int t = y1;
/* 428 */       y1 = y2;
/* 429 */       y2 = t;
/*     */     } 
/*     */     
/* 432 */     if (z1 > z2) {
/* 433 */       int t = z1;
/* 434 */       z1 = z2;
/* 435 */       z2 = t;
/*     */     } 
/*     */     
/* 438 */     if (y1 >= 0 && y2 < 256) {
/* 439 */       int blockNum = (x2 - x1 + 1) * (y2 - y1 + 1) * (z2 - z1 + 1);
/* 440 */       if (blockNum > 3000000) {
/* 441 */         throw new CommandException("commands.setblock.tooManyBlocks " + blockNum + " limit=327680", new Object[] { Integer.valueOf(blockNum), Integer.valueOf(3276800) });
/*     */       }
/* 443 */       boolean result = false;
/* 444 */       boolean keep = (args.length >= 10 && args[9].equals("keep"));
/* 445 */       boolean destroy = (args.length >= 10 && args[9].equals("destroy"));
/* 446 */       boolean override = (args.length >= 10 && args[9].equals("override"));
/* 447 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 448 */       boolean flag = false;
/* 449 */       if (args.length >= 11 && block.hasTileEntity(iblockstate)) {
/* 450 */         String s = getChatComponentFromNthArg(sender, args, 10).getUnformattedText();
/*     */ 
/*     */         
/*     */         try {
/* 454 */           nbttagcompound = JsonToNBT.getTagFromJson(s);
/* 455 */           flag = true;
/* 456 */         } catch (NBTException var27) {
/* 457 */           throw new CommandException("commands.setblock.tagError", new Object[] { var27.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/* 461 */       for (int x = x1; x <= x2; x++) {
/* 462 */         for (int y = y1; y <= y2; y++) {
/* 463 */           for (int z = z1; z <= z2; z++) {
/* 464 */             BlockPos blockpos = new BlockPos(x, y, z);
/* 465 */             if (world.isBlockLoaded(blockpos) && (world.isAirBlock(blockpos) ? !override : !keep)) {
/* 466 */               if (destroy) {
/* 467 */                 world.destroyBlock(blockpos, false);
/*     */               }
/*     */               
/* 470 */               TileEntity block2 = world.getTileEntity(blockpos);
/* 471 */               if (block2 instanceof IInventory) { IInventory ii = (IInventory)block2;
/*     */                 
/* 473 */                 for (int i = 0; i < ii.getSizeInventory(); i++) {
/* 474 */                   ItemStack is = ii.removeStackFromSlot(i);
/* 475 */                   if (!is.isEmpty()) {
/* 476 */                     is.setCount(0);
/*     */                   }
/*     */                 }  }
/*     */ 
/*     */               
/* 481 */               if (world.setBlockState(blockpos, iblockstate, 3)) {
/* 482 */                 if (flag) {
/* 483 */                   TileEntity tileentity = world.getTileEntity(blockpos);
/* 484 */                   if (tileentity != null) {
/* 485 */                     nbttagcompound.setInteger("x", x);
/* 486 */                     nbttagcompound.setInteger("y", y);
/* 487 */                     nbttagcompound.setInteger("z", z);
/* 488 */                     tileentity.readFromNBT(nbttagcompound);
/*     */                   } 
/*     */                 } 
/*     */                 
/* 492 */                 result = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 499 */       if (result) {
/* 500 */         notifyCommandListener(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */       } else {
/* 502 */         throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */       } 
/*     */     } else {
/*     */       
/* 506 */       throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<String> getTabCompletions(@NotNull MinecraftServer server, @NotNull ICommandSender sender, String[] prm, BlockPos targetPos) {
/* 512 */     if (!MCH_Config.EnableCommand.prmBool)
/* 513 */       return super.getTabCompletions(server, sender, prm, targetPos); 
/* 514 */     if (prm.length <= 1) {
/* 515 */       return getListOfStringsMatchingLastWord(prm, ALL_COMMAND);
/*     */     }
/* 517 */     if (prm[0].equalsIgnoreCase("sendss")) {
/* 518 */       if (prm.length == 2)
/* 519 */         return getListOfStringsMatchingLastWord(prm, server.getOnlinePlayerNames()); 
/*     */     } else {
/* 521 */       if (prm[0].equalsIgnoreCase("modlist")) {
/* 522 */         return getListOfStringsMatchingLastWord(prm, server.getOnlinePlayerNames());
/*     */       }
/* 524 */       if (prm[0].equalsIgnoreCase("fill")) {
/* 525 */         if ((prm.length == 2 || prm.length == 5) && sender instanceof Entity) { Entity entity = (Entity)sender;
/* 526 */           List<String> a = new ArrayList<>();
/* 527 */           int x = (entity.posX < 0.0D) ? (int)(entity.posX - 1.0D) : (int)entity.posX;
/* 528 */           int z = (entity.posZ < 0.0D) ? (int)(entity.posZ - 1.0D) : (int)entity.posZ;
/* 529 */           a.add(x + " " + (int)(entity.posY + 0.5D) + " " + z);
/* 530 */           return a; }
/*     */ 
/*     */         
/* 533 */         return (prm.length == 10) ? 
/* 534 */           getListOfStringsMatchingLastWord(prm, new String[] { "replace", "destroy", "keep", "override"
/* 535 */             }) : ((prm.length == 8) ? getListOfStringsMatchingLastWord(prm, Block.REGISTRY.getKeys()) : null);
/*     */       } 
/*     */       
/* 538 */       if (prm[0].equalsIgnoreCase("status")) {
/* 539 */         if (prm.length == 2) {
/* 540 */           return getListOfStringsMatchingLastWord(prm, new String[] { "entity", "tile" });
/*     */         }
/* 542 */       } else if (prm[0].equalsIgnoreCase("attackentity")) {
/* 543 */         if (prm.length == 4) {
/* 544 */           return getListOfStringsMatchingLastWord(prm, new String[] { "player", "inFire", "onFire", "lava", "inWall", "drown", "starve", "cactus", "fall", "outOfWorld", "generic", "magic", "wither", "anvil", "fallingBlock" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 562 */       else if (prm[0].equalsIgnoreCase("showboundingbox") && prm.length == 2) {
/* 563 */         return getListOfStringsMatchingLastWord(prm, new String[] { "true", "false" });
/*     */       } 
/*     */     } 
/*     */     
/* 567 */     return super.getTabCompletions(server, sender, prm, targetPos);
/*     */   }
/*     */ }


/* Location:              C:\Users\ASUS\Desktop\De-Compile\plmcjar\mchelionukebeta.jar!\com\norwood\mcheli\command\MCH_Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */