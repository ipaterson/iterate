/*
 * Copyright (c) 2007-2009 Brian Cavalier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bc.iterate.iterable;

import org.bc.iterate.Function;
import org.bc.iterate.function.ToString;
import org.bc.iterate.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class LeftIncrementalHashJoinIterableTest
{
    @Test
    public void testNext() throws Exception {
        List<Integer> items = Arrays.asList(1, 2, 3, 4);
        List<String> toJoin = Arrays.asList("one", "two", "four");
        LeftIncrementalHashJoinIterable<Integer, String, String> j = new LeftIncrementalHashJoinIterable<Integer, String, String>(items, new ToString<Integer>(), toJoin, new Function<String, String>()
        {
            public String apply(String s)
            {
                return "one".equals(s) ? "1" : "two".equals(s) ? "2" : "4";
            }
        });
        Map<Integer, String> results = new HashMap<Integer, String>(4);
        for (Pair<Integer, String> joined : j) {
            results.put(joined.getX(), joined.getY());
        }

        Assert.assertEquals(4, results.size());
        Assert.assertEquals("one", results.get(1));
        Assert.assertEquals("two", results.get(2));
        Assert.assertEquals("four", results.get(4));
        // Left join should still contain items that did not appear in toJoin
        Assert.assertTrue(results.containsKey(3));
        // But those items should have a null value for the right hand side
        Assert.assertNull(results.get(3));
    }

    @Test
    public void testNextDuplicateKeys() throws Exception {
        List<Integer> items = Arrays.asList(1, 2, 3, 4);
        List<String> toJoin = Arrays.asList("one", "two", "four", "four2");
        LeftIncrementalHashJoinIterable<Integer, String, String> j = new LeftIncrementalHashJoinIterable<Integer, String, String>(items, new ToString<Integer>(), toJoin, new Function<String, String>()
        {
            public String apply(String s)
            {
                return "one".equals(s) ? "1" : "two".equals(s) ? "2" : "4";
            }
        });
        List<Pair<Integer, String>> results = new ArrayList<Pair<Integer, String>>(5);
        for (Pair<Integer, String> joined : j) {
            results.add(new Pair<Integer, String>(joined.getX(), joined.getY()));
        }

        Assert.assertEquals(5, results.size());
    }
}