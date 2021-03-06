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

package org.bc.iterate.iterable;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class IntegerRangeTest
{
    @SuppressWarnings({"UnusedDeclaration"})
    @Test
    public void ascending()
    {
        final int start = 10;
        final int end = 500;
        IntegerRange range = new IntegerRange(start, end);
        int count = 0;
        for (int i : range) {
            count++;
        }

        assertEquals(end - start, count);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    @Test
    public void descending()
    {
        final int start = 500;
        final int end = 10;
        IntegerRange range = new IntegerRange(start, end);
        int count = 0;
        for (int i : range) {
            count++;
        }

        assertEquals(start - end, count);
    }
}
