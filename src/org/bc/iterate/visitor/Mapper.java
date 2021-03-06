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

package org.bc.iterate.visitor;

import org.bc.iterate.TernaryVisitor;

import java.util.Map;

/**
 * @deprecated will be removed in v1.0
 */
public class Mapper<X, Y> implements TernaryVisitor<X, Y, Map<Y, X>>
{
    public void visit(X value, Y key, Map<Y, X> map)
    {
        //noinspection unchecked
        map.put(key, value);
    }
}
