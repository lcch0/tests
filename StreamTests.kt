package com.dss.manetestapp

import org.junit.Before
import org.junit.Test
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.system.measureTimeMillis

class Main
{
    var list = mutableListOf<Second>()
}

class Second
{
    var list = mutableListOf<Third>()
}

class Third
{
    var value = 0
}



class RealTest
{
    private var list = mutableListOf<Main>()

    @Before
    fun setUp()
    {
        val list3 = mutableListOf<Third>()
        for (i in 0..10)
            list3.add(Third().apply { value = i })

        val list2 = mutableListOf<Second>()
        for (j in 0..1)
            list2.add(Second().apply { list.addAll(list3) })

        list.add(Main().apply { list.addAll(list2) })
    }


    @Test
    fun testStreams()
    {
        val res = measureTimeMillis {
            val flattened = list.stream()
                    .flatMap { s ->
                        s.list.stream()
                                .flatMap { s1 ->
                                    s1.list.stream() }
                    }

            val list1 = mutableListOf<Int>()
            for (t in flattened)
            {
                list1.add(t.value)
            }
        }

        val res2 = measureTimeMillis {
        val test1 = list.asSequence()
                .flatMap { s ->
                    s.list.asSequence()
                            .flatMap { s1 ->
                                s1.list.asSequence() }
                }

            val list1 = mutableListOf<Int>()
            for (t in test1)
            {
                list1.add(t.value)
            }
        }

        var diff = res - res2
    }

    @Test
    fun testFib()
    {
        val fibonacciSequence = sequence {
            var a = 0
            var b = 1

            while (true)
            {
                yield(a)
                val f = a + b
                a = b
                b = f
            }
        }

        val res = measureTimeMillis {
            fibonacciSequence
                    .take(10)
                    .groupBy { it % 2 == 0 }
                    .forEach(::println)
        }

    }

    @Test
    fun testFib1()
    {
        val fibonacciStream = Stream.iterate(Pair(1, 0)) {
            (a, b) -> Pair(b, a + b)
        }

        val res = measureTimeMillis {
            fibonacciStream
                    .limit(10)
                    .map { it.second }
                    .collect(Collectors.groupingBy { it % 2 == 0 })
                    .forEach(::println)
        }
    }

    @Test
    fun testFib2()
    {
        val sequence = generateSequence(1 to 0) { (a, b) -> b to a + b }

        val res = measureTimeMillis {
            sequence
                    .take(10)
                    .map { it.second }
                    .groupBy { it % 2 == 0 }
                    .forEach(::println)
        }
    }
}



