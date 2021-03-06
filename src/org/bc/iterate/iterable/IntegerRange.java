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

import org.bc.iterate.Iterate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerRange extends Iterate<Integer>
{
    protected int start;
    protected final int end;

    public IntegerRange()
    {
        this(0, Integer.MAX_VALUE);
    }

    public IntegerRange(int start, int end)
    {
        super(Math.abs(end - start));
        this.start = start;
        this.end = end;
    }

    public IntegerRange(int start)
    {
        this(start, Integer.MAX_VALUE);
    }

    @SuppressWarnings({"ClassReferencesSubclass"})
    public StepIntegerRange by(int step)
    {
        return new StepIntegerRange(start, end, step);
    }

    public Iterator<Integer> iterator()
    {
        return (start <= end) ? new AscendingIntegerRangeIterator() : new DescendingIntegerRangeIterator();
    }

    @Override
    public String toString()
    {
        return "[" + start + ".." + end + ']';
    }

    protected class AscendingIntegerRangeIterator implements Iterator<Integer>
    {
        protected int index;

        public AscendingIntegerRangeIterator()
        {
            index = start;
        }

        public boolean hasNext()
        {
            return index < end;
        }

        public Integer next()
        {
            if(index < end) {
                return index++;
            } else {
                throw new NoSuchElementException("Reached end value " + end);
            }
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    protected class DescendingIntegerRangeIterator implements Iterator<Integer>
    {
        protected int index;

        public DescendingIntegerRangeIterator()
        {
            index = start;
        }

        public boolean hasNext()
        {
            return index > end;
        }

        public Integer next()
        {
            if(index > end) {
                return index--;
            } else {
                throw new NoSuchElementException("Reached end value " + end);
            }
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
