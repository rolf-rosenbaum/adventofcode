package day9

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.PI

class StationLocationOptimizerTest {

    @Test
    fun readAsteroidFieldCorrectly() {
        val optimizer = StationLocationOptimizer("testmap.txt")
        assertThat(optimizer.asteroidMap).isEqualTo(
            setOf(
                Asteroid(0, 0),
                Asteroid(4, 0),
                Asteroid(1, 1),
                Asteroid(2, 2),
                Asteroid(4, 2)
            ))
    }

    @Test
    fun `2 asteroids should be visible from 0,0`() {
        val optimizer = StationLocationOptimizer("testmap2.txt")
        assertThat(optimizer.countVisibleAsteroidsFor(Asteroid(0,0)).visibleCounter).isEqualTo(2)

    }

    @Test
    fun `2 asteroids should be visible from 2,2`() {
        val optimizer = StationLocationOptimizer("testmap2.txt")
        assertThat(optimizer.countVisibleAsteroidsFor(Asteroid(2,2)).visibleCounter).isEqualTo(2)
    }

    @Test
    fun `3 asteroids should be visible from 4,0`() {
        val optimizer = StationLocationOptimizer("testmap2.txt")
        assertThat(optimizer.countVisibleAsteroidsFor(Asteroid(4,0)).visibleCounter).isEqualTo(3)
    }



    @Test
    fun `find best asteroid`() {
        val optimizer = StationLocationOptimizer("testmap3.txt")
        assertThat(optimizer.findBestAsteroid()).isEqualTo(Asteroid(4,0, 4))
    }

    @Test
    fun `find best asteroid testmap4`() {
        val optimizer = StationLocationOptimizer("testMap4.txt")
        assertThat(optimizer.countVisibleAsteroidsFor(Asteroid(6,3))).isEqualTo(Asteroid(6,3,41))
        assertThat(optimizer.findBestAsteroid()).isEqualTo(Asteroid(6,3, 41))
    }

    @Test
    fun `find best asteroid testmap5`() {
        val optimizer = StationLocationOptimizer("testMap_5.txt")

        assertThat(optimizer.countVisibleAsteroidsFor(Asteroid(1,0))).isEqualTo(Asteroid(1,0,7))
        assertThat(optimizer.findBestAsteroid()).isEqualTo(Asteroid(3,4, 8))
    }

    @Test
    fun `find best asteroid largeMap`() {
        val optimizer = StationLocationOptimizer("largeMap.txt")
        assertThat(optimizer.findBestAsteroid()).isEqualTo(Asteroid(11,13, 210))
    }

    @Test
    fun dependant() {
        assertThat(Vector(2,2).isDependant(Vector(3,3))).isTrue()
        assertThat(Vector(2,1).isDependant(Vector(-4,-2))).isTrue()
        assertThat(Vector(2,0).isDependant(Vector(7,0))).isTrue()
        assertThat(Vector(1,3).isDependant(Vector(-1,3))).isFalse()
        assertThat(Vector(1,3).isDependant(Vector(-1,-3))).isTrue()
        assertThat(Vector(1,3).isDependant(Vector(-2,-6))).isTrue()
        assertThat(Vector(-1,-1).isDependant(Vector(4,2))).isFalse()
        assertThat(Vector(4,2).isDependant(Vector(-1,-1))).isFalse()
    }

    @Test
    fun `find next to vaporize`() {
        val optimizer = StationLocationOptimizer("testmap3.txt")
        assertThat(optimizer.findNextToVaporize(optimizer.asteroidMap.toMutableSet(), Asteroid(2,2))).isEqualTo(Asteroid(4,0))
    }

    @Test
    fun vaporizeField() {

//        val optimizer = StationLocationOptimizer("testmap3.txt")
//        assertThat(optimizer.vaporize()).isEqualTo(listOf(Asteroid(3,3), Asteroid(2,2), Asteroid(1,1), Asteroid(0,0)))

        val optimizer2 = StationLocationOptimizer("largeMap.txt")
        val list = optimizer2.vaporize()
        val result  = list.get(199).x * 100 + list.get(199).y
        assertThat(result).isEqualTo(802)
    }
}