/*******************************************************************************
 * Copyright 2013 Raphael Jolivet
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package java2typescript.jaxrs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Param {
    private String name;
    private ParamType type;
    private boolean context = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParamType getType() {
        return type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

    /**
     * If true, this param corresponds to a technical @Context param, and should be ignored in output generation
     */
    @JsonIgnore
    public boolean isContext() {
        return context;
    }

    public void setContext(boolean context) {
        this.context = context;
    }
}
