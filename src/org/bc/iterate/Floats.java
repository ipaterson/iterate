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
package org.bc.iterate;

import org.bc.iterate.function.Identity;
import org.bc.iterate.iterable.IterableBase;

import java.util.Random;

/**
 * This class provides {@link Function}s and {@link Visitor}s for {@code float}s and {@link Float}s.
 *
 * @author Brian Cavalier
 */
public class Floats
{
    public static Function<Float, Float> identity()
    {
        return new Identity<Float>();
    }

    public static Function<String, Float> parse()
    {
        return new Function<String, Float>()
        {
            public Float apply(String s)
            {
                return Float.valueOf(s);
            }
        };
    }

    public static BinaryFunction<Float, Float, Float> sum()
    {
        return new BinaryFunction<Float, Float, Float>()
        {
            public Float apply(Float i1, Float i2)
            {
                return i1 + i2;
            }
        };
    }

    public static BinaryFunction<Float, Float, Float> multiply()
    {
        return new BinaryFunction<Float, Float, Float>()
        {
            public Float apply(Float i1, Float i2)
            {
                return i1 * i2;
            }
        };
    }

    public static BinaryFunction<Float, Double, Double> pow()
    {
        return new BinaryFunction<Float, Double, Double>()
        {
            public Double apply(Float i, Double exponent)
            {
                return Math.pow(i, exponent);
            }
        };
    }

    public static Monoid<Float> square()
    {
        return new Monoid<Float>()
        {
            public Float apply(Float i)
            {
                return i * i;
            }
        };
    }

    public static Monoid<Float> cube()
    {
        return new Monoid<Float>()
        {
            public Float apply(Float i)
            {
                return i * i * i;
            }
        };
    }

    public static Iterable<Float> random()
    {
        final Random random = new Random();
        return new IterableBase<Float>()
        {
            public boolean hasNext()
            {
                return true;
            }

            @SuppressWarnings({"IteratorNextCanNotThrowNoSuchElementException"})
            public Float next()
            {
                return random.nextFloat();
            }
        };
    }
}