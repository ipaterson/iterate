/*
 * Copyright 2007-2009 Brian Cavalier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bc.iterate;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.*;

public class AlgorithmsTest
{
    @Test
    public void count()
    {
        final List<String> values = Arrays.asList("a", "b", "a", "c", "a", "d", "e");
        assertEquals(3, Algorithms.count(values, Conditions.eq("a")));
        assertEquals(1, Algorithms.count(values, Conditions.eq("e")));
        assertEquals(0, Algorithms.count(values, Conditions.eq("f")));
    }

    @Test
    public void first()
    {
        final List<String> values = Arrays.asList("a", "b", "a", "c", "a", "d", "e");
        assertEquals("c", Algorithms.first(values, Conditions.eq("c")));
        assertEquals("a", Algorithms.first(values, Conditions.eq("a")));
        assertEquals("e", Algorithms.first(values, Conditions.eq("e")));
        assertNull(Algorithms.first(values, Conditions.eq("f")));        
    }

    @Test
    public void one()
    {
        final List<String> values = Arrays.asList("a", "b", "a", "c", "a", "d", "e");
        assertTrue(Algorithms.one(values, Conditions.eq("c")));
        assertTrue(Algorithms.one(values, Conditions.eq("a")));
        assertTrue(Algorithms.one(values, Conditions.eq("e")));
        assertFalse(Algorithms.one(values, Conditions.eq("f")));
    }

    @Test
    public void all()
    {
        final List<String> values = Arrays.asList("a", "b", "a", "c", "a", "d", "e");
        assertTrue(Algorithms.all(values, Conditions.gte("a")));
        assertTrue(Algorithms.all(values, Conditions.lt("z")));
        assertTrue(Algorithms.all(values, Conditions.lte("e")));

        assertFalse(Algorithms.all(values, Conditions.gt("b")));
        assertFalse(Algorithms.all(values, Conditions.eq("a")));
        assertFalse(Algorithms.all(values, Conditions.eq("f")));
    }

    @Test
    public void partition1()
    {
        final List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Map<Boolean, Collection<Integer>> partitioned = Algorithms.partition(values, new Condition<Integer>()
        {
            public boolean eval(Integer integer)
            {
                return integer % 2 == 0;
            }
        });

        assertEquals(Arrays.asList(1, 3, 5, 7, 9), partitioned.get(false));
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), partitioned.get(true));
    }

    @Test
    public void partition2()
    {
        final List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> trueResults = new ArrayList<Integer>(5);
        List<Integer> falseResults = new ArrayList<Integer>(5);

        Algorithms.partition(values, trueResults, falseResults, new Condition<Integer>()
        {
            public boolean eval(Integer integer)
            {
                return integer % 2 == 0;
            }
        });

        assertEquals(Arrays.asList(1, 3, 5, 7, 9), falseResults);
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), trueResults);
    }

    @Test
    public void select1()
    {
        final List<String> values = Arrays.asList("a", "b", "a", "c", "a", "d", "e");
        assertEquals(Arrays.asList("a", "a", "a"), Algorithms.select(values, Conditions.eq("a")));
    }

    @Test
    public void select2()
    {
        final List<String> values = Arrays.asList("a", "b", "a", "c", "a", "d", "e");
        assertEquals(Arrays.asList("a", "a", "a"), Algorithms.select(values, new ArrayList<String>(10), Conditions.eq("a")));
    }
}