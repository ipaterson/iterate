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
import org.bc.iterate.Iterate;
import org.bc.iterate.util.Pair;

import java.util.*;

/**
 * Performs a simple left outer join, using a <a href="http://en.wikipedia.org/wiki/Hash_join">hash join</a>, yielding
 * the joined {@link org.bc.iterate.util.Pair}s as its iterator's items.  The join is done incrementally rather
 * than as a batch operation.
 *
 * @param <X>
 * @param <K>
 * @param <Y>
 */
public class LeftIncrementalHashJoinIterable<X, K, Y> extends IterableBase<Pair<X, Y>>
{
    private final Iterator<X> leftIterator;
    private final Function<X, K> xKeyFunction;
    private final Iterable<Y> rightIterable;
    private final Function<Y,K> yKeyFunction;
    private Map<K, List<Y>> rightMap;
    private Iterator<Pair<X, Y>> currentJoinIter;

    public LeftIncrementalHashJoinIterable(Iterable<X> left,
                                Function<X, K> xKeyFunction,
                                Iterable<Y> right,
                                Function<Y, K> yKeyFunction)
    {
        this.leftIterator = left.iterator();
        this.xKeyFunction = xKeyFunction;
        this.rightIterable = right;
        this.yKeyFunction = yKeyFunction;
    }

    @Override
    public Iterator<Pair<X, Y>> iterator()
    {
        prepareJoin(rightIterable, yKeyFunction);
        return super.iterator();
    }

    private void prepareJoin(Iterable<Y> right, Function<Y, K> yKeyFunction)
    {
        rightMap = new HashMap<K, List<Y>>(Iterate.estimateSize(right));
        for (final Y y : right) {
            //noinspection SuspiciousNameCombination
            K k = yKeyFunction.apply(y);
            List<Y> ys = rightMap.get(k);
            if (ys == null) {
                ys = new ArrayList<Y>(16);
                rightMap.put(k, ys);
            }
            ys.add(y);
        }
    }

    public boolean hasNext()
    {
        return leftIterator.hasNext() || (currentJoinIter != null && currentJoinIter.hasNext());
    }

    public Pair<X, Y> next()
    {
        if (currentJoinIter == null || !currentJoinIter.hasNext()) {
            final X x = leftIterator.next();
            final K xKey = xKeyFunction.apply(x);
            List<Y> currentYList = rightMap.get(xKey);
            if (currentYList != null) {
                final ArrayList<Pair<X, Y>> currentJoinList = new ArrayList<Pair<X, Y>>(currentYList.size());
                for (Y y : currentYList) {
                    currentJoinList.add(new Pair<X, Y>(x, y));
                }
                currentJoinIter = currentJoinList.iterator();
            } else {
                currentJoinIter = null;
                return new Pair<X, Y>(x, null);
            }
        }

        return currentJoinIter.next();
    }
}