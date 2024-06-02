package com.pecant.primordialsoups.datagen;

import com.pecant.primordialsoups.PrimordialSoups;
import com.pecant.primordialsoups.Registration;
import com.pecant.primordialsoups.blocks.CrockBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;

public class PsBlockStates extends BlockStateProvider {

    public static final ResourceLocation BOTTOM = new ResourceLocation(PrimordialSoups.MODID, "block/machine_bottom");
    public static final ResourceLocation TOP = new ResourceLocation(PrimordialSoups.MODID, "block/machine_top");
    public static final ResourceLocation SIDE = new ResourceLocation(PrimordialSoups.MODID, "block/machine_side");

    public PsBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PrimordialSoups.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerCrock();

    }


    private void registerCrock() {
//        models().  TODO: Create the model in CODE or find a way to use the model file properly!!!
//        ModelFile.ExistingModelFile model = models().getExistingFile(modLoc("block/iron_crock"));
//        directionBlock(Registration.CROCK_BLOCK.get(), (state, builder) -> {
//            builder.modelFile(model);
//        });

        RegistryObject<CrockBlock> crock = Registration.CROCK_BLOCK;
        String path = "crock";

        BlockModelBuilder base = models().getBuilder("block/" + path);
        base.parent(models().getExistingFile(mcLoc("cube")));

        // Base element
        base.element()
                .from(0f,0f,0f)
                .to(16f,14f,16f)
                .allFaces((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.texture("#shell_top");
                        case DOWN -> faceBuilder.texture("#shell_bottom").uvs(0f, 2f, 16f, 16f);
                        case NORTH -> faceBuilder.texture("#shell_front").uvs(0f, 2f, 16f, 16f);
                        default -> faceBuilder.texture("#shell_side").uvs(0f, 2f, 16f, 16f);
                    }
                })
                .end();

        // west edge
        base.element()
                .from(0f,14f,0f)
                .to(2f,16f,16f)
                .allFaces((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.texture("#shell_top").uvs(0f, 14f, 16f, 16f).rotation(ModelBuilder.FaceRotation.CLOCKWISE_90);
                        case NORTH -> faceBuilder.texture("#shell_top").uvs(14f, 0f, 16f, 2f);
                        case WEST -> faceBuilder.texture("#shell_top").uvs(0f, 14f, 16f, 16f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN);
                        case SOUTH -> faceBuilder.texture("#shell_top").uvs(0f, 0f, 2f, 2f);
                        case EAST -> faceBuilder.texture("#shell_top").uvs(2f, 14f, 14f, 16f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN);
                        default -> faceBuilder.texture("#shell_top");
                    }
                })
                .end();

        // east edge
        base.element()
                .from(14f,14f,0f)
                .to(16f,16f,16f)
                .allFaces((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.texture("#shell_top").uvs(0f, 14f, 16f, 16f).rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90);
                        case SOUTH -> faceBuilder.texture("#shell_top").uvs(14f, 0f, 16f, 2f);
                        case EAST -> faceBuilder.texture("#shell_top").uvs(0f, 0f, 2f, 16f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN);
                        case NORTH -> faceBuilder.texture("#shell_top").uvs(0f, 0f, 2f, 2f);
                        case WEST -> faceBuilder.texture("#shell_top").uvs(2f, 14f, 14f, 16f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN);
                        default -> faceBuilder.texture("#shell_top");
                    }
                })
                .end();

        // south edge
        base.element()
                .from(2f,14f,14f)
                .to(14f,16f,16f)
                .allFaces((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.texture("#shell_top").uvs(2f, 14f, 14f, 16f);
                        case NORTH, SOUTH -> faceBuilder.texture("#shell_top").uvs(2f, 14f, 14f, 16f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN);
                        default -> faceBuilder.texture("#shell_top");
                    }
                })
                .end();

        // TODO: Northeast edge and Northwest edge
        // Northeast edge
        base.element()
                .from(9f,14f,0f)
                .to(14f,16f,2f)
                .allFaces((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.texture("#shell_top").uvs(2f, 14f, 14f, 16f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN);
                        case NORTH -> faceBuilder.texture("#shell_front").uvs(2f, 0f, 7f, 2f);
                        case SOUTH -> faceBuilder.texture("#shell_front").uvs(9f, 0f, 14f, 2f);
                        case WEST -> faceBuilder.texture("#shell_front").uvs(0f,0f, 1f, 1f);
                        default -> faceBuilder.texture("#shell_top");
                    }
                })
                .end();

        // Northwest edge
        base.element()
                .from(2f,14f,0f)
                .to(7f,16f,2f)
                .allFaces((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.texture("#shell_top").uvs(2f, 14f, 14f, 16f).rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN);
                        case SOUTH -> faceBuilder.texture("#shell_front").uvs(2f, 0f, 7f, 2f);
                        case NORTH -> faceBuilder.texture("#shell_front").uvs(9f, 0f, 14f, 2f);
                        case EAST -> faceBuilder.texture("#shell_front").uvs(0f,0f, 1f, 1f);
                        default -> faceBuilder.texture("#shell_top");
                    }
                })
                .end();



        base.texture("shell_top", modLoc("block/crock_top"));
        base.texture("shell_bottom", modLoc("block/crock_bottom"));
        base.texture("shell_front", modLoc("block/crock_front"));
        base.texture("shell_side", modLoc("block/crock_side"));
        base.texture("particle", modLoc("block/crock_top"));

        base.renderType("solid");

        createCrockModel(crock.get(), path, base);
    }


    private void createCrockModel(Block block, String path, BlockModelBuilder frame) {
        Direction[] dirs = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        MultiPartBlockStateBuilder bld = getMultipartBuilder(block);

        for (Direction dir : dirs ) {
            int angleOffset = (((int) dir.toYRot()) + 180) % 360;
            bld.part().modelFile(frame).rotationY(angleOffset).addModel().condition(BlockStateProperties.FACING, dir).end();
        }


    }


    private VariantBlockStateBuilder directionBlock(Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            model.accept(state, bld);
            applyRotationBld(bld, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
            return bld.build();
        });
        return builder;
    }

    private void applyRotationBld(ConfiguredModel.Builder<?> builder, Direction direction) {
        switch (direction) {
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
            default -> { }
        }
    }
}
