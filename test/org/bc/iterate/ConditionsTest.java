/*
 * Copyright (c) 2007-2010 Brian Cavalier
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

package org.bc.iterate;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class ConditionsTest
{
    @Test
    public void isNull()
    {
        assertTrue(Conditions.isNull().eval(null));
        assertTrue(Conditions.notNull().eval(new Object()));
    }

    @Test
    public void eq()
    {
        assertTrue(Conditions.eq("c").eval("c"));
        assertFalse(Conditions.eq("c").eval("abc"));
        assertFalse(Conditions.eq("a").eval("abc"));

        assertTrue(Conditions.eq(1).eval(1));
        assertFalse(Conditions.eq(1).eval(0));
    }

    @Test
    public void neq()
    {
        assertFalse(Conditions.neq("c").eval("c"));
        assertTrue(Conditions.neq("c").eval("abc"));
        assertTrue(Conditions.neq("a").eval("abc"));

        assertFalse(Conditions.neq(1).eval(1));
        assertTrue(Conditions.neq(1).eval(0));
    }

    @Test
    public void lt()
    {
        assertTrue(Conditions.lt(10).eval(9));
        assertFalse(Conditions.lt(10).eval(10));
        assertFalse(Conditions.lt(10).eval(11));
    }

    @Test
    public void gt()
    {
        assertFalse(Conditions.gt(10).eval(9));
        assertFalse(Conditions.gt(10).eval(10));
        assertTrue(Conditions.gt(10).eval(11));
    }

    @Test
    public void lte()
    {
        assertTrue(Conditions.lte(10).eval(9));
        assertTrue(Conditions.lte(10).eval(10));
        assertFalse(Conditions.lte(10).eval(11));
    }

    @Test
    public void gte()
    {
        assertFalse(Conditions.gte(10).eval(9));
        assertTrue(Conditions.gte(10).eval(10));
        assertTrue(Conditions.gte(10).eval(11));
    }

    @Test
    public void and()
    {
        assertTrue(Conditions.and(Conditions.eq("a"), Conditions.eq("a")).eval("a"));
        assertFalse(Conditions.and(Conditions.eq("a"), Conditions.eq("b")).eval("a"));
        assertFalse(Conditions.and(Conditions.eq("a"), Conditions.eq("b")).eval("b"));
    }

    @Test
    public void or()
    {
        assertTrue(Conditions.or(Conditions.eq("a"), Conditions.eq("b")).eval("a"));
        assertTrue(Conditions.or(Conditions.eq("a"), Conditions.eq("b")).eval("b"));
        assertFalse(Conditions.or(Conditions.eq("a"), Conditions.eq("b")).eval("c"));
    }

    @Test
    public void xor()
    {
        assertTrue(Conditions.xor(Conditions.eq("a"), Conditions.eq("b")).eval("a"));
        assertTrue(Conditions.xor(Conditions.eq("a"), Conditions.eq("b")).eval("b"));
        assertFalse(Conditions.xor(Conditions.eq("a"), Conditions.eq("a")).eval("a"));
        assertFalse(Conditions.xor(Conditions.eq("a"), Conditions.eq("b")).eval("c"));
    }

    @Test
    public void not()
    {
        assertFalse(Conditions.not(Conditions.eq("a")).eval("a"));
        assertTrue(Conditions.not(Conditions.eq("a")).eval("b"));
    }

    @Test
    public void none()
    {
        assertFalse(Conditions.none(Conditions.eq("a"), Conditions.eq("b"), Conditions.eq("c")).eval("b"));
        assertTrue(Conditions.none(Conditions.eq("a"), Conditions.eq("b"), Conditions.eq("c")).eval("d"));
    }

    @Test
    public void all()
    {
        assertFalse(Conditions.all(Conditions.eq("a"), Conditions.eq("a"), Conditions.eq("a")).eval("b"));
        assertTrue(Conditions.all(Conditions.eq("a"), Conditions.eq("a"), Conditions.eq("a")).eval("a"));
    }

    @Test
    public void one()
    {
        assertFalse(Conditions.one(Conditions.eq("a"), Conditions.eq("b"), Conditions.eq("c")).eval("d"));
        assertTrue(Conditions.one(Conditions.eq("a"), Conditions.eq("b"), Conditions.eq("c")).eval("b"));
    }

    @Test
    public void regex()
    {
        final List<String> test = Arrays.asList("a", "ab", "b", "c");
        final Condition<String> condition = Conditions.regex("a|b");

        int count = 0;
        //noinspection UnusedDeclaration
        for(String s : Iterate.each(test).where(condition)) {
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    public void in()
    {
        assertTrue(Conditions.in(Arrays.asList(1, 2, 3)).eval(2));
        assertFalse(Conditions.in(Arrays.asList(1, 2, 3)).eval(4));        
    }

    @Test
    public void chance()
    {
        // Test the chance algorithm, NOT Java's random number generator
        Random fake0 = new FakeRandom(0.0);
        Random fake4 = new FakeRandom(.4);
        Random fake5 = new FakeRandom(.5);
        Random fake6 = new FakeRandom(.6);
        Random fake10 = new FakeRandom(1.0);
        
        assertTrue(Conditions.chance(.5, fake0).eval(new Object()));
        assertTrue(Conditions.chance(.5, fake4).eval(new Object()));
        assertFalse(Conditions.chance(.5, fake5).eval(new Object()));
        assertFalse(Conditions.chance(.5, fake6).eval(new Object()));
        assertFalse(Conditions.chance(.5, fake10).eval(new Object()));

        assertTrue(Conditions.chance(.6, fake5).eval(new Object()));
        assertFalse(Conditions.chance(.4, fake5).eval(new Object()));
    }

    private static class FakeRandom extends Random
    {
        private final double doubleValue;

        public FakeRandom(double doubleValue)
        {
            this.doubleValue = doubleValue;
        }

        @Override
        public double nextDouble()
        {
            return doubleValue;
        }
    }
}
