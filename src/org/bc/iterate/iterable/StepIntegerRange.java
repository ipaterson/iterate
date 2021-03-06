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

import java.util.Iterator;

public class StepIntegerRange extends IntegerRange
{
    private int step = 1;

    public StepIntegerRange(int start)
    {
        super(start);
    }

    public StepIntegerRange(int start, int end, int step)
    {
        super(start, end);
        this.step = step;
    }

    public StepIntegerRange by(int step)
    {
        this.step = step;
        return this;
    }

    @SuppressWarnings({"RefusedBequest"})
    @Override
    public Iterator<Integer> iterator()
    {
        return (start <= end) ? new StepAscendingIntegerRangeInterator() : new StepDescendingIntegerRangeIterator();
    }

    @Override
    public String toString()
    {
        return step == 1 ? super.toString() : "[" + start + ".." + end + " by " + step + ']';
    }

    private class StepAscendingIntegerRangeInterator extends AscendingIntegerRangeIterator
    {
        @SuppressWarnings({"IteratorNextCanNotThrowNoSuchElementException", "RefusedBequest"})
        @Override
        public Integer next()
        {
            int current = index;
            index += step;
            return current;
        }
    }

    private class StepDescendingIntegerRangeIterator extends DescendingIntegerRangeIterator
    {
        @SuppressWarnings({"IteratorNextCanNotThrowNoSuchElementException", "RefusedBequest"})
        @Override
        public Integer next()
        {
            int current = index;
            index -= step;
            return current;
        }        
    }
}
