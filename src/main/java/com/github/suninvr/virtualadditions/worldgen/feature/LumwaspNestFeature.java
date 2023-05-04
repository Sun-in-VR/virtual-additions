package com.github.suninvr.virtualadditions.worldgen.feature;

import com.github.suninvr.virtualadditions.block.LumwaspNestBlock;
import com.github.suninvr.virtualadditions.registry.VABlockTags;
import com.github.suninvr.virtualadditions.registry.VABlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LumwaspNestFeature extends Feature<DefaultFeatureConfig> {
    private static final BlockState SILK_BLOCK = VABlocks.SILK_BLOCK.getDefaultState();
    private static final BlockState NEST = VABlocks.LUMWASP_NEST.getDefaultState();
    private static final BlockState NEST_LARVAE = VABlocks.LUMWASP_NEST.getDefaultState().with(LumwaspNestBlock.LARVAE, true);
    private static final BlockState SILKBULB = VABlocks.SILKBULB.getDefaultState();
    private static final BlockState ACID = VABlocks.ACID.getDefaultState();
    private static final BlockState GLOWING_SILK = VABlocks.GLOWING_SILK.getDefaultState();
    private static final BlockState AIR = Blocks.AIR.getDefaultState();

    public LumwaspNestFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {

        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        boolean large = random.nextInt(4) == 1;

        if (!isValidSpace(world, origin, false)) return false;
        boolean canPlaceLarge = large && isValidSpace(world, origin, true);

        if (canPlaceLarge) generateLarge(world, origin, random);
        else generateSmall(world, origin, random);

        return true;
    }

    private void generateSmall(StructureWorldAccess world, BlockPos origin, Random random) {
        setBlockStateIfReplaceable(world, origin, SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(1, 0, 0), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-1, 0, 0), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(0, 0, 1), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(0, 0, -1), SILK_BLOCK);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 1, -1, j - 1), AIR);
            }
        }

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 2, -2, j - 2), ACID);
            }
        }

        for (int i = 0; i < 3; ++i) {
            int k = i - 1;
            for (int j = 0; j < 3; ++j) {
                int l = switch (j) {
                    case 0, 2 -> 2;
                    case 1 -> 3;
                    default -> 0;
                };
                int m = -1 - j;

                if (m != -3) {
                    setBlockStateIfReplaceable(world, origin.add(k, m, l), SILK_BLOCK);
                    setBlockStateIfReplaceable(world, origin.add(k, m, -l), SILK_BLOCK);
                    setBlockStateIfReplaceable(world, origin.add(l, m, k), SILK_BLOCK);
                    setBlockStateIfReplaceable(world, origin.add(-l, m, k), SILK_BLOCK);
                } else {
                    setRandomNestState(world, origin.add(k, m, l), random);
                    setRandomNestState(world, origin.add(k, m, -l), random);
                    setRandomNestState(world, origin.add(l, m, k), random);
                    setRandomNestState(world, origin.add(-l, m, k), random);
                }
            }
        }
        
        for (int i = 0; i < 2; ++i) {
            int j = i + 1;
            setBlockStateIfReplaceable(world, origin.add(j, -j, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -j, -j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(-j, -j, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(-j, -j, -j), SILK_BLOCK);
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                setRandomNestState(world, origin.add(i - 1, -3, j - 1), random);
            }
        }
    }
    
    private void generateLarge(StructureWorldAccess world, BlockPos origin, Random random) {
        // Layer 0
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 1, 0, j - 1), SILK_BLOCK);
            }
        }

        // Layer 1
        for (int i = 0; i < 3; ++i) {
            int j = i - 1;
            setBlockStateIfReplaceable(world, origin.add(2, -1, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(-2, -1, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -1, 2), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -1, -2), SILK_BLOCK);
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 1, -1, j - 1), AIR);
            }
        }

        // Layer 2
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 1, -2, j - 1), AIR);
            }
        }
        for (int i = 0; i < 3; ++i) {
            int j = i - 1;
            setBlockStateIfReplaceable(world, origin.add(2, -2, j), AIR);
            setBlockStateIfReplaceable(world, origin.add(-2, -2, j), AIR);
            setBlockStateIfReplaceable(world, origin.add(j, -2, 2), AIR);
            setBlockStateIfReplaceable(world, origin.add(j, -2, -2), AIR);
            setBlockStateIfReplaceable(world, origin.add(3, -2, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(-3, -2, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -2, 3), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -2, -3), SILK_BLOCK);
        }

        setBlockStateIfReplaceable(world, origin.add(2, -2, 2), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(2, -2, -2), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-2, -2, 2), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-2, -2, -2), SILK_BLOCK);

        // Layer 3
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 2, -3, j - 2), AIR);
            }
        }
        for (int i = 0; i < 5; ++i) {
            int j = i - 2;
            setBlockStateIfReplaceable(world, origin.add(3, -3, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(-3, -3, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -3, 3), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -3, -3), SILK_BLOCK);
        }

        // Layer 4
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 2, -4, j - 2), ACID);
            }
        }
        for (int i = 0; i < 3; ++i) {
            int j = i - 1;
            setBlockStateIfReplaceable(world, origin.add(3, -4, j), ACID);
            setBlockStateIfReplaceable(world, origin.add(-3, -4, j), ACID);
            setBlockStateIfReplaceable(world, origin.add(j, -4, 3), ACID);
            setBlockStateIfReplaceable(world, origin.add(j, -4, -3), ACID);
            setBlockStateIfReplaceable(world, origin.add(4, -4, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(-4, -4, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -4, 4), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -4, -4), SILK_BLOCK);
        }

        setBlockStateIfReplaceable(world, origin.add(3, -4, 2), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(3, -4, -2), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-3, -4, 2), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-3, -4, -2), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(2, -4, 3), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(2, -4, -3), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-2, -4, 3), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-2, -4, -3), SILK_BLOCK);

        // Layer 5
        for (int i = 0; i < 5; ++i) {
            int j = i - 2;
            setBlockStateIfReplaceable(world, origin.add(4, -5, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(-4, -5, j), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -5, 4), SILK_BLOCK);
            setBlockStateIfReplaceable(world, origin.add(j, -5, -4), SILK_BLOCK);
        }

        setBlockStateIfReplaceable(world, origin.add(3, -5, 3), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(3, -5, -3), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-3, -5, 3), SILK_BLOCK);
        setBlockStateIfReplaceable(world, origin.add(-3, -5, -3), SILK_BLOCK);

        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 3, -5, j - 3), ACID);
            }
        }

        // Layer 6

        setRandomNestState(world, origin.add(2, -6, 2), random);
        setRandomNestState(world, origin.add(2, -6, -2), random);
        setRandomNestState(world, origin.add(-2, -6, 2), random);
        setRandomNestState(world, origin.add(-2, -6, -2), random);
        for (int i = 0; i < 5; ++i) {
            int j = i - 2;
            setRandomNestState(world, origin.add(3, -6, j), random);
            setRandomNestState(world, origin.add(-3, -6, j), random);
            setRandomNestState(world, origin.add(j, -6, 3), random);
            setRandomNestState(world, origin.add(j, -6, -3), random);
        }
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                setBlockStateIfReplaceable(world, origin.add(i - 2, -6, j - 2), ACID);
            }
        }

        // Layer 7
        for (int i = 0; i < 3; ++i) {
            int j = i - 1;
            setRandomNestState(world, origin.add(2, -7, j), random);
            setRandomNestState(world, origin.add(-2, -7, j), random);
            setRandomNestState(world, origin.add(j, -7, 2), random);
            setRandomNestState(world, origin.add(j, -7, -2), random);
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                setRandomNestState(world, origin.add(i - 1, -7, j - 1), random);
            }
        }

    }

    private void setRandomNestState(StructureWorldAccess world, BlockPos pos, Random random) {
        boolean bl = random.nextInt(3) == 1;
        BlockState state = bl ? NEST_LARVAE : NEST;
        setBlockStateIfReplaceable(world, pos, state);
        //if (bl) setBlockStateIfReplaceable(world, pos.up(), SILKBULB);
        if (bl && random.nextInt(2) == 1) setBlockStateIfReplaceable(world, pos.down(), GLOWING_SILK);
    }

    private boolean isValidSpace(StructureWorldAccess worldAccess, BlockPos origin, boolean large) {
        int height = large ? 9 : 6;
        for (int i = 0; i < height; ++i) {
            BlockState state = worldAccess.getBlockState(origin.down(i));
            if ( (i < 2 && canReplace(state)) || state.isAir() ) continue;
            return false;
        }

        return true;
    }
    
    private boolean canReplace(BlockState state) {
        return state.isIn(VABlockTags.LUMWASP_NEST_REPLACEABLE) || state.isReplaceable();
    }
    
    protected void setBlockStateIfReplaceable(StructureWorldAccess world, BlockPos pos, BlockState state) {
        if (state == ACID) {
            if (world.getBlockState(pos).isAir()) setBlockState(world, pos, state);
            return;
        }
        if (canReplace(world.getBlockState(pos))) setBlockState(world, pos, state);
    }
}
