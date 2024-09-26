package com.gildedrose;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

class GildedRoseTest {
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";

    // quality never below 0

    @Property
    void quality_never_above_50(
        @ForAll("name") String name,
        @ForAll int sellIn,
        @ForAll @IntRange(min = 0, max = 50) int quality
    ) {
        Item item = new Item(name, sellIn, quality);
        GildedRose gildedRose = new GildedRose(new Item[]{ item });

        gildedRose.updateQuality();

        assertThat(item.quality).isLessThanOrEqualTo(50);
//        Statistics.collect(name);
    }

    @Property
    void sell_in_is_decreased_by_1(
        @ForAll("name") String name,
        @ForAll int sellIn,
        @ForAll @IntRange(min = 0, max = 50) int quality
    ) {
        assumeThat(name).isNotEqualTo(SULFURAS);
        Item item = new Item(name, sellIn, quality);
        GildedRose gildedRose = new GildedRose(new Item[]{ item });

        gildedRose.updateQuality();

        assertThat(item.sellIn).isEqualTo(sellIn - 1);
//        Statistics.collect(name);
    }

    @Property
    void regular_item_decreases_in_quality(
        @ForAll("name") String name,
        @ForAll int sellIn,
        @ForAll @IntRange(min = 1, max = 50) int quality
    ) {
        assumeThat(name)
            .isNotEqualTo(SULFURAS)
            .isNotEqualTo(AGED_BRIE)
            .isNotEqualTo(BACKSTAGE_PASSES);
        Item item = new Item(name, sellIn, quality);
        GildedRose gildedRose = new GildedRose(new Item[]{ item });

        gildedRose.updateQuality();

        assertThat(item.quality).isLessThan(quality);
//        Statistics.collect(name);
    }

    @Provide
    Arbitrary<String> name() {
        return Arbitraries.strings().edgeCases(configurator ->
            configurator.add(AGED_BRIE, BACKSTAGE_PASSES, SULFURAS)
        );
    }
}
