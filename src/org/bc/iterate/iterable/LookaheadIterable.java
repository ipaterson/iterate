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

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class LookaheadIterable<X> extends IterableBase<X>
{
    protected X next;

    @Override
    @SuppressWarnings({"RefusedBequest"})
    public Iterator<X> iterator()
    {
        return this;
    }

    public boolean hasNext()
    {
        if(next == null) {
            next = findNext();
        }

        return (next != null);
    }

    @SuppressWarnings({"IteratorNextCanNotThrowNoSuchElementException"})
    public X next()
    {
        if(next == null) {
            throw new NoSuchElementException();
        }
        X current = next;
        next = findNext();

        return current;
    }

    protected abstract X findNext();
}
