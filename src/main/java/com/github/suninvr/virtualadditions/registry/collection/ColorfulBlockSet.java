package com.github.suninvr.virtualadditions.registry.collection;

import com.ibm.icu.impl.Pair;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class ColorfulBlockSet {
    private final Map<Tile, Block> blockMap;
    private final Item dye;

    private ColorfulBlockSet(Item dye, TileBlockPair... entries) {
        this.blockMap = Map.ofEntries(entries);
        this.dye = dye;
    }

    public Item dye() {
        return this.dye;
    }

    public Block wool() {
        return this.blockMap.get(Tile.WOOL);
    }

    public void ifWool(Consumer<Block> consumer) {
        Block block = this.wool();
        if (block != null) consumer.accept(block);
    }

    public Block carpet() {
        return this.blockMap.get(Tile.CARPET);
    }

    public void ifCarpet(Consumer<Block> consumer) {
        Block block = this.carpet();
        if (block != null) consumer.accept(block);
    }

    public Block terracotta() {
        return this.blockMap.get(Tile.TERRACOTTA);
    }

    public void ifTerracotta(Consumer<Block> consumer) {
        Block block = this.terracotta();
        if (block != null) consumer.accept(block);
    }

    public Block glazedTerracotta() {
        return this.blockMap.get(Tile.GLAZED_TERRACOTTA);
    }

    public void ifGlazedTerracotta(Consumer<Block> consumer) {
        Block block = this.glazedTerracotta();
        if (block != null) consumer.accept(block);
    }

    public Block concrete() {
        return this.blockMap.get(Tile.CONCRETE);
    }

    public void ifConcrete(Consumer<Block> consumer) {
        Block block = this.concrete();
        if (block != null) consumer.accept(block);
    }

    public Block concretePowder() {
        return this.blockMap.get(Tile.CONCRETE_POWDER);
    }

    public void ifConcretePowder(Consumer<Block> consumer) {
        Block block = this.concretePowder();
        if (block != null) consumer.accept(block);
    }

    public Block stainedGlass() {
        return this.blockMap.get(Tile.STAINED_GLASS);
    }

    public void ifStainedGlass(Consumer<Block> consumer) {
        Block block = this.stainedGlass();
        if (block != null) consumer.accept(block);
    }

    public Block stainedGlassPane() {
        return this.blockMap.get(Tile.STAINED_GLASS_PANE);
    }

    public void ifStainedGlassPane(Consumer<Block> consumer) {
        Block block = this.stainedGlassPane();
        if (block != null) consumer.accept(block);
    }

    public Block candle() {
        return this.blockMap.get(Tile.CANDLE);
    }

    public void ifCandle(Consumer<Block> consumer) {
        Block block = this.candle();
        if (block != null) consumer.accept(block);
    }

    public Block candleCake() {
        return this.blockMap.get(Tile.CANDLE_CAKE);
    }

    public void ifCandleCake(Consumer<Block> consumer) {
        Block block = this.candleCake();
        if (block != null) consumer.accept(block);
    }

    public Block silkbulb() {
        return this.blockMap.get(Tile.SILKBULB);
    }

    public void ifSilkbulb(Consumer<Block> consumer) {
        Block block = this.silkbulb();
        if (block != null) consumer.accept(block);
    }

    public Block bed() {
        return this.blockMap.get(Tile.BED);
    }

    public void ifBed(Consumer<Block> consumer) {
        Block block = this.bed();
        if (block != null) consumer.accept(block);
    }

    public Block shulkerBox() {
        return this.blockMap.get(Tile.SHULKER_BOX);
    }

    public void ifShulkerBox(Consumer<Block> consumer) {
        Block block = this.shulkerBox();
        if (block != null) consumer.accept(block);
    }

    public Block banner() {
        return this.blockMap.get(Tile.BANNER);
    }

    public void ifBanner(Consumer<Block> consumer) {
        Block block = this.banner();
        if (block != null) consumer.accept(block);
    }

    public Block wallBanner() {
        return this.blockMap.get(Tile.WALL_BANNER);
    }

    public void ifWallBanner(Consumer<Block> consumer) {
        Block block = this.wallBanner();
        if (block != null) consumer.accept(block);
    }

    public static class Builder {
        private final ArrayList<TileBlockPair> entries;
        private final Item dye;

        private Builder(Item dye) {
            this.entries = new ArrayList<>();
            this.dye = dye;
        }

        public static Builder create(Item dye) {
            return new Builder(dye);
        }

        public ColorfulBlockSet build() {
            return new ColorfulBlockSet(this.dye, this.entries.toArray(new TileBlockPair[0]));
        }

        public Builder wool(Block block) {
            this.entries.add(TileBlockPair.of(Tile.WOOL, block));
            return this;
        }

        public Builder carpet(Block block) {
            this.entries.add(TileBlockPair.of(Tile.CARPET, block));
            return this;
        }

        public Builder terracotta(Block block) {
            this.entries.add(TileBlockPair.of(Tile.TERRACOTTA, block));
            return this;
        }

        public Builder glazedTerracotta(Block block) {
            this.entries.add(TileBlockPair.of(Tile.GLAZED_TERRACOTTA, block));
            return this;
        }

        public Builder concrete(Block block) {
            this.entries.add(TileBlockPair.of(Tile.CONCRETE, block));
            return this;
        }

        public Builder concretePowder(Block block) {
            this.entries.add(TileBlockPair.of(Tile.CONCRETE_POWDER, block));
            return this;
        }

        public Builder stainedGlass(Block block) {
            this.entries.add(TileBlockPair.of(Tile.STAINED_GLASS, block));
            return this;
        }

        public Builder stainedGlassPane(Block block) {
            this.entries.add(TileBlockPair.of(Tile.STAINED_GLASS_PANE, block));
            return this;
        }

        public Builder candle(Block block) {
            this.entries.add(TileBlockPair.of(Tile.CANDLE, block));
            return this;
        }

        public Builder candleCake(Block block) {
            this.entries.add(TileBlockPair.of(Tile.CANDLE_CAKE, block));
            return this;
        }

        public Builder silkbulb(Block block) {
            this.entries.add(TileBlockPair.of(Tile.SILKBULB, block));
            return this;
        }

        public Builder bed(Block block) {
            this.entries.add(TileBlockPair.of(Tile.BED, block));
            return this;
        }

        public Builder shulkerBox(Block block) {
            this.entries.add(TileBlockPair.of(Tile.SHULKER_BOX, block));
            return this;
        }

        public Builder banner(Block block) {
            this.entries.add(TileBlockPair.of(Tile.BANNER, block));
            return this;
        }

        public Builder wallBanner(Block block) {
            this.entries.add(TileBlockPair.of(Tile.WALL_BANNER, block));
            return this;
        }
    }

    private static class TileBlockPair extends Pair<Tile, Block> implements Map.Entry<Tile, Block> {
        protected TileBlockPair(Tile first, Block second) {
            super(first, second);
        }

        public static TileBlockPair of(Tile tile, Block block) {
            return new TileBlockPair(tile, block);
        }

        @Override
        public Tile getKey() {
            return this.first;
        }

        @Override
        public Block getValue() {
            return this.second;
        }

        @Override
        public Block setValue(Block value) {
            return this.second;
        }
    }

    private enum Tile {
        WOOL,
        CARPET,
        TERRACOTTA,
        GLAZED_TERRACOTTA,
        CONCRETE,
        CONCRETE_POWDER,
        STAINED_GLASS,
        STAINED_GLASS_PANE,
        CANDLE,
        CANDLE_CAKE,
        SILKBULB,
        BED,
        SHULKER_BOX,
        BANNER,
        WALL_BANNER
    }
}
