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

package org.bc.iterate.iterable;

public class IntegerRange extends IterableBase<Integer>
{
    protected int start;
    protected int end;

    public IntegerRange()
    {
        this(0, Integer.MAX_VALUE);
    }

    public IntegerRange(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    public IntegerRange(int start)
    {
        this(start, Integer.MAX_VALUE);
    }

    public IntegerRange to(int end)
    {
        this.end = end;
        return this;
    }

    public boolean hasNext()
    {
        return start < end;
    }

    @SuppressWarnings({"IteratorNextCanNotThrowNoSuchElementException"})
    public Integer next()
    {
        return start++;
    }
}
